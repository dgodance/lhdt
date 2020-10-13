/**
 * 
 */
package lhdt.admin.svc.sign.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import lhdt.admin.svc.lhdt.domain.CacheManager;
import lhdt.admin.svc.lhdt.domain.Policy;
import lhdt.admin.svc.lhdt.domain.RoleKey;
import lhdt.admin.svc.lhdt.domain.UserInfo;
import lhdt.admin.svc.lhdt.domain.UserSession;
import lhdt.admin.svc.lhdt.domain.UserStatus;
import lhdt.admin.svc.lhdt.service.PolicyService;
import lhdt.admin.svc.lhdt.service.SigninService;
import lhdt.admin.svc.lhdt.support.PasswordSupport;
import lhdt.admin.svc.lhdt.support.RoleSupport;
import lhdt.admin.svc.lhdt.support.SessionUserSupport;
import lhdt.cmmn.misc.CmmnConst;
import lhdt.cmmn.misc.CmmnController;
import lhdt.cmmn.misc.CmmnSessionUtils;
import lhdt.cmmn.misc.CmmnUtils;
import lhdt.cmmn.misc.CmmnWebUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gravity
 * @since 2020. 8. 14.
 *
 */
@Slf4j
@Controller
@RequestMapping("/sign")
public class SigninController extends CmmnController {
	
	@Autowired
	private PolicyService policyService;
	@Autowired
	private SigninService signinService;
	
	
	private static final String P = "sign/";
	
	
	@RequestMapping("/signin")
	public String signin(Model model) {
		
		model.addAttribute("signinForm", new UserInfo());
		model.addAttribute("policy", new HashMap<>());
		model.addAttribute("contentCacheVersion", CmmnUtils.createShortUid(""));
		
		return P + "signin";
	}
	
	

	/**
	 * Sign in 처리
	 * @param request
	 * @param signinForm
	 * @param bindingResult
	 * @param model
	 * @return
	 */
	@PostMapping(value = "/process-signin")
	public RedirectView processSignin(HttpServletRequest request, @Valid @ModelAttribute("signinForm") UserInfo signinForm, BindingResult bindingResult, Model model) {
		Policy policy = policyService.getPolicy();
			
		signinForm.setPasswordChangeTerm(policy.getPasswordChangeTerm());
		signinForm.setUserLastSigninLock(policy.getUserLastSigninLock());
		UserSession userSession = signinService.getUserSession(signinForm);
		log.info("@@ userSession = {} ", userSession);
		
		String errorCode = validate(request, policy, signinForm, userSession);
//		if(errorCode != null) {
//			if("usersession.password.invalid".equals(errorCode)) {
//				userSession.setFailSigninCount(userSession.getFailSigninCount() + 1);
//				// 실패 횟수가 운영 정책의 횟수와 일치할 경우 잠금(비밀번호 실패횟수 초과)
//				if(userSession.getFailSigninCount() >= policy.getUserFailSigninCount()) {
//					log.error("@@ 비밀번호 실패 횟수 초과에 의해 잠김 처리됨");
//					userSession.setStatus(UserStatus.FAIL_SIGNIN_COUNT_OVER.getValue());
//					signinForm.setStatus(UserStatus.FAIL_SIGNIN_COUNT_OVER.getValue());
//				}
//				signinService.updateSigninUserSession(userSession);
//				
//				bindingResult.rejectValue("userId", "usersession.password.invalid");
//			} else if("usersession.lastsignin.invalid".equals(errorCode)) {
//				userSession.setStatus(UserStatus.SLEEP.getValue());
//				signinService.updateSigninUserSession(userSession);
//				
//				bindingResult.rejectValue("userId", "usersession.lastsignin.invalid");
//			} else {
//				bindingResult.rejectValue("userId", errorCode);
//			}
//			
//			log.error("@@ errorCode = {} ", errorCode);
//			signinForm.setErrorCode(errorCode);
//			signinForm.setUserId(null);
//			signinForm.setPassword(null);
//			model.addAttribute("signinForm", signinForm);
//			model.addAttribute("policy", policy);
//			
//			return "/sign/signin";
//		}
		
		// 사용자 정보를 갱신
		userSession.setFailSigninCount(0);
		signinService.updateSigninUserSession(userSession);
		
		// TODO 고민을 하자. 사인인 시점에 토큰을 발행해서 사용하고.... 비밀번호와 SALT는 초기화 해서 세션에 저장할지
//		userSession.setPassword(null);
//		userSession.setSalt(null);
		
		userSession.setSigninIp(CmmnWebUtils.getConectIp(request));
//		LhdtHttpSessionBindingListener sessionListener = new LhdtHttpSessionBindingListener();
//		request.getSession().setAttribute("USER_SESSION", userSession);
//		request.getSession().setAttribute(userSession.getUserId(), sessionListener);
//		if(DsConst.Y.equals(policy.getSecuritySessionTimeoutYn())) {
//			// 세션 타임 아웃 시간을 초 단위로 변경해서 설정
//			request.getSession().setMaxInactiveInterval(Integer.valueOf(policy.getSecuritySessionTimeout()).intValue() * 60);
//		}

		// 패스워드 변경 기간이 오버 되었거나 , 6:임시 비밀번호(비밀번호 찾기, 관리자 설정에 의한 임시 비밀번호 발급 시)
//		if(userSession.getPasswordChangeTermOver() || UserStatus.TEMP_PASSWORD == UserStatus.findBy(userSession.getStatus())){
//			return "redirect:/user/modify-password";
//		}
		
		//TODO 중복로그인이면...
		
		//세션에 로그인 정보 저장
		CmmnSessionUtils.set(request, CmmnConst.USER_SESSION, userSession);
		
		//
		return new RedirectView("../main");
	}
	
	

