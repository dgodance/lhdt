drop table if exists access_log cascade;
drop table if exists access_log_2020 cascade;
drop table if exists access_log_2021 cascade;
drop table if exists access_log_2022 cascade;
drop table if exists access_log_2023 cascade;
drop table if exists access_log_2024 cascade;
drop table if exists access_log_2025 cascade;
drop table if exists access_log_2026 cascade;
drop table if exists access_log_2027 cascade;
drop table if exists access_log_2028 cascade;
drop table if exists access_log_2029 cascade;
drop table if exists access_log_2030 cascade;
drop table if exists access_log_2031 cascade;
drop table if exists access_log_2032 cascade;
drop table if exists access_log_2033 cascade;
drop table if exists access_log_2034 cascade;
drop table if exists access_log_2035 cascade;
drop table if exists access_log_2036 cascade;
drop table if exists access_log_2037 cascade;
drop table if exists access_log_2038 cascade;
drop table if exists access_log_2039 cascade;
drop table if exists access_log_2040 cascade;
commit;


-- 관리자 페이지 사용 이력
create table access_log(
	access_log_id				bigint,
	user_id						varchar(32)	 		not null,
	user_name					varchar(64),
	client_ip					varchar(45)			not null,
	request_uri					varchar(256)		not null,
	parameters					varchar(1000),
	user_agent					varchar(256),
	referer						varchar(256),
	year						char(4)				default to_char(now(), 'YYYY'),
	month						varchar(2)			default to_char(now(), 'MM'),
	day							varchar(2)			default to_char(now(), 'DD'),
	year_week					varchar(2)			default to_char(now(), 'WW'),
	week						varchar(2)			default to_char(now(), 'W'),
	hour						varchar(2)			default to_char(now(), 'HH24'),
	minute						varchar(2)			default to_char(now(), 'MI'),
	insert_date					timestamp with time zone			default now()
) partition by range(insert_date);

comment on table access_log is '관리자 페이지 사용 이력';
comment on column access_log.access_log_id is '고유번호';
comment on column access_log.user_id is '사용자 아이디';
comment on column access_log.user_name is '사용자 이름';
comment on column access_log.client_ip is '요청 IP';
comment on column access_log.request_uri is 'URI';
comment on column access_log.parameters is '요청 Paramter';
comment on column access_log.user_agent is 'User-Agent';
comment on column access_log.referer is 'Referer';
comment on column access_log.year is '년';
comment on column access_log.month is '월';
comment on column access_log.day is '일';
comment on column access_log.year_week is '일년중 몇주';
comment on column access_log.week is '이번달 몇주';
comment on column access_log.hour is '시간';
comment on column access_log.minute is '분';
comment on column access_log.insert_date is '등록일';


create table access_log_2020 partition of access_log for values from ('2020-01-01 00:00:00.000000') to ('2021-01-01 00:00:00.000000');
create table access_log_2021 partition of access_log for values from ('2021-01-01 00:00:00.000000') to ('2022-01-01 00:00:00.000000');
create table access_log_2022 partition of access_log for values from ('2022-01-01 00:00:00.000000') to ('2023-01-01 00:00:00.000000');
create table access_log_2023 partition of access_log for values from ('2023-01-01 00:00:00.000000') to ('2024-01-01 00:00:00.000000');
create table access_log_2024 partition of access_log for values from ('2024-01-01 00:00:00.000000') to ('2025-01-01 00:00:00.000000');
create table access_log_2025 partition of access_log for values from ('2025-01-01 00:00:00.000000') to ('2026-01-01 00:00:00.000000');
create table access_log_2026 partition of access_log for values from ('2026-01-01 00:00:00.000000') to ('2027-01-01 00:00:00.000000');
create table access_log_2027 partition of access_log for values from ('2027-01-01 00:00:00.000000') to ('2028-01-01 00:00:00.000000');
create table access_log_2028 partition of access_log for values from ('2028-01-01 00:00:00.000000') to ('2029-01-01 00:00:00.000000');
create table access_log_2029 partition of access_log for values from ('2029-01-01 00:00:00.000000') to ('2030-01-01 00:00:00.000000');
create table access_log_2030 partition of access_log for values from ('2030-01-01 00:00:00.000000') to ('2031-01-01 00:00:00.000000');
create table access_log_2031 partition of access_log for values from ('2031-01-01 00:00:00.000000') to ('2032-01-01 00:00:00.000000');
create table access_log_2032 partition of access_log for values from ('2032-01-01 00:00:00.000000') to ('2033-01-01 00:00:00.000000');
create table access_log_2033 partition of access_log for values from ('2033-01-01 00:00:00.000000') to ('2034-01-01 00:00:00.000000');
create table access_log_2034 partition of access_log for values from ('2034-01-01 00:00:00.000000') to ('2035-01-01 00:00:00.000000');
create table access_log_2035 partition of access_log for values from ('2035-01-01 00:00:00.000000') to ('2036-01-01 00:00:00.000000');
create table access_log_2036 partition of access_log for values from ('2036-01-01 00:00:00.000000') to ('2037-01-01 00:00:00.000000');
create table access_log_2037 partition of access_log for values from ('2037-01-01 00:00:00.000000') to ('2038-01-01 00:00:00.000000');
create table access_log_2038 partition of access_log for values from ('2038-01-01 00:00:00.000000') to ('2039-01-01 00:00:00.000000');
create table access_log_2039 partition of access_log for values from ('2039-01-01 00:00:00.000000') to ('2040-01-01 00:00:00.000000');
create table access_log_2040 partition of access_log for values from ('2040-01-01 00:00:00.000000') to ('2041-01-01 00:00:00.000000');

-- FK, Index 는 별도 파일로 분리. 맨 마지막에 작업 예정
drop table if exists converter_job cascade;
drop table if exists converter_job_file cascade;

-- 파일 변환 job
create table converter_job(
	converter_job_id				bigint,
	upload_data_id					bigint,
	data_group_target				varchar(5)							default 'user',
	user_id							varchar(32),
	title							varchar(256)						not null,
	converter_template				varchar(30)							default 'basic',
	usf								numeric(13,5),
	y_axis_up						char(1)								default 'N',
	file_count						integer								default 0,
	status							varchar(20)							default 'ready',
	error_code						varchar(4000),
	year							char(4)								default to_char(now(), 'yyyy'),
	month							varchar(2)							default to_char(now(), 'MM'),
	day								varchar(2)							default to_char(now(), 'DD'),
	year_week						varchar(2)							default to_char(now(), 'WW'),
	week							varchar(2)							default to_char(now(), 'W'),
	hour							varchar(2)							default to_char(now(), 'HH24'),
	minute							varchar(2)							default to_char(now(), 'MI'),
	update_date						timestamp with time zone,
	insert_date						timestamp with time zone			default now(),
	constraint converter_job_pk 	primary key (converter_job_id)
);

comment on table converter_job is '파일 변환 job';
comment on column converter_job.converter_job_id is '고유번호';
comment on column converter_job.upload_data_id is '업로드 데이터 고유번호';
comment on column converter_job.data_group_target is '[중복] admin : 관리자용 데이터 그룹, user : 일반 사용자용 데이터 그룹';
comment on column converter_job.title is '제목';
comment on column converter_job.user_id is '사용자 아이디';
comment on column converter_job.converter_template is '변환 유형. basic : 기본, building : 빌딩, extra-big-building : 초대형 빌딩, point-cloud : point cloud 데이터';
comment on column converter_job.usf is 'unit scale factor. 설계 파일의 1이 의미하는 단위. 기본 1 = 0.01m';
comment on column converter_job.y_axis_up is '높이방향. y축이 건물의 천장을 향하는 경우 Y. default = N';
comment on column converter_job.file_count is '대상 file 개수';
comment on column converter_job.status is '상태. ready : 준비, success : 성공, waiting : 승인대기, fail : 실패';
comment on column converter_job.error_code is '에러 코드';
comment on column converter_job.year is '년';
comment on column converter_job.month is '월';
comment on column converter_job.day is '일';
comment on column converter_job.year_week is '일년중 몇주';
comment on column converter_job.week is '이번달 몇주';
comment on column converter_job.hour is '시간';
comment on column converter_job.minute is '분';
comment on column converter_job.update_date is '수정일';
comment on column converter_job.insert_date is '등록일';


-- 사용자 f4d 변환 이력. Job과 파일의 관계를 1 : N 으로 설계 했으나 지금은 Converter가 1 : 1 만 지원해서 필요 없는 테이블
create table converter_job_file(
	converter_job_file_id				bigint,
	converter_job_id					bigint,
	upload_data_id						bigint,
	upload_data_file_id					bigint,
	data_group_id						int,
	user_id								varchar(32),
	status								varchar(20)							default 'ready',
	error_code							varchar(4000),
	year								char(4)								default to_char(now(), 'yyyy'),
	month								varchar(2)							default to_char(now(), 'MM'),
	day									varchar(2)							default to_char(now(), 'DD'),
	year_week							varchar(2)							default to_char(now(), 'WW'),
	week								varchar(2)							default to_char(now(), 'W'),
	hour								varchar(2)							default to_char(now(), 'HH24'),
	minute								varchar(2)							default to_char(now(), 'MI'),
	insert_date							timestamp with time zone			default now(),
	constraint converter_job_file_pk 	primary key (converter_job_file_id)
);

comment on table converter_job_file is '변환 대상 파일 정보';
comment on column converter_job_file.converter_job_file_id is '고유번호';
comment on column converter_job_file.converter_job_id is '파일 변환 job 고유번호';
comment on column converter_job_file.upload_data_id is '데이터 업로드 고유번호';
comment on column converter_job_file.upload_data_file_id is '데이터 업로드 파일 고유번호';
comment on column converter_job_file.data_group_id is '데이터 그룹 고유번호(중복)';
comment on column converter_job_file.user_id is '사용자 아이디';
comment on column converter_job_file.status is '상태. ready : 준비, success : 성공, fail : 실패';
comment on column converter_job_file.error_code is '에러 코드';
comment on column converter_job_file.year is '년';
comment on column converter_job_file.month is '월';
comment on column converter_job_file.day is '일';
comment on column converter_job_file.year_week is '일년중 몇주';
comment on column converter_job_file.week is '이번달 몇주';
comment on column converter_job_file.hour is '시간';
comment on column converter_job_file.minute is '분';
comment on column converter_job_file.insert_date is '등록일';




-- FK, Index 는 별도 파일로 분리. 맨 마지막에 작업 예정
drop table if exists data_adjust_log cascade;
drop table if exists data_adjust_log_2020 cascade;
drop table if exists data_adjust_log_2021 cascade;
drop table if exists data_adjust_log_2022 cascade;
drop table if exists data_adjust_log_2023 cascade;
drop table if exists data_adjust_log_2024 cascade;
drop table if exists data_adjust_log_2025 cascade;
drop table if exists data_adjust_log_2026 cascade;
drop table if exists data_adjust_log_2027 cascade;
drop table if exists data_adjust_log_2028 cascade;
drop table if exists data_adjust_log_2029 cascade;
drop table if exists data_adjust_log_2030 cascade;
drop table if exists data_adjust_log_2031 cascade;
drop table if exists data_adjust_log_2032 cascade;
drop table if exists data_adjust_log_2033 cascade;
drop table if exists data_adjust_log_2034 cascade;
drop table if exists data_adjust_log_2035 cascade;
drop table if exists data_adjust_log_2036 cascade;
drop table if exists data_adjust_log_2037 cascade;
drop table if exists data_adjust_log_2038 cascade;
drop table if exists data_adjust_log_2039 cascade;
drop table if exists data_adjust_log_2040 cascade;

-- 데이터 위치 변경 요청 이력. 파티션 pruning 확인해야 함(지금 임시버전)
create table data_adjust_log(
	data_adjust_log_id				bigint,
	data_group_id					integer,
	data_id							bigint,
	user_id							varchar(32),
	update_user_id					varchar(32),
	location		 				GEOMETRY(POINT, 4326),
	altitude						numeric(13,7),
	heading							numeric(8,5),
	pitch							numeric(8,5),
	roll							numeric(8,5),
	before_location					GEOMETRY(POINT, 4326),
	before_altitude					numeric(13,7),
	before_heading					numeric(8,5),
	before_pitch					numeric(8,5),
	before_roll						numeric(8,5),
	status							varchar								default 'request',
	change_type						varchar(30),
	description						varchar(256),
	update_date						timestamp with time zone,
	insert_date						timestamp with time zone			default now()
) partition by range(insert_date);

comment on table data_adjust_log is 'Data geometry 변경 이력 정보';
comment on column data_adjust_log.data_adjust_log_id is '고유번호';
comment on column data_adjust_log.data_group_id is '데이터 그룹 고유번호, join 성능때문에 중복 허용';
comment on column data_adjust_log.data_id is 'Data 고유번호';
comment on column data_adjust_log.user_id is '사용자 아이디';
comment on column data_adjust_log.update_user_id is '수정 요청자 아이디';
comment on column data_adjust_log.location is '위치';
comment on column data_adjust_log.altitude is '높이';
comment on column data_adjust_log.heading is 'heading';
comment on column data_adjust_log.pitch is 'pitch';
comment on column data_adjust_log.roll is 'roll';
comment on column data_adjust_log.before_location is '변경전 위치';
comment on column data_adjust_log.before_altitude is '변경전 높이';
comment on column data_adjust_log.before_heading is '변경전 heading';
comment on column data_adjust_log.before_pitch is '변경전 pitch';
comment on column data_adjust_log.before_roll is '변경전 roll';
comment on column data_adjust_log.status is '상태. request : 요청, approval : 승인, reject : 기각, rollback : 원복';
comment on column data_adjust_log.change_type is '변경 유형';
comment on column data_adjust_log.description is '설명';
comment on column data_adjust_log.update_date is '수정일';
comment on column data_adjust_log.insert_date is '등록일';


create table data_adjust_log_2020 partition of data_adjust_log for values from ('2020-01-01 00:00:00.000000') to ('2021-01-01 00:00:00.000000');
create table data_adjust_log_2021 partition of data_adjust_log for values from ('2021-01-01 00:00:00.000000') to ('2022-01-01 00:00:00.000000');
create table data_adjust_log_2022 partition of data_adjust_log for values from ('2022-01-01 00:00:00.000000') to ('2023-01-01 00:00:00.000000');
create table data_adjust_log_2023 partition of data_adjust_log for values from ('2023-01-01 00:00:00.000000') to ('2024-01-01 00:00:00.000000');
create table data_adjust_log_2024 partition of data_adjust_log for values from ('2024-01-01 00:00:00.000000') to ('2025-01-01 00:00:00.000000');
create table data_adjust_log_2025 partition of data_adjust_log for values from ('2025-01-01 00:00:00.000000') to ('2026-01-01 00:00:00.000000');
create table data_adjust_log_2026 partition of data_adjust_log for values from ('2026-01-01 00:00:00.000000') to ('2027-01-01 00:00:00.000000');
create table data_adjust_log_2027 partition of data_adjust_log for values from ('2027-01-01 00:00:00.000000') to ('2028-01-01 00:00:00.000000');
create table data_adjust_log_2028 partition of data_adjust_log for values from ('2028-01-01 00:00:00.000000') to ('2029-01-01 00:00:00.000000');
create table data_adjust_log_2029 partition of data_adjust_log for values from ('2029-01-01 00:00:00.000000') to ('2030-01-01 00:00:00.000000');
create table data_adjust_log_2030 partition of data_adjust_log for values from ('2030-01-01 00:00:00.000000') to ('2031-01-01 00:00:00.000000');
create table data_adjust_log_2031 partition of data_adjust_log for values from ('2031-01-01 00:00:00.000000') to ('2032-01-01 00:00:00.000000');
create table data_adjust_log_2032 partition of data_adjust_log for values from ('2032-01-01 00:00:00.000000') to ('2033-01-01 00:00:00.000000');
create table data_adjust_log_2033 partition of data_adjust_log for values from ('2033-01-01 00:00:00.000000') to ('2034-01-01 00:00:00.000000');
create table data_adjust_log_2034 partition of data_adjust_log for values from ('2034-01-01 00:00:00.000000') to ('2035-01-01 00:00:00.000000');
create table data_adjust_log_2035 partition of data_adjust_log for values from ('2035-01-01 00:00:00.000000') to ('2036-01-01 00:00:00.000000');
create table data_adjust_log_2036 partition of data_adjust_log for values from ('2036-01-01 00:00:00.000000') to ('2037-01-01 00:00:00.000000');
create table data_adjust_log_2037 partition of data_adjust_log for values from ('2037-01-01 00:00:00.000000') to ('2038-01-01 00:00:00.000000');
create table data_adjust_log_2038 partition of data_adjust_log for values from ('2038-01-01 00:00:00.000000') to ('2039-01-01 00:00:00.000000');
create table data_adjust_log_2039 partition of data_adjust_log for values from ('2039-01-01 00:00:00.000000') to ('2040-01-01 00:00:00.000000');
create table data_adjust_log_2040 partition of data_adjust_log for values from ('2040-01-01 00:00:00.000000') to ('2041-01-01 00:00:00.000000');

