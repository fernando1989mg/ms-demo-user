CREATE TABLE users (
    id VARCHAR(80) PRIMARY KEY,
    created_at DATETIME NOT NULL,
    modified_at DATETIME NOT NULL,
    last_login DATETIME NOT NULL,
    is_active BOOLEAN NOT NULL,
    name VARCHAR(80) NOT NULL,
    email VARCHAR(80) NOT NULL,
    password VARCHAR(256) NOT NULL,
    token VARCHAR(256) NOT NULL
);

CREATE TABLE user_phones (
   id VARCHAR(80) PRIMARY KEY,
   user_id VARCHAR(80) NOT NULL,
   number INTEGER NOT NULL,
   city_code INTEGER NOT NULL,
   country_code INTEGER NOT NULL,
   foreign key (user_id) references users(id)
);