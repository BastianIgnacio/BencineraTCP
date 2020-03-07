--
-- PostgreSQL database dump
--

-- Dumped from database version 11.3
-- Dumped by pg_dump version 11.3

-- Started on 2020-03-07 16:44:45 -03

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 197 (class 1259 OID 42455)
-- Name: Surtidor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Surtidor" (
    "idSurtidor" text NOT NULL,
    precio93 bigint,
    precio95 bigint,
    precio97 bigint,
    "precioDiesel" bigint,
    "precioKerosene" bigint
);


ALTER TABLE public."Surtidor" OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 42463)
-- Name: Trabajador; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Trabajador" (
    rut text NOT NULL,
    nombre text,
    apellido text,
    cargo text
);


ALTER TABLE public."Trabajador" OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 42447)
-- Name: Transaccion; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Transaccion" (
    "idTransaccion" text NOT NULL,
    fecha timestamp without time zone,
    "tipoCombustible" text,
    litros bigint,
    "precioPorLitro" bigint,
    total bigint,
    "refSurtidor" text,
    "refTrabajador" text
);


ALTER TABLE public."Transaccion" OWNER TO postgres;

--
-- TOC entry 3141 (class 0 OID 42455)
-- Dependencies: 197
-- Data for Name: Surtidor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Surtidor" ("idSurtidor", precio93, precio95, precio97, "precioDiesel", "precioKerosene") FROM stdin;
\.


--
-- TOC entry 3142 (class 0 OID 42463)
-- Dependencies: 198
-- Data for Name: Trabajador; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Trabajador" (rut, nombre, apellido, cargo) FROM stdin;
\.


--
-- TOC entry 3140 (class 0 OID 42447)
-- Dependencies: 196
-- Data for Name: Transaccion; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Transaccion" ("idTransaccion", fecha, "tipoCombustible", litros, "precioPorLitro", total, "refSurtidor", "refTrabajador") FROM stdin;
\.


--
-- TOC entry 3014 (class 2606 OID 42462)
-- Name: Surtidor Surtidor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Surtidor"
    ADD CONSTRAINT "Surtidor_pkey" PRIMARY KEY ("idSurtidor");


--
-- TOC entry 3016 (class 2606 OID 42470)
-- Name: Trabajador Trabajador_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Trabajador"
    ADD CONSTRAINT "Trabajador_pkey" PRIMARY KEY (rut);


--
-- TOC entry 3012 (class 2606 OID 42454)
-- Name: Transaccion Transaccion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Transaccion"
    ADD CONSTRAINT "Transaccion_pkey" PRIMARY KEY ("idTransaccion");


--
-- TOC entry 3017 (class 2606 OID 42471)
-- Name: Transaccion Transaccion_refSurtidor_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Transaccion"
    ADD CONSTRAINT "Transaccion_refSurtidor_fkey" FOREIGN KEY ("refSurtidor") REFERENCES public."Surtidor"("idSurtidor");


--
-- TOC entry 3018 (class 2606 OID 42476)
-- Name: Transaccion Transaccion_refTrabajador_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Transaccion"
    ADD CONSTRAINT "Transaccion_refTrabajador_fkey" FOREIGN KEY ("refTrabajador") REFERENCES public."Trabajador"(rut);


-- Completed on 2020-03-07 16:44:45 -03

--
-- PostgreSQL database dump complete
--

