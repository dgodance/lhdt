-- alter table converter_job drop constraint converter_job_fk_upload_data_id;
-- alter table converter_job_file drop constraint converter_job_file_fk_converter_job_id;
--
-- alter table data_attribute_file_info drop constraint data_attribute_file_info_fk_data_id;
-- alter table data_object_attribute_file_info drop constraint data_object_attribute_file_info_fk_data_id;
--
-- alter table data_attribute drop constraint data_attribute_fk_data_id;
-- alter table data_object_attribute drop constraint data_object_attribute_fk_data_id;
--
-- alter table data_smart_tiling_file_info drop constraint data_smart_tiling_file_info_fk_data_group_id;
-- alter table data_smart_tiling_file_parse_log drop constraint data_smart_tiling_file_parse_log_fk_data_smart_tiling_file_info;
--
-- alter table data_info_origin drop constraint data_info_origin_fk_data_id;
-- alter table data_group drop constraint data_group_fk_user_id;
-- alter table data_info drop constraint data_info_fk_user_id;
--
-- alter table upload_data drop constraint upload_data_fk_data_group_id;
-- alter table upload_data_file drop constraint upload_data_file_fk_upload_data_id;
