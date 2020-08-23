package lhdt.controller.rest;

import lhdt.domain.Key;
import lhdt.domain.newtown.NewTownGroup;
import lhdt.domain.user.UserSession;
import lhdt.service.NewTownGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 뉴타운 ajax 처리 관리
 */
@Slf4j
@RestController
@RequestMapping("/new-town-groups")
public class NewTownGroupRestController {

	@Autowired
	private NewTownGroupService newTownGroupService;

	/**
	 * 뉴타운 그룹 등록
	 * @param request
	 * @param newTownGroup
	 * @param bindingResult
	 * @return
	 */
	@PostMapping(value = "/insert")
	public Map<String, Object> insert(HttpServletRequest request, @Valid @ModelAttribute NewTownGroup newTownGroup, BindingResult bindingResult) {
		log.info("@@@@@ insert newTownGroup = {}", newTownGroup);

		Map<String, Object> result = new HashMap<>();
		String errorCode = null;
		String message = null;

		UserSession userSession = (UserSession)request.getSession().getAttribute(Key.USER_SESSION.name());

		if(bindingResult.hasErrors()) {
			message = bindingResult.getAllErrors().get(0).getDefaultMessage();
			log.info("@@@@@ message = {}", message);
			result.put("statusCode", HttpStatus.BAD_REQUEST.value());
			result.put("errorCode", errorCode);
			result.put("message", message);
            return result;
		}

		newTownGroup.setUserId(userSession.getUserId());

		newTownGroupService.insertNewTownGroup(newTownGroup);
		int statusCode = HttpStatus.OK.value();
			
		result.put("statusCode", statusCode);
		result.put("errorCode", errorCode);
		result.put("message", message);
		return result;
	}

	/**
	 * 뉴타운 그룹 수정
	 * @param request
	 * @param newTownGroup
	 * @param bindingResult
	 * @return
	 */
	@PostMapping(value = "/update")
	public Map<String, Object> update(HttpServletRequest request, @Valid NewTownGroup newTownGroup, BindingResult bindingResult) {
		log.info("@@ newTownGroup = {}", newTownGroup);
		Map<String, Object> result = new HashMap<>();
		String errorCode = null;
		String message = null;

		if(bindingResult.hasErrors()) {
			message = bindingResult.getAllErrors().get(0).getDefaultMessage();
			log.info("@@@@@ message = {}", message);
			result.put("statusCode", HttpStatus.BAD_REQUEST.value());
			result.put("errorCode", errorCode);
			result.put("message", message);
            return result;
		}

		newTownGroupService.updateNewTownGroup(newTownGroup);
		int statusCode = HttpStatus.OK.value();

		result.put("statusCode", statusCode);
		result.put("errorCode", errorCode);
		result.put("message", message);
		return result;
	}

	/**
	 * 뉴타운 그룹 트리 순서 수정 (up/down)
	 * @param request
	 * @param newTownGroupId
	 * @param newTownGroup
	 * @return
	 */
	@PostMapping(value = "/view-order/{newTownGroupId:[0-9]+}")
	public Map<String, Object> moveNewTownGroup(HttpServletRequest request, @PathVariable Integer newTownGroupId, @ModelAttribute NewTownGroup newTownGroup) {
		log.info("@@ newTownGroup = {}", newTownGroup);

		Map<String, Object> result = new HashMap<>();
		String errorCode = null;
		String message = null;
		
		newTownGroup.setNewTownGroupId(newTownGroupId);

		int updateCount = newTownGroupService.updateNewTownGroupViewOrder(newTownGroup);
		int statusCode = HttpStatus.OK.value();
		if(updateCount == 0) {
			statusCode = HttpStatus.BAD_REQUEST.value();
			errorCode = "layer.group.view-order.invalid";
		}
			
		result.put("statusCode", statusCode);
		result.put("errorCode", errorCode);
		result.put("message", message);
		return result;
	}
}
