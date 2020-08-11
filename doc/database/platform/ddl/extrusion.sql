drop table if exists extrusion_model_group cascade;
drop table if exists extrusion_model cascade;
drop table if exists extrusion_model_file_info cascade;
drop table if exists extrusion_model_attribute cascade;
drop table if exists data_library_group cascade;
drop table if exists data_library cascade;

drop table if exists extrusion_model_log cascade;
drop table if exists extrusion_model_log_2020 cascade;
drop table if exists extrusion_model_log_2021 cascade;
drop table if exists extrusion_model_log_2022 cascade;
drop table if exists extrusion_model_log_2023 cascade;
drop table if exists extrusion_model_log_2024 cascade;
drop table if exists extrusion_model_log_2025 cascade;
drop table if exists extrusion_model_log_2026 cascade;
drop table if exists extrusion_model_log_2027 cascade;
drop table if exists extrusion_model_log_2028 cascade;
drop table if exists extrusion_model_log_2029 cascade;
drop table if exists extrusion_model_log_2030 cascade;
drop table if exists extrusion_model_log_2031 cascade;
drop table if exists extrusion_model_log_2032 cascade;
drop table if exists extrusion_model_log_2033 cascade;
drop table if exists extrusion_model_log_2034 cascade;
drop table if exists extrusion_model_log_2035 cascade;
drop table if exists extrusion_model_log_2036 cascade;
drop table if exists extrusion_model_log_2037 cascade;
drop table if exists extrusion_model_log_2038 cascade;
drop table if exists extrusion_model_log_2039 cascade;
drop table if exists extrusion_model_log_2040 cascade;
commit;

-- extrusion model 그룹
create table extrusion_model_group (
	extrusion_model_group_id		        integer,
	extrusion_model_group_name      		varchar(256)					not null,
	user_id						            varchar(32),
	ancestor					            integer							default 0,
	parent                		            integer							default 0,
	depth                	  	            integer							default 1,
	view_order					            integer							default 1,
	children					            integer							default 0,
	available					            boolean							default true,
	description					            varchar(256),
	update_date             	            timestamp with time zone,
	insert_date					            timestamp with time zone		default now(),
	constraint extrusion_model_group_pk     primary key (extrusion_model_group_id)
);

comment on table extrusion_model_group is 'extrusion model 그룹';
comment on column extrusion_model_group.extrusion_model_group_id is 'extrusion model 그룹 고유번호';
comment on column extrusion_model_group.extrusion_model_group_name is 'extrusion model 그룹 그룹명';
comment on column extrusion_model_group.user_id is '사용자 아이디';
comment on column extrusion_model_group.ancestor is '조상';
comment on column extrusion_model_group.parent is '부모';
comment on column extrusion_model_group.depth is '깊이';
comment on column extrusion_model_group.view_order is '나열 순서';
comment on column extrusion_model_group.children is '자식 존재 개수';
comment on column extrusion_model_group.available is '사용 여부';
comment on column extrusion_model_group.description is '설명';
comment on column extrusion_model_group.update_date is '수정일';
comment on column extrusion_model_group.insert_date is '등록일';

-- extrusion model
create table extrusion_model (
	extrusion_model_id					bigint,
	extrusion_model_group_id			integer,
	extrusion_model_key					varchar(100)					not null,
	extrusion_model_name				varchar(256)					not null,
	user_id						        varchar(32),
	service_type				        varchar(30),

	geometry_type				        varchar(30),
	layer_fill_color                    varchar(30),
	layer_line_color			        varchar(30),
	layer_line_style			        numeric,
	layer_alpha_style			        numeric,
	
	view_order					        integer							default 1,
	z_index						        integer,
	available					        boolean							default true,
	label_display				        boolean							default false,
	cache_available				        boolean							default false,
	
	coordinate					        varchar(256),
	description					        varchar(256),
	update_date					        timestamp with time zone		default now(),
	insert_date					        timestamp with time zone 		default now(),
	constraint extrusion_model_pk 		primary key (extrusion_model_id)
);

comment on table extrusion_model is 'extrusion model';
comment on column extrusion_model.extrusion_model_id is 'extrusion model 고유번호';
comment on column extrusion_model.extrusion_model_group_id is 'extrusion model 그룹 고유번호';
comment on column extrusion_model.extrusion_model_key is 'extrusion model 고유키(API용)';
comment on column extrusion_model.extrusion_model_name is 'extrusion model 명';
comment on column extrusion_model.user_id is '사용자명';
comment on column extrusion_model.service_type is '서비스 타입 (정적, 동적)';
comment on column extrusion_model.geometry_type is '도형 타입';
comment on column extrusion_model.layer_fill_color is '외곽선 색상';
comment on column extrusion_model.layer_line_color is '외곽선 두께';
comment on column extrusion_model.layer_line_style is '채우기 색상';
comment on column extrusion_model.layer_alpha_style is '투명도';
comment on column extrusion_model.view_order is '나열 순서';
comment on column extrusion_model.z_index is '지도위에 노출 순위(css z-index와 동일)';
comment on column extrusion_model.available is '사용유무.';
comment on column extrusion_model.label_display is '레이블 표시';
comment on column extrusion_model.cache_available is '캐시 사용 유무';
comment on column extrusion_model.coordinate is '좌표계 정보';
comment on column extrusion_model.description is '설명';
comment on column extrusion_model.update_date is '수정일';
comment on column extrusion_model.insert_date is '등록일';


