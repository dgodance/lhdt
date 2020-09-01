package lhdt.controller.view;

import lhdt.config.PropertiesConfig;
import lhdt.domain.Key;
import lhdt.domain.PageType;
import lhdt.domain.common.Pagination;
import lhdt.domain.common.Search;
import lhdt.domain.converter.ConverterJob;
import lhdt.domain.extrusionmodel.DataLibrary;
import lhdt.domain.extrusionmodel.DataLibraryGroup;
import lhdt.domain.extrusionmodel.DataLibraryUpload;
import lhdt.domain.user.UserSession;
import lhdt.service.DataLibraryGroupService;
import lhdt.service.DataLibraryService;
import lhdt.service.PolicyService;
import lhdt.support.SQLInjectSupport;
import lhdt.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 데이터 라이브러리
 */
@Slf4j
@Controller
@RequestMapping("/data-library-converter")
public class DataLibraryConverterController {

    // 파일 copy 시 버퍼 사이즈
    public static final int BUFFER_SIZE = 8192;

	@Autowired
	private DataLibraryGroupService dataLibraryGroupService;
	@Autowired
	private DataLibraryService dataLibraryService;
	@Autowired
	private PolicyService policyService;
	@Autowired
	private PropertiesConfig propertiesConfig;

	/**
     * 검색 조건
	 * @param pageType
     * @param search
     * @return
     */
	private String getSearchParameters(PageType pageType, Search search) {
		StringBuffer buffer = new StringBuffer(search.getParameters());
		boolean isListPage = true;
		if(pageType == PageType.MODIFY || pageType == PageType.DETAIL) {
			isListPage = false;
		}

//		if(!isListPage) {
//			buffer.append("pageNo=" + request.getParameter("pageNo"));
//			buffer.append("&");
//			buffer.append("list_count=" + dataLibraryUpload.getList_counter());
//		}

		return buffer.toString();
	}
}
