/**
 * 
 */
package lhdt.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author gravity@daumsoft.com
 * @since 2020. 8. 20.
 *
 */
@Controller
@RequestMapping("/cityplan/")
public class CityPlanController {
	private static final String P = "/cityplan/";
	
	@RequestMapping("view-point")
	public String viewPoint() {
		
		return P + "view-point"; 
	}
}
