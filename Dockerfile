FROM tomcat:8-jre8

COPY ./target/ACManager.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080