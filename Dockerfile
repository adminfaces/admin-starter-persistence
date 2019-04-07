FROM rmpestano/wildfly:16.0.1
MAINTAINER Rafael Pestano

COPY ./docker/standalone.conf ${WILDFLY_HOME}/bin/

COPY ./target/admin-starter.war ${DEPLOYMENT_DIR}
