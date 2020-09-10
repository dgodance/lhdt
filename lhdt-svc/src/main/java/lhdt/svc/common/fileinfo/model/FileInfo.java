/**
 * 
 */
package lhdt.svc.common.fileinfo.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import lhdt.ds.common.domain.DsDomain;
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
public class FileInfo extends DsDomain {
	@Column(name = "file_ext")
	private String fileExt;
	
	@Column(name = "file_name")
	private String fileName;
	
	@Column(name = "file_path")
	private String filePath;
	
	@Column(name = "origin_file_name")
	private String originFileName;
}
