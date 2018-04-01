FROM tomcat:8-jre8

COPY traget/ACManager.war /usr/local/tomcat/webapps/
EXPOSE 8080