-- FK, Index 는 별도 파일로 분리. 맨 마지막에 작업 예정
drop table if exists data_attribute cascade;
drop table if exists data_object_attribute cascade;


-- Data 기본정보
create table data_attribute (
	data_attribute_id			bigint,
	data_id						bigint,
	attributes					json,
	update_date					timestamp with time zone,
	insert_date					timestamp with time zone			default now(),
	constraint data_attribute_pk 	primary key(data_attribute_id)
);

comment on table data_attribute is 'Data 속성 정보';
comment on column data_attribute.data_attribute_id is '고유번호';
comment on column data_attribute.data_id is 'Data 고유번호';
comment on column data_attribute.attributes is '속성';
comment on column data_attribute.update_date is '수정일';
comment on column data_attribute.insert_date is '등록일';


create table data_object_attribute (
	data_object_attribute_id			bigint,
	data_id								bigint,
	object_id							varchar(256),
	attributes							json,
	update_date							timestamp with time zone,
	insert_date							timestamp with time zone			default now(),
	constraint data_object_attribute_pk 	primary key(data_object_attribute_id)
);

comment on table data_object_attribute is '데이터 Object 속성 정보';
comment on column data_object_attribute.data_object_attribute_id is '고유번호';
comment on column data_object_attribute.data_id is 'Data 고유번호';
comment on column data_object_attribute.object_id is '오브젝트 번호';
comment on column data_object_attribute.attributes is 'Object 속성';
comment on column data_object_attribute.update_date is '수정일';
comment on column data_object_attribute.insert_date is '등록일';
drop table if exists data_attribute_file_info cascade;
drop table if exists data_object_attribute_file_info cascade;

-- 데이터 속성 파일 관리
create table data_attribute_file_info(
	data_attribute_file_info_id				bigint,
	data_id									bigint,
	user_id									varchar(32)	 		not null,
	file_name								varchar(100)		not null,
	file_real_name							varchar(100)		not null,
	file_path								varchar(256)		not null,
	file_size								varchar(12)			not null,
	file_ext								varchar(10)			not null,
	total_count								bigint				default 0,
	parse_success_count						bigint				default 0,
	parse_error_count						bigint				default 0,
	insert_success_count					bigint				default 0,
	insert_error_count						bigint				default 0,
	insert_date								timestamp with time zone			default now(),
	constraint data_attribute_file_info_pk primary key (data_attribute_file_info_id)
);

comment on table data_attribute_file_info is '데이터 속성 파일 관리';
comment on column data_attribute_file_info.data_attribute_file_info_id is '고유번호';
comment on column data_attribute_file_info.data_id is '데이터 고유번호';
comment on column data_attribute_file_info.user_id is '사용자 아이디';
comment on column data_attribute_file_info.file_name is '파일 이름';
comment on column data_attribute_file_info.file_real_name is '파일 실제 이름';
comment on column data_attribute_file_info.file_path is '파일 경로';
comment on column data_attribute_file_info.file_size is '파일 사이즈';
comment on column data_attribute_file_info.file_ext is '파일 확장자';
comment on column data_attribute_file_info.total_count is '전체 데이터 건수';
comment on column data_attribute_file_info.parse_success_count is '파싱 성공 건수';
comment on column data_attribute_file_info.parse_error_count is '파싱 오류';
comment on column data_attribute_file_info.insert_success_count is 'SQL Insert 성공 건수';
comment on column data_attribute_file_info.insert_error_count is 'SQL Insert 실패 건수';
comment on column data_attribute_file_info.insert_date is '등록일';


-- 데이터 Object 속성 파일 관리
create table data_object_attribute_file_info (
	data_object_attribute_file_info_id		bigint,
	data_id									bigint,
	user_id									varchar(32)	 		not null,
	file_name								varchar(100)		not null,
	file_real_name							varchar(100)		not null,
	file_path								varchar(256)		not null,
	file_size								varchar(12)			not null,
	file_ext								varchar(10)			not null,
	total_count								bigint				default 0,
	parse_success_count						bigint				default 0,
	parse_error_count						bigint				default 0,
	insert_success_count					bigint				default 0,
	insert_error_count						bigint				default 0,
	insert_date								timestamp with time zone			default now(),
	constraint data_object_attribute_file_info_pk primary key (data_object_attribute_file_info_id)
);

comment on table data_object_attribute_file_info is '데이터 Object 속성 파일 관리';
comment on column data_object_attribute_file_info.data_object_attribute_file_info_id is '고유번호';
comment on column data_object_attribute_file_info.data_id is '데이터 고유번호';
comment on column data_object_attribute_file_info.user_id is '사용자 아이디';
comment on column data_object_attribute_file_info.file_name is '파일 이름';
comment on column data_object_attribute_file_info.file_real_name is '파일 실제 이름';
comment on column data_object_attribute_file_info.file_path is '파일 경로';
comment on column data_object_attribute_file_info.file_size is '파일 사이즈';
comment on column data_object_attribute_file_info.file_ext is '파일 확장자';
comment on column data_object_attribute_file_info.total_count is '전체 데이터 건수';
comment on column data_object_attribute_file_info.parse_success_count is '파싱 성공 건수';
comment on column data_object_attribute_file_info.parse_error_count is '파싱 오류';
comment on column data_object_attribute_file_info.insert_success_count is 'SQL Insert 성공 건수';
comment on column data_object_attribute_file_info.insert_error_count is 'SQL Insert 실패 건수';
comment on column data_object_attribute_file_info.insert_date is '등록일';


drop table if exists data_file_info cascade;
drop table if exists data_file_parse_log cascade;

-- 데이터 bulk upload 파일 관리
create table data_file_info(
	data_file_info_id						bigint,
	data_group_id							integer,
	user_id									varchar(32)	 		not null,
	file_name								varchar(100)		not null,
	file_real_name							varchar(100)		not null,
	file_path								varchar(256)		not null,
	file_size								varchar(12)			not null,
	file_ext								varchar(10)			not null,
	total_count								bigint				default 0,
	parse_success_count						bigint				default 0,
	parse_error_count						bigint				default 0,
	insert_success_count					bigint				default 0,
	insert_error_count						bigint				default 0,
	insert_date								timestamp with time zone			default now(),
	constraint data_file_info_pk primary key (data_file_info_id)
);

comment on table data_file_info is '데이터 파일 관리';
comment on column data_file_info.data_file_info_id is '고유번호';
comment on column data_file_info.data_group_id is '데이터 그룹 고유번호';
comment on column data_file_info.user_id is '사용자 아이디';
comment on column data_file_info.file_name is '파일 이름';
comment on column data_file_info.file_real_name is '파일 실제 이름';
comment on column data_file_info.file_path is '파일 경로';
comment on column data_file_info.file_size is '파일 사이즈';
comment on column data_file_info.file_ext is '파일 확장자';
comment on column data_file_info.total_count is '전체 데이터 건수';
comment on column data_file_info.parse_success_count is '파싱 성공 건수';
comment on column data_file_info.parse_error_count is '파싱 오류';
comment on column data_file_info.insert_success_count is 'SQL Insert 성공 건수';
comment on column data_file_info.insert_error_count is 'SQL Insert 실패 건수';
comment on column data_file_info.insert_date is '등록일';

create table data_file_parse_log(
	data_file_parse_log_id			bigint,
	data_file_info_id				bigint 				not null,
	identifier_value							varchar(100)	 	not null,
	error_code									varchar(256),
	log_type									varchar(20),
	status										varchar(20),
	insert_date									timestamp with time zone			default now(),
	constraint data_file_parse_log_pk primary key (data_file_parse_log_id)
);

comment on table data_file_parse_log is '파일 파싱 이력';
comment on column data_file_parse_log.data_file_parse_log_id is '고유번호';
comment on column data_file_parse_log.data_file_info_id is '파일 정보 고유번호';
comment on column data_file_parse_log.identifier_value is '식별자 값';
comment on column data_file_parse_log.error_code is '에러 코드';
comment on column data_file_parse_log.log_type is '로그 타입. file: 파일, db: DB Insert';
comment on column data_file_parse_log.status is '상태. success, error';
comment on column data_file_parse_log.insert_date is '등록일';

drop table if exists data_library_group cascade;
drop table if exists data_library cascade;


-- data library 그룹
create table data_library_group (
	data_library_group_id		            integer,
	data_library_group_key				    varchar(60)				        		not null,
	data_library_group_name      		    varchar(256)			        		not null,
	data_library_group_path				    varchar(256),
	data_library_group_target			    varchar(5)		        				default 'user',
	sharing						            varchar(30)					        	default 'public',
	user_id						            varchar(32),
	ancestor					            integer					        		default 0,
	parent                		            integer					        		default 0,
	depth                	  	            integer					        		default 1,
	view_order					            integer					        		default 1,
	children					            integer						        	default 0,
	basic						            boolean							        default false,
	available					            boolean							        default true,
	data_library_count					    integer							        default 0,
	description					            varchar(256),
	update_date             	            timestamp with time zone,
	insert_date					            timestamp with time zone		        default now(),
	constraint data_library_group_pk        primary key (data_library_group_id)
);

comment on table data_library_group is 'data library 그룹';
comment on column data_library_group.data_library_group_id is 'data library 그룹 고유번호';
comment on column data_library_group.data_library_group_key is '링크 활용 등을 위한 확장 컬럼';
comment on column data_library_group.data_library_group_name is 'data library 그룹 그룹명';
comment on column data_library_group.data_library_group_path is '서비스 경로';
comment on column data_library_group.data_library_group_target is 'admin : 관리자용 data library 그룹, user : 일반 사용자용 data library 그룹';
comment on column data_library_group.sharing is 'common : 공통, public : 공개, private : 비공개, group : 그룹';
comment on column data_library_group.user_id is '사용자 아이디';
comment on column data_library_group.data_library_count is '데이터 라이브러리 총 건수';
comment on column data_library_group.ancestor is '조상';
comment on column data_library_group.parent is '부모';
comment on column data_library_group.depth is '깊이';
comment on column data_library_group.view_order is '나열 순서';
comment on column data_library_group.children is '자식 존재 개수';
comment on column data_library_group.basic is 'true : 기본, false : 선택';
comment on column data_library_group.available is 'true : 사용, false : 사용안함';
comment on column data_library_group.description is '설명';
comment on column data_library_group.update_date is '수정일';
comment on column data_library_group.insert_date is '등록일';

-- data library
create table data_library (
	data_library_id					    bigint,
	data_library_group_id			    integer,
	converter_job_id			        bigint,
	data_library_key					varchar(100)					not null,
	data_library_name				    varchar(256)					not null,
	data_library_path				    varchar(256),
	data_type					        varchar(30),
	user_id						        varchar(32),
	service_type				        varchar(30),
    view_order					        integer							default 1,
	available					        boolean							default true,
    status						        varchar(20)						default 'use',
	description					        varchar(256),
	update_date					        timestamp with time zone,
	insert_date					        timestamp with time zone 		default now(),
	constraint data_library_pk 		    primary key (data_library_id)
);


comment on table data_library is 'data library';
comment on column data_library.data_library_id is 'data library 고유번호';
comment on column data_library.data_library_group_id is 'data library 그룹 고유번호';
comment on column data_library.converter_job_id is 'converter job 고유번호';
comment on column data_library.data_library_key is 'data library 고유키(API용)';
comment on column data_library.data_library_name is 'data library명';
comment on column data_library.data_library_path is 'data library 경로';
comment on column data_library.data_type is '데이터 타입(중복). 3ds,obj,dae,collada,ifc,las,citygml,indoorgml,etc';
comment on column data_library.user_id is '사용자명';
comment on column data_library.service_type is '서비스 타입 (정적, 동적)';
comment on column data_library.view_order is '나열 순서';
comment on column data_library.available is '사용유무.';
comment on column data_library.status is '상태. processing : 변환중, use : 사용중, unused : 사용중지(관리자), delete : 삭제(비표시)';
comment on column data_library.description is '설명';
comment on column data_library.update_date is '수정일';
comment on column data_library.insert_date is '등록일';


drop table if exists data_smart_tiling_file_info cascade;
drop table if exists data_smart_tiling_file_parse_log cascade;

-- Smart Tiling 데이터 파일 관리
create table data_smart_tiling_file_info(
	data_smart_tiling_file_info_id			bigint,
	data_group_id							integer,
	user_id									varchar(32)	 		not null,
	file_name								varchar(100)		not null,
	file_real_name							varchar(100)		not null,
	file_path								varchar(256)		not null,
	file_size								varchar(12)			not null,
	file_ext								varchar(10)			not null,
	total_count								bigint				default 0,
	parse_success_count						bigint				default 0,
	parse_error_count						bigint				default 0,
	insert_success_count					bigint				default 0,
	insert_error_count						bigint				default 0,
	insert_date								timestamp with time zone			default now(),
	constraint data_smart_tiling_file_info_pk primary key (data_smart_tiling_file_info_id)
);

comment on table data_smart_tiling_file_info is 'Smart Tiling 데이터 파일 관리';
comment on column data_smart_tiling_file_info.data_smart_tiling_file_info_id is '고유번호';
comment on column data_smart_tiling_file_info.data_group_id is '데이터 그룹 고유번호';
comment on column data_smart_tiling_file_info.user_id is '사용자 아이디';
comment on column data_smart_tiling_file_info.file_name is '파일 이름';
comment on column data_smart_tiling_file_info.file_real_name is '파일 실제 이름';
comment on column data_smart_tiling_file_info.file_path is '파일 경로';
comment on column data_smart_tiling_file_info.file_size is '파일 사이즈';
comment on column data_smart_tiling_file_info.file_ext is '파일 확장자';
comment on column data_smart_tiling_file_info.total_count is '전체 데이터 건수';
comment on column data_smart_tiling_file_info.parse_success_count is '파싱 성공 건수';
comment on column data_smart_tiling_file_info.parse_error_count is '파싱 오류';
comment on column data_smart_tiling_file_info.insert_success_count is 'SQL Insert 성공 건수';
comment on column data_smart_tiling_file_info.insert_error_count is 'SQL Insert 실패 건수';
comment on column data_smart_tiling_file_info.insert_date is '등록일';

create table data_smart_tiling_file_parse_log(
	data_smart_tiling_file_parse_log_id			bigint,
	data_smart_tiling_file_info_id				bigint 				not null,
	identifier_value							varchar(100)	 	not null,
	error_code									varchar(256),
	log_type									varchar(20),
	status										varchar(20),
	insert_date									timestamp with time zone			default now(),
	constraint data_smart_tiling_file_parse_log_pk primary key (data_smart_tiling_file_parse_log_id)
);

comment on table data_smart_tiling_file_parse_log is '파일 파싱 이력';
comment on column data_smart_tiling_file_parse_log.data_smart_tiling_file_parse_log_id is '고유번호';
comment on column data_smart_tiling_file_parse_log.data_smart_tiling_file_info_id is '파일 정보 고유번호';
comment on column data_smart_tiling_file_parse_log.identifier_value is '식별자 값';
comment on column data_smart_tiling_file_parse_log.error_code is '에러 코드';
comment on column data_smart_tiling_file_parse_log.log_type is '로그 타입. file: 파일, db: DB Insert';
comment on column data_smart_tiling_file_parse_log.status is '상태. success, error';
comment on column data_smart_tiling_file_parse_log.insert_date is '등록일';

