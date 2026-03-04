-- Script para criar a tabela 'books' no MySQL
CREATE TABLE IF NOT EXISTS books (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    author VARCHAR(255),
    details VARCHAR(255)
);
