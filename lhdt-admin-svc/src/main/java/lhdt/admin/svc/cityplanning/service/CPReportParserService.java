package lhdt.admin.svc.cityplanning.service;

import lhdt.admin.svc.cityplanning.domain.CPReportDetail;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 지구계획 레포트의 핵심 비즈니스 로직 기능을 제공합니다
 */
@Service
public class CPReportParserService {
    /**
     * 데이터로 부터 CP 레포트 객체를 리턴해줍니다
     * @param CPReportProc
     * @param fullPath
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     */
    public List<CPReportDetail> procDataByCityPlanReport(CPReportProc CPReportProc, String fullPath) throws IOException, InvalidFormatException {
        var p = CPReportProc.readData(fullPath);
        var result = CPReportProc.procDataObj(p);
        return result;
    }
}
