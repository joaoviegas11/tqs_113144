-- =============================
-- Tabela: refectory
-- =============================
CREATE TABLE  refectory (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    location VARCHAR(255),
    available_seats INTEGER
);

-- =============================
-- Tabela: menu
-- =============================
CREATE TABLE  menu (
    id SERIAL PRIMARY KEY,
    date DATE,
    meal TEXT,
    occupied_seats INTEGER DEFAULT 0 NOT NULL,
    meal_type VARCHAR(20),
    refectory_id INTEGER,
    CONSTRAINT fk_menu_refectory FOREIGN KEY (refectory_id) REFERENCES refectory(id)
);

-- =============================
-- Tabela: reservations
-- =============================
CREATE TABLE  reservations (
    id SERIAL PRIMARY KEY,
    token VARCHAR(255) UNIQUE NOT NULL,
    menu_id INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL,
    used SMALLINT DEFAULT 1,
    CONSTRAINT fk_reservation_menu FOREIGN KEY (menu_id) REFERENCES menu(id)
);


INSERT INTO refectory (name, location, available_seats) VALUES ('Cantina Central', 'Edifício A', 3);
INSERT INTO refectory (name, location, available_seats) VALUES ('Cantina de Engenharia', 'Edifício E', 80);
INSERT INTO refectory (name, location, available_seats) VALUES ('Cantina de Ciências', 'Edifício C', 70);
INSERT INTO refectory (name, location, available_seats) VALUES ('Cantina de Letras', 'Edifício L', 60);

INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-08', 'Arroz de frango', 'LUNCH', 1);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-08', 'Sopa + omelete', 'DINNER', 1);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-08', 'Arroz de frango', 'LUNCH', 2);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-08', 'Sopa + omelete', 'DINNER', 2);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-08', 'Arroz de frango', 'LUNCH', 3);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-08', 'Sopa + omelete', 'DINNER', 3);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-08', 'Arroz de frango', 'LUNCH', 4);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-08', 'Sopa + omelete', 'DINNER', 4);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-09', 'Feijoada', 'LUNCH', 1);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-09', 'Arroz de atum', 'DINNER', 1);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-09', 'Feijoada', 'LUNCH', 2);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-09', 'Arroz de atum', 'DINNER', 2);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-09', 'Feijoada', 'LUNCH', 3);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-09', 'Arroz de atum', 'DINNER', 3);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-09', 'Feijoada', 'LUNCH', 4);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-09', 'Arroz de atum', 'DINNER', 4);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-10', 'Bacalhau à Brás', 'LUNCH', 1);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-10', 'Hambúrguer no prato', 'DINNER', 1);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-10', 'Bacalhau à Brás', 'LUNCH', 2);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-10', 'Hambúrguer no prato', 'DINNER', 2);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-10', 'Bacalhau à Brás', 'LUNCH', 3);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-10', 'Hambúrguer no prato', 'DINNER', 3);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-10', 'Bacalhau à Brás', 'LUNCH', 4);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-10', 'Hambúrguer no prato', 'DINNER', 4);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-11', 'Esparguete à bolonhesa', 'LUNCH', 1);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-11', 'Frango assado', 'DINNER', 1);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-11', 'Esparguete à bolonhesa', 'LUNCH', 2);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-11', 'Frango assado', 'DINNER', 2);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-11', 'Esparguete à bolonhesa', 'LUNCH', 3);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-11', 'Frango assado', 'DINNER', 3);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-11', 'Esparguete à bolonhesa', 'LUNCH', 4);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-11', 'Frango assado', 'DINNER', 4);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-12', 'Lasanha', 'LUNCH', 1);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-12', 'Tofu salteado', 'DINNER', 1);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-12', 'Lasanha', 'LUNCH', 2);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-12', 'Tofu salteado', 'DINNER', 2);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-12', 'Lasanha', 'LUNCH', 3);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-12', 'Tofu salteado', 'DINNER', 3);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-12', 'Lasanha', 'LUNCH', 4);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-12', 'Tofu salteado', 'DINNER', 4);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-13', 'Arroz de frango', 'LUNCH', 1);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-13', 'Sopa + omelete', 'DINNER', 1);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-13', 'Arroz de frango', 'LUNCH', 2);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-13', 'Sopa + omelete', 'DINNER', 2);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-13', 'Arroz de frango', 'LUNCH', 3);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-13', 'Sopa + omelete', 'DINNER', 3);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-13', 'Arroz de frango', 'LUNCH', 4);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-13', 'Sopa + omelete', 'DINNER', 4);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-14', 'Feijoada', 'LUNCH', 1);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-14', 'Arroz de atum', 'DINNER', 1);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-14', 'Feijoada', 'LUNCH', 2);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-14', 'Arroz de atum', 'DINNER', 2);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-14', 'Feijoada', 'LUNCH', 3);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-14', 'Arroz de atum', 'DINNER', 3);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-14', 'Feijoada', 'LUNCH', 4);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-14', 'Arroz de atum', 'DINNER', 4);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-15', 'Bacalhau à Brás', 'LUNCH', 1);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-15', 'Hambúrguer no prato', 'DINNER', 1);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-15', 'Bacalhau à Brás', 'LUNCH', 2);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-15', 'Hambúrguer no prato', 'DINNER', 2);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-15', 'Bacalhau à Brás', 'LUNCH', 3);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-15', 'Hambúrguer no prato', 'DINNER', 3);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-15', 'Bacalhau à Brás', 'LUNCH', 4);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-15', 'Hambúrguer no prato', 'DINNER', 4);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-16', 'Esparguete à bolonhesa', 'LUNCH', 1);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-16', 'Frango assado', 'DINNER', 1);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-16', 'Esparguete à bolonhesa', 'LUNCH', 2);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-16', 'Frango assado', 'DINNER', 2);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-16', 'Esparguete à bolonhesa', 'LUNCH', 3);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-16', 'Frango assado', 'DINNER', 3);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-16', 'Esparguete à bolonhesa', 'LUNCH', 4);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-16', 'Frango assado', 'DINNER', 4);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-17', 'Lasanha', 'LUNCH', 1);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-17', 'Tofu salteado', 'DINNER', 1);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-17', 'Lasanha', 'LUNCH', 2);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-17', 'Tofu salteado', 'DINNER', 2);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-17', 'Lasanha', 'LUNCH', 3);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-17', 'Tofu salteado', 'DINNER', 3);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-17', 'Lasanha', 'LUNCH', 4);
INSERT INTO menu (date, meal, meal_type, refectory_id) VALUES ('2025-04-17', 'Tofu salteado', 'DINNER', 4);