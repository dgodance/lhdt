package lhdt.admin.svc.lhdt.persistence;

import java.util.List;

import lhdt.admin.svc.lhdt.domain.ConverterJob;
import lhdt.admin.svc.lhdt.domain.ConverterJobFile;
import lhdt.cmmn.config.LhdtConnMapper;

/**
 * f4d converter manager
 * @author jeongdae
 *
 */
@LhdtConnMapper
public interface ConverterMapper {
	
	/**
	 * converter job 총 건수
	 * @param converterJob
	 * @return
	 */
	Long getConverterJobTotalCount(ConverterJob converterJob);
	
	/**
	 * converter job file 총 건수
	 * @param converterJobFile
	 * @return
	 */
	Long getConverterJobFileTotalCount(ConverterJobFile converterJobFile);
	
	/**
	 * f4d converter job 목록
	 * @param converterJob
	 * @return
	 */
	List<ConverterJob> getListConverterJob(ConverterJob converterJob);

	/**
	 * f4d converter job 목록
	 * @param converterJobFile
	 * @return
	 */
	List<ConverterJobFile> getListConverterJobFile(ConverterJobFile converterJobFile);

	/**
	 * 데이터 변환 현황
	 * @return
	 */
	List<ConverterJobFile> getConverterJobFileStatistics();
	
	/**
	 * insert converter job
	 * @param converterJob
	 * @return
	 */
	Long insertConverterJob(ConverterJob converterJob);
	
	/**
	 * insert converter job file
	 * @param converterJobFile
	 * @return
	 */
	Long insertConverterJobFile(ConverterJobFile converterJobFile);
	
	/**
	 * update
	 * @param converterJob
	 */
	int updateConverterJob(ConverterJob converterJob);
}
