FROM openjdk

ADD . .

ENTRYPOINT ["./gradlew"]
CMD ["build"]
