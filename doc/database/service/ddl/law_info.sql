drop table if exists law_info cascade;
commit;

CREATE TABLE "law_info" (
	"id" BIGINT NOT NULL DEFAULT 'nextval(''law_info_id_seq''::regclass)',
	"regist_dt" TIMESTAMP NULL DEFAULT NULL,
	"updt_dt" TIMESTAMP NULL DEFAULT NULL,
	"law_info_nm" VARCHAR(255) NULL DEFAULT 'NULL::character varying',
	PRIMARY KEY ("id")
)
COMMENT='법 정보'
;
COMMENT ON COLUMN "law_info"."id" IS '아이디';
COMMENT ON COLUMN "law_info"."regist_dt" IS '등록 일시';
COMMENT ON COLUMN "law_info"."updt_dt" IS '수정 일시';
COMMENT ON COLUMN "law_info"."law_info_nm" IS '법 정보 명';
