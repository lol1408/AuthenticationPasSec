CREATE TABLE public.session (
  id BIGINT PRIMARY KEY NOT NULL DEFAULT nextval('session_id_seq'::regclass),
  date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  including BOOLEAN,
  token INTEGER NOT NULL,
  user_id BIGINT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES public.user_rest (user_id)
  MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);