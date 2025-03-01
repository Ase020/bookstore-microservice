TRUNCATE TABLE orders CASCADE;
ALTER SEQUENCE order_id_seq RESTART WITH 100;
ALTER SEQUENCE order_item_id_seq RESTART WITH 100;

INSERT INTO orders (id,order_number,username,
                    customer_name,customer_email,customer_phone,
                    delivery_address_line1,delivery_address_line2,delivery_address_city,
                    delivery_address_state,delivery_address_zip_code,delivery_address_country,
                    status,comments)
VALUES
(1, 'order-123', 'username', 'Felix', 'felix@gmail.com', '712345678', '123 Main St', 'Apt 1', 'Nairobi', 'Nairobi', '75001', 'USA', 'NEW', null),
(2, 'order-456', 'username', 'Felix', 'felix@gmail.com', '712345678', '123 Main St', 'Apt 1', 'Kisumu', 'Nairobi', '500072', 'KENYA', 'NEW', null);

INSERT INTO order_items(order_id, code, name, price, quantity)
VALUES
(1, 'P100', 'The Hunger Games', 34.0, 2),
(1, 'P101', 'To Kill a Mockingbird', 45.40, 1),
(2, 'P102', 'The Chronicles of Narnia', 44.50, 1);