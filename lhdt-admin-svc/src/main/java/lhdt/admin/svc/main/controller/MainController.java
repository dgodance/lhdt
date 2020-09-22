/**
 * 
 */
package lhdt.admin.svc.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lhdt.admin.svc.hello.service.HelloService;
import lhdt.cmmn.misc.CmmnConst;
import lhdt.cmmn.misc.CmmnController;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gravity
 * @since 2020. 8. 14.
 *
 */
@Slf4j
@Controller
@RequestMapping("/main")
public class MainController extends CmmnController {
	private static final String P = "main/";
	
	@Autowired
	private HelloService service;
	
	@RequestMapping("")
	public String index(Model model) {
		
		model.addAttribute(CmmnConst.DATAS, service.findAll());
		log.debug("{}", model);
		
		return P + "dashboard";
	}
}
