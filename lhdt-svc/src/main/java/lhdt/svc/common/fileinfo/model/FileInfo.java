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
 *
 */
@SuppressWarnings("serial")
@Entity(name = "file_info")
@Getter 
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo extends CmmnDomain {
	@Column(name = "file_ext")
	private String fileExt;
	
	@Column(name = "file_name")
	private String fileName;
	
	@Column(name = "file_path")
	private String filePath;
	
	@Column(name = "origin_file_name")
	private String originFileName;
}
