PRAGMA foreign_keys = ON;

DROP TABLE IF EXISTS pelicula;
DROP TABLE IF EXISTS cliente;
DROP TABLE IF EXISTS alquila;

DROP TRIGGER IF EXISTS actualizarPelicula;
DROP TRIGGER IF EXISTS actualizarCliente;
DROP TRIGGER IF EXISTS actualizarAlquila;

CREATE TABLE pelicula (
	codigo TEXT PRIMARY KEY,
	titulo TEXT,
	director TEXT,
	anio TEXT,
	genero TEXT);
	
CREATE TABLE cliente(
	dni TEXT PRIMARY KEY,
	nombre TEXT,
	apellidos TEXT,
	edad TEXT);

CREATE TABLE alquila(
	fecha_alquilada TEXT,
	fecha_devolucion TEXT,
	dniCliente TEXT,
	codPelicula TEXT,
	PRIMARY KEY (dniCliente, codPelicula),
	FOREIGN KEY (dniCliente) REFERENCES cliente (dni) ON DELETE CASCADE,
	FOREIGN KEY (codPelicula) REFERENCES pelicula (codigo) ON DELETE CASCADE);

CREATE VIEW vistaAlquiler AS select alquila.dniCliente, cliente.nombre, cliente.apellidos, 
		alquila.codPelicula, pelicula.titulo, alquila.fecha_alquilada, alquila.fecha_devolucion 
		from pelicula, cliente, alquila
		where cliente.dni = alquila.dniCliente and pelicula.codigo = alquila.codPelicula;
	
CREATE TRIGGER actualizarPelicula BEFORE UPDATE
	ON pelicula
	BEGIN	
	insert into pelicula (codigo, titulo, director, anio, genero) values ('25323421', '007', 'Peter Hunt Sins', '1969', 'accion');
	END;

CREATE TRIGGER actualizarCliente BEFORE UPDATE
	ON cliente
	BEGIN
	insert into cliente (dni, nombre, apellidos, edad) values ('34709833A', 'David', 'Perez Ruiz', '18');
	END;

CREATE TRIGGER actualizarAlquila BEFORE UPDATE
	ON alquila
	BEGIN
	insert into alquila (fecha_alquilada, fecha_devolucion, dniCliente, codPelicula) values ('01/05/2016', '14/06/2016', '25323421', '34709833A');
	END;	

insert into pelicula (codigo, titulo, director, anio, genero) values ('25323421', '007', 'Peter Hunt Sins', '1969', 'accion');
insert into pelicula (codigo, titulo, director, anio, genero) values ('15321627', 'Amigos hasta el final', 'Pepe Talavera Perez', '1997', 'accion');
insert into pelicula (codigo, titulo, director, anio, genero) values ('85321221', '21 Gramos', 'Alejandro Gonz�lez Inairritu', '2003', 'drama');
insert into pelicula (codigo, titulo, director, anio, genero) values ('55326211', 'Aguas arriba', 'Deborah Rodrigrez Jurado', '1998' , 'drama');
insert into pelicula (codigo, titulo, director, anio, genero) values ('35323231', 'Ajuste de Cuentas', 'John Irvin Kein', '1996', 'drama');
insert into pelicula (codigo, titulo, director, anio, genero) values ('75121221', 'Al final del invierno', 'Glenn Jordan Math', '1999', 'drama');
insert into pelicula (codigo, titulo, director, anio, genero) values ('89321721', 'Arma perfecta', 'Mark DiSalle Curse', '1991', 'accion');
insert into pelicula (codigo, titulo, director, anio, genero) values ('62323251', 'Asalto a la isla', 'Jon Cassar Plius', '1997', 'accion');
insert into pelicula (codigo, titulo, director, anio, genero) values ('53214564', 'El paraiso de Luxemburgo', 'Claste Juls', '2010', 'aventura');
insert into pelicula (codigo, titulo, director, anio, genero) values ('57345621', 'No mires atras', 'Joana Felix', '2005', 'terror');
insert into pelicula (codigo, titulo, director, anio, genero) values ('66774321', 'Los 7 mares', 'Ana Lixus Moza', '2007', 'drama');
insert into pelicula (codigo, titulo, director, anio, genero) values ('56432432', 'El Exorcismo', 'Liana Duke Lara', '2011', 'terror');

