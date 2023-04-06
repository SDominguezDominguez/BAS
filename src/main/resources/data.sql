INSERT INTO users(username, password, api_key, email, office_number, advisor_number)
VALUES ('admin', '$2y$10$ggDMLN/5M5RiK2pbr7pySuGyYDg9tgYdPvF.6cAkAj5VSnGifvS5m', '7847493', 'test@testy.nl', 710, null),
       ('employee', '$2y$10$mP8tMNMTMKsy0D.Gzj.nnOslkX733YSTOGSn698.vzTYZiyRnymGu', '951847', 'test@testy.nl', 710, null),
       ('advisor', '$2y$10$D4yD2mE7bChalDKzuhMXd.7mjJF2..KHUAOhNLzPljfRUB8FwzX9C', '326159', 'test@testy.nl',103, 123456),
       ('advisor2', '$2y$10$u1S6jKlRGkPBM4uGQCXrZOM03de0CEPAAWsB56mWjMvO8SAlsMPdS', '739146', 'test@testy.nl',103, 209830);

INSERT INTO authority(username, authority)
VALUES ('admin', 'ROLE_ADMIN'),
       ('employee', 'ROLE_EMPLOYEE'),
       ('advisor', 'ROLE_ADVISOR'),
       ('advisor2', 'ROLE_ADVISOR');

INSERT INTO company(id, name, contact_person, email)
VALUES (1, 'BLG Wonen', 'Dennis Schuurman', 'blg.psk@test.nl'),
       (2, 'SNS Bank', 'Dennis Schuurman', 'sns.psk@test.nl'),
       (3, 'RegioBank', 'Dennis Schuurman', 'rb.psk@test.nl');

INSERT INTO customer(id, name, customer_number, label, email, office_number, advisor_number)
VALUES (1, 'Samantha', '12345', 'SNS_BANK', 'samantha@test.com', 103, 123456),
       (2, 'Jeffrey', '23456', 'REGIOBANK', 'jeffrey@test.com', 103, 209830),
       (3, 'Melissa', 'Melissa', 'BLG_WONEN', 'melissa@test.com', null, null);

INSERT INTO file(id, status, status_comment, comment, file_type, contract_amount, customer_id, application_form_present)
VALUES (1, 'WACHTEN_OP_PSK_EN_BEDRAG', 'Nabestaandendossier', 'Lhk nee', 'LUR', 5500, 1, false),
       (2, 'BIJZONDERHEID', 'Niet compliant', 'Dep ja', 'LOR', 0, 2, false),
       (3, 'WACHTEN_OP_BEDRAG', '', 'Lhk ja', 'GHUR', 4000, 3, false);

INSERT INTO policy(id, policy_number, amount, receive_date_amount, receive_date_psk, reminder_date_psk, company_id, file_id)
VALUES (1, 'L123', 2500, '2023-02-01', '2023-01-01', null, 1, 1),
       (2, 'L1234', null, null, null, null, 2, 1),
       (3, 'K504', 6593.21, '2023-03-10', null, '2023-03-24', 3, 2);