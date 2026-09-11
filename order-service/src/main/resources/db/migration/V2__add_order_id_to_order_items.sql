ALTER TABLE order_items ADD COLUMN order_id uuid NOT NULL;
ALTER TABLE order_items ADD CONSTRAINT fk_order_items_order FOREIGN KEY (order_id) REFERENCES orders(id);