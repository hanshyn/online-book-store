INSERT INTO categories (id, name, description, is_deleted) VALUES (1, 'Fiction', 'fiction books', false);

INSERT INTO categories (id, name, description, is_deleted) VALUES (2, 'Romance', 'Romance books', false);

INSERT INTO categories (id, name, description, is_deleted) VALUES (3, 'Not category', 'not category', false);

INSERT INTO books (id, title, author, isbn, price, description, cover_image, is_deleted)
VALUES (1, 'Sample Book 1', 'Author a', '9780545010009', 10.0, 'Yet another sample book description.', 'http://example.com/cover3.jpg', false);

INSERT INTO books (id, title, author, isbn, price, description, cover_image, is_deleted)
VALUES (2, 'Sample Book 2', 'Author b', '9781122334453', 20.0, 'Yet another sample book description.', 'http://example.com/cover2.jpg', false);

INSERT INTO books (id, title, author, isbn, price, description, cover_image, is_deleted)
VALUES (3, 'Sample Book 3', 'Author c', '9781234567897', 30.0, 'Yet another sample book description.', 'http://example.com/cover2.jpg', false);

INSERT INTO book_categories (books_id, categories_id) VALUES (1,1);

INSERT INTO book_categories (books_id, categories_id) VALUES (2,2);

INSERT INTO book_categories (books_id, categories_id) VALUES (3,2);

INSERT INTO book_categories (books_id, categories_id) VALUES (3,3);
