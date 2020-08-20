/**
 * 
 */
package lhdt.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 지구 계획
 * @author gravity@daumsoft.com
 * @since 2020. 8. 20.
 *
 */
@Controller
@RequestMapping("/cityplan/")
public class CityPlanController {
	private static final String P = "/cityplan/";
	
	/**
	 * 지구 단위 계획 확인
	 * @return
	 */
	@RequestMapping("city-unit-plan-confm")
	public String cityUnitPlanConfm() {
		
		return P + "city-unit-plan-confm"; 
	}
	
	/**
	 * 평균 높이 확인
	 * @return
	 */
	@RequestMapping("avrg-hg-confm")
	public String avrgHgConfm() {
		
		return P + "avrg-hg-confm"; 
	}
	
	/**
	 * 지구 계획 실행
	 * @return
	 */
	@RequestMapping("city-plan-exc")
	public String cityPlanExc() {
		
		return P + "city-plan-exc"; 
	}
	
}