-- FK, Index 는 별도 파일로 분리. 맨 마지막에 작업 예정
drop table if exists data_group cascade;
drop table if exists data_info cascade;

-- data의 논리적인 그룹
create table data_group (
	data_group_id				integer,
	data_group_key				varchar(60)							not null,
	data_group_name				varchar(100)						not null,
	data_group_path				varchar(256),
	data_group_target			varchar(5)							default 'user',
	sharing						varchar(30)							default 'public',
	user_id						varchar(32),
	ancestor					integer								default 0,
	parent						integer								default 1,
	depth						integer								default 1,
	view_order					integer								default 1,
	children					integer								default 0,
	basic						boolean								default false,
	available					boolean								default true,
	tiling						boolean								default false,
	data_count					integer								default 0,
	location		 			GEOMETRY(POINT, 4326),
	altitude					numeric(13,7),
	duration					integer,
	location_update_type		varchar(20)							default 'auto',
	metainfo					jsonb,
	description					varchar(256),
	update_date					timestamp with time zone,
	insert_date					timestamp with time zone			default now(),
	constraint data_group_pk 	primary key (data_group_id)
);

comment on table data_group is '데이터 그룹';
comment on column data_group.data_group_id is '고유번호';
comment on column data_group.data_group_key is '링크 활용 등을 위한 확장 컬럼';
comment on column data_group.data_group_name is '그룹명';
comment on column data_group.data_group_path is '서비스 경로';
comment on column data_group.data_group_target is 'admin : 관리자용 데이터 그룹, user : 일반 사용자용 데이터 그룹';
comment on column data_group.sharing is 'common : 공통, public : 공개, private : 비공개, group : 그룹';
comment on column data_group.user_id is '사용자 아이디';
comment on column data_group.data_count is '데이터 총 건수';
comment on column data_group.view_order is '나열 순서';
comment on column data_group.children is '자식 존재 개수';
comment on column data_group.basic is 'true : 기본, false : 선택';
comment on column data_group.available is 'true : 사용, false : 사용안함';
comment on column data_group.tiling is 'true : 사용, false : 사용안함(기본)';
comment on column data_group.location is 'POINT(위도, 경도). 공간 검색 속도 때문에 altitude는 분리';
comment on column data_group.altitude is '높이';
comment on column data_group.duration is 'Map 이동시간';
comment on column data_group.location_update_type is 'location 업데이트 방법. auto : data 입력시 자동, user : 사용자가 직접 입력';
comment on column data_group.metainfo is '데이터 그룹 메타 정보. 그룹 control을 위해 인위적으로 만든 속성';
comment on column data_group.description is '설명';
comment on column data_group.update_date is '수정일';
comment on column data_group.insert_date is '등록일';


-- Data 기본정보
create table data_info(
	data_id						bigint,
	data_group_id				integer								not null,
	converter_job_id			bigint,
	data_key					varchar(128)						not null,
	data_name					varchar(256),
	data_type					varchar(30),
	sharing						varchar(30)							default 'public',
	user_id						varchar(32),
	update_user_id				varchar(32),
	mapping_type				varchar(30)							default 'origin',
	location		 			GEOMETRY(POINT, 4326),
	altitude					numeric(13,7),
	heading						numeric(8,5),
	pitch						numeric(8,5),
	roll						numeric(8,5),
	children_ancestor			integer								default 0,
	children_parent				integer								default 1,
	children_depth				integer								default 1,
	children_view_order			integer								default 1,
	metainfo					jsonb,
	status						varchar(20)							default 'use',
	attribute_exist				boolean								default false,
	object_attribute_exist		boolean								default false,
	description					varchar(256),
	update_date					timestamp with time zone,
	insert_date					timestamp with time zone			default now(),
	constraint data_info_pk 	primary key(data_id)
);

comment on table data_info is 'Data 정보';
comment on column data_info.data_id is '고유번호';
comment on column data_info.data_group_id is 'data_group 고유번호';
comment on column data_info.converter_job_id is 'converter job 고유번호';
comment on column data_info.data_key is 'data 고유 식별번호';
comment on column data_info.data_name is 'data 이름';
comment on column data_info.data_type is '데이터 타입(중복). 3ds,obj,dae,collada,ifc,las,citygml,indoorgml,etc';
comment on column data_info.sharing is 'common : 공통, public : 공개, private : 비공개, group : 그룹';
comment on column data_info.user_id is '사용자 아이디';
comment on column data_info.update_user_id is '수정 수정자 아이디';
comment on column data_info.mapping_type is '기본값 origin : latitude, longitude, height를 origin에 맞춤. boundingboxcenter : latitude, longitude, height를 boundingboxcenter 맞춤';
comment on column data_info.location is 'POINT(위도, 경도). 공간 검색 속도 때문에 altitude는 분리';
comment on column data_info.altitude is '높이';
comment on column data_info.heading is 'heading';
comment on column data_info.pitch is 'pitch';
comment on column data_info.roll is 'roll';
comment on column data_info.children_ancestor is '조상';
comment on column data_info.children_parent is '부모';
comment on column data_info.children_depth is '깊이';
comment on column data_info.children_view_order is '표시 순서';
comment on column data_info.metainfo is '데이터 메타 정보. 데이터  control을 위해 인위적으로 만든 속성';
comment on column data_info.status is '상태. processing : 변환중, use : 사용중, unused : 사용중지(관리자), delete : 삭제(비표시)';
comment on column data_info.attribute_exist is '속성 존재 유무. true : 존재, false : 존재하지 않음(기본값)';
comment on column data_info.object_attribute_exist is 'Object 속성 존재 유무. true : 존재, false : 존재하지 않음(기본값)';
comment on column data_info.description is '설명';
comment on column data_info.update_date is '수정일';
comment on column data_info.insert_date is '등록일';

-- point 는 경도, 위도 순서다.
-- insert into data_info(location) values(st_geometryfromtext('POINT(128.5952254 34.93630567)'))
-- FK, Index 는 별도 파일로 분리. 맨 마지막에 작업 예정
drop table if exists data_info_log cascade;
drop table if exists data_info_log_2020 cascade;
drop table if exists data_info_log_2021 cascade;
drop table if exists data_info_log_2022 cascade;
drop table if exists data_info_log_2023 cascade;
drop table if exists data_info_log_2024 cascade;
drop table if exists data_info_log_2025 cascade;
drop table if exists data_info_log_2026 cascade;
drop table if exists data_info_log_2027 cascade;
drop table if exists data_info_log_2028 cascade;
drop table if exists data_info_log_2029 cascade;
drop table if exists data_info_log_2030 cascade;
drop table if exists data_info_log_2031 cascade;
drop table if exists data_info_log_2032 cascade;
drop table if exists data_info_log_2033 cascade;
drop table if exists data_info_log_2034 cascade;
drop table if exists data_info_log_2035 cascade;
drop table if exists data_info_log_2036 cascade;
drop table if exists data_info_log_2037 cascade;
drop table if exists data_info_log_2038 cascade;
drop table if exists data_info_log_2039 cascade;
drop table if exists data_info_log_2040 cascade;

-- 데이터 위치 변경 요청 이력. 파티션 pruning 확인해야 함(지금 임시버전)
create table data_info_log(
	data_log_id						bigint,
	data_group_id					integer,
	data_id							bigint,
	data_key						varchar(128)						not null,
	data_name						varchar(256),
	data_type						varchar(30),
	converter_job_id				bigint,
	sharing							varchar(30)							default 'public',
	user_id							varchar(32),
	update_user_id					varchar(32),
	mapping_type					varchar(30),
	location		 				GEOMETRY(POINT, 4326),
	altitude						numeric(13,7),
	heading							numeric(8,5),
	pitch							numeric(8,5),
	roll							numeric(8,5),
	change_type						varchar(30) default 'insert',
	metainfo						jsonb,
	year							char(4)								default to_char(now(), 'yyyy'),
	month							varchar(2)							default to_char(now(), 'MM'),
	day								varchar(2)							default to_char(now(), 'DD'),
	year_week						varchar(2)							default to_char(now(), 'WW'),
	week							varchar(2)							default to_char(now(), 'W'),
	hour							varchar(2)							default to_char(now(), 'HH24'),
	minute							varchar(2)							default to_char(now(), 'MI'),
	description						varchar(256),
	update_date						timestamp with time zone,
	insert_date						timestamp with time zone			default now()
) partition by range(insert_date);

comment on table data_info_log is '데이터 변경 이력 정보';
comment on column data_info_log.data_log_id is '고유번호';
comment on column data_info_log.data_group_id is '데이터 그룹 고유번호, join 성능때문에 중복 허용';
comment on column data_info_log.data_id is '데이터 고유번호';
comment on column data_info_log.data_key is '데이터 고유 식별키';
comment on column data_info_log.data_name is '데이터명';
comment on column data_info_log.data_type is '데이터 타입(중복). 3ds,obj,dae,collada,ifc,las,citygml,indoorgml,etc';
comment on column data_info_log.converter_job_id is 'converter job 고유번호';
comment on column data_info_log.sharing is 'common : 공통, public : 공개, private : 비공개, group : 그룹';
comment on column data_info_log.user_id is '사용자 아이디';
comment on column data_info_log.update_user_id is '수정 요청자 아이디';
comment on column data_info_log.mapping_type is '기본값 origin : latitude, longitude, height를 origin에 맞춤. boundingboxcenter : latitude, longitude, height를 boundingboxcenter 맞춤';
comment on column data_info_log.location is 'POINT(위도, 경도). 공간 검색 속도 때문에 altitude는 분리';
comment on column data_info_log.altitude is '높이';
comment on column data_info_log.heading is 'heading';
comment on column data_info_log.pitch is 'pitch';
comment on column data_info_log.roll is 'roll';
comment on column data_info_log.change_type is '변경 유형. insert : 등록, update : 수정';
comment on column data_info_log.metainfo is '데이터 메타 정보. 데이터  control을 위해 인위적으로 만든 속성';
comment on column data_info_log.year is '년';
comment on column data_info_log.month is '월';
comment on column data_info_log.day is '일';
comment on column data_info_log.year_week is '일년중 몇주';
comment on column data_info_log.week is '이번달 몇주';
comment on column data_info_log.hour is '시간';
comment on column data_info_log.minute is '분';
comment on column data_info_log.description is '설명';
comment on column data_info_log.update_date is '수정일';
comment on column data_info_log.insert_date is '등록일';


create table data_info_log_2020 partition of data_info_log for values from ('2020-01-01 00:00:00.000000') to ('2021-01-01 00:00:00.000000');
create table data_info_log_2021 partition of data_info_log for values from ('2021-01-01 00:00:00.000000') to ('2022-01-01 00:00:00.000000');
create table data_info_log_2022 partition of data_info_log for values from ('2022-01-01 00:00:00.000000') to ('2023-01-01 00:00:00.000000');
create table data_info_log_2023 partition of data_info_log for values from ('2023-01-01 00:00:00.000000') to ('2024-01-01 00:00:00.000000');
create table data_info_log_2024 partition of data_info_log for values from ('2024-01-01 00:00:00.000000') to ('2025-01-01 00:00:00.000000');
create table data_info_log_2025 partition of data_info_log for values from ('2025-01-01 00:00:00.000000') to ('2026-01-01 00:00:00.000000');
create table data_info_log_2026 partition of data_info_log for values from ('2026-01-01 00:00:00.000000') to ('2027-01-01 00:00:00.000000');
create table data_info_log_2027 partition of data_info_log for values from ('2027-01-01 00:00:00.000000') to ('2028-01-01 00:00:00.000000');
create table data_info_log_2028 partition of data_info_log for values from ('2028-01-01 00:00:00.000000') to ('2029-01-01 00:00:00.000000');
create table data_info_log_2029 partition of data_info_log for values from ('2029-01-01 00:00:00.000000') to ('2030-01-01 00:00:00.000000');
create table data_info_log_2030 partition of data_info_log for values from ('2030-01-01 00:00:00.000000') to ('2031-01-01 00:00:00.000000');
create table data_info_log_2031 partition of data_info_log for values from ('2031-01-01 00:00:00.000000') to ('2032-01-01 00:00:00.000000');
create table data_info_log_2032 partition of data_info_log for values from ('2032-01-01 00:00:00.000000') to ('2033-01-01 00:00:00.000000');
create table data_info_log_2033 partition of data_info_log for values from ('2033-01-01 00:00:00.000000') to ('2034-01-01 00:00:00.000000');
create table data_info_log_2034 partition of data_info_log for values from ('2034-01-01 00:00:00.000000') to ('2035-01-01 00:00:00.000000');
create table data_info_log_2035 partition of data_info_log for values from ('2035-01-01 00:00:00.000000') to ('2036-01-01 00:00:00.000000');
create table data_info_log_2036 partition of data_info_log for values from ('2036-01-01 00:00:00.000000') to ('2037-01-01 00:00:00.000000');
create table data_info_log_2037 partition of data_info_log for values from ('2037-01-01 00:00:00.000000') to ('2038-01-01 00:00:00.000000');
create table data_info_log_2038 partition of data_info_log for values from ('2038-01-01 00:00:00.000000') to ('2039-01-01 00:00:00.000000');
create table data_info_log_2039 partition of data_info_log for values from ('2039-01-01 00:00:00.000000') to ('2040-01-01 00:00:00.000000');
create table data_info_log_2040 partition of data_info_log for values from ('2040-01-01 00:00:00.000000') to ('2041-01-01 00:00:00.000000');

-- FK, Index 는 별도 파일로 분리. 맨 마지막에 작업 예정
drop table if exists data_info_origin cascade;

-- Data 변환전 정보(origin)
create table data_info_origin(
	data_origin_id				bigint,
	data_id						bigint								not null,
	data_origin_name			varchar(256),
	location		 			GEOMETRY(POINT, 4326),
	altitude					numeric(13,7),
	heading						numeric(8,5),
	pitch						numeric(8,5),
	roll						numeric(8,5),
	link						varchar(512),
	update_date					timestamp with time zone,
	insert_date					timestamp with time zone			default now(),
	constraint data_info_origin_pk 	primary key(data_origin_id)
);

comment on table data_info_origin is 'Data 정보';
comment on column data_info_origin.data_origin_id is '고유번호';
comment on column data_info_origin.data_id is '고유번호';
comment on column data_info_origin.data_origin_name is 'data 고유 이름';
comment on column data_info_origin.location is '위도, 경도 정보';
comment on column data_info_origin.altitude is '높이';
comment on column data_info_origin.heading is 'heading';
comment on column data_info_origin.pitch is 'pitch';
comment on column data_info_origin.roll is 'roll';
comment on column data_info_origin.link is '참조 링크';
comment on column data_info_origin.update_date is '수정일';
comment on column data_info_origin.insert_date is '등록일';

drop table if exists design_layer_group cascade;
drop table if exists design_layer cascade;
drop table if exists design_layer_land cascade;
drop table if exists design_layer_building cascade;
drop table if exists design_layer_file_info cascade;
drop table if exists design_layer_attribute cascade;

drop table if exists design_layer_log cascade;
drop table if exists design_layer_log_2020 cascade;
drop table if exists design_layer_log_2021 cascade;
drop table if exists design_layer_log_2022 cascade;
drop table if exists design_layer_log_2023 cascade;
drop table if exists design_layer_log_2024 cascade;
drop table if exists design_layer_log_2025 cascade;
drop table if exists design_layer_log_2026 cascade;
drop table if exists design_layer_log_2027 cascade;
drop table if exists design_layer_log_2028 cascade;
drop table if exists design_layer_log_2029 cascade;
drop table if exists design_layer_log_2030 cascade;
drop table if exists design_layer_log_2031 cascade;
drop table if exists design_layer_log_2032 cascade;
drop table if exists design_layer_log_2033 cascade;
drop table if exists design_layer_log_2034 cascade;
drop table if exists design_layer_log_2035 cascade;
drop table if exists design_layer_log_2036 cascade;
drop table if exists design_layer_log_2037 cascade;
drop table if exists design_layer_log_2038 cascade;
drop table if exists design_layer_log_2039 cascade;
drop table if exists design_layer_log_2040 cascade;
commit;

