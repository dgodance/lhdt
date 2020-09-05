package lhdt.domain.extrusionmodel;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Relation(collectionRelation = "designLayerLands")
public class DesignLayerLandDto {

    // design layer lnad 고유 번호
    private Long designLayerLandId;
    // design layer 고유번호
    private Long designLayerId;
    //  design layer 그룹 고유 번호
    private Integer designLayerGroupId;
    // shape 파일 고유 번호
    private Long shapeId;
    // 사업유형
    private String businessType;
    // 사업지구
    private String businessDistrict;
    // 가구번호
    private String blockNumber;
    // 획지번호
    private String landNumber;
    // 획지면적
    private String landArea;
    // 용도지역
    private String useageArea;
    // 토지이용
    private String landUseage;
    // 대지분할합필
    private String landDivision;
    // 용도
    private String useage;
    // 용도-지정
    private String useageSpecification;
    // 용도-권장
    private String useageRecommended;
    // 용도-허용
    private String useageAllowed;
    // 용도-제한
    private String useageLimited;
    // 용도-불허
    private String useageDisapproval;
    // 건폐율
    private String buildingLandRatio;
    // 용적률
    private String floorAreaRatio;
    // 용적률-기준
    private String floorAreaRatioStandard;
    // 용적률-허용
    private String floorAreaRatioAllowed;
    // 용적률-상한
    private String floorAreaRatioUpperLimit;
    // 최고높이
    private String highestHeight;
    // 최고층수
    private String highestFloor;
    // 주택유형
    private String housingType;
    // 세대수
    private String householdsNumber;
    // 기준시점
    private String standardPoint;
    // 속성
    private String properties;
    // 수정일
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDate;
    // 등록일
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime insertDate;
    // wkt
    private String theGeom;
}
