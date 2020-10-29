/**
 * 
 */
package lhdt.svc.cityplanning.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import dev.hyunlab.core.util.PpDateUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gravity
 * @since 2020. 10. 28.
 *
 */
@Slf4j
@Component("cityPlanExcelView")
public class CityPlanExcelView extends AbstractXlsxView {

	@PostConstruct
	private void init() {
		log.debug("<<.init - {}", this);
	}

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try(InputStream is = new ClassPathResource("excel/cityplan_template.xlsx").getInputStream()){

			try(OutputStream os = response.getOutputStream()){

				String filename = URLEncoder.encode("지구설계_"+PpDateUtil.getYmdhms()+".xlsx", StandardCharsets.UTF_8);
				response.setContentType("application/msexcel");
				response.setHeader("Content-Disposition", "attachment;filename=\""+filename+"\"");
				
				Context context = new Context();
				
				
				Iterator<String> iter = model.keySet().iterator();
				while(iter.hasNext()) {
					String k = iter.next();
					context.putVar(k, model.get(k));					
				}

				JxlsHelper.getInstance().processTemplate(is,  os, context);
				os.flush();

			}
		}

	}

}
