FROM openjdk:11
ADD /build/libs/ReactiveElectronicLibrary-0.0.1-SNAPSHOT.jar backend.jar
ENTRYPOINT ["java", "-jar", "backend.jar"]