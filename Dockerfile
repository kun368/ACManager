FROM tomcat:8-jre8

COPY ./target/ACManager.war /usr/local/tomcat/webapps/
EXPOSE 8080