CREATE TABLE pelicula (
	codigo TEXT PRMARY KEY,
	titulo TEXT,
	autor TEXT,
	anio TEXT,
	genero TEXT);
	
CREATE TABLE cliente(
	dni TEXT PRIMARY KEY,
	nombre TEXT,
	apellidos TEXT,
	edad TEXT);

CREATE TABLE alquila(
	fecha_alquilada TEXT PRIMARY KEY,
	fecha_devolucion TEXT,
	codPelicula TEXT,
	dniCliente TEXT,
	FOREIGN KEY (codPelicula) REFERENCES pelicula (codigo),
	FOREIGN KEY (dniCliente) REFERENCES cliente (dni));

CREATE TRIGGER borrarPelicula BEFORE DELETE
	ON pelicula
	BEGIN
	insert into pelicula (codigo, titulo, autor, anio, genero) values ('25323421', '007', 'Peter Hunt Sins', '1969', 'accion');
	END;

CREATE TRIGGER actualizarPelicula BEFORE UPDATE
	ON pelicula
	BEGIN	
	insert into pelicula (codigo, titulo, autor, anio, genero) values ('25323421', '007', 'Peter Hunt Sins', '1969', 'accion');
	END;

CREATE TRIGGER actualizarCliente BEFORE UPDATE
	ON cliente
	BEGIN
	insert into cliente (dni, nombre, apellidos, edad) values ('34709833A', 'David', 'Perez Ruiz', '18');
	END;

CREATE TRIGGER borrarCliente BEFORE DELETE
	ON cliente
	BEGIN
	insert into cliente (dni, nombre, apellidos, edad) values ('34709833A', 'David', 'Perez Ruiz', '18');
	END;

CREATE TRIGGER borrarAlquila BEFORE DELETE
	ON alquila
	BEGIN
	insert into alquila (fecha_alquilada, fecha_devolucion, dniCliente, codPelicula) values ('01/05/2016', '14/06/2016', '25323421', '34709833A');
	END;

CREATE TRIGGER actualizarAlquila BEFORE UPDATE
	ON alquila
	BEGIN
	insert into alquila (fecha_alquilada, fecha_devolucion, dniCliente, codPelicula) values ('01/05/2016', '14/06/2016', '25323421', '34709833A');
	END;	