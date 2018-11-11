FROM openjdk

ADD . .

RUN chmod +x 'gradlew'

ENTRYPOINT ["./gradlew"]
CMD ["build"]
