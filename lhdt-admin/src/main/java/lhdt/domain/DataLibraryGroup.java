package lhdt.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 데이터 Library 그룹
 * @author Cheon JeongDae
 *
 */
@ToString(callSuper = true)
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataLibraryGroup extends Search {

	/****** 화면 표시용 *******/
	private String parentName;
	// 부모 depth
	private Integer parentDepth;
	// up : 위로, down : 아래로
	private String updateType;

	/****** validator ********/
	private String methodMode;
	
	// 고유번호
	private Integer dataLibraryGroupId;
	// 그룹명
	@Size(max = 256)
	private String dataLibraryGroupName;
	// 서비스 경로
	private String dataLibraryGroupPath;
	// 사용자명
	private String userId;
	private String userName;
	
	// 조상
	private Integer ancestor;
	// 부모
	private Integer parent;
	// 깊이
	private Integer depth;
	// 순서
	private Integer viewOrder;
	// 자식 존재 유무
	private Integer children;
	
	// true : 사용, false : 사용안함
	private Boolean available;

	// 설명
	private String description;
	
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime viewUpdateDate;
	
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime viewInsertDate;
	
	public LocalDateTime getViewUpdateDate() {
		return this.updateDate;
	}
	public LocalDateTime getViewInsertDate() {
		return this.insertDate;
	}
	
	// 수정일
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updateDate;
	// 등록일
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime insertDate;
}