-- design layer 그룹
create table design_layer_group (
	design_layer_group_id		            integer,
	design_layer_group_name      		    varchar(256)					not null,
	user_id						            varchar(32),
	ancestor					            integer							default 0,
	parent                		            integer							default 0,
	depth                	  	            integer							default 1,
	view_order					            integer							default 1,
	children					            integer							default 0,
	basic						            boolean							default false,
	available					            boolean							default true,
	description					            varchar(256),
	update_date             	            timestamp with time zone,
	insert_date					            timestamp with time zone		default now(),
	constraint design_layer_group_pk        primary key (design_layer_group_id)
);

comment on table design_layer_group is 'design layer 그룹';
comment on column design_layer_group.design_layer_group_id is 'design layer 그룹 고유번호';
comment on column design_layer_group.design_layer_group_name is 'design layer 그룹 그룹명';
comment on column design_layer_group.user_id is '사용자 아이디';
comment on column design_layer_group.ancestor is '조상';
comment on column design_layer_group.parent is '부모';
comment on column design_layer_group.depth is '깊이';
comment on column design_layer_group.view_order is '나열 순서';
comment on column design_layer_group.children is '자식 존재 개수';
comment on column design_layer_group.basic is 'true : 기본(초기 등록), false : 선택';
comment on column design_layer_group.available is '사용 여부';
comment on column design_layer_group.description is '설명';
comment on column design_layer_group.update_date is '수정일';
comment on column design_layer_group.insert_date is '등록일';

-- design layer
create table design_layer (
	design_layer_id					    bigint,
	urban_group_id                      integer,
	design_layer_group_id			    integer,
	design_layer_key					varchar(100)					not null,
	design_layer_name				    varchar(256)					not null,
	design_layer_type                   varchar(30)                     default 'land',
	user_id						        varchar(32),
	sharing						        varchar(30)						default 'public',
	ogc_web_services				    varchar(30),
	geometry_type				        varchar(30),
	layer_fill_color                    varchar(30),
	layer_line_color			        varchar(30),
	layer_line_style			        numeric,
	layer_alpha_style			        numeric,

	view_order					        integer							default 1,
	z_index						        integer,
	available					        boolean							default true,
	cache_available				        boolean							default false,

	coordinate					        varchar(256),
	description					        varchar(256),
	update_date					        timestamp with time zone,
	insert_date					        timestamp with time zone 		default now(),
	constraint design_layer_pk 		    primary key (design_layer_id)
);

comment on table design_layer is 'design layer';
comment on column design_layer.design_layer_id is 'design layer 고유번호';
comment on column design_layer.urban_group_id is '도시 그룹 고유번호';
comment on column design_layer.design_layer_group_id is 'design layer 그룹 고유번호';
comment on column design_layer.design_layer_key is 'design layer 고유키(API용)';
comment on column design_layer.design_layer_name is 'design layer 명';
comment on column design_layer.design_layer_type is 'design layer 분류. land : 땅, building : 빌딩';
comment on column design_layer.user_id is '사용자 아이디';
comment on column design_layer.sharing is '공유 유형. common : 공통, public : 공개, private : 개인, group : 그룹';
comment on column design_layer.ogc_web_services is 'OGC Web Services (wms, wfs, wcs, wps)';
comment on column design_layer.geometry_type is '도형 타입';
comment on column design_layer.layer_fill_color is '외곽선 색상';
comment on column design_layer.layer_line_color is '외곽선 두께';
comment on column design_layer.layer_line_style is '채우기 색상';
comment on column design_layer.layer_alpha_style is '투명도';
comment on column design_layer.view_order is '나열 순서';
comment on column design_layer.z_index is '지도위에 노출 순위(css z-index와 동일)';
comment on column design_layer.available is '사용유무.';
comment on column design_layer.cache_available is '캐시 사용 유무';
comment on column design_layer.coordinate is '좌표계 정보';
comment on column design_layer.description is '설명';
comment on column design_layer.update_date is '수정일';
comment on column design_layer.insert_date is '등록일';


-- design layer land
create table design_layer_land (
	design_layer_land_id                        bigint,
	design_layer_id					            bigint,
	design_layer_group_id			            integer,
    properties					                jsonb,
	update_date					                timestamp with time zone,
	insert_date					                timestamp with time zone 		default now(),
	constraint design_layer_land_pk 		    primary key (design_layer_land_id)
);

comment on table design_layer_land is 'design layer land';
comment on column design_layer_land.design_layer_land_id is 'design layer land 고유번호';
comment on column design_layer_land.design_layer_id is 'design layer 고유번호';
comment on column design_layer_land.design_layer_group_id is 'design layer 그룹 고유번호';
comment on column design_layer_land.update_date is '수정일';
comment on column design_layer_land.insert_date is '등록일';

-- design layer building
create table design_layer_building (
	design_layer_building_id                    bigint,
	design_layer_id					            bigint,
	design_layer_group_id			            integer,
    properties					                jsonb,
	update_date					                timestamp with time zone,
	insert_date					                timestamp with time zone 		default now(),
	constraint design_layer_building_pk 		primary key (design_layer_building_id)
);

comment on table design_layer_building is 'design layer building';
comment on column design_layer_building.design_layer_building_id is 'design layer building 고유번호';
comment on column design_layer_building.design_layer_id is 'design layer 고유번호';
comment on column design_layer_building.design_layer_group_id is 'design layer 그룹 고유번호';
comment on column design_layer_building.update_date is '수정일';
comment on column design_layer_building.insert_date is '등록일';


-- design layer 속성
create table design_layer_attribute (
	design_layer_attribute_id			            bigint,
	design_layer_id						            bigint,
	attributes					                    json,
	update_date					                    timestamp with time zone,
	insert_date					                    timestamp with time zone			default now(),
	constraint design_layer_attribute_pk 	        primary key(design_layer_attribute_id)
);

comment on table design_layer_attribute is 'design layer 필수 외 속성 정보';
comment on column design_layer_attribute.design_layer_attribute_id is '고유번호';
comment on column design_layer_attribute.design_layer_id is 'design layer 고유번호';
comment on column design_layer_attribute.attributes is '속성';
comment on column design_layer_attribute.update_date is '수정일';
comment on column design_layer_attribute.insert_date is '등록일';


-- design layer shape 파일 정보
create table design_layer_file_info (
	design_layer_file_info_id		        bigint,
	design_layer_id					        bigint							not null,
	design_layer_file_info_team_id	        bigint,
	user_id						            varchar(32)						not null,
	enable_yn					            char(1)							default 'N',
	file_name					            varchar(100)					not null,
	file_real_name				            varchar(100)					not null,
	file_path					            varchar(256)					not null,
	file_size					            varchar(12)						not null,
	file_ext					            varchar(10)						not null,
	shape_encoding				            varchar(100),
	version_id					            integer							default 0,
	update_date					            timestamp with time zone,
	insert_date				            	timestamp with time zone		default now(),
	constraint design_layer_file_info_pk    primary key (design_layer_file_info_id)
);

comment on table design_layer_file_info is 'design layer shape 파일 관리';
comment on column design_layer_file_info.design_layer_file_info_id is 'design layer 파일 고유번호';
comment on column design_layer_file_info.design_layer_id is 'design layer 고유번호';
comment on column design_layer_file_info.design_layer_file_info_team_id is 'shape 파일 팀 아이디. .shp 파일의 design_layer_file_info_id 를 team_id로 함';
comment on column design_layer_file_info.user_id is '사용자 id';
comment on column design_layer_file_info.enable_yn is 'layer 활성화 유무. Y: 활성화, N: 비활성화';
comment on column design_layer_file_info.file_name is '파일 이름';
comment on column design_layer_file_info.file_real_name is '파일 실제 이름';
comment on column design_layer_file_info.file_path is '파일 경로';
comment on column design_layer_file_info.file_size is '파일 용량';
comment on column design_layer_file_info.file_ext is '파일 확장자';
comment on column design_layer_file_info.shape_encoding is 'shape 파일 인코딩';
comment on column design_layer_file_info.version_id is 'shape 파일 버전 정보';
comment on column design_layer_file_info.update_date is '갱신일';
comment on column design_layer_file_info.insert_date is '등록일';


-- design layer 사용 이력
create table design_layer_log(
	design_layer_log_id				    bigint,
	design_layer_id				        bigint,
	user_id						        varchar(32)	 		not null,
	user_name					        varchar(64),
	status						        varchar(30),
	year						        char(4)				default to_char(now(), 'YYYY'),
	month						        varchar(2)			default to_char(now(), 'MM'),
	day							        varchar(2)			default to_char(now(), 'DD'),
	year_week					        varchar(2)			default to_char(now(), 'WW'),
	week						        varchar(2)			default to_char(now(), 'W'),
	hour						        varchar(2)			default to_char(now(), 'HH24'),
	minute						        varchar(2)			default to_char(now(), 'MI'),
	insert_date					        timestamp with time zone			default now()
) partition by range(insert_date);

comment on table design_layer_log is 'design layer 사용 이력';
comment on column design_layer_log.design_layer_log_id is '고유번호';
comment on column design_layer_log.design_layer_id is 'design layer 고유번호';
comment on column design_layer_log.user_id is '사용자 아이디';
comment on column design_layer_log.user_name is '사용자 이름';
comment on column design_layer_log.status is '상태. ready : 시뮬레이션전, use : 사용중, temp : 임시저장, complete : 완료';
comment on column design_layer_log.year is '년';
comment on column design_layer_log.month is '월';
comment on column design_layer_log.day is '일';
comment on column design_layer_log.year_week is '일년중 몇주';
comment on column design_layer_log.week is '이번달 몇주';
comment on column design_layer_log.hour is '시간';
comment on column design_layer_log.minute is '분';
comment on column design_layer_log.insert_date is '등록일';


create table design_layer_log_2020 partition of design_layer_log for values from ('2020-01-01 00:00:00.000000') to ('2021-01-01 00:00:00.000000');
create table design_layer_log_2021 partition of design_layer_log for values from ('2021-01-01 00:00:00.000000') to ('2022-01-01 00:00:00.000000');
create table design_layer_log_2022 partition of design_layer_log for values from ('2022-01-01 00:00:00.000000') to ('2023-01-01 00:00:00.000000');
create table design_layer_log_2023 partition of design_layer_log for values from ('2023-01-01 00:00:00.000000') to ('2024-01-01 00:00:00.000000');
create table design_layer_log_2024 partition of design_layer_log for values from ('2024-01-01 00:00:00.000000') to ('2025-01-01 00:00:00.000000');
create table design_layer_log_2025 partition of design_layer_log for values from ('2025-01-01 00:00:00.000000') to ('2026-01-01 00:00:00.000000');
create table design_layer_log_2026 partition of design_layer_log for values from ('2026-01-01 00:00:00.000000') to ('2027-01-01 00:00:00.000000');
create table design_layer_log_2027 partition of design_layer_log for values from ('2027-01-01 00:00:00.000000') to ('2028-01-01 00:00:00.000000');
create table design_layer_log_2028 partition of design_layer_log for values from ('2028-01-01 00:00:00.000000') to ('2029-01-01 00:00:00.000000');
create table design_layer_log_2029 partition of design_layer_log for values from ('2029-01-01 00:00:00.000000') to ('2030-01-01 00:00:00.000000');
create table design_layer_log_2030 partition of design_layer_log for values from ('2030-01-01 00:00:00.000000') to ('2031-01-01 00:00:00.000000');
create table design_layer_log_2031 partition of design_layer_log for values from ('2031-01-01 00:00:00.000000') to ('2032-01-01 00:00:00.000000');
create table design_layer_log_2032 partition of design_layer_log for values from ('2032-01-01 00:00:00.000000') to ('2033-01-01 00:00:00.000000');
create table design_layer_log_2033 partition of design_layer_log for values from ('2033-01-01 00:00:00.000000') to ('2034-01-01 00:00:00.000000');
create table design_layer_log_2034 partition of design_layer_log for values from ('2034-01-01 00:00:00.000000') to ('2035-01-01 00:00:00.000000');
create table design_layer_log_2035 partition of design_layer_log for values from ('2035-01-01 00:00:00.000000') to ('2036-01-01 00:00:00.000000');
create table design_layer_log_2036 partition of design_layer_log for values from ('2036-01-01 00:00:00.000000') to ('2037-01-01 00:00:00.000000');
create table design_layer_log_2037 partition of design_layer_log for values from ('2037-01-01 00:00:00.000000') to ('2038-01-01 00:00:00.000000');
create table design_layer_log_2038 partition of design_layer_log for values from ('2038-01-01 00:00:00.000000') to ('2039-01-01 00:00:00.000000');
create table design_layer_log_2039 partition of design_layer_log for values from ('2039-01-01 00:00:00.000000') to ('2040-01-01 00:00:00.000000');
create table design_layer_log_2040 partition of design_layer_log for values from ('2040-01-01 00:00:00.000000') to ('2041-01-01 00:00:00.000000');
drop table if exists geopolicy cascade;

-- 2D, 3D 운영 정책
create table geopolicy(
	geopolicy_id										integer,

	basic_globe											varchar(20)			default 'cesium',
	cesium_ion_token									varchar(256),
	terrain_type										varchar(30)			default 'cesium-default',
	terrain_value										varchar(256),

	data_api_url										varchar(256),
	data_service_path									varchar(256)		default '/f4d/',
	data_change_request_decision						varchar(20)			default 'approval',

	geoserver_enable									boolean				default true,
	geoserver_wms_version								varchar(5)         	default '1.1.1',
	geoserver_data_url									varchar(256),
	geoserver_data_workspace							varchar(60),
	geoserver_data_store								varchar(60),
	geoserver_user										varchar(256),
	geoserver_password									varchar(256),

	geoserver_imageprovider_enable						boolean				default false,
	geoserver_imageprovider_url							varchar(256),
	geoserver_imageprovider_layer_name					varchar(60),
	geoserver_imageprovider_style_name					varchar(60),
	geoserver_imageprovider_parameters_width			integer				default 256,
	geoserver_imageprovider_parameters_height			integer				default 256,
	geoserver_imageprovider_parameters_format			varchar(30),

	geoserver_terrainprovider_layer_name				varchar(60),
	geoserver_terrainprovider_style_name				varchar(60),
	geoserver_terrainprovider_parameters_width			integer				default 256,
	geoserver_terrainprovider_parameters_height			integer				default 256,
	geoserver_terrainprovider_parameters_format			varchar(30),

	init_camera_enable									boolean				default true,
	init_latitude										varchar(30)			default '37.521168',
	init_longitude										varchar(30)			default '126.924185',
	init_altitude										varchar(30)			default '3000.0',
	init_duration										integer				default 3,
	init_default_terrain								varchar(64),
	init_default_fov									numeric(3,2)		default 0,

	lod0												varchar(20)			default '15',
	lod1												varchar(20)			default '60',
	lod2												varchar(20)			default '90',
	lod3												varchar(20)			default '200',
	lod4												varchar(20)			default '1000',
	lod5												varchar(20)			default '50000',

	ssao_radius											numeric(8,2)		default 0.15,
	cull_face_enable									boolean				default false,
	time_line_enable									boolean				default false,

	max_partitions_lod0 								integer				default 4,
	max_partitions_lod1 								integer				default 2,
	max_partitions_lod2_or_less 						integer				default 1,
	max_ratio_points_dist_0m 							integer				default 10,
	max_ratio_points_dist_100m 							integer				default 120,
	max_ratio_points_dist_200m 							integer				default 240,
	max_ratio_points_dist_400m 							integer				default 480,
	max_ratio_points_dist_800m 							integer				default 960,
	max_ratio_points_dist_1600m 						integer				default 1920,
	max_ratio_points_dist_over_1600m 					integer				default 3840,
	max_point_size_for_pc								numeric(4,1)		default 40.0,
	min_point_size_for_pc								numeric(4,1)		default 3.0,
	pendent_point_size_for_pc							numeric(4,1)		default 60.0,
	memory_management									boolean				default false,

	layer_source_coordinate								varchar(50)			default 'EPSG:4326',
	layer_target_coordinate								varchar(50)			default 'EPSG:4326',

    shape_land_required_columns                         varchar(1000)       default 'location',
    shape_building_required_columns                     varchar(1000)       default 'location',

	insert_date											timestamp with time zone	default now(),

	constraint geopolicy_pk primary key (geopolicy_id)
);

