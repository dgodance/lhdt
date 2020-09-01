package lhdt.persistence;

import lhdt.domain.extrusionmodel.DataLibrary;
import lhdt.domain.extrusionmodel.DataLibraryGroup;
import lhdt.domain.extrusionmodel.DataLibraryUpload;
import lhdt.domain.extrusionmodel.DataLibraryUploadFile;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 데이터 라이브러리
 * @author jeongdae
 *
 */
@Repository
public interface DataLibraryUploadMapper {

	/**
	 * 데이터 라이브러리 업로드 파일 총 건수
	 * @param dataLibraryUpload
	 * @return
	 */
	Long getDataLibraryUploadTotalCount(DataLibraryUpload dataLibraryUpload);

	/**
	 * 데이터 라이브러리 업로드 파일 목록
	 * @param dataLibraryUpload
	 * @return
	 */
	List<DataLibraryUpload> getListDataLibraryUpload(DataLibraryUpload dataLibraryUpload);

	/**
	 * 데이터 라이브러리 업로드 파일 목록
	 * @param dataLibraryUpload
	 * @return
	 */
	List<DataLibraryUploadFile> getListDataLibraryUploadFile(DataLibraryUpload dataLibraryUpload);

	/**
	 * 데이터 라이브러리 업로딩 정보
	 * @param dataLibraryUpload
	 * @return
	 */
	DataLibraryUpload getDataLibraryUpload(DataLibraryUpload dataLibraryUpload);

	/**
	 * 데이터 라이브러리 업로딩 정보 입력
	 * @param dataLibraryUpload
	 * @return
	 */
	int insertDataLibraryUpload(DataLibraryUpload dataLibraryUpload);

	/**
	 * 데이터 라이브러리 업로딩 파일 정보 입력
	 * @param dataLibraryUploadFile
	 * @return
	 */
	int insertDataLibraryUploadFile(DataLibraryUploadFile dataLibraryUploadFile);
}
