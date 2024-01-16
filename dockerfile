FROM eclipse-temurin:21


COPY target/ratingapp-0.0.1-SNAPSHOT.jar /app/ratingapp-0.0.1-SNAPSHOT.jar
WORKDIR /app

RUN jar -xf your-spring-app.jar

WORKDIR /app/BOOT-INF/classes

CMD ["java", "-cp", ".:../lib/*", "com.yourpackage.YourSpringApplicationClass"]
