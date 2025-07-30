INSERT INTO customer (name, created_by, created_date) VALUES
('Muriel Donnelly', 'system', CURRENT_TIMESTAMP),
('Lance Stiedemann Sr.', 'system', CURRENT_TIMESTAMP),
('Denise Harris', 'system', CURRENT_TIMESTAMP),
('Dianne Lemke', 'system', CURRENT_TIMESTAMP),
('Jean Daniel', 'system', CURRENT_TIMESTAMP),
('Vicki Kutch', 'system', CURRENT_TIMESTAMP),
('Dr. Winifred Morissette', 'system', CURRENT_TIMESTAMP),
('Robin Steuber', 'system', CURRENT_TIMESTAMP),
('Leticia MacGyver', 'system', CURRENT_TIMESTAMP),
('Dr. Natalie Oberbrunner', 'system', CURRENT_TIMESTAMP);

INSERT INTO product (description, name, price, quantity_in_stock, category, created_by, created_date) VALUES
('Handcrafted Soft Chair', 'Soft Chair', 199.99, 10, 'Furniture', 'system', CURRENT_TIMESTAMP),
('Awesome Metal Fish', 'Metal Fish', 29.99, 50, 'Decor', 'system', CURRENT_TIMESTAMP),
('Handmade Frozen Salad', 'Frozen Salad', 9.99, 100, 'Food', 'system', CURRENT_TIMESTAMP),
('Sleek Soft Chair', 'Sleek Chair', 249.99, 5, 'Furniture', 'system', CURRENT_TIMESTAMP),
('Recycled Wooden Shoes', 'Wooden Shoes', 79.99, 20, 'Footwear', 'system', CURRENT_TIMESTAMP);

SET @customer1 = (SELECT id FROM customer WHERE name = 'Muriel Donnelly');
SET @customer2 = (SELECT id FROM customer WHERE name = 'Lance Stiedemann Sr.');
SET @customer3 = (SELECT id FROM customer WHERE name = 'Denise Harris');
SET @customer4 = (SELECT id FROM customer WHERE name = 'Dianne Lemke');
SET @customer5 = (SELECT id FROM customer WHERE name = 'Jean Daniel');

INSERT INTO "order" (description, customer_id, created_by, created_date) VALUES
('Handcrafted Soft Chair', @customer1, 'system', CURRENT_TIMESTAMP),
('Awesome Metal Fish', @customer2, 'system', CURRENT_TIMESTAMP),
('Handmade Frozen Salad', @customer3, 'system', CURRENT_TIMESTAMP),
('Sleek Soft Chair', @customer4, 'system', CURRENT_TIMESTAMP),
('Recycled Wooden Shoes', @customer4, 'system', CURRENT_TIMESTAMP),
('Small Concrete Table', @customer5, 'system', CURRENT_TIMESTAMP),
('Awesome Metal Fish', @customer5, 'system', CURRENT_TIMESTAMP),
('Small Concrete Table', @customer5, 'system', CURRENT_TIMESTAMP);


INSERT INTO order_product (order_id, product_id) SELECT o.id, p.id FROM "order" o, product p WHERE o.description LIKE '%' || p.name || '%';