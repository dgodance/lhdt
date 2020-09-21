/**
 * 
 */
package lhdt.svc.lhdt.userinfo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lhdt.svc.lhdt.userinfo.service.UserInfoService;

/**
 * @author gravity
 * @since 2020. 8. 13.
 *
 */
@RestController
@RequestMapping("/userinfo")
public class UserInfoController {
	@Autowired
	private UserInfoService service;
	
	
//	@GetMapping("/")
//	public PpMap list() {
//		return new PpMap().add("datas", service.findAll()).add("totcnt", service.getTotcnt());
//	}
}
