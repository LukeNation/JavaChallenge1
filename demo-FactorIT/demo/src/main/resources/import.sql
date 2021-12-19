INSERT INTO ITEM_Catalog (id,name,price_unit) VALUES (101,'Auriculares',4199.00),(102,'Patines',7918.23),(103,'Cinturon',1514.49),(104,'Dobok',8750.00),(105,'taza termica',592.25),(106,'bombilla',1187.50),(107,'wafflera',2449.00);
Insert into Items (id,quantity,item_catalog_id) values (173,3,101), (179,6,102),(165,1,104)
INSERT INTO SHOP (id,date_time,discount,special,total,total_no_discount) VALUES (254,20211215,23.00,true,100.00,123.00),(233,20211015,29.00,true,120.00,149.00);
insert into Shop_items_list (shop_id,items_list_id) values (254,173),(254,179),(233,165)
INSERT INTO USER (dni,address,email,last_name,name,vip) VALUES (14415550,'una calle de por aqui','unemail@mailservice.com','Aprea','Lucas',true)
INSERT Into user_shopping_list (user_dni,shopping_list_id) VALUES (14415550,254),(14415550,233)