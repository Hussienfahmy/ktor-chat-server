FROM openjdk:22-jdk

WORKDIR /app

COPY . .

RUN chmod +x ./gradlew

RUN ./gradlew shadowJar

EXPOSE 8080

CMD ["java", "-jar", "build/libs/com.h_fahmy.chat.ktor-chat-all.jar"]