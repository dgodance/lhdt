/**
 * 
 */
package lhdt.admin.svc.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lhdt.admin.svc.hello.service.HelloService;
import lhdt.ds.common.misc.DsConst;
import lhdt.ds.common.misc.DsController;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gravity@daumsoft.com
 * @since 2020. 8. 14.
 *
 */
@Slf4j
@Controller
@RequestMapping("/main")
public class MainController extends DsController {
	private static final String P = "main/";
	
	@Autowired
	private HelloService service;
	
	@RequestMapping("/index")
	public String index(Model model) {
		
		model.addAttribute(DsConst.DATAS, service.findAll());
		log.debug("{}", model);
		
		return P + "index";
	}
}
