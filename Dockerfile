FROM tomcat:8-jre8

COPY $TRAVIS_BUILD_DIR/traget/ACManager.war /usr/local/tomcat/webapps/
EXPOSE 8080