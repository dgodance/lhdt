drop table if exists ctypln_dstrc_info cascade;
commit;

CREATE TABLE "ctypln_dstrc_info" (
	"id" BIGINT NOT NULL DEFAULT 'nextval(''ctypln_dstrc_info_id_seq''::regclass)',
	"regist_dt" TIMESTAMP NULL DEFAULT NULL,
	"updt_dt" TIMESTAMP NULL DEFAULT NULL,
	"dstrc_nm" VARCHAR(255) NULL DEFAULT 'NULL::character varying',
	"zone_id" BIGINT NULL DEFAULT NULL,
	PRIMARY KEY ("id"),
	CONSTRAINT "fkh3vik5fc70c6xaasi08m06k3f" FOREIGN KEY ("zone_id") REFERENCES "public"."ctypln_zone_info" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
)
COMMENT='도시계획 지구 정보'
;
COMMENT ON COLUMN "ctypln_dstrc_info"."id" IS '아이디';
COMMENT ON COLUMN "ctypln_dstrc_info"."regist_dt" IS '등록 일시';
COMMENT ON COLUMN "ctypln_dstrc_info"."updt_dt" IS '수정 일시';
COMMENT ON COLUMN "ctypln_dstrc_info"."dstrc_nm" IS '지구 명';
COMMENT ON COLUMN "ctypln_dstrc_info"."zone_id" IS '영역 아이디';
