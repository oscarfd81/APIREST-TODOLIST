/*TUVE QUE PONER NEXTVAL DEBIDO A QUE COMO EN EL MODEL LO DECLARE COMO GENERATEDVALUE,USO LA SECUENCIA GENERADA PARA GENERAR EL ID AUTOMATICAMENTE POR CADA NUEVA TAREA*/
INSERT INTO user_entity (id, email, username, password, is_admin,is_gestor,is_user) VALUES (NEXTVAL('user_entity_seq'), 'admin@gmail.com', 'admin', '{noop}1234', true,false,false);

INSERT INTO user_entity (id, email, username, password, is_admin,is_gestor,is_user) VALUES (NEXTVAL('user_entity_seq'), 'gestor@gmail.com', 'gestor', '{noop}1234', false,true,false);

INSERT INTO user_entity (id, email, username, password, is_admin,is_gestor,is_user) VALUES (NEXTVAL('user_entity_seq'), 'user@gmail.com', 'user', '{noop}1234', false,false,true);

INSERT INTO cat (id,name) VALUES(NEXTVAL('task_seq'), 'Trabajo');
INSERT INTO cat (id,name) VALUES(NEXTVAL('task_seq'), 'Estudio');
INSERT INTO cat (id,name) VALUES(NEXTVAL('task_seq'), 'Personal');
INSERT INTO cat (id,name) VALUES(NEXTVAL('task_seq'), 'Gimnasio');
INSERT INTO cat (id,name) VALUES(NEXTVAL('task_seq'), 'Hobbie');
INSERT INTO cat (id,name) VALUES(NEXTVAL('task_seq'), 'Amigos');
INSERT INTO cat (id,name) VALUES(NEXTVAL('task_seq'), 'Familia');

INSERT INTO tagd (id,name) VALUES (NEXTVAL('tagd_seq'),'Baloncesto');
INSERT INTO tagd (id,name) VALUES (NEXTVAL('tagd_seq'),'Rugby');
INSERT INTO tagd (id,name) VALUES (NEXTVAL('tagd_seq'),'Cumpleaños');
INSERT INTO tagd (id,name) VALUES (NEXTVAL('tagd_seq'),'Quedada');
INSERT INTO tagd (id,name) VALUES (NEXTVAL('tagd_seq'),'Examen');
INSERT INTO tagd (id,name) VALUES (NEXTVAL('tagd_seq'),'Tarea');
INSERT INTO tagd (id,name) VALUES (NEXTVAL('tagd_seq'),'Ver pelicula');

INSERT INTO task (id, created_at, deadline, title, description, status, priority, important)VALUES (NEXTVAL('task_seq'), CURRENT_TIMESTAMP, '2026-04-22 08:00:00', 'Ir al gimnasio', 'Rutina de pierna y 20 minutos de cardio', 'EN_PROCESO', 'MEDIA',TRUE);
INSERT INTO task (id, created_at, deadline, title, description, status, priority,important)VALUES (NEXTVAL('task_seq'), CURRENT_TIMESTAMP, '2026-04-22 10:30:00', 'Estudiar Java Spring', 'Ver el módulo de persistencia de datos y JPA', 'PENDIENTE', 'ALTA',TRUE);
INSERT INTO task (id, created_at, deadline, title, description, status, priority,important)VALUES (NEXTVAL('task_seq'), CURRENT_TIMESTAMP, '2026-04-22 14:00:00', 'Comprar comida', 'Ir al súper por pechuga de pollo, arroz y verduras', 'NO_HECHO', 'BAJA',FALSE);
INSERT INTO task (id, created_at, deadline, title, description, status, priority,important)VALUES (NEXTVAL('task_seq'), CURRENT_TIMESTAMP, '2026-04-23 16:00:00', 'Diseño Web UI/UX', 'Practicar con Figma el rediseño del dashboard personal', 'EN_PROCESO', 'MEDIA',TRUE);
INSERT INTO task (id, created_at, deadline, title, description, status, priority,important)VALUES (NEXTVAL('task_seq'), CURRENT_TIMESTAMP, '2026-04-23 21:00:00', 'Noche de cine', 'Ver la película que recomendaron en el foro de programación', 'HECHO', 'BAJA',FALSE);
INSERT INTO task (id, created_at, deadline, title, description, status, priority,important)VALUES (NEXTVAL('task_seq'), CURRENT_TIMESTAMP, '2026-04-24 09:00:00', 'Repasar SQL', 'Practicar JOINs y subconsultas en la base de datos de prueba', 'PENDIENTE', 'ALTA',FALSE);
INSERT INTO task (id, created_at, deadline, title, description, status, priority,important)VALUES (NEXTVAL('task_seq'), CURRENT_TIMESTAMP, '2026-04-24 18:00:00', 'Llamar a soporte', 'Resolver el problema con la conexión a internet de casa', 'NO_HECHO', 'MEDIA',TRUE);
INSERT INTO task (id, created_at, deadline, title, description, status, priority,important)VALUES (NEXTVAL('task_seq'), CURRENT_TIMESTAMP, '2026-04-25 11:00:00', 'Limpieza general', 'Organizar el escritorio y limpiar el equipo de computación', 'HECHO', 'BAJA',FALSE);
INSERT INTO task (id, created_at, deadline, title, description, status, priority,important)VALUES (NEXTVAL('task_seq'), CURRENT_TIMESTAMP, '2026-04-25 19:00:00', 'Leer libro técnico', 'Leer 2 capítulos del libro de Clean Code', 'PENDIENTE', 'MEDIA',TRUE);
INSERT INTO task (id, created_at, deadline, title, description, status, priority,important)VALUES (NEXTVAL('task_seq'), CURRENT_TIMESTAMP, '2026-04-26 23:59:00', 'Planificar semana', 'Organizar las tareas y objetivos de la próxima semana', 'EN_PROCESO', 'ALTA',FALSE); 

UPDATE task SET author_id = CURRVAL('user_entity_seq');


