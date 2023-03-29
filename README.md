# Stock Manager

The objective of this project is to provide an algorithm to determine whether a product will be shown in the front end based on its stock. The algorithm needs to consider these premises:

- For each product, all existing sizes should be analyzed
- A product will be shown in the front end if at least one size has available stock
- A size is considered in stock if its quantity is at least one or if its marked as "Back soon"
- A product might have "special sizes". A product with special sizes should be shown only if it has at least one special size and one regular size in stock.
- The output should be the IDs of the products to be shown, sorted by their sequence value

Please check below for the resolution of this problem.

## Tech stack

- Language: [Java 1.8](https://www.java.com/es/)
- Framework and libraries: [Spring Framework, Spring Boot, Spring JPA, etc](https://spring.io/)
- Project build and dependencies manager: [Maven](https://maven.apache.org/)
- Database: [H2](https://www.h2database.com/html/main.html)
- API testing: [Postman](https://www.postman.com/)
- Unit Testing: [jUnit](http://junit.org/junit5/)
- DB migration: [Flyway](https://flywaydb.org/)
- ORM: [Hibernate](https://hibernate.org/)
- Other tools: [MapStruct](https://mapstruct.org/), [Lombok](https://projectlombok.org/), [Podam](https://mtedone.github.io/podam/)

## How to use

### Local setup
You must have Java 1.8 and maven to manage dependenies and build the project


- Clone the [repo](https://github.com/nicoyamin/stockmanager.git)
- The application uses an H2 in memory DB to store products, sizes and stock. You can modify the application.properties file to 
control different parameters such as data persistence, username/password, or if the H2 console is enabled.
- The application has a flyway script to create tables and populate them. 
  - During the creation step, it adds data checks to prevent insertion of invalid data and creates the relationship between the tables.
  - During the population insert, it takes the records from .CSV files. 
  - Inside the resources/samples folder, you can find different testing scenarios for the algorithm. You can customize which case you want to insert in the DB by modifyng the following property in application.properties:
  ```
  spring.flyway.placeholders.csv.folder=original
  ```
  - Current values are: case1, case2, case3, case4, original, invalid-data
  - Inside samples, you can also find the correct output for each case
- Make a build and locally start the app. By default, it starts in (localhost:8080)
- Once up and running you can make API calls with a REST client such as POSTMAN. There are only two endpoint, which you can find below.

### Endpoints

#### Get all products

Description: 
- Return a list of all products, with their sizes and stock

Request: 
```
  curl --location --request GET 'localhost:8080/products'
```

Response:

![image](https://user-images.githubusercontent.com/1058798/228644023-539c8623-f56c-4647-b52d-15266c51207c.png)


#### Get products in stock

Description: 
- Return a list of all products that match the algorithm criteria described above:

Request: 
```
  curl --location --request GET 'localhost:8080/products/inStock'
```

Response:
```
[
    5,
    1,
    3
]
```


## Algorithm

The resolution of this problem involves the following steps:

- First, we know the "in stock" condition: when a size has at least one size with stock quantity > 0 or when at least one size is marked as "back soon". We extract this logic to a simple boolean evaluation method.
- Then, to account for the "special" size case, we need to make a distinction between three conditions: (1)a product has special sizes; (2)a product has regular sizes in stock; (3) a product has special sizes in stock. 
- With that information, the approach was straightforward: 
  - Iterate over each product
  - For each product, iterate over the sizes list
  - Verify if a size is special and if the special size is in stock
  - Verify if regular sizes are in stock
  - Use boolean flags for the three conditions described above. Avoid changing the value of any flag if it is already set to true
  - Extract the products to be shown, sorting them by sequence (it is assumed that the sequence order is ASCENDING)
  - Return an array with the IDs of the visible products.
  
  The whole logic can be summarized in the following table of conditions:
  
| Has special size | Special size in stock | Regular size in stock | Show product on frontend |
|---------------------|------------------------|------------------------|------------------------|
|         false        |          -          |          false         |         false           |
|         false        |          -          |          true          |         true           |
|         true        |          false         |          false          |         false           |
|         true        |          false         |          true         |         false          |
|         true       |          true          |          false          |         false           |
|         true       |          true         |          true         |         true          |
  

### Data structures used

Given the description above, and considering that the data is previously stored and retrieved from DB, I relied on DTOs to isolate the needed representation of the Product entity along their nested sizes and stock. For the processing, I used a regular Array List of ProductDTO and a simple Array for returning the result. So, there were no need of fancy data structures like Sets, Trees or Maps.

However, if instead of pre storing the data in a DB, we read the csv directly in code, we could use HashMaps to store the products and sizes, providing constant access time and search and iterating over each element only once.

### Asymptotic analysis

With the current implementation, we perform an iteration over each product and each size inside those products. So, a worst case scenario would result in a runtime efficiency of O(n*m) where "n" is the number of products and "m" is an average of the Sizes list length.

The runtime could be improved by reading the CSV files directly and storing products and keeping track of sizes and stocks with HashMaps. Then, by following the logic of the table above, we would end up with an average runtime of O(n) because we only read the products, sizes and stock files only once.

However, I opted for the former approach since it provides better data structuration, persistence, and it's easier to test and follow the logic.

## Unit testing

Junit 5 was used for unit testing and Mockito and Podam for dependency mocking. A good number of test cases and branches are covered. Special attention was given to testing the algorithm itself, covering all relevant use cases.

## Features to build upon/improve and things to add if the project is to evolve

Project can be improved in many aspects, which I'd be more than happy to discuss. While considering improvements over EXISTING feature, I can mention the following:
- Increasing Unit test coverage and test cases.
- Improving the global exception handler. Right now, it's pretty generic.
- Documenting the API with something like Swagger, and methods and classes should be documented as well with JavaDocs.

When it comes to NEW features that can be added to improve and polish the application, the below considerations can be made:

- Add the option to read CSV files directly and apply the algorithm over that data.
- CI/CD pipeline: A CI/CD pipeling implementation such as Git Actions or Jenkins is pending. 
- Containerization and Cloud deployment: This application could be easily containerized with Docker or Kubernetes and then deployed to a cloud provider such as AWS. This will allow for easy scalability and redundancy, plus the container tech is a perfect match for the monitoring bundle described above.
- Monitoring: To add CM (continuos monitoring) to our CI/CD, we need a tool such as [Prometheus](https://prometheus.io/), which is an open source tool that acts as a scrapper for any metric that needs to be exposed from the application and also offers libraries to implement custom metrics in our code while allowing aggregation and querying of this information.
- Graphic Monitoring: We can use something like [Grafana](https://grafana.com/) to set the Prometheus service as Datasource to compile exposed metrics and present them graphically on a dashboard.
- Logging: There is no logging system set up, but it could be easily added using Log4j and later integrated with an elastic search tool such as [Loki](https://grafana.com/oss/loki/), which is compatible with Grafana.
- Containerization and Cloud deployment: This application could be easily containerized with Docker or Kubernetes and then deployed to a cloud provider such as AWS. This will allow for easy scalability and redundancy, plus the container tech is a perfect match for the monitoring bundle described above.
- API performance: Load tests using JMeter or simillar remain pending. It is possible to integrate performance metrics in a Grafana panel so they can be watched from there,
- Using API first approach, with OpenApi plugin to autogenerate models and controllers.

 **GENERAL SOFTWARE PRACTICES**
 
 - This being prototype, I decided to take some "shortcuts" in order to speed up development time.
 - I went for only DTOs for requests and responses and entities(as DAOs), since the amount of information was manageable that
 way. Under normal circumstances, a response model needs to be added in order to fully isolate the entity from the request/response involved in a REST call.
 - I used Lombok to create necessary getters, setters, hashcodes, etc.
 - I did not create any branches on my repo, and worked directly on master.


Thanks for reading!
In case of questions or doubts, feel free to drop me an email at nicoyamin@hotmail.com.ar