comment on table geopolicy is '2D, 3D 운영정책';
comment on column geopolicy.geopolicy_id is '고유번호';

comment on column geopolicy.basic_globe is 'javascript library 3D globe. 기본 cesium';
comment on column geopolicy.cesium_ion_token is 'Cesium ion token 발급. 기본 mago3D';
comment on column geopolicy.terrain_type is	'Terrain 유형. geoserver, cesium-default, cesium-ion-default, cesium-ion-cdn : 우리 dem 을 업로딩, cesium-customer : cesium docker provier';
comment on column geopolicy.terrain_value is 'url 또는 cesium ion code 값';

comment on column geopolicy.data_api_url is 'F4D converter file 정보 취득 api url';
comment on column geopolicy.data_service_path is '데이터 서비스 Root Path';
comment on column geopolicy.data_change_request_decision is '데이터 정보 변경 요청에 대한 처리. auto : 자동승인, approval : 결재(초기값)';

comment on column geopolicy.geoserver_enable is 'geoserver 사용유무. true : 사용, false : 미사용';
comment on column geopolicy.geoserver_wms_version is 'geoserver wms 버전';
comment on column geopolicy.geoserver_data_url is 'geoserver 데이터 URL';
comment on column geopolicy.geoserver_data_workspace is 'geoserver 데이터 작업공간';
comment on column geopolicy.geoserver_data_store is 'geoserver 데이터 저장소';
comment on column geopolicy.geoserver_user is 'geoserver 사용자 계정';
comment on column geopolicy.geoserver_password is 'geoserver 비밀번호';

comment on column geopolicy.geoserver_imageprovider_enable is 'geoserver imageprovider 사용 유무. 기본 false';
comment on column geopolicy.geoserver_imageprovider_url is 'geoserver imageprovider 요청 URL';
comment on column geopolicy.geoserver_imageprovider_layer_name is 'geoserver imageprovider 로 사용할 레이어명';
comment on column geopolicy.geoserver_imageprovider_style_name is 'geoserver imageprovider 에 사용할 스타일명';
comment on column geopolicy.geoserver_imageprovider_parameters_width is 'geoserver 레이어 이미지 가로크기';
comment on column geopolicy.geoserver_imageprovider_parameters_height is 'geoserver 레이어 이미지 세로크기';
comment on column geopolicy.geoserver_imageprovider_parameters_format is 'geoserver 레이어 포맷형식';

comment on column geopolicy.geoserver_terrainprovider_layer_name is 'geoserver terrainprovider 로 사용할 레이어명';
comment on column geopolicy.geoserver_terrainprovider_style_name  is 'geoserver terrainprovider 에 사용할 스타일명';
comment on column geopolicy.geoserver_terrainprovider_parameters_width is 'geoserver 레이어 이미지 가로크기';
comment on column geopolicy.geoserver_terrainprovider_parameters_height is 'geoserver 레이어 이미지 세로크기';
comment on column geopolicy.geoserver_terrainprovider_parameters_format is 'geoserver 레이어 포맷형식';

comment on column geopolicy.init_camera_enable is '초기 카메라 이동 유무. true : 기본, false : 없음';
comment on column geopolicy.init_latitude is '초기 카메라 이동 위도';
comment on column geopolicy.init_longitude is '초기 카메라 이동 경도';
comment on column geopolicy.init_altitude is '초기 카메라 이동 높이';
comment on column geopolicy.init_duration is '초기 카메라 이동 시간. 초 단위';
comment on column geopolicy.init_default_terrain is '기본 Terrain';
comment on column geopolicy.init_default_fov is 'field of view. 기본값 0(1.8 적용)';

comment on column geopolicy.lod0 is 'LOD0. 기본값 15M';
comment on column geopolicy.lod1 is 'LOD1. 기본값 60M';
comment on column geopolicy.lod2 is 'LOD2. 기본값 90M';
comment on column geopolicy.lod3 is 'LOD3. 기본값 200M';
comment on column geopolicy.lod4 is 'LOD4. 기본값 1000M';
comment on column geopolicy.lod5 is 'LOD5. 기본값 50000M';

comment on column geopolicy.ssao_radius is '그림자 반경';
comment on column geopolicy.cull_face_enable is 'cullFace 사용유무. 기본 false';
comment on column geopolicy.time_line_enable is 'timeLine 사용유무. 기본 false';

comment on column geopolicy.max_partitions_lod0 is 'LOD0일시 PointCloud 데이터 파티션 개수. 기본값 4';
comment on column geopolicy.max_partitions_lod1 is 'LOD1일시 PointCloud 데이터 파티션 개수. 기본값 2';
comment on column geopolicy.max_partitions_lod2_or_less is 'LOD2 이상 일시 PointCloud 데이터 파티션 개수. 기본값 1';
comment on column geopolicy.max_ratio_points_dist_0m is '카메라와의 거리가 10미터 미만일때, PointCloud 점의 갯수의 비율의 분모, 기본값 10';
comment on column geopolicy.max_ratio_points_dist_100m is '카메라와의 거리가 100미터 미만일때, PointCloud 점의 갯수의 비율의 분모, 기본값 120';
comment on column geopolicy.max_ratio_points_dist_200m is '카메라와의 거리가 200미터 미만일때, PointCloud 점의 갯수의 비율의 분모, 기본값 240';
comment on column geopolicy.max_ratio_points_dist_400m is '카메라와의 거리가 400미터 미만일때, PointCloud 점의 갯수의 비율의 분모, 기본값 480';
comment on column geopolicy.max_ratio_points_dist_800m is '카메라와의 거리가 800미터 미만일때, PointCloud 점의 갯수의 비율의 분모, 기본값 960';
comment on column geopolicy.max_ratio_points_dist_1600m is '카메라와의 거리가 1600미터 미만일때, PointCloud 점의 갯수의 비율의 분모, 기본값 1920';
comment on column geopolicy.max_ratio_points_dist_over_1600m is '카메라와의 거리가 1600미터 이상일때, PointCloud 점의 갯수의 비율의 분모, 기본값 3840';
comment on column geopolicy.max_point_size_for_pc is 'PointCloud 점의 최대 크기. 기본값 40.0';
comment on column geopolicy.min_point_size_for_pc is 'PointCloud 점의 최소 크기. 기본값 3.0';
comment on column geopolicy.pendent_point_size_for_pc is 'PointCloud 점의 크기 보정치. 높아질수록 점이 커짐. 기본값 60.0';
comment on column geopolicy.memory_management is 'GPU Memory Pool 사용유무. 기본값 false';

comment on column geopolicy.layer_source_coordinate is 'Layer 원본 좌표계';
comment on column geopolicy.layer_target_coordinate is 'Layer 좌표계 정의';

comment on column geopolicy.shape_land_required_columns is '디자인 레이어 토지 타입 shape 가시화를 위한 필수 속성명. 콤마로 구분';
comment on column geopolicy.shape_building_required_columns is '디자인 레이어 빌딩 타입 shape 가시화를 위한 필수 속성명. 콤마로 구분';

comment on column geopolicy.insert_date is '등록일';

drop table if exists issue cascade;
drop table if exists issue_detail cascade;
drop table if exists issue_comment cascade;
drop table if exists issue_file cascade;
drop table if exists issue_people cascade;

-- 이슈
create table issue (
	issue_id					bigint,
	data_group_id				integer							not null,
	data_id						bigint							not null,
	data_key 					varchar(128) 					not null,
	object_key 					varchar(256),

	user_id						varchar(32)	 					not null,
	title						varchar(300)					not null,
	priority					varchar(30),
	due_date					timestamp with time zone,
	issue_type					varchar(30),
	status						varchar(20),
	location 					GEOMETRY(Point, 4326),
	altitude					numeric(13,7),

	year						char(4)				default to_char(now(), 'YYYY'),
	month						varchar(2)			default to_char(now(), 'MM'),
	day							varchar(2)			default to_char(now(), 'DD'),
	year_week					varchar(2)			default to_char(now(), 'WW'),
	week						varchar(2)			default to_char(now(), 'W'),
	hour						varchar(2)			default to_char(now(), 'HH24'),
	minute						varchar(2)			default to_char(now(), 'MI'),

	client_ip					varchar(45)			not null,
	update_date					timestamp with time zone,
	insert_date					timestamp with time zone			default now(),
	constraint issue_pk primary key (issue_id)
);

comment on table issue is '이슈';
comment on column issue.issue_id is '고유번호';
comment on column issue.data_group_id is '데이터 그룹 고유 번호';
comment on column issue.data_id is '데이터 고유 번호';
comment on column issue.data_key is 'Data key';
comment on column issue.object_key is 'Object key';

comment on column issue.user_id is '사용자 아이디';
comment on column issue.title is '이슈명';
comment on column issue.priority is '우선순위. common_code 동적 생성';
comment on column issue.due_date is '예정일. 마감일';
comment on column issue.issue_type is '이슈 유형. common_code 동적 생성';
comment on column issue.status is '상태. 등록 : insert, 진행중 : process, 완료 : complete, reject : 반려';
comment on column issue.location is 'location(위도, 경도)';
comment on column issue.altitude is '높이';

comment on column issue.year is '년';
comment on column issue.month is '월';
comment on column issue.day is '일';
comment on column issue.year_week is '일년중 몇주';
comment on column issue.week is '이번달 몇주';
comment on column issue.hour is '시간';
comment on column issue.minute is '분';

comment on column issue.client_ip is '요청 IP';
comment on column issue.update_date is '수정일';
comment on column issue.insert_date is '등록일';

-- 이슈 상세
create table issue_detail (
	issue_detail_id				bigint,
	issue_id					bigint 								not null,
	contents					text								not null,
	insert_date					timestamp with time zone			default now(),
	constraint issue_detail_pk 	primary key (issue_detail_id)
);

comment on table issue_detail is '이슈 상세';
comment on column issue_detail.issue_detail_id is '이슈 상세 고유번호';
comment on column issue_detail.issue_id is '이슈 고유번호';
comment on column issue_detail.contents is '내용';
comment on column issue.insert_date is '등록일';

drop table if exists layer cascade;
drop table if exists layer_group cascade;
drop table if exists layer_file_info cascade;

create table layer_group (
	layer_group_id				integer,
	layer_group_name      		varchar(256)					not null,
	user_id						varchar(32),
	ancestor					integer							default 0,
	parent                		integer							default 0,
	depth                	  	integer							default 1,
	view_order					integer							default 1,
	children					integer							default 0,
	available					boolean							default true,
	description					varchar(256),
	update_date             	timestamp with time zone,
	insert_date					timestamp with time zone		default now(),
	constraint layer_group_pk 		primary key (layer_group_id)
);

comment on table layer_group is '레이어 그룹';
comment on column layer_group.layer_group_id is '레이어 그룹 고유번호';
comment on column layer_group.layer_group_name is '레이어 그룹명';
comment on column layer_group.user_id is '사용자 아이디';
comment on column layer_group.ancestor is '조상';
comment on column layer_group.parent is '부모';
comment on column layer_group.depth is '깊이';
comment on column layer_group.view_order is '나열 순서';
comment on column layer_group.children is '자식 존재 개수';
comment on column layer_group.available is '사용 여부';
comment on column layer_group.description is '설명';
comment on column layer_group.update_date is '수정일';
comment on column layer_group.insert_date is '등록일';

-- layer 관리
create table layer (
	layer_id					integer,
	layer_group_id				integer,
	layer_key					varchar(100)					not null,
	layer_name					varchar(256)					not null,
	user_id						varchar(32),

	sharing						varchar(30)						default 'public',
	ogc_web_services			varchar(30),
	layer_type					varchar(30),
	layer_insert_type			varchar(30),
	geometry_type				varchar(30),

	layer_fill_color			varchar(30),
	layer_line_color			varchar(30),
	layer_line_style			numeric,
	layer_alpha_style			numeric,

	view_order					integer							default 1,
	z_index						integer,
	default_display				boolean							default false,
	available					boolean							default true,
	label_display				boolean							default false,
	cache_available				boolean							default false,

	coordinate					varchar(256),
	description					varchar(256),
	update_date					timestamp with time zone		default now(),
	insert_date					timestamp with time zone 		default now(),
	constraint layer_pk 		primary key (layer_id)
);

comment on table layer is '레이어';
comment on column layer.layer_id is '레이어 고유번호';
comment on column layer.layer_group_id is '레이어 그룹 고유번호';
comment on column layer.layer_key is '레이어 고유키(API용)';
comment on column layer.layer_name is '레이어명';
comment on column layer.user_id is '사용자명';
comment on column layer.sharing is '공유 유형. common : 공통, public : 공개, private : 개인, group : 그룹';
comment on column layer.ogc_web_services is 'OGC Web Services (wms, wfs, wcs, wps)';
comment on column layer.layer_type is '레이어 타입 (Raster, Vector)';
comment on column layer.layer_insert_type is '레이어 등록 타입(파일, geoserver)';
comment on column layer.geometry_type is '도형 타입';
comment on column layer.layer_fill_color is '외곽선 색상';
comment on column layer.layer_line_color is '외곽선 두께';
comment on column layer.layer_line_style is '채우기 색상';
comment on column layer.layer_alpha_style is '투명도';
comment on column layer.view_order is '나열 순서';
comment on column layer.z_index is '지도위에 노출 순위(css z-index와 동일)';
comment on column layer.default_display is '기본 표시';
comment on column layer.available is '사용유무.';
comment on column layer.label_display is '레이블 표시';
comment on column layer.cache_available is '캐시 사용 유무';
comment on column layer.coordinate is '좌표계 정보';
comment on column layer.description is '설명';
comment on column layer.update_date is '수정일';
comment on column layer.insert_date is '등록일';


-- layer shape 파일 관리
create table layer_file_info (
	layer_file_info_id			    integer,
	layer_id					    integer							not null,
	layer_file_info_team_id	        integer,
	user_id						    varchar(32)						not null,
	enable_yn					    char(1)							default 'N',
	file_name					    varchar(100)					not null,
	file_real_name				    varchar(100)					not null,
	file_path					    varchar(256)					not null,
	file_size					    varchar(12)						not null,
	file_ext					    varchar(10)						not null,
	shape_encoding				    varchar(100),
	version_id					    integer							default 0,
	update_date					    timestamp with time zone,
	insert_date					    timestamp with time zone		default now(),
	constraint layer_file_info_pk primary key (layer_file_info_id)
);

comment on table layer_file_info is 'layer shape 파일 관리';
comment on column layer_file_info.layer_file_info_id is '파일 고유번호';
comment on column layer_file_info.layer_id is '파일 고유번호';
comment on column layer_file_info.layer_file_info_team_id is 'shape 파일 그룹 아이디. .shp 파일의 layer_file_info_id 를 team_id로 함';
comment on column layer_file_info.user_id is '사용자 id';
comment on column layer_file_info.enable_yn is 'layer 활성화 유무. Y: 활성화, N: 비활성화';
comment on column layer_file_info.file_name is '파일 이름';
comment on column layer_file_info.file_real_name is '파일 실제 이름';
comment on column layer_file_info.file_path is '파일 경로';
comment on column layer_file_info.file_size is '파일 용량';
comment on column layer_file_info.file_ext is '파일 확장자';
comment on column layer_file_info.shape_encoding is 'Shape 파일 인코딩';
comment on column layer_file_info.version_id is 'shape 파일 버전 정보';
comment on column layer_file_info.update_date is '갱신일';
comment on column layer_file_info.insert_date is '등록일';


drop table if exists menu cascade;

