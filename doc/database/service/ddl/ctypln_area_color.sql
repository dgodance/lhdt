drop table if exists ctypln_area_color cascade;
commit;


CREATE TABLE "ctypln_area_color" (
	"id" BIGINT NOT NULL DEFAULT 'nextval(''ctypln_area_color_id_seq''::regclass)',
	"regist_dt" TIMESTAMP NULL DEFAULT NULL,
	"updt_dt" TIMESTAMP NULL DEFAULT NULL,
	"area_nm" VARCHAR(255) NULL DEFAULT 'NULL::character varying',
	"area_nm_cn" VARCHAR(4000) NULL DEFAULT 'NULL::character varying',
	"area_color_nm" VARCHAR(255) NULL DEFAULT 'NULL::character varying',
	PRIMARY KEY ("id")
)
COMMENT='도시계획 지역 색'
;
COMMENT ON COLUMN "ctypln_area_color"."id" IS '아이디';
COMMENT ON COLUMN "ctypln_area_color"."regist_dt" IS '등록 일시';
COMMENT ON COLUMN "ctypln_area_color"."updt_dt" IS '수정 일시';
COMMENT ON COLUMN "ctypln_area_color"."area_nm" IS '지역 명';
COMMENT ON COLUMN "ctypln_area_color"."area_nm_cn" IS '지역 명 내용';
COMMENT ON COLUMN "ctypln_area_color"."area_color_nm" IS '지역 색 명';
