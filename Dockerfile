FROM maven:3-jdk-8

ENV TOMCAT_MINOR_VERSION=8.5.24 \
 CATALINA_HOME=/opt/tomcat \
 TOMCAT_URL=https://archive.apache.org/dist/tomcat/tomcat-8

RUN curl -O ${TOMCAT_URL}/v${TOMCAT_MINOR_VERSION}/bin/apache-tomcat-${TOMCAT_MINOR_VERSION}.tar.gz && \
 curl ${TOMCAT_URL}/v${TOMCAT_MINOR_VERSION}/bin/apache-tomcat-${TOMCAT_MINOR_VERSION}.tar.gz.md5 | md5sum -c - && \
 tar xzf apache-tomcat-*.tar.gz && \
 rm apache-tomcat-*.tar.gz* && mv apache-tomcat* ${CATALINA_HOME} && \
 rm -rf ${CATALINA_HOME}/webapps/* \
  ${CATALINA_HOME}/RELEASE-NOTES ${CATALINA_HOME}/RUNNING.txt \
  ${CATALINA_HOME}/bin/*.bat ${CATALINA_HOME}/bin/*.tar.gz

COPY pom.xml /tmp/pom.xml
RUN mvn -B -f /tmp/pom.xml -s /usr/share/maven/ref/settings-docker.xml dependency:resolve

WORKDIR /opt/tomcat
EXPOSE 8080

ENTRYPOINT [ "/opt/tomcat/bin/catalina.sh" ]
CMD [ "run" ]
