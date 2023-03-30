INSERT INTO customer(id, name, customer_number, label, email)
VALUES (1, 'Samantha', '12345', 'SNS_BANK', 'samantha@test.com'),
       (2, 'Jeffrey', '23456', 'REGIOBANK', 'jeffrey@test.com'),
       (3, 'Melissa', 'Melissa', 'BLG_WONEN', 'melissa@test.com');

INSERT INTO file(id, status, status_comment, comment, file_type, contract_amount, customer_id)
VALUES (1, 'WACHTEN_OP_PSK_EN_BEDRAG', 'Nabestaandendossier', 'Lhk nee', 'LUR', 5500, 1),
       (2, 'BIJZONDERHEID', 'Niet compliant', 'Dep ja', 'LOR', 0, 2),
       (3, 'WACHTEN_OP_BEDRAG', '', 'Lhk ja', 'GHUR', 4000, 3);