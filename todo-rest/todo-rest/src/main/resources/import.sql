/*TUVE QUE PONER NEXTVAL DEBIDO A QUE COMO EN EL MODEL LO DECLARE COMO GENERATEDVALUE,USO LA SECUENCIA GENERADA PARA GENERAR EL ID AUTOMATICAMENTE POR CADA NUEVA TAREA*/
INSERT INTO user_entity (id, email, username, password, is_admin) VALUES (NEXTVAL('user_entity_seq'), 'oscar@gmail.com', 'oscar_fdez', '{noop}1234', true);

INSERT INTO cat (id,name) VALUES(NEXTVAL('task_seq'), 'Trabajo');
INSERT INTO cat (id,name) VALUES(NEXTVAL('task_seq'), 'Estudio');
INSERT INTO cat (id,name) VALUES(NEXTVAL('task_seq'), 'Personal');
INSERT INTO cat (id,name) VALUES(NEXTVAL('task_seq'), 'Gimnasio');
INSERT INTO cat (id,name) VALUES(NEXTVAL('task_seq'), 'Hobbie');
INSERT INTO cat (id,name) VALUES(NEXTVAL('task_seq'), 'Amigos');
INSERT INTO cat (id,name) VALUES(NEXTVAL('task_seq'), 'Familia');
INSERT INTO cat (id,name) VALUES(NEXTVAL('task_seq'), 'Cumpleaños');

INSERT INTO tagd (id,name) VALUES (NEXTVAL('tagd_seq'),'Urgente');
INSERT INTO tagd (id,name) VALUES (NEXTVAL('tagd_seq'),'Importante');
INSERT INTO tagd (id,name) VALUES (NEXTVAL('tagd_seq'),'Media Urgencia');
INSERT INTO tagd (id,name) VALUES (NEXTVAL('tagd_seq'),'Baja Urgencia');

INSERT INTO statu (id,name) VALUES(NEXTVAL('statu_seq'),'Hecho');
INSERT INTO statu (id,name) VALUES(NEXTVAL('statu_seq'),'No hecho');
INSERT INTO statu (id,name) VALUES(NEXTVAL('statu_seq'),'Pendiente');
INSERT INTO statu (id,name) VALUES(NEXTVAL('statu_seq'),'En proceso');

INSERT INTO task (id, created_at, deadline, title, description) VALUES (NEXTVAL('task_seq'), CURRENT_TIMESTAMP, '2026-04-22 08:00:00', 'Ir al gimnasio', 'Rutina de pierna y 20 minutos de cardio');
INSERT INTO task (id, created_at, deadline, title, description) VALUES (NEXTVAL('task_seq'), CURRENT_TIMESTAMP, '2026-04-22 10:30:00', 'Estudiar Java Spring', 'Ver el módulo de persistencia de datos y JPA');
INSERT INTO task (id, created_at, deadline, title, description) VALUES (NEXTVAL('task_seq'), CURRENT_TIMESTAMP, '2026-04-22 14:00:00', 'Comprar comida', 'Ir al súper por pechuga de pollo, arroz y verduras');
INSERT INTO task (id, created_at, deadline, title, description) VALUES (NEXTVAL('task_seq'), CURRENT_TIMESTAMP, '2026-04-23 16:00:00', 'Diseño Web UI/UX', 'Practicar con Figma el rediseño del dashboard personal');
INSERT INTO task (id, created_at, deadline, title, description) VALUES (NEXTVAL('task_seq'), CURRENT_TIMESTAMP, '2026-04-23 21:00:00', 'Noche de cine', 'Ver la película que recomendaron en el foro de programación');
INSERT INTO task (id, created_at, deadline, title, description) VALUES (NEXTVAL('task_seq'), CURRENT_TIMESTAMP, '2026-04-24 09:00:00','Repasar SQL', 'Practicar JOINs y subconsultas en la base de datos de prueba');
INSERT INTO task (id, created_at, deadline, title, description) VALUES (NEXTVAL('task_seq'), CURRENT_TIMESTAMP, '2026-04-24 18:00:00', 'Llamar a soporte', 'Resolver el problema con la conexión a internet de casa');
INSERT INTO task (id, created_at, deadline, title, description) VALUES (NEXTVAL('task_seq'), CURRENT_TIMESTAMP, '2026-04-25 11:00:00','Limpieza general', 'Organizar el escritorio y limpiar el equipo de computación');
INSERT INTO task (id, created_at, deadline, title, description) VALUES (NEXTVAL('task_seq'), CURRENT_TIMESTAMP, '2026-04-25 19:00:00', 'Leer libro técnico', 'Leer 2 capítulos del libro de Clean Code');
INSERT INTO task (id, created_at, deadline, title, description) VALUES (NEXTVAL('task_seq'), CURRENT_TIMESTAMP, '2026-04-26 23:59:00',  'Planificar semana', 'Organizar las tareas y objetivos de la próxima semana');

UPDATE task SET author_id = CURRVAL('user_entity_seq');


