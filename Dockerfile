# Linux build stage
FROM eclipse-temurin:17-jdk-focal

WORKDIR /usr/src/app

RUN apt update; apt install -y file
# Copy pom.xml and resolve dependencies, cache this layer for subsequent builds
COPY pom.xml .
COPY mvnw .
COPY ./.mvn/ ./.mvn/
RUN ./mvnw -B dependency:resolve dependency:resolve-plugins

COPY . .

RUN mkdir /installer && \
    ./mvnw install -P linux

CMD cp /usr/src/app/target/*.deb /installer && \
  cp /usr/src/app/target/*.AppImage /installer && \
  cp /usr/src/app/target/*.rpm /installer
