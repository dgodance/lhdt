/**
 * 
 */
package lhdt.svc.cityplanning.controller.view;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import lhdt.svc.cityplanning.view.CityPlanExcelView;
import lhdt.svc.common.SvcController;

/**
 * @author gravity
 * @since 2020. 10. 28.
 *
 */
@Controller
public class CityPlanExcelController extends SvcController {

	/**
	 * 지구설계 - 엑셀 다운로드
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("cityplan/exceldwld")
	public ModelAndView cityPlanExcelDwld(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ModelAndView mav = new ModelAndView(new CityPlanExcelView());
		 		
		String jsonString = request.getParameter("jsonString");
		Map<String,Object> paramMap = (new ObjectMapper()).readValue(jsonString, Map.class);
		
		Map<String,Object> urbanMap = (Map<String, Object>) paramMap.get("urban");
		mav.addObject("urban", urbanMap);
		

		return mav;
	}

}
