package lhdt.admin.svc.lhdt.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExtrusionModelGroup implements Serializable {

	private static final long serialVersionUID = -1480761819761262002L;

	/****** 화면 표시용 *******/
	// up : 위로, down : 아래로
	private String updateType;

	// 그룹Key 중복 확인 hidden 값
	private String duplicationValue;

	// extrusion model 그룹 고유번호
	private Integer extrusionModelGroupId;
	// extrusion model 그룹 그룹명
	private String extrusionModelGroupName;
	// 사용자 아이디
	private String userId;
	// 조상
	private Integer ancestor;

	// 부모
	private Integer parent;
	private String parentName;
	// 깊이
	private Integer depth;
	// 나열 순서
	private Integer viewOrder;
	// 자식 존재 유무
	private Integer children;
	// 사용 유무
	private Boolean available;
	// 설명
	private String description;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updateDate;

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime insertDate;
}
