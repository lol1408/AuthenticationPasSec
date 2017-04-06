CREATE TABLE public.resource_data (
  id BIGINT PRIMARY KEY NOT NULL DEFAULT nextval('resource_data_id_seq'::regclass),
  url CHARACTER VARYING(255),
  password CHARACTER VARYING(255),
  user_id BIGINT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES public.user_rest (user_id)
  MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);