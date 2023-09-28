-- Voeg gebruiker (User) toe
INSERT INTO users (username, firstname, lastname, dateofbirth, email, password, enabled)
VALUES ('User1', 'Maria', 'Cruz', '2000-02-02', 'user1@test.nl', '12345test', true);

-- Voeg autoriteit (Authority) toe voor de gebruiker
INSERT INTO authorities (authority, username)
VALUES ('ROLE_USER', 'User1');

/*-- Voeg boekingen (Booking) toe met geldige treatment_id-waarden
-- Verwijs naar geldige treatment-ID's (1 en 2) in de "treatments" -tabel
INSERT INTO bookings (id, date, total_amount, booking_status, treatment_id, customer_id, user_id)
VALUES (1, '2023-09-30', 90, 'NEW', 1, 1, 1);
INSERT INTO bookings (id, date, total_amount, booking_status, treatment_id, customer_id, user_id)
VALUES (2, '2023-10-15', 120, 'CONFIRMED', 2, 2, 2);

-- Voeg beschikbare tijden (Calendar) toe
INSERT INTO calendars (available_time, date, start_time, end_time)
VALUES ('2023-10-10', '10:00:00', '11:00:00'),
       ('2021-10-11', '11:30:00', '12:30:00');

-- Voeg klant (Customer) toe
INSERT INTO customers (first_name, last_name, email, phone_number)
VALUES ('Maria', 'Cruz', 'user1@test.nl', '1234567890');

-- Voeg factuur (Invoice) toe
INSERT INTO invoices (amount, invoicedate, booking_id)
VALUES (100.00, '2023-09-30', 1);
*/
-- Zorg ervoor dat geldige behandelingen (Treatment) bestaan in de "treatments" -tabel
-- Voeg behandelingen toe met geldige ID's die kunnen worden gerefereerd in de "bookings" -tabel
INSERT INTO treatments (id, name, type, description, duration, price, quantity)
VALUES (1, 'Kobido Facial Massage', 'FACIAL_TREATMENT', 'Kobido is a traditional Japanese facial massage that utilizes gentle, rhythmic hand movements and acupressure points to improve blood circulation and rejuvenate the skin. It is known for its relaxing and invigorating effects, helping to reduce tension and promote a radiant complexion.', 60, 100, 1),
       (2, 'Carboxy Treatment', 'FACIAL_TREATMENT', 'A carboxy facial treatment utilizes carbon dioxide gas to boost skin circulation, enhancing collagen production for improved skin texture and tone, offering rejuvenating and renewing benefits.', 45, 80, 1),
       (3, 'Hydrafacial Treatment', 'FACIAL_TREATMENT', 'The hydrafacial is a five-step facial treatment that cleanses, hydrates, and rejuvenates the skin, providing a dewy glow by extracting debris, moisturizing, and exfoliating with a suction device.', 60, 110, 1),
       (4, 'LED Light Therapy', 'FACIAL_TREATMENT', 'LED light therapy rejuvenates skin with targeted light wavelengths, effectively addressing various concerns without downtime or discomfort.', 30, 45, 1),
       (5, 'Carboxy Treatment', 'BODY_TREATMENT', 'A carboxy body treatment utilizes carbon dioxide gas to boost skin circulation, enhancing collagen production for improved skin texture and tone, offering rejuvenating and renewing benefits.', 45, 100, 1),
       (6, 'Massage Treatment', 'BODY_TREATMENT', 'A carboxy facial treatment utilizes carbon dioxide gas to boost skin circulation, enhancing collagen production for improved skin texture and tone, offering rejuvenating and renewing benefits.', 60, 85, 1),
       (7, 'Detox Treatment', 'BODY_TREATMENT', 'A carboxy facial treatment utilizes carbon dioxide gas to boost skin circulation, enhancing collagen production for improved skin texture and tone, offering rejuvenating and renewing benefits.', 60, 125, 1),
       (8, 'Body mask Treatment', 'BODY_TREATMENT', 'A carboxy facial treatment utilizes carbon dioxide gas to boost skin circulation, enhancing collagen production for improved skin texture and tone, offering rejuvenating and renewing benefits.', 45, 115, 1);
