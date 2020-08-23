package lhdt.service.impl;

import lhdt.domain.newtown.NewTown;
import lhdt.persistence.NewTownMapper;
import lhdt.service.NewTownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 뉴타운
 * @author jeongdae
 *
 */
@Service
public class NewTownServiceImpl implements NewTownService {
	
	@Autowired
	private NewTownMapper newTownMapper;
	
	/**
	 * 뉴타운 수
	 * @param newTown
	 * @return
	 */
	@Transactional(readOnly=true)
	public Long getNewTownTotalCount(NewTown newTown) {
		return newTownMapper.getNewTownTotalCount(newTown);
	}
	
	/**
	 * 뉴타운 목록
	 * @param newTown
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<NewTown> getListNewTown(NewTown newTown) {
		return newTownMapper.getListNewTown(newTown);
	}
	
	/**
	 * 뉴타운 정보
	 * @param newTownId
	 * @return
	 */
	@Transactional(readOnly=true)
	public NewTown getNewTown(Integer newTownId) {
		return newTownMapper.getNewTown(newTownId);
	}
	
	/**
	 * 뉴타운 등록
	 * @param newTown
	 * @return
	 */
	@Transactional
	public int insertNewTown(NewTown newTown) {
		return newTownMapper.insertNewTown(newTown);
	}
	
	/**
	 * 뉴타운 수정
	 * @param newTown
	 * @return
	 */
	@Transactional
	public int updateNewTown(NewTown newTown) {
		return newTownMapper.updateNewTown(newTown);
	}
	
	/**
	 * 뉴타운 삭제
	 * @param  newTownId
	 * @return
	 */
	@Transactional
	public int deleteNewTown(Integer newTownId) {
		return newTownMapper.deleteNewTown( newTownId);
	}
}
