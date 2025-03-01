package lhdt.domain.role;

import lhdt.domain.common.Search;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * Role
 * @author jeongdae
 *
 */
@ToString(callSuper = true)
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends Search {
	
	/****** validator ********/
	private String methodMode;
	
	// 고유번호
	private Integer roleId;
	// Role 명
	@NotBlank
	private String roleName;
	// Role KEY
	@NotBlank
	private String roleKey;
	// Role 타켓. 0 : 사용자 사이트, 1 : 관리자  사이트, 2 : 서버
	private String roleTarget;
	// 업무 유형. 0 : 사용자, 1 : 서버, 3 : api
	private String roleType;
	// 사용유무. Y : 사용, N : 사용안함
	private String useYn;
	// 기본 사용유무. Y : 사용, N : 사용안함
	private String defaultYn;
	// 설명
	private String description;
	
	// 등록일
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime insertDate;
}
