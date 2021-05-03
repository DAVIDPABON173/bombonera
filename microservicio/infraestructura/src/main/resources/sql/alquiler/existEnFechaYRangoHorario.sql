select count(1) from alquiler
where
(fecha_alquiler = :fecha_alquiler and hora_inicio = :hora_inicio)
or
(fecha_alquiler = :fecha_alquiler and hora_inicio < :hora_inicio and hora_fin > :hora_inicio)
or
(fecha_alquiler = :fecha_alquiler and hora_inicio < :hora_fin and hora_fin > :hora_inicio)