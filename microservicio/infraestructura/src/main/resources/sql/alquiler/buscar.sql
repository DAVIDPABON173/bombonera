select id, documento, fecha_solicitud, fecha_alquiler, hora_inicio, hora_fin, valor_pagado
from alquiler
where id = :id