-- 메뉴
create table menu(
	menu_id				integer,
	menu_type			char(1)									default '0',
	menu_target			char(1),
	name				varchar(100)							not null,
	name_en				varchar(30)								not null,
	lang				varchar(10)								default 'ko',
	ancestor			integer,
	parent				integer									default 1,
	depth				integer									default 1,
	view_order			integer									default 1,
	url					varchar(256),
	url_alias			varchar(256),
	alias_menu_id		integer,
	html_id				varchar(100),
	html_content_id		varchar(100),
	image				varchar(256),
	image_alt			varchar(100),
	css_class			varchar(30),
	default_yn			char(1)									default 'N',
	use_yn				char(1)									default 'Y',
	display_yn			char(1)									default 'Y',
	description			varchar(256),
	insert_date			timestamp with time zone				default now(),
	constraint menu_pk primary key (menu_id)
);


comment on table menu is '메뉴';
comment on column menu.menu_id is '고유번호';
comment on column menu.menu_type is '메뉴 타입, 0 : URL 기반(기본값), 1 : HTML ID';
comment on column menu.menu_target is '메뉴 Target, 0 : 사용자 사이트, 1 : 관리자 사이트';
comment on column menu.name is '메뉴명';
comment on column menu.name_en is '영어 메뉴명';
comment on column menu.lang is '언어';
comment on column menu.ancestor is '조상';
comment on column menu.parent is '부모 고유번호';
comment on column menu.depth is '깊이';
comment on column menu.view_order is '나열 순서';
comment on column menu.url is 'URL';
comment on column menu.url_alias is 'URL Alias';
comment on column menu.alias_menu_id is 'URL Alias Menu id, 현재 선택 메뉴를 표시하기 위함';
comment on column menu.html_id is '메뉴 타입이 HTML ID 일 경우 id값';
comment on column menu.html_content_id is '메뉴 타입이 HTML ID 일 경우 메뉴와 한쌍으로 묶이는 content id값';
comment on column menu.image is '이미지';
comment on column menu.image_alt is '이미지 Alt';
comment on column menu.css_class is 'css class명';
comment on column menu.default_yn is '기본 표시 메뉴, Y : 기본, N : 선택';
comment on column menu.use_yn is '사용유무, Y : 사용, N : 사용안함';
comment on column menu.display_yn is '화면표시 여부, Y : 표시, N : 비표시';
comment on column menu.description is '설명';
comment on column menu.insert_date is '등록일';

drop table if exists policy cascade;

-- 운영정책
create table policy(
	policy_id								integer,

	user_id_min_length						integer				default 5,
	user_fail_signin_count					integer				default 3,
	user_fail_lock_release					varchar(3)			default '30',
	user_last_signin_lock					varchar(3)			default '90',
	user_duplication_signin_yn				char(1)				default 'N',
	user_signin_type						char(1)				default '0',
	user_update_check						char(1)				default '0',
	user_delete_check						char(1)				default '0',
	user_delete_type						char(1)				default '0',

	password_change_term 					varchar(3)			default '30',
	password_min_length						integer				default 8,
	password_max_length						integer				default 32,
	password_eng_upper_count 				integer				default 1,
	password_eng_lower_count 				integer				default 1,
	password_number_count 					integer				default 1,
	password_special_char_count 			integer				default 1,
	password_continuous_char_count 			integer				default 3,
	password_create_type					char(1)				default '0',
	password_create_char					varchar(32)			default '!@#',
	password_exception_char					varchar(10)			default '<>&',

	notice_service_yn						char(1)				default 'Y',
	notice_service_send_type				char(1)				default '0',
	notice_approval_request_yn				char(1)				default 'N',
	notice_approval_sign_yn					char(1)				default 'N',
	notice_password_update_yn				char(1)				default 'N',
	notice_approval_delay_yn				char(1)				default 'N',
	notice_risk_yn							char(1)				default 'N',
	notice_risk_send_type					char(1)				default '0',
	notice_risk_grade						char(1)				default '1',

	security_session_timeout_yn				char(1)				default 'N',
	security_session_timeout				varchar(4)			default '30',
	security_user_ip_check_yn				char(1)				default 'N',
	security_session_hijacking				char(1)				default '0',
	security_log_save_type					char(1)				default '0',
	security_log_save_term					varchar(3)			default '2',
	security_dynamic_block_yn				char(1)				default 'N',
	security_api_result_secure_yn			char(1)				default 'N',
	security_masking_yn						char(1)				default 'Y',

	content_cache_version					integer				default 1,
	content_main_widget_count				integer				default 6,
	content_main_widget_interval			integer				default 65,
	content_monitoring_interval				integer				default 1,
	content_statistics_interval				char(1)				default '0',
	content_load_balancing_interval			integer				default 10,
	content_menu_group_root					varchar(60)			default 'LHDT',
	content_user_group_root					varchar(60)			default 'LHDT',
	content_layer_group_root				varchar(60)			default 'LHDT',
	content_data_group_root					varchar(60)			default 'LHDT',
	content_design_layer_group_root			varchar(60)			default 'LHDT',
	content_data_library_group_root			varchar(60)			default 'LHDT',
	content_urban_group_root			    varchar(60)			default 'LHDT',

	user_upload_type						varchar(256)		default '3ds,obj,dae,collada,ifc,las,gml,citygml,indoorgml,jpg,jpeg,gif,png,bmp,dds,zip,mtl,max',
	user_converter_type						varchar(256)		default '3ds,obj,dae,collada,ifc,las,gml,citygml,indoorgml',
	user_upload_max_filesize				integer				default 10000,
	user_upload_max_count					integer				default 500,
	shape_upload_type						varchar(256)		default 'cpg,dbf,idx,sbn,sbx,shp,shx,prj,qpj,zip',

	insert_date								timestamp with time zone			default now(),
	constraint policy_pk primary key (policy_id)
);

comment on table policy is '운영정책';
comment on column policy.policy_id is '고유번호';

comment on column policy.user_id_min_length is '사용자 아이디 최소 길이. 기본값 5';
comment on column policy.user_fail_signin_count is '사용자 사인인 실패 횟수';
comment on column policy.user_fail_lock_release is '사용자 사인인 실패 잠금 해제 기간';
comment on column policy.user_last_signin_lock is '사용자 마지막 사인인으로 부터 잠금 기간';
comment on column policy.user_duplication_signin_yn is '중복 사인인 허용 여부. Y : 허용, N : 허용안함(기본값)';
comment on column policy.user_signin_type is '사용자 사인인 인증 방법. 0 : 일반(아이디/비밀번호(기본값)), 1 : 기업용(사번추가), 2 : 일반 + OTP, 3 : 일반 + 인증서, 4 : OTP + 인증서, 5 : 일반 + OTP + 인증서';
comment on column policy.user_update_check is '사용자 정보 수정시 확인';
comment on column policy.user_delete_check is '사용자 정보 삭제시 확인';
comment on column policy.user_delete_type is '사용자 정보 삭제 방법. 0 : 논리적(기본값), 1 : 물리적(DB 삭제)';

comment on column policy.password_change_term is '패스워드 변경 주기 기본 30일';
comment on column policy.password_min_length is '패스워드 최소 길이 기본 8';
comment on column policy.password_max_length is '패스워드 최대 길이 기본 32';
comment on column policy.password_eng_upper_count is '패스워드 영문 대문자 개수 기본 1';
comment on column policy.password_eng_lower_count is '패스워드 영문 소문자 개수 기본 1';
comment on column policy.password_number_count is '패스워드 숫자 개수 기본 1';
comment on column policy.password_special_char_count is '패스워드 특수 문자 개수 1';
comment on column policy.password_continuous_char_count is '패스워드 연속문자 제한 개수 3';
comment on column policy.password_create_type is '초기 패스워드 생성 방법. 0 : 사용자 아이디 + 초기문자(기본값), 1 : 초기문자';
comment on column policy.password_create_char is '초기 패스워드 생성 문자열. 엑셀 업로드 등';
comment on column policy.password_exception_char is '패스워드로 사용할수 없는 특수문자(XSS). <,>,&,작은따음표,큰따움표';

comment on column policy.notice_service_yn is '알림 서비스 사용 유무. Y : 사용, N : 사용안함(기본값)';
comment on column policy.notice_service_send_type is '알림 발송 매체. 0 : SMS(기본값), 1 : 이메일, 2 : 메신저';
comment on column policy.notice_risk_yn is '알림 장애 발생시. Y : 사용, N 사용안함(기본값)';
comment on column policy.notice_risk_send_type is '알림 장애 발송 매체. 0 : SMS(기본값), 1 : 이메일, 2 : 메신저';
comment on column policy.notice_risk_grade is '알림 발송 장애 등급. 1 : 1등급(기본값), 2 : 2등급, 3 : 3등급';

comment on column policy.security_session_timeout_yn is '보안 세션 타임아웃. Y : 사용, N 사용안함(기본값)';
comment on column policy.security_session_timeout is '보안 세션 타임아웃 시간. 기본값 30분';
comment on column policy.security_user_ip_check_yn is '로그인 사용자 IP 체크 유무. Y : 사용, N 사용안함(기본값)';
comment on column policy.security_session_hijacking is '보안 세션 하이재킹 처리. 0 : 사용안함, 1 : 사용(기본값), 2 : OTP 추가 인증 ';
comment on column policy.security_log_save_type is '보안 로그 저장 방법. 0 : DB(기본값), 1 : 파일';
comment on column policy.security_log_save_term is '보안 로그 보존 기간. 2년 기본값';
comment on column policy.security_dynamic_block_yn is '보안 동적 차단. Y : 사용, N 사용안함(기본값)';
comment on column policy.security_api_result_secure_yn is 'API 결과 암호화 사용. Y : 사용, N 사용안함(기본값)';
comment on column policy.security_masking_yn is '개인정보 마스킹 처리. Y : 사용(기본값), N 사용안함';

comment on column policy.content_cache_version is 'css, js 갱신용 cache version.';
comment on column policy.content_main_widget_count is '메인 화면 위젯 표시 갯수. 기본 6개';
comment on column policy.content_main_widget_interval is '메인 화면 위젯 Refresh 간격. 기본 65초(모니터링 간격 60초에 대한 시간 간격 고려)';
comment on column policy.content_statistics_interval is '통계 기본 검색 기간. 0 : 1년 단위, 1 : 상/하반기, 2 : 분기 단위, 3 : 월 단위';
comment on column policy.content_menu_group_root is '메뉴 그룹 최상위 그룹명';
comment on column policy.content_user_group_root is '사용자 그룹 최상위 그룹명';
comment on column policy.content_layer_group_root is '레이어 그룹 최상위 그룹명';
comment on column policy.content_data_group_root is '데이터 그룹 최상위 그룹명';
comment on column policy.content_design_layer_group_root is '디자인 레이어 그룹 최상위 그룹명';
comment on column policy.content_data_library_group_root is '데이터 라이브러리 그룹 최상위 그룹명';
comment on column policy.content_urban_group_root is '도시 그룹 최상위 그룹명';

comment on column policy.user_upload_type is '업로딩 가능 확장자. 3ds,obj,dae,collada,ifc,las,gml,citygml,indoorgml,jpg,jpeg,gif,png,bmp,zip';
comment on column policy.user_converter_type is '변환 가능 확장자. 3ds,obj,dae,collada,ifc,las,gml,citygml,indoorgml';
comment on column policy.user_upload_max_filesize is '최대 업로딩 사이즈(단위M). 500M';
comment on column policy.user_upload_max_count is '1회, 최대 업로딩 파일 수. 50개';
comment on column policy.shape_upload_type is 'shape 파일 업로드 가능 확장자';

comment on column policy.insert_date is '등록일';

-- Thanks to Patrick Lightbody for submitting this...
--
-- In your Quartz properties file, you'll need to set
-- org.quartz.jobStore.driverDelegateClass = org.quartz.impl.jdbcjobstore.PostgreSQLDelegate

DROP TABLE IF EXISTS qrtz_fired_triggers CASCADE;
DROP TABLE IF EXISTS qrtz_paused_trigger_grps CASCADE;
DROP TABLE IF EXISTS qrtz_scheduler_state CASCADE;
DROP TABLE IF EXISTS qrtz_locks CASCADE;
DROP TABLE IF EXISTS qrtz_simple_triggers CASCADE;
DROP TABLE IF EXISTS qrtz_cron_triggers CASCADE;
DROP TABLE IF EXISTS qrtz_simprop_triggers CASCADE;
DROP TABLE IF EXISTS qrtz_blob_triggers CASCADE;
DROP TABLE IF EXISTS qrtz_triggers CASCADE;
DROP TABLE IF EXISTS qrtz_job_details CASCADE;
DROP TABLE IF EXISTS qrtz_calendars CASCADE;

CREATE TABLE qrtz_job_details
(
    SCHED_NAME VARCHAR(120) NOT NULL,
    JOB_NAME  VARCHAR(200) NOT NULL,
    JOB_GROUP VARCHAR(200) NOT NULL,
    DESCRIPTION VARCHAR(250) NULL,
    JOB_CLASS_NAME   VARCHAR(250) NOT NULL,
    IS_DURABLE BOOL NOT NULL,
    IS_NONCONCURRENT BOOL NOT NULL,
    IS_UPDATE_DATA BOOL NOT NULL,
    REQUESTS_RECOVERY BOOL NOT NULL,
    JOB_DATA BYTEA NULL,
    PRIMARY KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
);

CREATE TABLE qrtz_triggers
(
    SCHED_NAME VARCHAR(120) NOT NULL,
    TRIGGER_NAME VARCHAR(200) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    JOB_NAME  VARCHAR(200) NOT NULL,
    JOB_GROUP VARCHAR(200) NOT NULL,
    DESCRIPTION VARCHAR(250) NULL,
    NEXT_FIRE_TIME BIGINT NULL,
    PREV_FIRE_TIME BIGINT NULL,
    PRIORITY INTEGER NULL,
    TRIGGER_STATE VARCHAR(16) NOT NULL,
    TRIGGER_TYPE VARCHAR(8) NOT NULL,
    START_TIME BIGINT NOT NULL,
    END_TIME BIGINT NULL,
    CALENDAR_NAME VARCHAR(200) NULL,
    MISFIRE_INSTR SMALLINT NULL,
    JOB_DATA BYTEA NULL,
    PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
        REFERENCES QRTZ_JOB_DETAILS(SCHED_NAME,JOB_NAME,JOB_GROUP)
);

CREATE TABLE qrtz_simple_triggers
(
    SCHED_NAME VARCHAR(120) NOT NULL,
    TRIGGER_NAME VARCHAR(200) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    REPEAT_COUNT BIGINT NOT NULL,
    REPEAT_INTERVAL BIGINT NOT NULL,
    TIMES_TRIGGERED BIGINT NOT NULL,
    PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
        REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);

CREATE TABLE qrtz_cron_triggers
(
    SCHED_NAME VARCHAR(120) NOT NULL,
    TRIGGER_NAME VARCHAR(200) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    CRON_EXPRESSION VARCHAR(120) NOT NULL,
    TIME_ZONE_ID VARCHAR(80),
    PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
        REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);

CREATE TABLE qrtz_simprop_triggers
(
    SCHED_NAME VARCHAR(120) NOT NULL,
    TRIGGER_NAME VARCHAR(200) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    STR_PROP_1 VARCHAR(512) NULL,
    STR_PROP_2 VARCHAR(512) NULL,
    STR_PROP_3 VARCHAR(512) NULL,
    INT_PROP_1 INT NULL,
    INT_PROP_2 INT NULL,
    LONG_PROP_1 BIGINT NULL,
    LONG_PROP_2 BIGINT NULL,
    DEC_PROP_1 NUMERIC(13,4) NULL,
    DEC_PROP_2 NUMERIC(13,4) NULL,
    BOOL_PROP_1 BOOL NULL,
    BOOL_PROP_2 BOOL NULL,
    PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
        REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);

CREATE TABLE qrtz_blob_triggers
(
    SCHED_NAME VARCHAR(120) NOT NULL,
    TRIGGER_NAME VARCHAR(200) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    BLOB_DATA BYTEA NULL,
    PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
        REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);

CREATE TABLE qrtz_calendars
(
    SCHED_NAME VARCHAR(120) NOT NULL,
    CALENDAR_NAME  VARCHAR(200) NOT NULL,
    CALENDAR BYTEA NOT NULL,
    PRIMARY KEY (SCHED_NAME,CALENDAR_NAME)
);