-- extrusion model 속성
create table extrusion_model_attribute (
	extrusion_model_attribute_id			        bigint,
	extrusion_model_id						        bigint,
	attributes					                    json,
	update_date					                    timestamp with time zone,
	insert_date					                    timestamp with time zone			default now(),
	constraint extrusion_model_attribute_pk 	    primary key(extrusion_model_attribute_id)
);

comment on table extrusion_model_attribute is 'extrusion model 필수 외 속성 정보';
comment on column extrusion_model_attribute.extrusion_model_attribute_id is '고유번호';
comment on column extrusion_model_attribute.extrusion_model_id is 'extrusion model 고유번호';
comment on column extrusion_model_attribute.attributes is '속성';
comment on column extrusion_model_attribute.update_date is '수정일';
comment on column extrusion_model_attribute.insert_date is '등록일';


-- extrusion model shape 파일 정보
create table extrusion_model_file_info (
	extrusion_model_file_info_id		    bigint,
	extrusion_model_id					    bigint							not null,
	extrusion_model_file_info_team_id	    bigint,
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
	constraint extrusion_model_file_info_pk primary key (extrusion_model_file_info_id)
);

comment on table extrusion_model_file_info is 'extrusion model shape 파일 관리';
comment on column extrusion_model_file_info.extrusion_model_file_info_id is 'extrusion model 파일 고유번호';
comment on column extrusion_model_file_info.extrusion_model_id is 'extrusion model 고유번호';
comment on column extrusion_model_file_info.extrusion_model_file_info_team_id is 'shape 파일 팀 아이디. .shp 파일의 extrusion_model_file_info_id 를 team_id로 함';
comment on column extrusion_model_file_info.user_id is '사용자 id';
comment on column extrusion_model_file_info.enable_yn is 'layer 활성화 유무. Y: 활성화, N: 비활성화';
comment on column extrusion_model_file_info.file_name is '파일 이름';
comment on column extrusion_model_file_info.file_real_name is '파일 실제 이름';
comment on column extrusion_model_file_info.file_path is '파일 경로';
comment on column extrusion_model_file_info.file_size is '파일 용량';
comment on column extrusion_model_file_info.file_ext is '파일 확장자';
comment on column extrusion_model_file_info.shape_encoding is 'shape 파일 인코딩';
comment on column extrusion_model_file_info.version_id is 'shape 파일 버전 정보';
comment on column extrusion_model_file_info.update_date is '갱신일';
comment on column extrusion_model_file_info.insert_date is '등록일';


-- data library 그룹
create table data_library_group (
	data_library_group_id		            integer,
	data_library_group_name      		    varchar(256)					not null,
	user_id						            varchar(32),
	ancestor					            integer							default 0,
	parent                		            integer							default 0,
	depth                	  	            integer							default 1,
	view_order					            integer							default 1,
	children					            integer							default 0,
	available					            boolean							default true,
	description					            varchar(256),
	update_date             	            timestamp with time zone,
	insert_date					            timestamp with time zone		default now(),
	constraint data_library_group_pk        primary key (data_library_group_id)
);

comment on table data_library_group is 'data library 그룹';
comment on column data_library_group.data_library_group_id is 'data library 그룹 고유번호';
comment on column data_library_group.data_library_group_name is 'data library 그룹 그룹명';
comment on column data_library_group.user_id is '사용자 아이디';
comment on column data_library_group.ancestor is '조상';
comment on column data_library_group.parent is '부모';
comment on column data_library_group.depth is '깊이';
comment on column data_library_group.view_order is '나열 순서';
comment on column data_library_group.children is '자식 존재 개수';
comment on column data_library_group.available is '사용 여부';
comment on column data_library_group.description is '설명';
comment on column data_library_group.update_date is '수정일';
comment on column data_library_group.insert_date is '등록일';

-- data library
create table data_library (
	data_library_id					    bigint,
	data_library_group_id			    integer,
	data_library_key					varchar(100)					not null,
	data_library_name				    varchar(256)					not null,
	data_id						        bigint,
	user_id						        varchar(32),

	service_type				        varchar(30),
    view_order					        integer							default 1,
	available					        boolean							default true,

	description					        varchar(256),
	update_date					        timestamp with time zone		default now(),
	insert_date					        timestamp with time zone 		default now(),
	constraint data_library_pk 		    primary key (data_library_id)
);


