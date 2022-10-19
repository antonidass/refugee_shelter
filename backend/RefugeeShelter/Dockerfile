# For java 8
FROM openjdk:8-jdk-alpine

# refer to maven build
ARG JAR_FILE=target/RefugeeShelter-0.0.1-SNAPSHOT.jar

# cd /opt/app
# WORKDIR /opt/app

# cp target/*.jar /opt/app/app.jar
COPY ${JAR_FILE} app.jar

# java -jar /opt/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

# after thata docker build -t ref_shel:0.0.1 ./
# and after that:  docker run --name bakcend_1 -d -p 8081:8081 -t ref_shel:0.0.1
