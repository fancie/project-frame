FROM java:8
EXPOSE 8888
VOLUME /tmp
ADD web-1.0.0-SNAPSHOT.jar web.jar
RUN sh -c 'touch /web.jar'

#RUN apt-get update --fix-missing && apt-get install -y vim --fix-missing

ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /web.jar"]
