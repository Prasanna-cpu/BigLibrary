create table order_events(
    id uuid primary key default gen_random_uuid(),
    event_id     text                                         not null unique,
    created_at   timestamp                                    not null,
    updated_at   timestamp
)