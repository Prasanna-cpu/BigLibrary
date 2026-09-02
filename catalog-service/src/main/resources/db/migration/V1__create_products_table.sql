create table products(
    id uuid primary key default gen_random_uuid(),
    code text not null unique ,
    name text not null,
    description text,
    image_url text,
    price numeric(10, 2) not null,
    created_at timestamp with time zone default current_timestamp,
    updated_at timestamp with time zone default current_timestamp,
    created_by text,
    updated_by text
)