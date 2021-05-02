select count(1) from alquiler
where
id = :id
and
fecha_solicitud >= curdate() and fecha_solicitud < DATE_ADD(curdate(), INTERVAL 1 DAY)
and
fecha_alquiler > curdate()