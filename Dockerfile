FROM adoptopenjdk:11-jre-hotspot
#ARG JAR_FILE=*.jar
#EXPOSE=9000
#COPY ${JAR_FILE} application.jar
#ENTRYPOINT ["java", "-jar", "application.jar"]

WORKDIR /home/app

COPY ./app/**.jar /home/app/app.jar

ENTRYPOINT ["java","-jar","app.jar"]