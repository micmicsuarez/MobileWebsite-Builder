
CREATE TABLE address (
	id bigint NOT NULL,
	version bigint NOT NULL,
	address_line_one character varying(255) NOT NULL,
	address_line_three character varying(255),
	address_line_two character varying(255),
	city character varying(255) NOT NULL,
	country character varying(255) NOT NULL
);

CREATE SEQUENCE address_sequence
	START WITH 1
	INCREMENT BY 1
	NO MINVALUE
	NO MAXVALUE
	CACHE 1;

CREATE TABLE authentication_token (
	id bigint NOT NULL,
	date_created timestamp without time zone NOT NULL,
	last_used timestamp without time zone,
	status character varying(255) NOT NULL,
	token character varying(255) NOT NULL,
	user_id uuid NOT NULL
);

CREATE SEQUENCE authentication_token_sequence
	START WITH 1
	INCREMENT BY 1
	NO MINVALUE
	NO MAXVALUE
	CACHE 1;

CREATE TABLE role (
	id bigint NOT NULL,
	version bigint NOT NULL,
	authority character varying(255) NOT NULL
);

CREATE SEQUENCE role_sequence
	START WITH 1
	INCREMENT BY 1
	NO MINVALUE
	NO MAXVALUE
	CACHE 1;

CREATE TABLE user_info (
	id bigint NOT NULL,
	version bigint NOT NULL,
	address_id bigint,
	email_address character varying(255),
	first_name character varying(255),
	last_name character varying(255),
	middle_name character varying(255)
);

CREATE SEQUENCE user_info_sequence
	START WITH 1
	INCREMENT BY 1
	NO MINVALUE
	NO MAXVALUE
	CACHE 1;

CREATE TABLE user_role (
	user_id uuid NOT NULL,
	role_id bigint NOT NULL
);

CREATE TABLE users (
	id uuid NOT NULL,
	version bigint NOT NULL,
	account_expired boolean NOT NULL,
	account_locked boolean NOT NULL,
	enabled boolean NOT NULL,
	password character varying(255),
	password_expired boolean NOT NULL,
	status character varying(255) NOT NULL,
	user_info_id bigint,
	username character varying(255) NOT NULL
);

ALTER TABLE ONLY address
	ADD CONSTRAINT address_pkey PRIMARY KEY (id);
ALTER TABLE ONLY authentication_token
	ADD CONSTRAINT authentication_token_pkey PRIMARY KEY (id);
ALTER TABLE ONLY role
	ADD CONSTRAINT role_pkey PRIMARY KEY (id);
ALTER TABLE ONLY authentication_token
	ADD CONSTRAINT uk_313pxbtcmtspm4y8rh9qgp2sy UNIQUE (token);
ALTER TABLE ONLY users
	ADD CONSTRAINT uk_r43af9ap4edm43mmtq01oddj6 UNIQUE (username);
ALTER TABLE ONLY user_info
	ADD CONSTRAINT user_info_pkey PRIMARY KEY (id);
ALTER TABLE ONLY user_role
	ADD CONSTRAINT user_role_pkey PRIMARY KEY (user_id, role_id);
ALTER TABLE ONLY users
	ADD CONSTRAINT users_pkey PRIMARY KEY (id);
ALTER TABLE ONLY user_info
	ADD CONSTRAINT fk_25i7k773g58yro3pwdscmq2ql FOREIGN KEY (address_id) REFERENCES address(id);
ALTER TABLE ONLY users
	ADD CONSTRAINT fk_65t6bc8nlb8lpnk86aimnl7pd FOREIGN KEY (user_info_id) REFERENCES user_info(id);
ALTER TABLE ONLY user_role
	ADD CONSTRAINT fk_apcc8lxk2xnug8377fatvbn04 FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE ONLY authentication_token
	ADD CONSTRAINT fk_gb0vks7d4k43iv8w0p8f86t10 FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE ONLY user_role
	ADD CONSTRAINT fk_it77eq964jhfqtu54081ebtio FOREIGN KEY (role_id) REFERENCES role(id);
