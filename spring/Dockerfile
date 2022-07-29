FROM adoptopenjdk/openjdk11

RUN mkdir -p /root/XlsxConverter/res/output

CMD ["./mvnw", "clean", "package"]
ARG JAR_FILE_PATH=target/*.jar
COPY ${JAR_FILE_PATH} /app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

# build
# docker build -t spring-xlsx

# run
# docker run -it spring-xlsx