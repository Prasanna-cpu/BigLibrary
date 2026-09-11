create table order_events(
    id                  uuid primary key default gen_random_uuid(),
    order_number        text not null references orders(order_number) on delete cascade,
    event_id            text not null unique,
    event_type          text not null,
    payload             text not null,
    created_at timestamp with time zone default current_timestamp,
    updated_at timestamp with time zone default current_timestamp,
    created_by text,
    updated_by text
)