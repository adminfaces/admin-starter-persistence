FROM rmpestano/wildfly:11.0.0
MAINTAINER Rafael Pestano

COPY ./docker/standalone.conf ${WILDFLY_HOME}/bin/

COPY ./target/admin-starter.war ${DEPLOYMENT_DIR}
