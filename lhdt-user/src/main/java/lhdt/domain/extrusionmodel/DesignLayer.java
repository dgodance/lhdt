package lhdt.domain.extrusionmodel;

import lhdt.domain.common.Search;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * design layer
 * @author Cheon JeongDae
 *
 */
@ToString(callSuper = true)
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DesignLayer extends Search implements Serializable {

    private static final long serialVersionUID = -4408734451145107109L;

    // 리스트 펼치기
    private String open;
    // 계층 타입
    private String nodeType;
    
    // 수정 유형
    private String updateType;
    // 쓰기 모드
    private String writeMode;
    
    // 화면 ui
    private Integer parent;
    private String parentName;
    private Integer depth;

    // 고유키
    private Long designLayerId;
    // 도시 그룹 아이디
    private Integer urbanGroupId;
    // 디자인 레이어 그룹 아이디
    private Integer designLayerGroupId;
    // 디자인 레이어 그룹명
    private String designLayerGroupName;
    // 디자인 레이어 키
    private String designLayerKey;
    // 디자인 레이어 명
    private String designLayerName;
    // design layer 분류. land : 땅, building : 빌딩
    private String designLayerType;
    
    // 업로딩 아이디
    private String userId;
    // 공유 타입. 0 : 공개, 1 : 개인, 2 : 그룹
    private String sharing;
    // OGC Web Services (정적, 동적)
    private String ogcWebServices;
    // 도형 타입 (point, line, polygon)
    private String geometryType;

    // style file 내용
    private String styleFileContent;
    
    // 레이어 색상
    private String layerFillColor;
    // 레이어 선 색상
    private String layerLineColor;
    // 레이어 선 스타일(두께)
    @Range(min=1, max=5)	
    private Float layerLineStyle;
    // 레이어 투명도
    @Range(min=1, max=100)	
    private Float layerAlphaStyle;

    // 나열 순서
    private Integer viewOrder;

    // 지도 레이어 표시 우선 순위
    private Integer zIndex;
    public Integer getViewZIndex() {
        return this.zIndex;
    }

    // 사용 유무
    private Boolean available;
    // 레이블 표시 유무. Y : 표시, N : 비표시(기본값)
    private Boolean labelDisplay;
    private Boolean cacheAvailable;
    
    // 좌표계
    private String coordinate;
    // 설명
    private String description;

    // shape info
    private Long shapeId;
    private String theGeom;
    private String attributes;
    
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updateDate;

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime insertDate;

    // shape 파일 종류
    public enum DesignLayerType {
        // 토지
        LAND,
        // 빌딩
        BUILDING
    }
}
