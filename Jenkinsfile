#!groovy
@Library('treatwell-jenkins') _

// now build, based on the configuration provided
pipeline {
    agent {
        label(env.LABEL)
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        disableConcurrentBuilds()
    }

    stages {
        stage('Preparing Workspace') {
            steps {
                gitClone(env.GIT_BRANCH, env.GIT_URL)

                // We need to mount maven directories so that the "local" repo can be passed between stages / the
                // containers that are used in those stages. Note: we don't ever want to mount `mavenLocalM2` in any
                // service builder containers as that would result in us overwriting settings.xml
                script {
                    def mavenLocalM2 = env.WORKSPACE + '/.m2'
                    def mavenRepository = mavenLocalM2 + '/repository'

                    sh 'mkdir -p ' + mavenRepository

                    workspaceRunArgs = '-v "' + env.WORKSPACE + '":/home/treatwell/src'
                    workspaceRunArgs += ' -v "' + mavenRepository + '":/home/treatwell/.m2/repository'
                }
            }
        }

        stage('Maven Clean Deploy') {
            steps {
                script {
                    def mavenCommand = '-Ddeploy.skip.default=false -Denforcer.skip=true'
                    mavenCommand += ' -DskipITs=false'
                    mavenCommand += ' -T1C -am -B clean deploy'
                    mavenCommand += ' -Djacoco.skip=false'

                    withCredentials([
                            usernamePassword(credentialsId: env.NEXUS_DEPLOYER_CREDENTIALS_ID, usernameVariable: 'NEXUS_USERNAME', passwordVariable: 'NEXUS_PASSWORD')
                    ]) {
                        def runArgs = workspaceRunArgs
                        runArgs += ' -e NEXUS_USERNAME=$NEXUS_USERNAME'
                        runArgs += ' -e NEXUS_PASSWORD=$NEXUS_PASSWORD'
                        runArgs += ' -e GIT_BRANCH=' + env.GIT_BRANCH
                        runArgs += ' -e MAVEN_OPTS="-XX:CICompilerCount=2"'
                        runArgs += ' -v /var/run/docker.sock:/var/run/docker.sock'
                        runArgs += ' --group-add ' + sh(script: 'getent group docker | cut -d: -f3', returnStdout: true)
                        runArgs += ' --entrypoint ""'

                        docker.withRegistry('https://docker.twtools.io') {
                            docker.image('treatwell/service-builder:0.0.17').inside(runArgs) {
                                sh '/home/treatwell/src/scripts/build.sh ' + mavenCommand
                            }
                        }
                    }
                }
            }
        }

        // Keep the analysis to a later stage so that it does not slow down the main build / deploy. It should only be
        // run in the event that we have performed the build step and we have not done an incremental build. The latter
        // could potentially give misleading results.
        stage('Analysis') {
            steps {
                script {
                    def runArgs = workspaceRunArgs
                    runArgs += ' -e GIT_BRANCH=' + env.GIT_BRANCH
                    runArgs += ' --entrypoint ""'

                    docker.withRegistry('https://docker.twtools.io') {
                        docker.image('treatwell/service-builder:0.0.17').inside(runArgs) {
                            sh 'mvn -B org.owasp:dependency-check-maven:3.2.1:check -Dformat=XML'
                        }
                    }
                }

                jacoco(exclusionPattern: '**/*Test*', inclusionPattern: '**/*.class')
                dependencyCheckPublisher(canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '**/dependency-check-report.xml', unHealthy: '')
            }
        }
    }

    post {
        always {
            script {
                // We always want to publish junit reports as they could tell us why a build is failing.
                junit(allowEmptyResults: true, keepLongStdio: true, testResults: '**/surefire-reports/*.xml')
                junit(allowEmptyResults: true, keepLongStdio: true, testResults: '**/failsafe-reports/*.xml')
                deleteDir()
            }
        }

        changed {
            script {
                slackNotify(env.TW_SLACK_CHANNEL, currentBuild.result, env.JOB_NAME, env.BUILD_NUMBER, env.BUILD_URL, env.GIT_BRANCH)
                emailNotify(env.TW_EMAIL_ADDRESS, currentBuild.result, env.JOB_NAME, env.BUILD_NUMBER, env.BUILD_URL, env.GIT_BRANCH)
            }
        }
    }
}