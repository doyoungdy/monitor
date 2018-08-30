FROM ab/java:8
MAINTAINER doyoung <ab053045duyang@ab-insurance.com>

ADD going-storm-monitor.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS="$JAVA_OPTS -Duser.timezone=GMT+08 -Dspring.profiles.active=docker"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
