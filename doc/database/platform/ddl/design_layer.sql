drop table if exists design_layer_group cascade;
drop table if exists design_layer cascade;
drop table if exists design_layer_land cascade;
drop table if exists design_layer_building cascade;
drop table if exists design_layer_file_info cascade;
drop table if exists design_layer_attribute cascade;
drop table if exists data_library_group cascade;
drop table if exists data_library cascade;

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
	tiling						            boolean							        default false,
	data_count					            integer							        default 0,
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
comment on column data_library_group.data_count is '데이터 총 건수';
comment on column data_library_group.ancestor is '조상';
comment on column data_library_group.parent is '부모';
comment on column data_library_group.depth is '깊이';
comment on column data_library_group.view_order is '나열 순서';
comment on column data_library_group.children is '자식 존재 개수';
comment on column data_library_group.basic is 'true : 기본, false : 선택';
comment on column data_library_group.available is 'true : 사용, false : 사용안함';
comment on column data_library_group.tiling is 'true : 사용, false : 사용안함(기본)';
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
	update_date					        timestamp with time zone,
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