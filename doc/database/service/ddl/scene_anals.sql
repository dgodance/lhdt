drop table if exists scene_anals cascade;
commit;

CREATE TABLE "scene_anals" (
	"id" BIGINT NOT NULL DEFAULT 'nextval(''scene_anals_id_seq''::regclass)',
	"regist_dt" TIMESTAMP NULL DEFAULT NULL,
	"updt_dt" TIMESTAMP NULL DEFAULT NULL,
	"scene_point_actn_ty_value" INTEGER NULL DEFAULT NULL,
	"end_lc_hg_value" DOUBLE PRECISION NULL DEFAULT NULL,
	"scene_end_lc_nm" BYTEA NULL DEFAULT NULL,
	"scene_point_nm" VARCHAR(255) NULL DEFAULT 'NULL::character varying',
	"scene_point_ty_value" INTEGER NULL DEFAULT NULL,
	"begin_lc_hg_value" DOUBLE PRECISION NULL DEFAULT NULL,
	"scene_begin_lc_nm" BYTEA NULL DEFAULT NULL,
	"scene_anals_nm" VARCHAR(255) NULL DEFAULT 'NULL::character varying',
	"scene_anals_ty_value" INTEGER NULL DEFAULT NULL,
	PRIMARY KEY ("id")
)
COMMENT='경관 분석'
;
COMMENT ON COLUMN "scene_anals"."id" IS '아이디';
COMMENT ON COLUMN "scene_anals"."regist_dt" IS '등록 일시';
COMMENT ON COLUMN "scene_anals"."updt_dt" IS '수정 일시';
COMMENT ON COLUMN "scene_anals"."scene_point_actn_ty_value" IS '경관 점 작용 타입 값';
COMMENT ON COLUMN "scene_anals"."end_lc_hg_value" IS '종료 위치 높이 값';
COMMENT ON COLUMN "scene_anals"."scene_end_lc_nm" IS '경관 종료 위치 명';
COMMENT ON COLUMN "scene_anals"."scene_point_nm" IS '경관 점 명';
COMMENT ON COLUMN "scene_anals"."scene_point_ty_value" IS '경관 점 타입 값';
COMMENT ON COLUMN "scene_anals"."begin_lc_hg_value" IS '시작 위치 높이 값';
COMMENT ON COLUMN "scene_anals"."scene_begin_lc_nm" IS '경관 시작 위치 명';
COMMENT ON COLUMN "scene_anals"."scene_anals_nm" IS '경관 분석 명';
COMMENT ON COLUMN "scene_anals"."scene_anals_ty_value" IS '경관 분석 타입 값';
