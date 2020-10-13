drop table if exists scene_cmpr_group cascade;
commit;

CREATE TABLE "scene_cmpr_group" (
	"id" BIGINT NOT NULL DEFAULT 'nextval(''scene_cmpr_group_id_seq''::regclass)',
	"regist_dt" TIMESTAMP NULL DEFAULT NULL,
	"updt_dt" TIMESTAMP NULL DEFAULT NULL,
	"scene_cmpr_group_nm" VARCHAR(255) NULL DEFAULT 'NULL::character varying',
	PRIMARY KEY ("id")
)
COMMENT='경관 비교 그룹'
;
COMMENT ON COLUMN "scene_cmpr_group"."id" IS '아이디';
COMMENT ON COLUMN "scene_cmpr_group"."regist_dt" IS '등록 일시';
COMMENT ON COLUMN "scene_cmpr_group"."updt_dt" IS '수정 일시';
COMMENT ON COLUMN "scene_cmpr_group"."scene_cmpr_group_nm" IS '경관 비교 그룹 명';