CREATE TABLE qrtz_paused_trigger_grps
(
    SCHED_NAME VARCHAR(120) NOT NULL,
    TRIGGER_GROUP  VARCHAR(200) NOT NULL,
    PRIMARY KEY (SCHED_NAME,TRIGGER_GROUP)
);

CREATE TABLE qrtz_fired_triggers
(
    SCHED_NAME VARCHAR(120) NOT NULL,
    ENTRY_ID VARCHAR(95) NOT NULL,
    TRIGGER_NAME VARCHAR(200) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    INSTANCE_NAME VARCHAR(200) NOT NULL,
    FIRED_TIME BIGINT NOT NULL,
    SCHED_TIME BIGINT NOT NULL,
    PRIORITY INTEGER NOT NULL,
    STATE VARCHAR(16) NOT NULL,
    JOB_NAME VARCHAR(200) NULL,
    JOB_GROUP VARCHAR(200) NULL,
    IS_NONCONCURRENT BOOL NULL,
    REQUESTS_RECOVERY BOOL NULL,
    PRIMARY KEY (SCHED_NAME,ENTRY_ID)
);

CREATE TABLE qrtz_scheduler_state
(
    SCHED_NAME VARCHAR(120) NOT NULL,
    INSTANCE_NAME VARCHAR(200) NOT NULL,
    LAST_CHECKIN_TIME BIGINT NOT NULL,
    CHECKIN_INTERVAL BIGINT NOT NULL,
    PRIMARY KEY (SCHED_NAME,INSTANCE_NAME)
);

CREATE TABLE qrtz_locks
(
    SCHED_NAME VARCHAR(120) NOT NULL,
    LOCK_NAME  VARCHAR(40) NOT NULL,
    PRIMARY KEY (SCHED_NAME,LOCK_NAME)
);

create index idx_qrtz_j_req_recovery on qrtz_job_details(SCHED_NAME,REQUESTS_RECOVERY);
create index idx_qrtz_j_grp on qrtz_job_details(SCHED_NAME,JOB_GROUP);

create index idx_qrtz_t_j on qrtz_triggers(SCHED_NAME,JOB_NAME,JOB_GROUP);
create index idx_qrtz_t_jg on qrtz_triggers(SCHED_NAME,JOB_GROUP);
create index idx_qrtz_t_c on qrtz_triggers(SCHED_NAME,CALENDAR_NAME);
create index idx_qrtz_t_g on qrtz_triggers(SCHED_NAME,TRIGGER_GROUP);
create index idx_qrtz_t_state on qrtz_triggers(SCHED_NAME,TRIGGER_STATE);
create index idx_qrtz_t_n_state on qrtz_triggers(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP,TRIGGER_STATE);
create index idx_qrtz_t_n_g_state on qrtz_triggers(SCHED_NAME,TRIGGER_GROUP,TRIGGER_STATE);
create index idx_qrtz_t_next_fire_time on qrtz_triggers(SCHED_NAME,NEXT_FIRE_TIME);
create index idx_qrtz_t_nft_st on qrtz_triggers(SCHED_NAME,TRIGGER_STATE,NEXT_FIRE_TIME);
create index idx_qrtz_t_nft_misfire on qrtz_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME);
create index idx_qrtz_t_nft_st_misfire on qrtz_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_STATE);
create index idx_qrtz_t_nft_st_misfire_grp on qrtz_triggers(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_GROUP,TRIGGER_STATE);

create index idx_qrtz_ft_trig_inst_name on qrtz_fired_triggers(SCHED_NAME,INSTANCE_NAME);
create index idx_qrtz_ft_inst_job_req_rcvry on qrtz_fired_triggers(SCHED_NAME,INSTANCE_NAME,REQUESTS_RECOVERY);
create index idx_qrtz_ft_j_g on qrtz_fired_triggers(SCHED_NAME,JOB_NAME,JOB_GROUP);
create index idx_qrtz_ft_jg on qrtz_fired_triggers(SCHED_NAME,JOB_GROUP);
create index idx_qrtz_ft_t_g on qrtz_fired_triggers(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP);
create index idx_qrtz_ft_tg on qrtz_fired_triggers(SCHED_NAME,TRIGGER_GROUP);


commit;

drop table if exists role cascade;


-- Role role_key unique 제약 조건 걸어야 함
create table role(
	role_id						integer,
	role_name					varchar(100)							not null,
	role_key					varchar(50)								not null,
	role_target					char(1)									not null,
	role_type					varchar(2)								not null,
	use_yn						char(1)									default 'Y',
	default_yn					char(1)									default 'N',
	description					varchar(256),
	insert_date					timestamp with time zone default now(),
	constraint role_pk primary key (role_id)
);

comment on table role is 'Role';
comment on column role.role_id is '고유번호';
comment on column role.role_name is 'Role 명';
comment on column role.role_key is 'Role KEY';
comment on column role.role_target is 'Role 타켓. 0 : 사용자 사이트, 1 : 관리자 사이트, 2 : 서버';
comment on column role.role_type is 'Role 업무 유형. 0 : 사용자, 1 : 서버, 2 : api';
comment on column role.use_yn is '사용유무. Y : 사용, N : 사용안함';
comment on column role.default_yn is '기본사용 유무. Y : 사용, N : 사용안함';
comment on column role.description is '설명';
comment on column role.insert_date is '등록일';

-- FK, Index 는 별도 파일로 분리. 맨 마지막에 작업 예정
drop table if exists upload_data cascade;
drop table if exists upload_data_file cascade;

-- 사용자 업로드 정보
create table upload_data (
	upload_data_id					bigint,
	data_group_id					int,
	sharing							varchar(30)							default 'public',
	data_type						varchar(30),
	data_name						varchar(256),
	user_id							varchar(32),
	mapping_type					varchar(30)							default 'origin',
	location		 				GEOMETRY(POINT, 4326),
	altitude						numeric(13,7),
	file_count						int									default 0,
	converter_target_count			int									default 0,
	converter_count					int 								default 0,
	status							varchar(20)							default 'upload',
	year							char(4)								default to_char(now(), 'yyyy'),
	month							varchar(2)							default to_char(now(), 'MM'),
	day								varchar(2)							default to_char(now(), 'DD'),
	year_week						varchar(2)							default to_char(now(), 'WW'),
	week							varchar(2)							default to_char(now(), 'W'),
	hour							varchar(2)							default to_char(now(), 'HH24'),
	minute							varchar(2)							default to_char(now(), 'MI'),
	description						varchar(256),
	update_date						timestamp with time zone,
	insert_date						timestamp with time zone			default now(),
	constraint upload_data_pk		primary key (upload_data_id)
);

comment on table upload_data is '사용자 업로드 정보';
comment on column upload_data.upload_data_id is '고유번호';
comment on column upload_data.data_group_id is '데이터 그룹 고유번호';
comment on column upload_data.sharing is '공유 유형. 0 : common, 1: public, 2 : private, 3 : sharing';
comment on column upload_data.data_type is '데이터 타입. 3ds,obj,dae,collada,ifc,las,citygml,indoorgml,gml,jpg,jpeg,gif,png,bmp,zip,mtl';
comment on column upload_data.data_name is '데이터명';
comment on column upload_data.user_id is '사용자 아이디';
comment on column upload_data.mapping_type is '기본값 origin : latitude, longitude, height를 origin에 맞춤. boundingboxcenter : latitude, longitude, height를 boundingboxcenter 맞춤';
comment on column upload_data.location is 'POINT(위도, 경도). 공간 검색 속도 때문에 altitude는 분리';
comment on column upload_data.altitude is '높이';
comment on column upload_data.file_count is '파일 개수';
comment on column upload_data.converter_target_count is 'converter 변환 대상 파일 수';
comment on column upload_data.converter_count is 'converter 횟수';
comment on column upload_data.status is '상태. upload : 업로딩 완료, converter : 변환';
comment on column upload_data.year is '년';
comment on column upload_data.month is '월';
comment on column upload_data.day is '일';
comment on column upload_data.year_week is '일년중 몇주';
comment on column upload_data.week is '이번달 몇주';
comment on column upload_data.hour is '시간';
comment on column upload_data.minute is '분';
comment on column upload_data.description is '설명';
comment on column upload_data.update_date is '수정일';
comment on column upload_data.insert_date is '등록일';


-- 사용자 업로드 파일 정보
create table upload_data_file(
	upload_data_file_id				bigint,
	upload_data_id					bigint,
	converter_target				boolean								default false,
	user_id							varchar(32),
	file_type						varchar(9)							default 'file',
	file_name						varchar(100)						not null,
	file_real_name					varchar(100)						not null,
	file_path						varchar(256)						not null,
	file_sub_path					varchar(256),
	file_size						varchar(12)							not null,
	file_ext						varchar(20),
	depth							int									default 1,
	error_message					varchar(256),
	insert_date						timestamp with time zone			default now(),
	constraint upload_data_file_pk	primary key (upload_data_file_id)
);

comment on table upload_data_file is '사용자 업로드 파일 정보 ';
comment on column upload_data_file.upload_data_file_id is '고유번호';
comment on column upload_data_file.upload_data_id is '사용자 업로드 정보 고유번호';
comment on column upload_data_file.converter_target is 'converter 대상 파일 유무. Y : 대상, N : 대상아님(기본값)';
comment on column upload_data_file.user_id is '사용자 아이디';
comment on column upload_data_file.file_type is '디렉토리/파일 구분. D : 디렉토리, F : 파일';
comment on column upload_data_file.file_name is '파일 이름';
comment on column upload_data_file.file_real_name is '파일 실제 이름';
comment on column upload_data_file.file_path is '파일 경로';
comment on column upload_data_file.file_sub_path is '프로젝트 경로 또는 공통 디렉토리 이하 부터의 파일 경로';
comment on column upload_data_file.file_size is '파일 사이즈';
comment on column upload_data_file.file_ext is '파일 확장자';
comment on column upload_data_file.depth is '계층구조 깊이. 1부터 시작';
comment on column upload_data_file.error_message is '오류 메시지';
comment on column upload_data_file.insert_date is '등록일';


-- FK, Index 는 별도 파일로 분리. 맨 마지막에 작업 예정
drop table if exists urban_group cascade;
drop table if exists urban cascade;

-- 도시 그룹
create table urban_group (
	urban_group_id				    integer,
	urban_group_key				    varchar(60)							not null ,
	urban_group_name				varchar(100)						not null,
	user_id						    varchar(32),
	ancestor					    integer								default 0,
	parent						    integer								default 1,
	depth						    integer								default 1,
	view_order					    integer								default 1,
	children					    integer								default 0,
	basic					    	boolean								default false,
	available					    boolean								default true,

    start_date                      timestamp with time zone,
    end_date                        timestamp with time zone,
    location		 			    GEOMETRY(POINT, 4326),
    area                            bigint                              default 0,
    receiving_population            int                                 default 0,
    receiving_household             int                                 default 0,
    project_operator                varchar(30),
    transfer_local_government       varchar(30),

    description					    varchar(256),
    update_date             	    timestamp with time zone,
	insert_date					    timestamp with time zone			default now(),
	constraint urban_group_pk 	    primary key (urban_group_id)
);

comment on table urban_group is '도시 그룹';
comment on column urban_group.urban_group_id is '고유번호';
comment on column urban_group.urban_group_key is '링크 활용 등을 위한 확장 컬럼';
comment on column urban_group.urban_group_name is '그룹명';
comment on column urban_group.user_id is '사용자 아이디';
comment on column urban_group.ancestor is '조상 고유번호';
comment on column urban_group.parent is '부모 고유번호';
comment on column urban_group.depth is '깊이';
comment on column urban_group.view_order is '나열 순서';
comment on column urban_group.children is '자식 존재 개수';
comment on column urban_group.basic is 'true : 기본(초기 등록), false : 선택';
comment on column urban_group.available is '사용유무, true : 사용, false : 사용안함';

comment on column urban_group.start_date is '시작일';
comment on column urban_group.end_date is '종료일';
comment on column urban_group.location is 'POINT(위도, 경도)';
comment on column urban_group.area is '면적';
comment on column urban_group.receiving_population is '수용 인구';
comment on column urban_group.receiving_household is '수용 세대';
comment on column urban_group.project_operator is '사업 시행자';
comment on column urban_group.transfer_local_government is '지자체로 양도 시기';
comment on column urban_group.available is '사용유무, true : 사용, false : 사용안함';

comment on column urban_group.description is '설명';
comment on column urban_group.insert_date is '등록일';

-- new town 정보

/*create table urban (
	urban_id						integer,
	urban_group_id				integer								not null,
	urban_name					varchar(64)							not null,
	user_id						    varchar(32),

    business_period                 varchar(30),
    development_area                int,
    population                      int,
    self_sufficient_area            int,
    house_number                    int,
    job_creation                    int,

	insert_date					timestamp with time zone			default now(),
	constraint urban_pk primary key(urban_id)
);

comment on table urban is 'new town 정보';
comment on column urban.urban_id is '고유번호';
comment on column urban.urban_group_id is 'new town 그룹 고유번호';
comment on column urban.urban_name is 'new town명';
comment on column urban.user_id is '사용자 아이디';

comment on column urban.business_period is '사업 기간';
comment on column urban.development_area is '개발 면적. 단위 m*m';
comment on column urban.population is '인구수. 단위 명';
comment on column urban.self_sufficient_area is '자족 용지. 단위 m*m';
comment on column urban.house_number is '주택수. 단위 호';
comment on column urban.job_creation is '고용 창출. 단위 명';

comment on column urban.insert_date is '등록일';*/








-- FK, Index 는 별도 파일로 분리. 맨 마지막에 작업 예정
drop table if exists user_info cascade;
drop table if exists user_device cascade;
drop table if exists user_group cascade;
drop table if exists user_group_role cascade;
drop table if exists user_group_menu cascade;


-- 사용자 기본정보
create table user_info(
	user_id						varchar(32),
	user_group_id				integer								not null,
	user_name					varchar(64)							not null,
	password					varchar(512)						not null,
	salt						varchar(256),
	telephone					varchar(256),
	mobile_phone				varchar(256),
	email						varchar(256),
	messanger					varchar(100),
	postal_code					varchar(6),
	address						varchar(256),
	address_etc					varchar(1000),
	status						varchar(20)							default '0',
	user_role_check_yn			char(1)								default 'Y',
	signin_count				bigint								default 0,
	fail_signin_count			integer								default 0,
	last_signin_date			timestamp with time zone,
	last_password_change_date	timestamp with time zone			default now(),
	update_date					timestamp with time zone,
	insert_date					timestamp with time zone			default now(),
	constraint user_info_pk primary key(user_id)
);

comment on table user_info is '사용자 기본정보';
comment on column user_info.user_id is '고유번호';
comment on column user_info.user_group_id is '사용자 그룹 고유번호';
comment on column user_info.user_name is '이름';
comment on column user_info.password is '비밀번호';
comment on column user_info.salt is 'SALT';
comment on column user_info.telephone is '전화번호';
comment on column user_info.mobile_phone is '핸드폰 번호';
comment on column user_info.email is '이메일';
comment on column user_info.messanger is '메신저 아이디';
comment on column user_info.postal_code is '우편번호';
comment on column user_info.address is '주소';
comment on column user_info.address_etc is '상세주소';
comment on column user_info.user_role_check_yn is '최초 사인인시 사용자 Role 권한 체크 패스 기능. 기본값 Y : 체크';
comment on column user_info.status is '사용자 상태. 0:사용중, 1:사용중지(관리자), 2:잠금(비밀번호 실패횟수 초과), 3:휴면(사인인 기간), 4:만료(사용기간 종료), 5:삭제(화면 비표시, policy.user_delete_type=0), 6:임시비밀번호, 7:승인대기';
comment on column user_info.signin_count is '사인인 횟수';
comment on column user_info.fail_signin_count is '사인인 실패 횟수';
comment on column user_info.last_signin_date is '마지막 사인인 날짜';
comment on column user_info.last_password_change_date is '마지막 사인인 비밀번호 변경 날짜';
comment on column user_info.update_date is '개인정보 수정 날짜';
comment on column user_info.insert_date is '등록일';


