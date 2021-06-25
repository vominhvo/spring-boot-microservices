## 1. Spring cloud version 
- Homepage: https://spring.io/projects/spring-cloud
- Repository: https://github.com/spring-cloud/spring-cloud-release/wiki/Spring-Cloud-2020.0-Release-Notes


## 2. Start from scratch with Axon Server
- Homepage: https://docs.axoniq.io <br>
- Docker: https://hub.docker.com/r/axoniq/axonserver/
```$xslt
# Http port: 8024
# GRPC port: 8124
docker run -d --name axon-server -p 8024:8024 -p 8124:8124 \
    -v /Users/ht/axon-server/data:/data \
    -v /Users/ht/axon-server/eventdata:/eventdata \
    -v /Users/ht/axon-server/config:/config \
    axoniq/axonserver
```
Dashboards: http://localhost:8024

## 3. CQRS pattern with Axon Framework 4
#### 3.1 Adding Axon Framework Spring Boot Starter 
https://mvnrepository.com/artifact/org.axonframework/axon-spring-boot-starter
```$xslt
<dependency>
    <groupId>org.axonframework</groupId>
    <artifactId>axon-spring-boot-starter</artifactId>
    <version>4.5.2</version>
</dependency>
```

#### 3.2 Creating a new Command class
Naming convention: `<Verb><Noun>Command`

```java
@Builder
@Data
public class CreateProductCommand {
    @TargetAggregateIdentifier
    private final String id;
    private final String title;
    private final BigDecimal sellingPrice;
    private final int subCategoryId;
    private final String image;
    private final List<String> tags;
}
```
Axon Framework will use the annotation @TargetAggregateIdentifier to associate this command with an aggregate object in your application.
#### 3.3 Send Command to a Command Gateway
The Command Gateway will be used to send your Command object to a command bus
```java
final CommandGateway commandGateway;

String returnValue = commandGateway.sendAndWait(creationCommand);
```

#### 3.4 Create Aggregate class, then add some logical validations
```java
@Aggregate
public class ProductAggregate {
    public ProductAggregate() {

    }

    @CommandHandler
    public ProductAggregate(CreateProductCommand createProductCommand) {
        // Validate the CreateProductCommand
        if (createProductCommand.getSellingPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Selling price cannot be less or equal than zero");
        }

        if (ObjectUtils.isEmpty(createProductCommand.getTitle())) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
    }
}
```

#### 3.5 Create Event class
Naming convention: `<Noun><PerformedAction>Event`
eg. ProductCreatedEvent, ProductShippedEvent, ProductDeletedEvent
```java
@Data
public class ProductCreatedEvent {
    private String code;
    private String title;
    private BigDecimal sellingPrice;
    private int subCategoryId;
    private String image;
    private List<String> tags;
}
```
#### 3.6 Apply and publish the Created Event
```java
@Aggregate
public class ProductAggregate {
    public ProductAggregate() {

    }

    @CommandHandler
    public ProductAggregate(CreateProductCommand createProductCommand) {
        // Validate the CreateProductCommand
        if (createProductCommand.getSellingPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Selling price cannot be less or equal than zero");
        }

        if (ObjectUtils.isEmpty(createProductCommand.getTitle())) {
            throw new IllegalArgumentException("Title cannot be empty");
        }

        // Apply and publish the Created Event
        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();

        // Copy the values of which the same properties from source to destination object
        BeanUtils.copyProperties(createProductCommand, productCreatedEvent);

        AggregateLifecycle.apply(productCreatedEvent);
    }
}
```
#### 3.7 Event sourcing handler method
Annotated with @EventSourcingHandler
```java
@Aggregate
public class ProductAggregate {
    @AggregateIdentifier
    private String code;
    private String title;
    private BigDecimal sellingPrice;
    private int subCategoryId;
    private String image;
    private List<String> tags;

    // ...

    @EventSourcingHandler
    public void on(ProductCreatedEvent productCreatedEvent) {
        this.code = productCreatedEvent.getCode();
        this.title = productCreatedEvent.getTitle();
        this.sellingPrice = productCreatedEvent.getSellingPrice();
        this.subCategoryId = productCreatedEvent.getSubCategoryId();
        this.image = productCreatedEvent.getImage();
        this.tags = productCreatedEvent.getTags();
    }

    // ...

}
```
- Update the current states of the created event
- The created event will be persisted to the event store

#### 3.8 Adding additional dependency: Google Guava
```
<!-- https://mvnrepository.com/artifact/com.google.guava/guava -->
<dependency>
    <groupId>com.google.guava</groupId>
    <artifactId>guava</artifactId>
    <version>30.1.1-jre</version>
</dependency>
```

#### 3.9 Previewing Event in EventStore
- Go to Axon Server at http://localhost:8024
- Look at the left navigation bar, click on "Search" item
- Check "Live Updates", then click "Search" button, the list of events will be displayed
- Choose the event item you desired to view it's details

## 4. Persisting event in the Products database
#### 4.1 Use Postgresql database
* Start using Postgresql
```
docker-compose up -d
```
* Add Maven dependencies to support Postgresql
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
</dependency>
```

* Add the configuration properties in order to deal with Postgresql
```yaml
spring:
  datasource:
    url: "jdbc:postgresql://localhost:5432/product-db"
    username: postgres
    password: postgres
    driver-class-name: org.postgresql.Driver
```
* Add Flyway dependency to the project
* Refer to: https://flywaydb.org/documentation/concepts/migrations
```xml
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
</dependency>
```
* Create new folder "db/migration" in the folder "resources"
* Add your migration script into resources\db\migration. Please refer to V1__init_all_tables.sql. P/S: the naming of Flyway migration will be "V{i}__migration_title" which {i} is an next number from the current revision.
```sql

-- PRODUCT table
DROP TABLE IF EXISTS PRODUCT;
DROP SEQUENCE IF EXISTS PRODUCT_ID_PK_SEQ;
CREATE SEQUENCE PRODUCT_ID_PK_SEQ;
CREATE TABLE PRODUCT  (
	ID INTEGER DEFAULT NEXTVAL('PRODUCT_ID_PK_SEQ'),
	CODE VARCHAR(255) NOT NULL,
	TITLE VARCHAR(500) NOT NULL,
	CATEGORY_ID INTEGER,
	SUB_CATEGORY_ID INTEGER,
	SELLING_PRICE DECIMAL 0,
	IMAGE VARCHAR(3000) NULL,
	TAGS VARCHAR(500) NULL,
	PRIMARY KEY (ID)
);
```
* Run or debug your application and look at your database will be run the migration scripts
#### 4.2 Create Entities
#### 4.3 Create Repositories
#### 4.4 Create Events Handler/Projection
```java
@Component
public class ProductEventsHandler {
    @EventHandler
    public void on(ProductCreatedEvent event) {
        
    }
}
```
* Implementing @EventHandler method
```java

```
