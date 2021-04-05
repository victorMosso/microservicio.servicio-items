FROM openjdk:12
VOLUME /tmp
EXPOSE 8002
ADD ./target/springboot.microservicio.items-v.1.0.jar microservicio-items.jar
ENTRYPOINT [ "java","-jar","microservicio-items.jar" ]