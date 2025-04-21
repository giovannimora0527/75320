-- Tabla de Usuarios
CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) UNIQUE NOT NULL,
    telefono VARCHAR(20),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de Autores
CREATE TABLE autores (
    id_autor INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    nacionalidad VARCHAR(50),
    fecha_nacimiento DATE
);

-- Tabla de Libros
CREATE TABLE libros (
    id_libro INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    id_autor INT NOT NULL,
    anio_publicacion YEAR,
    categoria VARCHAR(100),
    existencias INT NOT NULL CHECK (existencias >= 0), -- No puede haber existencias negativas
    FOREIGN KEY (id_autor) REFERENCES autores(id_autor) ON DELETE CASCADE
);

-- Tabla de Préstamos (relación muchos a muchos entre usuarios y libros)
CREATE TABLE prestamos (
    id_prestamo INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_libro INT NOT NULL,
    fecha_prestamo TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_devolucion DATE NOT NULL, -- Fecha esperada de devolución
    estado ENUM('PRESTADO', 'DEVUELTO', 'VENCIDO') DEFAULT 'PRESTADO',
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_libro) REFERENCES libros(id_libro) ON DELETE CASCADE
);

ALTER TABLE `biblioteca`.`libros` 
CHANGE COLUMN `anio_publicacion` `anio_publicacion` INT NULL DEFAULT NULL ;

-- Insertar autores (10 autores)
INSERT INTO autores (nombre, nacionalidad, fecha_nacimiento) VALUES
('Gabriel García Márquez', 'Colombiana', '1927-03-06'),
('Julio Cortázar', 'Argentina', '1914-08-26'),
('Isabel Allende', 'Chilena', '1942-08-02'),
('Mario Vargas Llosa', 'Peruana', '1936-03-28'),
('Jorge Luis Borges', 'Argentina', '1899-08-24'),
('Carlos Ruiz Zafón', 'España', '1964-09-25'),
('Haruki Murakami', 'Japonesa', '1949-01-12'),
('Jane Austen', 'Británica', '1775-12-16'),
('George Orwell', 'Británica', '1903-06-25'),
('J.K. Rowling', 'Británica', '1965-07-31');

-- Insertar libros (10 libros, cada uno con un autor distinto)
INSERT INTO libros (titulo, id_autor, anio_publicacion, categoria, existencias) VALUES
('Cien años de soledad', 1, 1967, 'Novela', 5),
('Rayuela', 2, 1963, 'Novela', 3),
('La casa de los espíritus', 3, 1982, 'Novela', 4),
('La ciudad y los perros', 4, 1963, 'Novela', 6),
('Ficciones', 5, 1944, 'Relatos', 2),
('La sombra del viento', 6, 2001, 'Misterio', 7),
('Tokio Blues', 7, 1987, 'Ficción contemporánea', 5),
('Orgullo y prejuicio', 8, 1813, 'Romance', 4),
('1984', 9, 1949, 'Ciencia ficción', 3),
('Harry Potter y la piedra filosofal', 10, 1997, 'Fantasía', 10);

-- Insertar usuarios (3 usuarios)
INSERT INTO usuarios (nombre, correo, telefono) VALUES
('Juan Pérez', 'juan.perez@email.com', '3001234567'),
('María Gómez', 'maria.gomez@email.com', '3107654321'),
('Carlos Rodríguez', 'carlos.rodriguez@email.com', '3209876543');
