package lhdt.admin.svc.cityplanning.service;

import lhdt.admin.svc.cityplanning.domain.CPReportDetail;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class CPReportParserService {
    protected ArrayList<List<String>> readExcelData(String fileName) throws IOException, InvalidFormatException {
        ArrayList<List<String>> resultCol = new ArrayList<>();

        Workbook wb = WorkbookFactory.create(new File(fileName)); // Or .xlsx
        Sheet s = wb.getSheetAt(0);
        Integer lastRowNum = s.getLastRowNum();
        for(int i = 1; i < lastRowNum; i++) {
            Row r1 = s.getRow(i);
            Short lastCelNum = r1.getLastCellNum();
            List<String> row = new ArrayList<>();
            for(int j = 0; j < lastCelNum; j++) {
                var p = r1.getCell(j).toString();
                row.add(p);
            }
            resultCol.add(row);
        }
        return resultCol;
    }

    protected abstract List<CPReportDetail> procExcelDataObj(ArrayList<List<String>> excelDatas);

    public List<CPReportDetail> procExcelDataByCityPlan(String fileName) throws IOException, InvalidFormatException {
        var p = readExcelData(fileName);
        var resultCol = procExcelDataObj(p);
        return resultCol;
    }

}
