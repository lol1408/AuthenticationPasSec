CREATE TABLE public.user_rest (
  user_id BIGINT PRIMARY KEY NOT NULL DEFAULT nextval('user_rest_user_id_seq'::regclass),
  active BOOLEAN,
  url CHARACTER VARYING(30) NOT NULL,
  mail CHARACTER VARYING(150) NOT NULL,
  password CHARACTER VARYING(100) NOT NULL
);
CREATE UNIQUE INDEX uk_p6ej96xif1vrmw5sc4ggw7n8b ON user_rest USING BTREE (url);
CREATE UNIQUE INDEX uk_4ybg8v5d72d42yai5cakgbmhr ON user_rest USING BTREE (mail);