comment on table data_library is 'data library';
comment on column data_library.data_library_id is 'data library 고유번호';
comment on column data_library.data_library_group_id is 'data library 그룹 고유번호';
comment on column data_library.data_library_key is 'data library 고유키(API용)';
comment on column data_library.data_library_name is 'data library명';
comment on column data_library.data_id is '데이터 고유키';
comment on column data_library.user_id is '사용자명';
comment on column data_library.service_type is '서비스 타입 (정적, 동적)';
comment on column data_library.view_order is '나열 순서';
comment on column data_library.available is '사용유무.';
comment on column data_library.description is '설명';
comment on column data_library.update_date is '수정일';
comment on column data_library.insert_date is '등록일';



-- extrusion model 사용 이력
create table extrusion_model_log(
	extrusion_model_log_id				bigint,
	extrusion_model_id				    bigint,
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

comment on table extrusion_model_log is 'extrusion model 사용 이력';
comment on column extrusion_model_log.extrusion_model_log_id is '고유번호';
comment on column extrusion_model_log.extrusion_model_id is 'extrusion model 고유번호';
comment on column extrusion_model_log.user_id is '사용자 아이디';
comment on column extrusion_model_log.user_name is '사용자 이름';
comment on column extrusion_model_log.status is '상태. ready : 시뮬레이션전, use : 사용중, temp : 임시저장, complete : 완료';
comment on column extrusion_model_log.year is '년';
comment on column extrusion_model_log.month is '월';
comment on column extrusion_model_log.day is '일';
comment on column extrusion_model_log.year_week is '일년중 몇주';
comment on column extrusion_model_log.week is '이번달 몇주';
comment on column extrusion_model_log.hour is '시간';
comment on column extrusion_model_log.minute is '분';
comment on column extrusion_model_log.insert_date is '등록일';


create table extrusion_model_log_2020 partition of extrusion_model_log for values from ('2020-01-01 00:00:00.000000') to ('2021-01-01 00:00:00.000000');
create table extrusion_model_log_2021 partition of extrusion_model_log for values from ('2021-01-01 00:00:00.000000') to ('2022-01-01 00:00:00.000000');
create table extrusion_model_log_2022 partition of extrusion_model_log for values from ('2022-01-01 00:00:00.000000') to ('2023-01-01 00:00:00.000000');
create table extrusion_model_log_2023 partition of extrusion_model_log for values from ('2023-01-01 00:00:00.000000') to ('2024-01-01 00:00:00.000000');
create table extrusion_model_log_2024 partition of extrusion_model_log for values from ('2024-01-01 00:00:00.000000') to ('2025-01-01 00:00:00.000000');
create table extrusion_model_log_2025 partition of extrusion_model_log for values from ('2025-01-01 00:00:00.000000') to ('2026-01-01 00:00:00.000000');
create table extrusion_model_log_2026 partition of extrusion_model_log for values from ('2026-01-01 00:00:00.000000') to ('2027-01-01 00:00:00.000000');
create table extrusion_model_log_2027 partition of extrusion_model_log for values from ('2027-01-01 00:00:00.000000') to ('2028-01-01 00:00:00.000000');
create table extrusion_model_log_2028 partition of extrusion_model_log for values from ('2028-01-01 00:00:00.000000') to ('2029-01-01 00:00:00.000000');
create table extrusion_model_log_2029 partition of extrusion_model_log for values from ('2029-01-01 00:00:00.000000') to ('2030-01-01 00:00:00.000000');
create table extrusion_model_log_2030 partition of extrusion_model_log for values from ('2030-01-01 00:00:00.000000') to ('2031-01-01 00:00:00.000000');
create table extrusion_model_log_2031 partition of extrusion_model_log for values from ('2031-01-01 00:00:00.000000') to ('2032-01-01 00:00:00.000000');
create table extrusion_model_log_2032 partition of extrusion_model_log for values from ('2032-01-01 00:00:00.000000') to ('2033-01-01 00:00:00.000000');
create table extrusion_model_log_2033 partition of extrusion_model_log for values from ('2033-01-01 00:00:00.000000') to ('2034-01-01 00:00:00.000000');
create table extrusion_model_log_2034 partition of extrusion_model_log for values from ('2034-01-01 00:00:00.000000') to ('2035-01-01 00:00:00.000000');
create table extrusion_model_log_2035 partition of extrusion_model_log for values from ('2035-01-01 00:00:00.000000') to ('2036-01-01 00:00:00.000000');
create table extrusion_model_log_2036 partition of extrusion_model_log for values from ('2036-01-01 00:00:00.000000') to ('2037-01-01 00:00:00.000000');
create table extrusion_model_log_2037 partition of extrusion_model_log for values from ('2037-01-01 00:00:00.000000') to ('2038-01-01 00:00:00.000000');
create table extrusion_model_log_2038 partition of extrusion_model_log for values from ('2038-01-01 00:00:00.000000') to ('2039-01-01 00:00:00.000000');
create table extrusion_model_log_2039 partition of extrusion_model_log for values from ('2039-01-01 00:00:00.000000') to ('2040-01-01 00:00:00.000000');
create table extrusion_model_log_2040 partition of extrusion_model_log for values from ('2040-01-01 00:00:00.000000') to ('2041-01-01 00:00:00.000000');