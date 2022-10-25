# JavaChallenge1
a Java Challenge to a company's technical test

# Notes:

- no Schema.sql file, the DB is automatically build with the application running, and is deleted when the application stop. 
- everytime the app start, some data is charged into the base with import.sql file. to have previous information into the DB. 
- all endpoint and method are included into Demo-postman collection
- when you delete an user's cart, only delete the relation between user and cart, the cart keeps in DB to reuse the method delete the cart and put the shop into the user's historical shops for when done a shop session.
- items has ids from 101 to 107
- if you add an item without create a cart before, the system automatically create a cart for the user, if you use User Id:14415550 you always have a VIP cart Promotion, - in other hand, if you use 44115550 you have Cart with Special Date Promotion y a cart without promotion, (one time each one) 
- the Postman Collection is into the github repository. 

# technologies used in this project:

- Java 11
- Spring boot 2.6.1
- Spring boot Starter Web
- JPA - Hibernate
- H2 Database
- lombok
- JUnit 4.13.2
- Mockito 1.10.19
- Postman
