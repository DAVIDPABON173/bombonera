select count(1) from alquiler
where
(fecha_alquiler = :fecha_alquiler and hora_inicio = :hora_inicio)
or
(fecha_alquiler = :fecha_alquiler and hora_inicio < :hora_inicio && hora_fin > :hora_inicio)
or
(fecha_alquiler = :fecha_alquiler and hora_inicio < :hora_fin && hora_fin > :hora_inicio)