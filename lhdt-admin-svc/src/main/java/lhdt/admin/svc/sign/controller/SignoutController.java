package lhdt.admin.svc.sign.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import dev.hyunlab.web.util.PpWebSessionUtil;
import lhdt.admin.svc.lhdt.domain.UserSession;
import lhdt.ds.common.misc.DsConst;
import lhdt.ds.common.misc.DsController;
import lombok.extern.slf4j.Slf4j;

/**
 * Sign Out 처리
 * 
 * @author jeongdae
 */
@Slf4j
@Controller
@RequestMapping("/sign")
public class SignoutController extends DsController {

	
	@Value("#{servletContext.contextPath}")
	private String contextPath;
	
	@Value("${app.login.uri}")
	private String loginUri;
	
	/**
	 * Sign out
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/signout")
	public RedirectView signout(HttpServletRequest request) {
		UserSession userSession =   (UserSession)PpWebSessionUtil.get(request, DsConst.USER_SESSION);

		if(userSession == null) {
			return new RedirectView(contextPath + loginUri);
		}

		//
		HttpSession session = request.getSession();
		session.removeAttribute(userSession.getUserId());
		session.removeAttribute(DsConst.USER_SESSION);
		session.invalidate();

		//
		return new RedirectView(contextPath + loginUri);
	}
}
