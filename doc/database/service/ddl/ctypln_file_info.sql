drop table if exists ctypln_file_info cascade;
commit;

CREATE TABLE "ctypln_file_info" (
	"id" BIGINT NOT NULL DEFAULT 'nextval(''ctypln_file_info_id_seq''::regclass)',
	"regist_dt" TIMESTAMP NULL DEFAULT NULL,
	"updt_dt" TIMESTAMP NULL DEFAULT NULL,
	"file_ty_value" INTEGER NULL DEFAULT NULL,
	"file_nm" VARCHAR(255) NULL DEFAULT 'NULL::character varying',
	"file_cours_nm" VARCHAR(255) NULL DEFAULT 'NULL::character varying',
	"origin_file_nm" VARCHAR(255) NULL DEFAULT 'NULL::character varying',
	"dstrc_id" BIGINT NULL DEFAULT NULL,
	PRIMARY KEY ("id"),
	CONSTRAINT "fkj0r0v5ouywkv3j6kdudl2rtjw" FOREIGN KEY ("dstrc_id") REFERENCES "public"."ctypln_dstrc_info" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
)
COMMENT='도시계획 파일 정보'
;
COMMENT ON COLUMN "ctypln_file_info"."id" IS '아이디';
COMMENT ON COLUMN "ctypln_file_info"."regist_dt" IS '등록 일시';
COMMENT ON COLUMN "ctypln_file_info"."updt_dt" IS '수정 일시';
COMMENT ON COLUMN "ctypln_file_info"."file_ty_value" IS '파일 타입 값';
COMMENT ON COLUMN "ctypln_file_info"."file_nm" IS '파일 명';
COMMENT ON COLUMN "ctypln_file_info"."file_cours_nm" IS '파일 경로 명';
COMMENT ON COLUMN "ctypln_file_info"."origin_file_nm" IS '원본 파일 명';
COMMENT ON COLUMN "ctypln_file_info"."dstrc_id" IS '지구 아이디';
