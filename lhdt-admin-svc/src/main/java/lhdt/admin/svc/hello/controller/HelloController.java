/**
 * 
 */
package lhdt.admin.svc.hello.controller;

import java.util.Date;

import javax.faces.annotation.RequestMap;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lhdt.admin.svc.hello.service.HelloService;
import lhdt.ds.common.misc.DsConst;
import lhdt.ds.common.misc.DsController;

/**
 * @author gravity@daumsoft.com
 * @since 2020. 8. 14.
 *
 */
@Controller
@RequestMapping("/hello")
public class HelloController extends DsController {
	
	@Autowired
	private HelloService service;

	
	@RequestMapping("/helloList.do")
	public String helloList(HttpServletRequest request, Model model) {
		
		model.addAttribute("dt", new Date());
		model.addAttribute(DsConst.DATAS, service.findAll());
		//
		return "hello/helloList";
	}
}
