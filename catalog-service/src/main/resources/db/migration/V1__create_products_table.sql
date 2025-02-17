CREATE SEQUENCE product_id_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE products (
    id BIGINT DEFAULT nextval('product_id_seq') NOT NULL,
    code TEXT NOT NULL UNIQUE,
    name TEXT NOT NULL,
    description TEXT,
    image_url TEXT,
    price NUMERIC NOT NULL,
    PRIMARY KEY (id)
);