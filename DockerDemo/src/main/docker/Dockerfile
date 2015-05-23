FROM java:8
EXPOSE 8080
VOLUME /tmp
ADD demouser-0.0.1-SNAPSHOT.jar demouser-0.0.1-SNAPSHOT.jar
RUN bash -c 'touch /demouser-0.0.1-SNAPSHOT.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/demouser-0.0.1-SNAPSHOT.jar"]