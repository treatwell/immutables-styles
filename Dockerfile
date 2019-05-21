FROM adoptopenjdk/maven-openjdk8

ENV term xterm
ENV workdir /root/immutable-styles

COPY . ${workdir}
COPY .git ${workdir}/.git

WORKDIR ${workdir}

CMD ["/bin/bash", "-c", "./scripts/ci.sh"]