insert into cliente (dni, nombre, apellidos, edad) values ('90081234J', 'Jesus', 'Martos Campos', '23');
insert into cliente (dni, nombre, apellidos, edad) values ('90085435K', 'Ana', 'Martos Cruz', '28');
insert into cliente (dni, nombre, apellidos, edad) values ('90086412O', 'Pepe', 'Sevilla Moreno', '34');
insert into cliente (dni, nombre, apellidos, edad) values ('92083336L', 'Dani', 'Martos Sanchez', '30');
insert into cliente (dni, nombre, apellidos, edad) values ('90033335U', 'Luisa', 'Sierra Campos', '10');
insert into cliente (dni, nombre, apellidos, edad) values ('90083136I', 'Marina', 'Moreno Campos', '18');
insert into cliente (dni, nombre, apellidos, edad) values ('90083336P', 'Luis', 'Ruiz Campos', '12');
insert into cliente (dni, nombre, apellidos, edad) values ('90082336R', 'Mario', 'Perez Nali', '50');
insert into cliente (dni, nombre, apellidos, edad) values ('90042326L', 'Marina', 'Lechuga Moya', '28');
insert into cliente (dni, nombre, apellidos, edad) values ('90021111E', 'Daniela', 'Cuote Malay', '44');
insert into cliente (dni, nombre, apellidos, edad) values ('90042112G', 'Monia', 'Sorana Lopez', '32');
insert into cliente (dni, nombre, apellidos, edad) values ('90345212S', 'Ramon', 'Campos Martinez', '41');

insert into alquila (fecha_alquilada, fecha_devolucion, dniCliente, codPelicula) values ('01/05/2016', '14/06/2016', '90081234J', '25323421');
insert into alquila (fecha_alquilada, fecha_devolucion, dniCliente, codPelicula) values ('03/06/2016', '20/07/2016', '90085435K', '15321627');
insert into alquila (fecha_alquilada, fecha_devolucion, dniCliente, codPelicula) values ('20/07/2016', '12/08/2016', '90086412O', '85321221');
insert into alquila (fecha_alquilada, fecha_devolucion, dniCliente, codPelicula) values ('13/07/2016', '20/06/2017', '92083336L', '55326211');
insert into alquila (fecha_alquilada, fecha_devolucion, dniCliente, codPelicula) values ('16/05/2016', '17/08/2016', '90033335U', '35323231');
insert into alquila (fecha_alquilada, fecha_devolucion, dniCliente, codPelicula) values ('20/08/2016', '01/01/2017', '90083136I', '75121221');
insert into alquila (fecha_alquilada, fecha_devolucion, dniCliente, codPelicula) values ('09/11/2016', '10/12/2016', '90083336P', '89321721');
insert into alquila (fecha_alquilada, fecha_devolucion, dniCliente, codPelicula) values ('01/12/2016', '12/12/2016', '90082336R', '62323251');
insert into alquila (fecha_alquilada, fecha_devolucion, dniCliente, codPelicula) values ('09/12/2016', '24/12/2016', '90042326L', '53214564');
insert into alquila (fecha_alquilada, fecha_devolucion, dniCliente, codPelicula) values ('01/01/2017', '02/05/2017', '90021111E', '57345621');
insert into alquila (fecha_alquilada, fecha_devolucion, dniCliente, codPelicula) values ('01/12/2017', '12/12/2017', '90042112G', '66774321');
insert into alquila (fecha_alquilada, fecha_devolucion, dniCliente, codPelicula) values ('20/05/2017', '24/05/2017', '90345212S', '56432432');