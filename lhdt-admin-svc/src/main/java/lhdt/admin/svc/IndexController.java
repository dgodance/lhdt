/**
 * 
 */
package lhdt.admin.svc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lhdt.admin.svc.common.AdminSvcController;

/**
 * @author gravity
 * @since 2020. 8. 20.
 *
 */
@Controller
@RequestMapping("/")
public class IndexController extends AdminSvcController {

	@GetMapping("index")
	public String index() {
		return "index";
	}
}
