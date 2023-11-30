create database dbordenes;
use dbordenes;

CREATE TABLE Ordenes1 (
	radicado int not null auto_increment primary key,
    tipo VARCHAR(50),
    codigo INT ,
    especificacion VARCHAR(100) ,
    fecha_solicitud TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_vencimiento DATETIME,
    observaciones VARCHAR(100)
);
/*insertar*/
insert into Ordenes (tipo,codigo,especificacion,observaciones, fecha_limite) values (
'Medicamentos',101,'ibuprofeno','tomar cada 8 horas', '2023-12-12');
insert into Ordenes1 (tipo,codigo,especificacion,observaciones, fecha_vencimiento) values (
'Cirugia',201,'clavicula','NA', '2023-12-12');

 select * from ordenes;
 
/*ALTER TABLE ordenes1
ADD COLUMN radicado int not null auto_increment primary key;

alter table ordenes1
modify column  radicado int first;*/



/* actualizar*/
update ordenes set ordenes.tipo =  "med", ordenes.codigo = 45, ordenes.especificacion = "fyy", ordenes.fecha_limite = "2023-12-12", ordenes.observaciones = "tturu" where ordenes.radicado =17;

/* Eliminar */
delete from ordenes where ordenes.radicado = 1;

DROP TABLE ordenes;

ALTER TABLE ordenes
CHANGE fecha_vencimiento fecha_limite DATE;

ALTER TABLE ordenes1
RENAME TO ordenes;

select * from ordenes