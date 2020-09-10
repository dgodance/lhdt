/**
 * 
 */
package lhdt.svc.lhdt.userinfo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lhdt.svc.lhdt.userinfo.domain.UserInfo;
import lhdt.svc.lhdt.userinfo.persistence.UserInfoMapper;
import lhdt.svc.lhdt.userinfo.persistence.UserInfoRepository;
import lhdt.svc.lhdt.userinfo.service.UserInfoService;

/**
 * @author gravity@daumsoft.com
 * @since 2020. 8. 13.
 *
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {
	@Autowired
	private UserInfoRepository jpaRepo;
	
	@Autowired
	private UserInfoMapper mapper;
	
	@Override
	public List<UserInfo> findAll() {
		return jpaRepo.findAll();
	}

	@Override
	public int getTotcnt() {
		return mapper.getTotcnt();
	}

}
