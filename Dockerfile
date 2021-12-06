FROM openjdk:11-jre-slim-buster

VOLUME [ "/root" ]

WORKDIR /app


ADD app/build/libs/app-0.0.1-SNAPSHOT.jar app.jar
COPY entrypoint.sh entrypoint.sh
RUN chmod +x ./entrypoint.sh

ENTRYPOINT ["./entrypoint.sh"]
