INSERT INTO customer(id, name, customer_number, brand, email)
VALUES (1, 'Samantha', '12345', 'SNS', 'samantha@test.com'),
       (2, 'Jeffrey', '23456', 'RegioBank', 'jeffrey@test.com'),
       (3, 'Melissa', 'Melissa', 'BLG', 'melissa@test.com');

INSERT INTO advisor(id, name, office, email)
VALUES (1, 'Taza SNS', 27, 'taza-sns@test.com'),
       (2, 'Taza BLG', 129, 'taza-blg@test.com'),
       (3, 'Eva Smit', 15, 'e.smit@test.com');

INSERT INTO company(id, name, contact_person, email)
VALUES (1, 'BLG Wonen', 'Dennis Schuurman', 'blg.psk@test.nl'),
       (2, 'SNS Bank', 'Dennis Schuurman', 'sns.psk@test.nl'),
       (3, 'RegioBank', 'Dennis Schuurman', 'rb.psk@test.nl');

INSERT INTO file(id, status, status_comment, comment, file_type, contract_amount)
VALUES (1, 'Wachten op bedrag en PSK', 'Nabestaandendossier', 'Lhk nee', 'LUR', 5500),
       (2, 'Bijzonderheid', 'Niet compliant', 'Dep ja', 'LOR', 0),
       (3, 'Wachten op bedrag', '', 'Lhk ja', 'GHUR', 4000);

INSERT INTO policy(id, policy_number, amount, receive_date_amount, receive_date_psk, reminder_date_psk)
VALUES (1, 'L123', 2500, '2023-02-01', '2023-01-01', null),
       (2, 'L1234', null, null, null, null),
       (3, 'K504', 6593.21, '2023-03-10', null, '2023-03-24');