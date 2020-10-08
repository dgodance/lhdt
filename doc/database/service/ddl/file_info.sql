drop table if exists file_info cascade;
commit;

CREATE TABLE "file_info" (
	"id" BIGINT NOT NULL DEFAULT 'nextval(''file_info_id_seq''::regclass)',
	"regist_dt" TIMESTAMP NULL DEFAULT NULL,
	"updt_dt" TIMESTAMP NULL DEFAULT NULL,
	"file_extsn_nm" VARCHAR(255) NULL DEFAULT 'NULL::character varying',
	"file_nm" VARCHAR(255) NULL DEFAULT 'NULL::character varying',
	"file_cours_nm" VARCHAR(255) NULL DEFAULT 'NULL::character varying',
	"origin_file_nm" VARCHAR(255) NULL DEFAULT 'NULL::character varying',
	"file_cl_value" INTEGER NULL DEFAULT NULL,
	"file_ty_value" INTEGER NULL DEFAULT NULL,
	"file_no" BIGINT NULL DEFAULT NULL,
	PRIMARY KEY ("id")
)
COMMENT='파일 정보'
;
COMMENT ON COLUMN "file_info"."id" IS '아이디';
COMMENT ON COLUMN "file_info"."regist_dt" IS '등록 일시';
COMMENT ON COLUMN "file_info"."updt_dt" IS '수정 일시';
COMMENT ON COLUMN "file_info"."file_extsn_nm" IS '파일 확장자 명';
COMMENT ON COLUMN "file_info"."file_nm" IS '파일 명';
COMMENT ON COLUMN "file_info"."file_cours_nm" IS '파일 경로 명';
COMMENT ON COLUMN "file_info"."origin_file_nm" IS '원본 파일 명';
COMMENT ON COLUMN "file_info"."file_cl_value" IS '파일 분류 값';
COMMENT ON COLUMN "file_info"."file_ty_value" IS '파일 타입 값';
COMMENT ON COLUMN "file_info"."file_no" IS '파일 번호';
