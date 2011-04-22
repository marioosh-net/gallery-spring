--
-- PostgreSQL database dump
--

SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'Standard public schema';


SET search_path = public, pg_catalog;

--
-- Name: main_seq; Type: SEQUENCE; Schema: public; Owner: gallery
--

CREATE SEQUENCE main_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.main_seq OWNER TO gallery;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: talbum; Type: TABLE; Schema: public; Owner: gallery; Tablespace: 
--

CREATE TABLE talbum (
    name character varying(255),
    mod_date timestamp with time zone,
    id bigint DEFAULT nextval('main_seq'::regclass) NOT NULL,
    path character varying(255),
    visibility integer,
    hash character(32)
);


ALTER TABLE public.talbum OWNER TO gallery;

--
-- Name: talbum_id_seq; Type: SEQUENCE; Schema: public; Owner: gallery
--

CREATE SEQUENCE talbum_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.talbum_id_seq OWNER TO gallery;

--
-- Name: talbum_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gallery
--

ALTER SEQUENCE talbum_id_seq OWNED BY talbum.id;


--
-- Name: tphoto; Type: TABLE; Schema: public; Owner: gallery; Tablespace: 
--

CREATE TABLE tphoto (
    id bigint NOT NULL,
    name character varying(255),
    album_id bigint NOT NULL,
    mod_date timestamp with time zone,
    img bytea,
    thumb bytea,
    file_path character varying(255),
    visibility integer,
    description character varying(1024),
    hash character(32)
);


ALTER TABLE public.tphoto OWNER TO gallery;

--
-- Name: tphoto_albumId_seq; Type: SEQUENCE; Schema: public; Owner: gallery
--

CREATE SEQUENCE "tphoto_albumId_seq"
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public."tphoto_albumId_seq" OWNER TO gallery;

--
-- Name: tphoto_albumId_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gallery
--

ALTER SEQUENCE "tphoto_albumId_seq" OWNED BY tphoto.album_id;


--
-- Name: tphoto_id_seq; Type: SEQUENCE; Schema: public; Owner: gallery
--

CREATE SEQUENCE tphoto_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.tphoto_id_seq OWNER TO gallery;

--
-- Name: tphoto_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: gallery
--

ALTER SEQUENCE tphoto_id_seq OWNED BY tphoto.id;


--
-- Name: tsearch; Type: TABLE; Schema: public; Owner: gallery; Tablespace: 
--

CREATE TABLE tsearch (
    id bigint DEFAULT nextval('main_seq'::regclass) NOT NULL,
    phrase character varying(255),
    mod_date timestamp with time zone,
    counter integer
);


ALTER TABLE public.tsearch OWNER TO gallery;

--
-- Name: id; Type: DEFAULT; Schema: public; Owner: gallery
--

ALTER TABLE tphoto ALTER COLUMN id SET DEFAULT nextval('tphoto_id_seq'::regclass);


--
-- Name: album_id; Type: DEFAULT; Schema: public; Owner: gallery
--

ALTER TABLE tphoto ALTER COLUMN album_id SET DEFAULT nextval('"tphoto_albumId_seq"'::regclass);


--
-- Name: talbum_id_primary; Type: CONSTRAINT; Schema: public; Owner: gallery; Tablespace: 
--

ALTER TABLE ONLY talbum
    ADD CONSTRAINT talbum_id_primary PRIMARY KEY (id);


--
-- Name: tphoto_id_primary; Type: CONSTRAINT; Schema: public; Owner: gallery; Tablespace: 
--

ALTER TABLE ONLY tphoto
    ADD CONSTRAINT tphoto_id_primary PRIMARY KEY (id);


--
-- Name: tsearch_id; Type: CONSTRAINT; Schema: public; Owner: gallery; Tablespace: 
--

ALTER TABLE ONLY tsearch
    ADD CONSTRAINT tsearch_id PRIMARY KEY (id);


--
-- Name: tphoto_albumId_foreign; Type: FK CONSTRAINT; Schema: public; Owner: gallery
--

ALTER TABLE ONLY tphoto
    ADD CONSTRAINT "tphoto_albumId_foreign" FOREIGN KEY (album_id) REFERENCES talbum(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

