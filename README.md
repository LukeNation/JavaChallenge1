# JavaChallenge1
a Java Challenge to a company's technical test

#Notes:

- no Schema.sql file, the DB is automatically build with the application running, and is deleted when the application stop. 
- everytime the app start, some data is charged into the base with import.sql file. to have previous information into the DB. 
- all endpoint and method from services classes have 100% coverage.
- when u delete an user's cart, only delete the relation between user and cart, the cart keeps in DB to reuse the method delete for when done a shop session.
- the 4x3 discount has been made in the moment you add the item that has 4 or more items, also if you add multiples of 4 for example 8,12,16 it add the discount for the correct number of offers you added (in case you add 8 items. you pay for 6.)
- items has 2 ways to create it, using one of previously charged into the DB (sending one itemId between 101 and 107) or adding new items (sending the name and priceUnit) having preference for the id.
- if u add an item without create a cart before, the system automatically create a non-special cart for the user. if you want a special cart you must do it manually with the correct endpoint.