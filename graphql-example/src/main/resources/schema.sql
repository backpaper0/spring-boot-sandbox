CREATE TABLE authors (
    id INT PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE books (
    id INT PRIMARY KEY,
    title VARCHAR(100),
    author_id INT
);

ALTER TABLE books ADD FOREIGN KEY (author_id) REFERENCES authors (id);