	/**
	 * 사용자 정보 유효성을 체크하여 에러 코드를 리턴
	 * @param request
	 * @param policy
	 * @param signinForm
	 * @param userSession
	 * @return
	 */
	private String validate(HttpServletRequest request, Policy policy, UserInfo signinForm, UserSession userSession) {
		// 사용자 정보가 존재하지 않을 경우
		if(userSession == null) {
			return "user.session.empty";
		}
		
		// 비밀번호 불일치
		if(!PasswordSupport.isEquals(userSession.getPassword(), signinForm.getPassword())) {
			return "usersession.password.invalid";
		}
		
		// 회원 상태 체크
		if(UserStatus.USE != UserStatus.findBy(userSession.getStatus()) && UserStatus.TEMP_PASSWORD != UserStatus.findBy(userSession.getStatus())) {
			// 0:사용중, 1:사용중지(관리자), 2:잠금(비밀번호 실패횟수 초과), 3:휴면(사인인 기간), 4:만료(사용기간 종료), 5:삭제(화면 비표시)
			signinForm.setStatus(userSession.getStatus());
			return "usersession.status.invalid";
		}
		
		// 사인인 실패 횟수
		if(userSession.getFailSigninCount() >= policy.getUserFailSigninCount()) {
			signinForm.setFailSigninCount(userSession.getFailSigninCount());
			return "usersession.failsignincount.invalid";
		}
		
		// 마지막 접속일(접속 정책이 3개월 미접속인 경우 접속 금지의 경우)
		if(userSession.getUserLastSigninLockOver()) {
			signinForm.setLastSigninDate(userSession.getLastSigninDate());
			signinForm.setUserLastSigninLock(policy.getUserLastSigninLock());
			return "usersession.lastsignin.invalid";
		}
		
		// 초기 세팅시만 이 값을 N으로 세팅해서 사용자 Role 체크 하지 않음
		if(CmmnConst.N.equals(userSession.getUserRoleCheckYn())) {
			// 사용자 그룹 ROLE 확인
			List<String> userGroupRoleKeyList = CacheManager.getUserGroupRoleKeyList(userSession.getUserGroupId());
			if(!RoleSupport.isUserGroupRoleValid(userGroupRoleKeyList, RoleKey.ADMIN_SIGNIN.name())) {
	 			return "usersession.role.invalid";
			}
		}
		
//		// 사용자 IP 체크
//		if(Policy.Y.equals(policy.getSecurity_user_ip_check_yn())) {
//			UserDevice userDevice = new UserDevice();
//			userDevice.setUser_id(userSession.getUser_id());
//			userDevice.setDevice_ip(WebUtil.getClientIp(request));
//			UserDevice dbUserDevice = userDeviceService.getUserDeviceByUserIp(userDevice);
//			if(dbUserDevice == null || dbUserDevice.getUser_device_id() == null || dbUserDevice.getUser_device_id().longValue() <= 0l) {
//				return "userdevice.ip.invalid";
//			}
//		}
			
		// TODO 사용기간이 종료 되었는지 확인할것
		
		// 중복 사인인 허용 하지 않을 경우, 동일 아이디로 생성된 세션이 존재할 경우 파기
		log.info("##################################### userDuplicationSigninYn() = {}", policy.getUserDuplicationSigninYn());
		if(CmmnConst.N.equals(policy.getUserDuplicationSigninYn())) {
			if(SessionUserSupport.isExistSession(userSession.getUserId())) {
				log.info("######################### 중복 사인인 userId = {}", userSession.getUserId());
				SessionUserSupport.invalidateSession(userSession.getUserId());
			}
		}
		
		return null;
	}
	
}
