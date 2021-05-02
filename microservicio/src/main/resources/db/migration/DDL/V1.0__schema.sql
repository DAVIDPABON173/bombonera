
create table alquiler (
 id int(11) not null auto_increment,
 documento varchar(12) not null,
 fecha_solicitud datetime not null,
 fecha_alquiler date not null,
 hora_inicio time not null,
 hora_fin time not null,
 valor_pagado double not null,
 primary key (id)
);