# springboot-redis-eureka-zuul
A simple Spring-Boot RESTful application with Spring Data JPA, Database Relational and Non-Relational Database with Redis.

### To download Redis: 
https://redis.io/download

### Case use operational system Windows:
https://github.com/MicrosoftArchive/redis/releases

To run application: 

mvn clean install

mvn spring-boot:run

Endpoints Expenses

* http://localhost:9090/santander/api/v1/expenses/{userCode}?page=0&size=10 (GET) get all expenses by user code
* http://localhost:9090/santander/api/v1/expenses?userCode=1&date=2018-04-30T00:00:00 (GET) find by filter
* http://localhost:9090/santander/api/v1/expenses (POST) insert. json= {"value": 100, "userCode": 1, "date": "2018-04-30T00:00:00", "description":"category 1"}
* http://localhost:9090/santander/api/v1/expenses (PUT) update. json= {"code": 1, "value": 100, "userCode": 1, "date": "2018-04-30T00:00:00", "description":"category updated", "version":0}

Endpoint Category

* http://localhost:9090/santander/api/v1/categories/{description} (GET) get all categories by description

#### TODO
* update database properties in application.properties (main and test)
* implement internacionalization
* implement authentication schema with JWT and Spring Security.
