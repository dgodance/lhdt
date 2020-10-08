drop table if exists scene_cmpr cascade;
commit;

CREATE TABLE "scene_cmpr" (
	"id" BIGINT NOT NULL DEFAULT 'nextval(''scene_cmpr_id_seq''::regclass)',
	"regist_dt" TIMESTAMP NULL DEFAULT NULL,
	"updt_dt" TIMESTAMP NULL DEFAULT NULL,
	"camera_cn" JSON NULL DEFAULT NULL,
	"scene_cmpr_nm" VARCHAR(255) NULL DEFAULT 'NULL::character varying',
	"scene_cmpr_group_id" BIGINT NULL DEFAULT NULL,
	"scene_cmpr_img_id" BIGINT NULL DEFAULT NULL,
	PRIMARY KEY ("id"),
	CONSTRAINT "fk1a0piij7ut2ccspf11dxblxga" FOREIGN KEY ("scene_cmpr_group_id") REFERENCES "public"."scene_cmpr_group" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "fkgfs3m5m9yfwbt8ebr5ue14pvt" FOREIGN KEY ("scene_cmpr_img_id") REFERENCES "public"."file_info" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
)
COMMENT='경관 비교'
;
COMMENT ON COLUMN "scene_cmpr"."id" IS '아이디';
COMMENT ON COLUMN "scene_cmpr"."regist_dt" IS '등록 일시';
COMMENT ON COLUMN "scene_cmpr"."updt_dt" IS '수정 일시';
COMMENT ON COLUMN "scene_cmpr"."camera_cn" IS '카메라 내용';
COMMENT ON COLUMN "scene_cmpr"."scene_cmpr_nm" IS '경관 비교 명';
COMMENT ON COLUMN "scene_cmpr"."scene_cmpr_group_id" IS '경관 비교 그룹 아이디';
COMMENT ON COLUMN "scene_cmpr"."scene_cmpr_img_id" IS '경관 비교 이미지 아이디';
