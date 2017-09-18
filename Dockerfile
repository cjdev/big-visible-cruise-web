FROM java:8-jdk

EXPOSE 4481
RUN mkdir big-visible-cruise-web
WORKDIR /big-visible-cruise-web

COPY target/bvc.jar /big-visible-cruise-web/

ENTRYPOINT ["java", "-jar", "bvc.jar", "--port=4481", "--"]
CMD []