FROM tomcat:8-jre8

RUN rm -rf /usr/local/tomcat/webapps/ROOT
COPY ./target/ACManager.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080