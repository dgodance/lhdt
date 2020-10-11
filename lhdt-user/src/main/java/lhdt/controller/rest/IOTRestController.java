package lhdt.controller.rest;

import lhdt.domain.Key;
import lhdt.domain.layer.LayerGroup;
import lhdt.domain.user.UserSession;
import lhdt.service.LayerGroupService;
import lhdt.service.UserPolicyService;
import lhdt.support.LayerDisplaySupport;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/iot")
public class IOTRestController {

	/**
	 * 레이어 그룹 목록
	 * @param request
	 * @param model
	 * @return
	 */
	@GetMapping
	public Map<String, Object> list(HttpServletRequest request, Model model) {
		UserSession userSession = (UserSession)request.getSession().getAttribute(Key.USER_SESSION.name());
		Map<String, Object> result = new HashMap<>();
		String errorCode = null;
		String message = null;
		
		int statusCode = HttpStatus.OK.value();
		
		result.put("statusCode", statusCode);
		result.put("errorCode", errorCode);
		result.put("message", message);
		
		return result;
	}
}
