
alter sequence hibernate_sequence owner to admin;

CREATE TABLE "role" (
                        "id" serial NOT NULL,
                        "role" TEXT NOT NULL,
                        CONSTRAINT "role_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
    );



CREATE TABLE "users" (
                         "id" serial NOT NULL,
                         "login" TEXT NOT NULL,
                         "password" TEXT NOT NULL,
                         "firstname" TEXT NOT NULL,
                         "lastname" TEXT NOT NULL,
                         "active" integer NOT NULL,
                         "role_id" integer NOT NULL,
                         CONSTRAINT "users_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
    );



CREATE TABLE "messages" (
                            "id" serial NOT NULL,
                            "text" TEXT NOT NULL,
                            "date_create" DATE NOT NULL,
                            "autor_id" integer NOT NULL,
                            CONSTRAINT "messages_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
    );




ALTER TABLE "users" ADD CONSTRAINT "users_fk0" FOREIGN KEY ("role_id") REFERENCES "role"("id");

ALTER TABLE "messages" ADD CONSTRAINT "messages_fk0" FOREIGN KEY ("autor_id") REFERENCES "users"("id");

create table persistent_logins
(
    username varchar(64) not null,
    series varchar(64) not null
    constraint persistent_logins_pkey
    primary key,
    token varchar(64) not null,
    last_used timestamp not null
);

alter table persistent_logins owner to postgres;
alter table role owner to postgres;
alter table users owner to postgres;

INSERT INTO public.role (role, id) VALUES ('Администратор', 1);
INSERT INTO public.role (role, id) VALUES ('Пользователь', 2);
INSERT INTO public.role (role, id) VALUES ('Time', 3);
-- create sequence hibernate_sequence;