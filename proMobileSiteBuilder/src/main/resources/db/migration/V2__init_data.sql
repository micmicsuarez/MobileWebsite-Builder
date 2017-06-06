INSERT INTO  role(id, version, authority) 
	VALUES(nextval('role_sequence'), 0, 'ROLE_ADMIN');
INSERT INTO  role(id, version, authority) 
	VALUES(nextval('role_sequence'), 0, 'ROLE_USER');

INSERT INTO address(id, version, address_line_one, address_line_three, address_line_two, city, country) 
	VALUES(nextval('address_sequence'), 0, 'Brgy. Cambaro', null, null, 'Mandaue', 'PH');

INSERT INTO user_info(id, version, address_id, email_address, first_name, last_name, middle_name)
	VALUES(nextval('user_info_sequence'), 0, currval('address_sequence'), 'michaelsuarez@gmail.com', 'Michael', 'Suarez', 'MiddleName');

INSERT INTO  users(id, version, account_expired, account_locked, enabled, password, password_expired, status, user_info_id, username)
	VALUES('39645d59-e671-43d9-82e3-e92c7e5d5d8b', 0, 'false', 'false', 'true', 'password', 'false', 'ACTIVE', currval('user_info_sequence'), 'michael');

INSERT INTO  user_role(user_id, role_id)
	VALUES('39645d59-e671-43d9-82e3-e92c7e5d5d8b', currval('role_sequence'));

