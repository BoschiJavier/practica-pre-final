FROM adoptopenjdk/openjdk15
ARG JAR_FILE=spring-boot-web.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE 8888
      