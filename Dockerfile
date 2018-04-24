FROM tomcat:8.5.24-jre8-alpine

# remove tomcat examples
RUN rm -rf \
  ${CATALINA_HOME}/webapps/* \
  ${CATALINA_HOME}/RELEASE-NOTES \
  ${CATALINA_HOME}/RUNNING.txt \
  ${CATALINA_HOME}/bin/*.bat \
  ${CATALINA_HOME}/bin/*.tar.gz

# copy war file
WORKDIR ${CATALINA_HOME}/webapps
COPY target/icm-analytics.war .
RUN mv icm-analytics.war ROOT.war

# no need to explicitly deploy and/or start tomcat, this is done implicitly
