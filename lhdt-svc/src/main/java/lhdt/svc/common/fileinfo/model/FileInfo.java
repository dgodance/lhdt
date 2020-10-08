/**
 * 
 */
package lhdt.svc.common.fileinfo.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import lhdt.cmmn.domain.CmmnDomain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author gravity
 * @since 2020. 9. 4.
 * 파일 정보 데이터 구조 정의
 */
@SuppressWarnings("serial")
@Entity(name = "file_info")
@Getter 
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo extends CmmnDomain {
	/**
	 * 파일 확장자
	 */
	@Column(name = "file_extsn_nm")
	private String fileExt;

	/**
	 * 파일명
	 */
	@Column(name = "file_nm")
	private String fileName;

	/**
	 * 파일 경로
	 */
	@Column(name = "file_cours_nm")
	private String filePath;

	/**
	 * 원본 파일명
	 */
	@Column(name = "origin_file_nm")
	private String originFileName;
}
