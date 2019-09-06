#!/bin/bash
set -x
MVN_ARGS=$*
bash -c "mvn clean install -B -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn $MVN_ARGS"
