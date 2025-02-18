CREATE SEQUENCE order_event_id_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE order_events
(
    id           BIGINT DEFAULT nextval('order_event_id_seq') NOT NULL,
    order_number TEXT                                         NOT NULL REFERENCES orders (order_number),
    event_id     TEXT                                         NOT NULL unique,
    event_type   TEXT                                         NOT NULL,
    payload      TEXT                                         NOT NULL,
    created_at   TIMESTAMP                                    NOT NULL,
    updated_at   TIMESTAMP,
    PRIMARY KEY (id)
);