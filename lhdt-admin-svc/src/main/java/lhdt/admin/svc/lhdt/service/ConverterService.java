package lhdt.admin.svc.lhdt.service;

import java.util.List;

import lhdt.admin.svc.lhdt.domain.ConverterJob;
import lhdt.admin.svc.lhdt.domain.ConverterJobFile;

/**
 * f4d converting manager
 * @author Cheon JeongDae
 *
 */
public interface ConverterService {
	
	/**
	 * converter job 총 건수
	 * @param converterJob
	 * @return
	 */
	public Long getConverterJobTotalCount(ConverterJob converterJob);
	
	/**
	 * converter job file 총 건수
	 * @param converterJobFile
	 * @return
	 */
	public Long getConverterJobFileTotalCount(ConverterJobFile converterJobFile);
	
	/**
	 * f4d converter job 목록
	 * @param converterJob
	 * @return
	 */
	public List<ConverterJob> getListConverterJob(ConverterJob converterJob);
	
	/**
	 * f4d converter job 목록
	 * @param converterJobFile
	 * @return
	 */
	public List<ConverterJobFile> getListConverterJobFile(ConverterJobFile converterJobFile);

	/**
	 * 데이터 변환 현황
	 * @return
	 */
	public List<ConverterJobFile> getConverterJobFileStatistics();
	
	/**
	 * f4d converter 변환 job 등록
	 * @param converterJob
	 * @return
	 */
	public int insertConverter(ConverterJob converterJob);
	
	/**
	 * 데이터 변환 작업 상태를 변경
	 * @param converterJob
	 * @return
	 */
	public int updateConverterJob(ConverterJob converterJob);
}
