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
public interface DataLibraryMapper {

	/**
	 * 데이터 라이브러리 수
	 * @param dataLibrary
	 * @return
	 */
	Long getDataLibraryTotalCount(DataLibrary dataLibrary);

	/**
	 * 데이터 라이브러리 목록
	 * @param dataLibrary
	 * @return
	 */
	List<DataLibrary> getListDataLibrary(DataLibrary dataLibrary);
	
	/**
	 * 데이터 그룹에 포함되는 모든 데이터를 취득
	 * @param dataLibraryGroupId
	 * @return
	 */
	List<DataLibrary> getListAllDataLibraryByDataLibraryGroupId(Integer dataLibraryGroupId);
	
	/**
	 * 데이터 라이브러리 Key 중복 건수
	 * @param dataLibrary
	 * @return
	 */
	Integer getDuplicationKeyCount(DataLibrary dataLibrary);
	
	/**
	 * 데이터 라이브러리 정보 취득
	 * @param dataLibrary
	 * @return
	 */
	DataLibrary getDataLibrary(DataLibrary dataLibrary);
	
	/**
	 * 데이터 라이브러리 정보 취득
	 * @param dataLibrary
	 * @return
	 */
	DataLibrary getDataLibraryByDataLibraryKey(DataLibrary dataLibrary);
	
	/**
	 * 최상위 root 데이터 라이브러리 정보 취득
	 * @param dataLibraryGroupId
	 * @return
	 */
	DataLibrary getRootDataLibraryByDataLibraryGroupId(Integer dataLibraryGroupId);

	/**
	 * 데이터 라이브러리 정보 취득
	 * @param dataLibrary
	 * @return
	 */
	List<DataLibrary> getDataLibraryByConverterJob(DataLibrary dataLibrary);

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
	
	/**
	 * 데이터 라이브러리 등록
	 * @param dataLibrary
	 * @return
	 */
	int insertDataLibrary(DataLibrary dataLibrary);
	
	/**
	 * 데이터 라이브러리 수정
	 * @param dataLibrary
	 * @return
	 */
	int updateDataLibrary(DataLibrary dataLibrary);
	
	/**
	 * 데이터 라이브러리 상태 수정
	 * @param dataLibrary
	 * @return
	 */
	int updateDataLibraryStatus(DataLibrary dataLibrary);
	
	/**
	 * 데이터 라이브러리 삭제
	 * @param dataLibrary
	 * @return
	 */
	int deleteDataLibrary(DataLibrary dataLibrary);
	
	/**
	 * 데이터 라이브러리 그룹 고유 번호를 이용한 삭제
	 * @param dataLibraryGroup
	 * @return
	 */
	int deleteDataLibraryByDataLibraryGroupId(DataLibraryGroup dataLibraryGroup);
	
	/**
	 * 데이터 라이브러리 삭제
	 * @param dataLibrary
	 * @return
	 */
	int deleteDataLibraryByConverterJob(DataLibrary dataLibrary);
	
	/**
	 * 사용자 아이디를 이용한 데이터 라이브러리 삭제
	 * @param userId
	 * @return
	 */
	int deleteDataLibraryByUserId(String userId);
}