-- 사용자 사용 디바이스
create table user_device (
	user_device_id				bigint,
	user_id						varchar(32)	 						not null,
	device_name1				varchar(60)							not null,
	device_type1				char(1)								default '0',
	device_ip1					varchar(45),
	device_priority1			integer								default 1,
	use_yn1						char(1)								default 'Y',
	description1				varchar(256),
	device_name2				varchar(60)							not null,
	device_type2				char(1)								default '0',
	device_ip2					varchar(45),
	device_priority2			integer								default 2,
	use_yn2						char(1)								default 'Y',
	description2				varchar(256),
	device_name3				varchar(60)							not null,
	device_type3				char(1)								default '0',
	device_ip3					varchar(45),
	device_priority3			integer								default 3,
	use_yn3						char(1)								default 'Y',
	description3				varchar(256),
	device_name4				varchar(60)							not null,
	device_type4				char(1)								default '0',
	device_ip4					varchar(45),
	device_priority4			integer								default 4,
	use_yn4						char(1)								default 'Y',
	description4				varchar(256),
	device_name5				varchar(60)							not null,
	device_type5				char(1)								default '0',
	device_ip5					varchar(45),
	device_priority5			integer								default 5,
	use_yn5						char(1)								default 'Y',
	description5				varchar(256),
	insert_date				timestamp with time zone				default now(),
	constraint user_device_pk primary key (user_device_id)
);


comment on table user_device is '사용자 사용 디바이스';
comment on column user_device.user_device_id is '고유번호';
comment on column user_device.user_id is '사용자 아이디';
comment on column user_device.device_name1 is '사용 기기명1';
comment on column user_device.device_type1 is '사용 기기 타입1. 0 : PC, 1 : 핸드폰';
comment on column user_device.device_ip1 is 'IP1';
comment on column user_device.device_priority1 is '우선순위1';
comment on column user_device.use_yn1 is '사용유무1. Y : 사용, N : 미사용';
comment on column user_device.description1 is '설명1';
comment on column user_device.device_name2 is '사용 기기명2';
comment on column user_device.device_type2 is '사용 기기 타입2. 0 : PC, 1 : 핸드폰';
comment on column user_device.device_ip2 is 'IP2';
comment on column user_device.device_priority2 is '우선순위2';
comment on column user_device.use_yn2 is '사용유무2. Y : 사용, N : 미사용';
comment on column user_device.description2 is '설명2';
comment on column user_device.device_name3 is '사용 기기명3';
comment on column user_device.device_type3 is '사용 기기 타입3. 0 : PC, 1 : 핸드폰';
comment on column user_device.device_ip3 is 'IP3';
comment on column user_device.device_priority3 is '우선순위3';
comment on column user_device.use_yn3 is '사용유무3. Y : 사용, N : 미사용';
comment on column user_device.description3 is '설명3';
comment on column user_device.device_name4 is '사용 기기명4';
comment on column user_device.device_type4 is '사용 기기 타입4. 0 : PC, 1 : 핸드폰';
comment on column user_device.device_ip4 is 'IP4';
comment on column user_device.device_priority4 is '우선순위4';
comment on column user_device.use_yn4 is '사용유무4. Y : 사용, N : 미사용';
comment on column user_device.description4 is '설명4';
comment on column user_device.device_name5 is '사용 기기명5';
comment on column user_device.device_type5 is '사용 기기 타입5. 0 : PC, 1 : 핸드폰';
comment on column user_device.device_ip5 is 'IP5';
comment on column user_device.device_priority5 is '우선순위5';
comment on column user_device.use_yn5 is '사용유무5. Y : 사용, N : 미사용';
comment on column user_device.description5 is '설명5';
comment on column user_device.insert_date is '등록일';



-- 사용자 그룹
create table user_group(
	user_group_id				integer,
	user_group_key				varchar(60)							not null ,
	user_group_name				varchar(100)						not null,
	ancestor					integer								default 0,
	parent						integer								default 1,
	depth						integer								default 1,
	view_order					integer								default 1,
	children					integer								default 0,
	basic						boolean								default false,
	available					boolean								default true,
	description					varchar(256),
	insert_date					timestamp with time zone			default now(),
	constraint user_group_pk 	primary key (user_group_id)
);

comment on table user_group is '사용자 그룹';
comment on column user_group.user_group_id is '고유번호';
comment on column user_group.user_group_key is '링크 활용 등을 위한 확장 컬럼';
comment on column user_group.user_group_name is '그룹명';
comment on column user_group.ancestor is '조상 고유번호';
comment on column user_group.parent is '부모 고유번호';
comment on column user_group.depth is '깊이';
comment on column user_group.view_order is '나열 순서';
comment on column user_group.children is '자식 존재 개수';
comment on column user_group.basic is '삭제 불가, true : 기본, false : 선택';
comment on column user_group.available is '사용유무, true : 사용, false : 사용안함';
comment on column user_group.description is '설명';
comment on column user_group.insert_date is '등록일';

-- 사용자 그룹별 Role
create table user_group_role (
	user_group_role_id				integer,
	user_group_id					integer 								not null,
	role_id							integer	 								not null,
	insert_date						timestamp with time zone				default now(),
	constraint user_group_role_pk 	primary key (user_group_role_id)
);

comment on table user_group_role is '사용자 그룹별 Role';
comment on column user_group_role.user_group_role_id is '고유번호';
comment on column user_group_role.user_group_id is '사용자 그룹 고유키';
comment on column user_group_role.role_id is 'Role 고유키';
comment on column user_group_role.insert_date is '등록일';

-- 사용자 그룹 권한
create table user_group_menu(
	user_group_menu_id				integer,
	user_group_id					integer 							not null,
	menu_id							integer 							not null,
	all_yn							char(1)								default 'N',
	read_yn							char(1)								default 'N',
	write_yn						char(1)								default 'N',
	update_yn						char(1)								default 'N',
	delete_yn						char(1)								default 'N',
	insert_date						timestamp with time zone			default now(),
	constraint user_group_menu_pk 	primary key (user_group_menu_id)
);

comment on table user_group_menu is '사용자 그룹 메뉴';
comment on column user_group_menu.user_group_menu_id is '고유번호';
comment on column user_group_menu.user_group_id is '사용자 그룹 고유키';
comment on column user_group_menu.menu_id is '메뉴 고유키';
comment on column user_group_menu.all_yn is '메뉴 접근 모든 권한';
comment on column user_group_menu.read_yn is '읽기 권한';
comment on column user_group_menu.write_yn is '쓰기 권한';
comment on column user_group_menu.update_yn is '수정 권한';
comment on column user_group_menu.delete_yn is '삭제 권한';
comment on column user_group_menu.insert_date is '등록일';






drop table if exists user_policy cascade;

create table user_policy (
	user_policy_id				integer,
	user_id						varchar(32)			not null,
	base_layers					text,
	init_latitude				varchar(30)			default '37.521168',
	init_longitude				varchar(30)			default '126.924185',
	init_altitude				varchar(30)			default '3000.0',
	init_duration				integer				default 3,
	init_default_fov			numeric(3,2)		default 0,
	lod0						varchar(20)			default '15',
	lod1						varchar(20)			default '60',
	lod2						varchar(20)			default '90',
	lod3						varchar(20)			default '200',
	lod4						varchar(20)			default '1000',
	lod5						varchar(20)			default '50000',
	ssao_radius					numeric(8,2)		default  0.15,
	update_date					timestamp with time zone,
	insert_date					timestamp with time zone		default now(),
	constraint user_policy_pk	primary key (user_policy_id)
);

comment on table user_policy is '사용자 설정';
comment on column user_policy.user_policy_id is '고유번호';
comment on column user_policy.init_latitude is '초기 카메라 이동 위도';
comment on column user_policy.init_longitude is '초기 카메라 이동 경도';
comment on column user_policy.init_altitude is '초기 카메라 이동 높이';
comment on column user_policy.init_duration is '초기 카메라 이동 시간. 초 단위';
comment on column user_policy.init_default_fov is 'field of view. 기본값 0(1.8 적용)';
comment on column user_policy.lod0 is 'lod0';
comment on column user_policy.lod1 is 'lod1';
comment on column user_policy.lod2 is 'lod2';
comment on column user_policy.lod3 is 'lod3';
comment on column user_policy.lod4 is 'lod4';
comment on column user_policy.lod5 is 'lod5';
comment on column user_policy.ssao_radius is '그림자 반경';
comment on column user_policy.update_date is '수정일';
comment on column user_policy.insert_date is '등록일';



drop table if exists widget cascade;

-- 위젯
create table widget(
	widget_id				integer,
	widget_name				varchar(100)					not null,
	widget_key				varchar(100)					not null,
	view_order				integer							default 1,
	user_id					varchar(32)	 					not null,
	insert_date				timestamp with time zone		default now(),
	constraint widget_pk 	primary key (widget_id)
);


comment on table widget is '위젯';
comment on column widget.widget_id is '고유번호';
comment on column widget.widget_name is '이름';
comment on column widget.widget_key is 'Key';
comment on column widget.view_order is '나열 순서';
comment on column widget.user_id is '사용자 아이디';
comment on column widget.insert_date is '등록일';



drop sequence if exists access_log_seq;
drop sequence if exists converter_job_seq;
drop sequence if exists converter_job_file_seq;
drop sequence if exists data_group_seq;
drop sequence if exists data_info_seq;
drop sequence if exists data_info_log_seq;
drop sequence if exists data_adjust_log_seq;
drop sequence if exists data_attribute_seq;
drop sequence if exists data_object_attribute_seq;
drop sequence if exists data_file_info_seq;
drop sequence if exists data_file_parse_log_seq;
drop sequence if exists data_smart_tiling_file_info_seq;
drop sequence if exists data_smart_tiling_file_parse_log_seq;
drop sequence if exists data_attribute_file_info_seq;
drop sequence if exists data_object_attribute_file_info_seq;

drop sequence if exists design_layer_group_seq;
drop sequence if exists design_layer_seq;
drop sequence if exists design_layer_land_seq;
drop sequence if exists design_layer_building_seq;
drop sequence if exists design_layer_attribute_seq;
drop sequence if exists design_layer_file_info_seq;
drop sequence if exists data_library_group_seq;
drop sequence if exists data_library_seq;

drop sequence if exists geopolicy_seq;
drop sequence if exists issue_seq;
drop sequence if exists issue_detail_seq;
drop sequence if exists issue_comment_seq;
drop sequence if exists issue_file_seq;
drop sequence if exists issue_people_seq;
drop sequence if exists layer_seq;
drop sequence if exists layer_group_seq;
drop sequence if exists layer_file_info_seq;
drop sequence if exists menu_seq;
drop sequence if exists policy_seq;
drop sequence if exists role_seq;
drop sequence if exists upload_data_seq;
drop sequence if exists upload_data_file_seq;
drop sequence if exists user_group_seq;
drop sequence if exists user_group_role_seq;
drop sequence if exists user_group_menu_seq;
drop sequence if exists user_device_seq;
drop sequence if exists user_policy_seq;
drop sequence if exists widget_seq;

create sequence access_log_seq increment 1 minvalue 1 maxvalue 9999999999999999 start 1 cache 1;
create sequence converter_job_seq increment 1 minvalue 1 maxvalue 999999999999 start 1 cache 1;
create sequence converter_job_file_seq increment 1 minvalue 1 maxvalue 999999999999 start 1 cache 1;
create sequence data_group_seq increment 1 minvalue 1 maxvalue 999999999999 start 10000 cache 1;
create sequence data_info_seq increment 1 minvalue 1 maxvalue 9999999999999999 start 200000 cache 1;
create sequence data_info_log_seq increment 1 minvalue 1 maxvalue 9999999999999999 start 1 cache 1;
create sequence data_adjust_log_seq increment 1 minvalue 1 maxvalue 9999999999999999 start 1 cache 1;
create sequence data_attribute_seq increment 1 minvalue 1 maxvalue 9999999999999999 start 1 cache 1;
create sequence data_object_attribute_seq increment 1 minvalue 1 maxvalue 9999999999999999 start 1 cache 1;
create sequence data_attribute_file_info_seq increment 1 minvalue 1 maxvalue 9999999999999999 start 1 cache 1;
create sequence data_file_info_seq increment 1 minvalue 1 maxvalue 9999999999999999 start 1 cache 1;
create sequence data_file_parse_log_seq increment 1 minvalue 1 maxvalue 9999999999999999 start 1 cache 1;
create sequence data_smart_tiling_file_info_seq increment 1 minvalue 1 maxvalue 9999999999999999 start 1 cache 1;
create sequence data_smart_tiling_file_parse_log_seq increment 1 minvalue 1 maxvalue 9999999999999999 start 1 cache 1;
create sequence data_object_attribute_file_info_seq increment 1 minvalue 1 maxvalue 9999999999999999 start 1 cache 1;

create sequence design_layer_group_seq increment 1 minvalue 1 maxvalue 999999999999 start 1 cache 1;
create sequence design_layer_seq increment 1 minvalue 1 maxvalue 9999999999999999 start 1 cache 1;
create sequence design_layer_land_seq increment 1 minvalue 1 maxvalue 9999999999999999 start 1 cache 1;
create sequence design_layer_building_seq increment 1 minvalue 1 maxvalue 9999999999999999 start 1 cache 1;
create sequence design_layer_attribute_seq increment 1 minvalue 1 maxvalue 9999999999999999 start 1 cache 1;
create sequence design_layer_file_info_seq increment 1 minvalue 1 maxvalue 9999999999999999 start 1 cache 1;
create sequence design_layer_log_seq increment 1 minvalue 1 maxvalue 9999999999999999 start 1 cache 1;
create sequence data_library_group_seq increment 1 minvalue 1 maxvalue 999999999999 start 1 cache 1;
create sequence data_library_seq increment 1 minvalue 1 maxvalue 9999999999999999 start 1 cache 1;

create sequence geopolicy_seq increment 1 minvalue 1 maxvalue 999999999999 start 2 cache 1;
create sequence issue_seq increment 1 minvalue 1 maxvalue 9999999999999999 start 1 cache 1;
create sequence issue_detail_seq increment 1 minvalue 1 maxvalue 9999999999999999 start 1 cache 1;
-- create sequence issue_comment_seq increment 1 minvalue 1 maxvalue 9999999999999999 start 1 cache 1;
-- create sequence issue_file_seq increment 1 minvalue 1 maxvalue 9999999999999999 start 1 cache 1;
-- create sequence issue_people_seq increment 1 minvalue 1 maxvalue 9999999999999999 start 1 cache 1;
create sequence layer_seq increment 1 minvalue 1 maxvalue 999999999999 start 10000 cache 1;
create sequence layer_group_seq increment 1 minvalue 1 maxvalue 999999999999 start 10000 cache 1;
create sequence layer_file_info_seq increment 1 minvalue 1 maxvalue 999999999999 start 1000 cache 1;
create sequence menu_seq increment 1 minvalue 1 maxvalue 999999999999 start 10000 cache 1;
create sequence urban_group_seq increment 1 minvalue 1 maxvalue 999999999999 start 10000 cache 1;
create sequence policy_seq increment 1 minvalue 1 maxvalue 999999999999 start 2 cache 1;
create sequence role_seq increment 1 minvalue 1 maxvalue 999999999999 start 2000 cache 1;
create sequence upload_data_seq increment 1 minvalue 1 maxvalue 9999999999999999 start 1 cache 1;
create sequence upload_data_file_seq increment 1 minvalue 1 maxvalue 9999999999999999 start 1 cache 1;
create sequence user_group_seq increment 1 minvalue 1 maxvalue 999999999999 start 100 cache 1;
create sequence user_group_role_seq increment 1 minvalue 1 maxvalue 999999999999 start 1 cache 1;
create sequence user_group_menu_seq increment 1 minvalue 1 maxvalue 999999999999 start 10000 cache 1;
create sequence user_device_seq increment 1 minvalue 1 maxvalue 999999999999 start 1 cache 1;
create sequence user_policy_seq increment 1 minvalue 1 maxvalue 999999999999 start 1 cache 1;
create sequence widget_seq increment 1 minvalue 1 maxvalue 999999999999 start 1 cache 1;
