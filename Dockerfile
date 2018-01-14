FROM tomcat:8.5.24-jre8-alpine

# copy war file
WORKDIR /opt/tomcat/webapps
COPY target/icm-analytics.war .

# start tomcat
ENTRYPOINT [ "/opt/tomcat/bin/catalina.sh" ]
CMD [ "run" ]
