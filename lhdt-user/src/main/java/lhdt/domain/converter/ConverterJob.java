package lhdt.domain.converter;

import lhdt.domain.common.Search;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * f4d converter 변환 job
 * @author jeongdae
 *
 */
@ToString(callSuper = true)
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConverterJob extends Search {
	
	/**
	 * F4D 변환 대상 업로딩 목록. uploadDataId 를 ,로 연결
	 */
	private String converterCheckIds;
	// converter 변환시, 단일 파일의 경우 validataion 체크용
	private String dataType;
	
	// job에 포함된 변환 파일 갯수
	private Integer converterFileCount;
	private Long converterJobFileId;
	
	/****** validator ********/
	private String methodMode;
	
	// Primary Key
	private Long converterJobId;
	// 업로드 고유키
	private Long uploadDataId;
	// [중복] admin : 관리자용 데이터 그룹, user : 일반 사용자용 데이터 그룹
	private String dataGroupTarget;
	// 사용자 고유번호
	private String userId;
	// title
	private String title;
	// 변환 유형. basic : 기본, building : 빌딩, extra-big-building : 초대형 빌딩, point-cloud : point cloud 데이터
	private String converterTemplate;
	// unit scale factor. 설계 파일의 1이 의미하는 단위. 기본 1 = 0.01m
	private BigDecimal usf;
	// 높이방향. y축이 건물의 천장을 향하는 경우 Y. default = N
	private String yAxisUp;
	public String getViewYAxisUp() {
		return this.yAxisUp;
	}
	// 대상 file 개수
	private Integer fileCount;
	// 상태. ready : 준비, success : 성공, waiting : 승인대기, fail : 실패
	private String status;
	// 상태(ENUM)
	private ConverterJobStatus ConverterJobStatus;
	// 데이터 변환 상태 집계
	private Long statusCount;

	// 에러 코드
	private String errorCode;
	
	// 년도
	private String year;
	// 월
	private String month;
	// 일
	private String day;
	// 일년중 몇주
	private String yearWeek;
	// 이번달 몇주
	private String week;
	// 시간
	private String hour;
	// 분
	private String minute;
	
	// 수정일 
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updateDate;
	// 등록일
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime insertDate;
}
