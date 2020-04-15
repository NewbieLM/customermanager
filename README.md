# Customer Manager application

| Technology | Badge |
|:-----------:|:-----:|
| Travis CI | [![Build Status](https://travis-ci.com/NewbieLM/customermanager.svg?branch=master)](https://travis-ci.com/NewbieLM/customermanager) |



This app provide CRUD operations via HTTP with such entities as Customer, Account, Transaction. 

## Technology Stack
* **Programming Language:** Java
* **Build System:** Maven
* **Database:** MySQL
* **ORM:** Hibernate
* **Continuous Integration:** Travis-CI 
* **Database schema migration:** Liquibase
* **Servlet container:** Apache Tomcat


## Getting Started
To run this application you must:
1. download copy of the project to your local machine;
2. import project to IDE;
3. reimport maven projects;
4. update hibernate.cfg.xml and liquibase.properties files acording to your database settings;
5. build project "mvn clean install" and start Tomcat server "mvn tomcat7:run"

## URL examples
####Customer
<br/>Get all
<br/>Method: GET
<br/>URL: http://localhost:8080/customer

Get by ID
<br/>Method: GET
<br/>URL: http://localhost:8080/customer?id=12345

Add new
<br/>Method: POST
<br/>URL: http://localhost:8080/customer?firstname=ABC&lastname=DEF&specialty=GHI

Update
<br/>Method: PUT
<br/>URL: http://localhost:8080/customer?id=1234&firstname=ABC100500&lastname=DEF&specialty=GHI

Delete
<br/>Method: DELETE
<br/>URL: http://localhost:8080/customer?id=12345

####Account
<br/>Get all
<br/>Method: GET
<br/>URL: http://localhost:8080/account

Get by ID
<br/>Method: GET
<br/>URL: http://localhost:8080/account?id=12345

Add new
<br/>Method: POST
<br/>URL: http://localhost:8080/account?customer-id=1234&account-data=SOMEDATA&balance=100500

Update
<br/>Method: PUT
<br/>URL: http://localhost:8080/account?id=1234&account-data=SOME-NEW-DATA&balance=100500

Delete
<br/>Method: DELETE
<br/>URL: http://localhost:8080/account?id=1234

####Transaction
<br/>Get all
<br/>Method: GET
<br/>URL: http://localhost:8080/transaction

Get by ID
<br/>Method: GET
<br/>URL: http://localhost:8080/transaction?id=5

Add new
<br/>Method: POST
<br/>URL: http://localhost:8080/transaction?account-id=1234&amount=100500

Update
<br/>Method: PUT
<br/>URL: http://localhost:8080/transaction?id=1234&amount=100500

Delete
<br/>Method: DELETE
<br/>URL: http://localhost:8080/transaction?id=15