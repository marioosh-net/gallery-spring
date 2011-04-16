--
-- PostgreSQL database dump
--

-- Started on 2011-03-25 14:28:39

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- TOC entry 1504 (class 1262 OID 14856654)
-- Name: linkownia_small; Type: DATABASE; Schema: -; Owner: linkownia
--

CREATE DATABASE linkownia_small WITH TEMPLATE = template0 ENCODING = 'UNICODE';


ALTER DATABASE linkownia_small OWNER TO linkownia;

\connect linkownia_small

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

--
-- TOC entry 17 (class 1255 OID 17230)
-- Dependencies: 4
-- Name: plpgsql_call_handler(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION plpgsql_call_handler() RETURNS language_handler
    LANGUAGE c
    AS '$libdir/plpgsql', 'plpgsql_call_handler';


ALTER FUNCTION public.plpgsql_call_handler() OWNER TO postgres;

--
-- TOC entry 18 (class 1255 OID 17231)
-- Dependencies: 4
-- Name: plpgsql_validator(oid); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION plpgsql_validator(oid) RETURNS void
    LANGUAGE c
    AS '$libdir/plpgsql', 'plpgsql_validator';


ALTER FUNCTION public.plpgsql_validator(oid) OWNER TO postgres;

--
-- TOC entry 265 (class 16402 OID 17232)
-- Dependencies: 17 18
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: public; Owner: postgres
--

CREATE TRUSTED PROCEDURAL LANGUAGE plpgsql HANDLER plpgsql_call_handler VALIDATOR plpgsql_validator;


ALTER PROCEDURAL LANGUAGE plpgsql OWNER TO postgres;

--
-- TOC entry 19 (class 1255 OID 17233)
-- Dependencies: 4
-- Name: database_size(name); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION database_size(name) RETURNS bigint
    LANGUAGE c STRICT
    AS '$libdir/dbsize', 'database_size';


ALTER FUNCTION public.database_size(name) OWNER TO postgres;

--
-- TOC entry 22 (class 1255 OID 17236)
-- Dependencies: 4
-- Name: pg_database_size(oid); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_database_size(oid) RETURNS bigint
    LANGUAGE c STRICT
    AS '$libdir/dbsize', 'pg_database_size';


ALTER FUNCTION public.pg_database_size(oid) OWNER TO postgres;

--
-- TOC entry 32 (class 1255 OID 17246)
-- Dependencies: 4
-- Name: pg_dir_ls(text, boolean); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_dir_ls(text, boolean) RETURNS SETOF text
    LANGUAGE c STRICT
    AS '$libdir/admin', 'pg_dir_ls';


ALTER FUNCTION public.pg_dir_ls(text, boolean) OWNER TO postgres;

--
-- TOC entry 26 (class 1255 OID 17240)
-- Dependencies: 4
-- Name: pg_file_length(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_length(text) RETURNS bigint
    LANGUAGE sql STRICT
    AS $_$SELECT len FROM pg_file_stat($1) AS s(len int8, c timestamp, a timestamp, m timestamp, i bool)$_$;


ALTER FUNCTION public.pg_file_length(text) OWNER TO postgres;

--
-- TOC entry 27 (class 1255 OID 17241)
-- Dependencies: 4
-- Name: pg_file_read(text, bigint, bigint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_read(text, bigint, bigint) RETURNS text
    LANGUAGE c STRICT
    AS '$libdir/admin', 'pg_file_read';


ALTER FUNCTION public.pg_file_read(text, bigint, bigint) OWNER TO postgres;

--
-- TOC entry 29 (class 1255 OID 17243)
-- Dependencies: 4
-- Name: pg_file_rename(text, text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_rename(text, text, text) RETURNS boolean
    LANGUAGE c
    AS '$libdir/admin', 'pg_file_rename';


ALTER FUNCTION public.pg_file_rename(text, text, text) OWNER TO postgres;

--
-- TOC entry 31 (class 1255 OID 17245)
-- Dependencies: 4
-- Name: pg_file_rename(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_rename(text, text) RETURNS boolean
    LANGUAGE sql STRICT
    AS $_$SELECT pg_file_rename($1, $2, NULL); $_$;


ALTER FUNCTION public.pg_file_rename(text, text) OWNER TO postgres;

--
-- TOC entry 25 (class 1255 OID 17239)
-- Dependencies: 4
-- Name: pg_file_stat(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_stat(text) RETURNS record
    LANGUAGE c STRICT
    AS '$libdir/admin', 'pg_file_stat';


ALTER FUNCTION public.pg_file_stat(text) OWNER TO postgres;

--
-- TOC entry 30 (class 1255 OID 17244)
-- Dependencies: 4
-- Name: pg_file_unlink(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_unlink(text) RETURNS boolean
    LANGUAGE c STRICT
    AS '$libdir/admin', 'pg_file_unlink';


ALTER FUNCTION public.pg_file_unlink(text) OWNER TO postgres;

--
-- TOC entry 28 (class 1255 OID 17242)
-- Dependencies: 4
-- Name: pg_file_write(text, text, boolean); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_file_write(text, text, boolean) RETURNS bigint
    LANGUAGE c STRICT
    AS '$libdir/admin', 'pg_file_write';


ALTER FUNCTION public.pg_file_write(text, text, boolean) OWNER TO postgres;

--
-- TOC entry 35 (class 1255 OID 17249)
-- Dependencies: 4
-- Name: pg_logdir_ls(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_logdir_ls() RETURNS SETOF record
    LANGUAGE c STRICT
    AS '$libdir/admin', 'pg_logdir_ls';


ALTER FUNCTION public.pg_logdir_ls() OWNER TO postgres;

--
-- TOC entry 34 (class 1255 OID 17248)
-- Dependencies: 4
-- Name: pg_postmaster_starttime(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_postmaster_starttime() RETURNS timestamp without time zone
    LANGUAGE c STRICT
    AS '$libdir/admin', 'pg_postmaster_starttime';


ALTER FUNCTION public.pg_postmaster_starttime() OWNER TO postgres;

--
-- TOC entry 23 (class 1255 OID 17237)
-- Dependencies: 4
-- Name: pg_relation_size(oid); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_relation_size(oid) RETURNS bigint
    LANGUAGE c STRICT
    AS '$libdir/dbsize', 'pg_relation_size';


ALTER FUNCTION public.pg_relation_size(oid) OWNER TO postgres;

--
-- TOC entry 33 (class 1255 OID 17247)
-- Dependencies: 4
-- Name: pg_reload_conf(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_reload_conf() RETURNS integer
    LANGUAGE c STABLE STRICT
    AS '$libdir/admin', 'pg_reload_conf';


ALTER FUNCTION public.pg_reload_conf() OWNER TO postgres;

--
-- TOC entry 24 (class 1255 OID 17238)
-- Dependencies: 4
-- Name: pg_size_pretty(bigint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_size_pretty(bigint) RETURNS text
    LANGUAGE c STRICT
    AS '$libdir/dbsize', 'pg_size_pretty';


ALTER FUNCTION public.pg_size_pretty(bigint) OWNER TO postgres;

--
-- TOC entry 21 (class 1255 OID 17235)
-- Dependencies: 4
-- Name: pg_tablespace_size(oid); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION pg_tablespace_size(oid) RETURNS bigint
    LANGUAGE c STRICT
    AS '$libdir/dbsize', 'pg_tablespace_size';


ALTER FUNCTION public.pg_tablespace_size(oid) OWNER TO postgres;

--
-- TOC entry 20 (class 1255 OID 17234)
-- Dependencies: 4
-- Name: relation_size(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION relation_size(text) RETURNS bigint
    LANGUAGE c STRICT
    AS '$libdir/dbsize', 'relation_size';


ALTER FUNCTION public.relation_size(text) OWNER TO postgres;

--
-- TOC entry 1178 (class 1259 OID 17250)
-- Dependencies: 1244 4
-- Name: pg_logdir_ls; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW pg_logdir_ls AS
    SELECT a.filetime, a.filename FROM pg_logdir_ls() a(filetime timestamp without time zone, filename text);


ALTER TABLE public.pg_logdir_ls OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 1179 (class 1259 OID 14856655)
-- Dependencies: 1497 1498 1499 4
-- Name: tlink; Type: TABLE; Schema: public; Owner: linkownia; Tablespace: 
--

CREATE TABLE tlink (
    id integer DEFAULT nextval('public.tlink_id_seq'::text) NOT NULL,
    address character varying(255) NOT NULL,
    name character varying(255),
    description text,
    ldate timestamp without time zone,
    date_mod timestamp without time zone,
    clicks integer DEFAULT 0,
    id_group smallint,
    shortcut character varying(20),
    icon bytea,
    pub boolean DEFAULT true,
    id_user smallint
);


ALTER TABLE public.tlink OWNER TO linkownia;

--
-- TOC entry 1181 (class 1259 OID 14856659)
-- Dependencies: 1179 4
-- Name: tlink_id_seq; Type: SEQUENCE; Schema: public; Owner: linkownia
--

CREATE SEQUENCE tlink_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.tlink_id_seq OWNER TO linkownia;

--
-- TOC entry 1507 (class 0 OID 0)
-- Dependencies: 1181
-- Name: tlink_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: linkownia
--

ALTER SEQUENCE tlink_id_seq OWNED BY tlink.id;


--
-- TOC entry 1180 (class 1259 OID 14856657)
-- Dependencies: 1500 1501 4
-- Name: tsearch; Type: TABLE; Schema: public; Owner: linkownia; Tablespace: 
--

CREATE TABLE tsearch (
    id integer DEFAULT nextval('public.tsearch_id_seq'::text) NOT NULL,
    phrase character varying(255),
    counter integer DEFAULT 1,
    date timestamp without time zone
);


ALTER TABLE public.tsearch OWNER TO linkownia;

--
-- TOC entry 1182 (class 1259 OID 14856685)
-- Dependencies: 1180 4
-- Name: tsearch_id_seq; Type: SEQUENCE; Schema: public; Owner: linkownia
--

CREATE SEQUENCE tsearch_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.tsearch_id_seq OWNER TO linkownia;

--
-- TOC entry 1508 (class 0 OID 0)
-- Dependencies: 1182
-- Name: tsearch_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: linkownia
--

ALTER SEQUENCE tsearch_id_seq OWNED BY tsearch.id;


--
-- TOC entry 1506 (class 0 OID 0)
-- Dependencies: 4
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2011-03-25 14:28:39

--
-- PostgreSQL database dump complete
--

