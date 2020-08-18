package lhdt.admin.svc.lhdt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lhdt.admin.svc.lhdt.domain.Policy;
import lhdt.admin.svc.lhdt.persistence.PolicyMapper;
import lhdt.admin.svc.lhdt.service.PolicyService;

/**
 * mago3d 운영 정책
 * @author jeongdae
 *
 */
@Service
public class PolicyServiceImpl implements PolicyService {

	@Autowired
	private PolicyMapper policyMapper;
	
	/**
	 * 운영 정책 정보
	 * TODO API
	 * @return
	 */
	@Transactional(readOnly=true)
	public Policy getPolicy() {
		String json = "{\"policyId\":1,\"userIdMinLength\":5,\"userFailSigninCount\":3,\"userFailLockRelease\":\"30\",\"userLastSigninLock\":\"90\",\"userDuplicationSigninYn\":\"N\",\"userSigninType\":\"0\",\"userUpdateCheck\":\"0\",\"userDeleteCheck\":\"0\",\"userDeleteType\":\"0\",\"passwordChangeTerm\":\"30\",\"passwordMinLength\":8,\"passwordMaxLength\":32,\"passwordEngUpperCount\":1,\"passwordEngLowerCount\":1,\"passwordNumberCount\":1,\"passwordSpecialCharCount\":1,\"passwordContinuousCharCount\":3,\"passwordCreateType\":\"0\",\"passwordCreateChar\":\"!@#\",\"passwordExceptionChar\":\"<>&'\\\"\",\"noticeServiceYn\":\"Y\",\"noticeServiceSendType\":\"0\",\"noticeApprovalRequestYn\":\"N\",\"noticeApprovalSignYn\":\"N\",\"noticePasswordUpdateYn\":\"N\",\"noticeApprovalDelayYn\":\"N\",\"noticeRiskYn\":\"N\",\"noticeRiskSendType\":\"0\",\"noticeRiskGrade\":\"1\",\"securitySessionTimeoutYn\":\"N\",\"securitySessionTimeout\":\"30\",\"securityUserIpCheckYn\":\"N\",\"securitySessionHijacking\":\"0\",\"securityLogSaveType\":\"0\",\"securityLogSaveTerm\":\"2\",\"securityDynamicBlockYn\":\"N\",\"securityApiResultSecureYn\":\"N\",\"securityMaskingYn\":\"Y\",\"contentCacheVersion\":1,\"contentMainWidgetCount\":6,\"contentMainWidgetInterval\":65,\"contentMonitoringInterval\":1,\"contentStatisticsInterval\":\"0\",\"contentLoadBalancingInterval\":10,\"contentMenuGroupRoot\":\"mago3D\",\"contentUserGroupRoot\":\"mago3D\",\"contentLayerGroupRoot\":\"mago3D\",\"contentDataGroupRoot\":\"mago3D\",\"contentDesignLayerGroupRoot\":null,\"contentDataLibraryGroupRoot\":null,\"userUploadType\":\"3ds,obj,dae,collada,ifc,las,gml,citygml,indoorgml,jpg,jpeg,gif,png,bmp,dds,zip,mtl,max\",\"userConverterType\":\"3ds,obj,dae,collada,ifc,las,gml,citygml,indoorgml\",\"userUploadMaxFilesize\":10000,\"userUploadMaxCount\":500,\"shapeUploadType\":\"cpg,dbf,idx,sbn,sbx,shp,shx,prj,qpj,zip\"}";
		try {
			return new ObjectMapper().readValue(json, Policy.class);
		} catch (JsonProcessingException e) {
			System.err.println(e);
			return null;
		}
//		return policyMapper.getPolicy();
	}
	
	/**
	 * 업로딩 가능 확장자 조회
	 * @return 업로딩 가능 확장자
	 */
	@Transactional(readOnly=true)
	public String getUserUploadType() {
		String userUploadType = policyMapper.getUserUploadType();
		String[] userUploadTypes = userUploadType.split(",");
		List<String> acceptedFiles = new ArrayList<>();
		for (String uploadType : userUploadTypes) {
			uploadType = "." + uploadType;
			acceptedFiles.add(uploadType);
		}
		return String.join(",", acceptedFiles);
	}
	
	/**
	 * 운영 정책 사용자 수정
	 * @param policy
	 * @return
	 */
	@Transactional
	public int updatePolicyUser(Policy policy) {
		return policyMapper.updatePolicyUser(policy);
	}
	
	/**
	 * 운영 정책 패스워드 수정
	 * @param policy
	 * @return
	 */
	@Transactional
	public int updatePolicyPassword(Policy policy) {
		return policyMapper.updatePolicyPassword(policy);
	}
	
	/**
	 * 운영 정책 알림 수정
	 * @param policy
	 * @return
	 */
	@Transactional
	public int updatePolicyNotice(Policy policy) {
		return policyMapper.updatePolicyNotice(policy);
	}
	
	/**
	 * 운영 정책 보안 수정
	 * @param policy
	 * @return
	 */
	@Transactional
	public int updatePolicySecurity(Policy policy) {
		return policyMapper.updatePolicySecurity(policy);
	}
	
	/**
	 * 운영 정책 기타 수정
	 * @param policy
	 * @return
	 */
	@Transactional
	public int updatePolicyContent(Policy policy) {
		return policyMapper.updatePolicyContent(policy);
	}
	
	/**
	 * 사용자 파일 업로딩 정책 수정
	 * @param policy
	 * @return
	 */
	@Transactional
	public int updatePolicyUserUpload(Policy policy) {
		return policyMapper.updatePolicyUserUpload(policy);
	}

}
