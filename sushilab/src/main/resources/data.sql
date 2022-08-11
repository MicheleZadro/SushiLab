INSERT INTO utente (email, password, is_gestore) VALUES
  ('gestore@email.it', 'password1234', 't'),
  ('client_1@email.it', 'password1234', 'f'),
  ('client_2@email.it', 'password4321', 'f'),
  ('client_3@email.it', 'password1243', 'f');

INSERT INTO id_token(id_token, cliente) VALUES
('519218980065662000', 'gestore@email.it');

INSERT INTO session (id_table) VALUES
('MSZw1DM');

INSERT INTO piatto_upload(numero, variante, nome, prezzo, immagine, alt) VALUES
(1, 'variante', 'nome', 90, 'immagine', 'alt')
