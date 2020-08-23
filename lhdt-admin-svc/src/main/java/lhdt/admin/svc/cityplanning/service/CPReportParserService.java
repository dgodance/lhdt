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

@Service
public class CPReportParserService {
    public List<CPReportDetail> procDataByCityPlanReport(CPReportProc CPReportProc, String fullPath) throws IOException, InvalidFormatException {
        var p = CPReportProc.readData(fullPath);
        var result = CPReportProc.procDataObj(p);
        return result;
    }
}
