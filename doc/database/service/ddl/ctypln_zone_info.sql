drop table if exists ctypln_zone_info cascade;
commit;

CREATE TABLE "ctypln_zone_info" (
	"id" BIGINT NOT NULL DEFAULT 'nextval(''ctypln_zone_info_id_seq''::regclass)',
	"regist_dt" TIMESTAMP NULL DEFAULT NULL,
	"updt_dt" TIMESTAMP NULL DEFAULT NULL,
	"zone_nm" VARCHAR(255) NULL DEFAULT 'NULL::character varying',
	PRIMARY KEY ("id")
)
COMMENT='도시계획 구역 정보'
;
COMMENT ON COLUMN "ctypln_zone_info"."id" IS '아이디';
COMMENT ON COLUMN "ctypln_zone_info"."regist_dt" IS '등록 일시';
COMMENT ON COLUMN "ctypln_zone_info"."updt_dt" IS '수정 일시';
COMMENT ON COLUMN "ctypln_zone_info"."zone_nm" IS '구역 명';
