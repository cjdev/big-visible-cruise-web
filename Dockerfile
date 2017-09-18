FROM java:8-jdk

EXPOSE 4481
EXPOSE 4480

RUN apt-get update && apt-get install -y \
    python 


RUN mkdir big-visible-cruise-web
WORKDIR /big-visible-cruise-web

COPY target/bvc.jar /big-visible-cruise-web/
COPY startup.sh /big-visible-cruise-web/
COPY speedyCC.py /big-visible-cruise-web/

ENTRYPOINT ["./startup.sh"]
CMD []