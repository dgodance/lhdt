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

