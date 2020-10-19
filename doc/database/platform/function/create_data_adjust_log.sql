-- DROP FUNCTION IF EXISTS create_data_adjust_log CASCADE;

CREATE OR REPLACE FUNCTION create_data_adjust_log(table_name varchar, start_time varchar(26), end_time varchar(26))
RETURNS INTEGER
LANGUAGE plpgsql
AS $function$
BEGIN
    EXECUTE FORMAT('CREATE TABLE data_adjust_log_%s PARTITION OF data_adjust_log FOR VALUES FROM (''%s'') TO (''%s'')', table_name, start_time, end_time);
    EXECUTE FORMAT('ALTER TABLE ONLY data_adjust_log_%s ADD CONSTRAINT data_adjust_log_%s_pk PRIMARY KEY (data_adjust_log_id)', table_name, table_name);

    RETURN 0;

EXCEPTION
    WHEN undefined_table THEN
        raise notice '%, %', SQLSTATE, SQLERRM;
        RETURN -1;

END;
$function$;
