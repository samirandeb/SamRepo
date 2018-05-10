# SpringBoot Drools Demo
This is a sample application with Spring Boot Drools integration. This application use local embedded drools rule engine.

## Usage
In order to run the local single node version execute the following commands (from command prompt in windows): 
* `mvn clean install`
* `java -jar target/SamDroolsDemo-0.0.1-SNAPSHOT.jar`.

* Use Postman or any REST client to invoke the below **POST** service
 [http://localhost:8080/cardApply](http://localhost:8080/cardApply)
* Sample JSON request
* {
      "name": "John Doe",
      "age": 45,
      "salary": 25000
  }