package lhdt.anals.cityplanning.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class CityPlaneReportParserServiceImpl {
    public void procExcelData(String filePath) throws IOException {
        FileInputStream inputStream = new FileInputStream(filePath);
        HSSFWorkbook workbook = new HSSFWorkbook(inputStream);//엑셀읽기
        HSSFSheet sheet = workbook.getSheetAt(0);//시트가져오기 0은 첫번째 시트
        int rows = sheet.getPhysicalNumberOfRows();//시트에서 총 행수
        for(int j=1;j<=12;j++) {
            for(int i=3;i<rows;i++) {
                //해당 셀 값 가져오기
                HSSFCell cell = sheet.getRow(i).getCell(j);
                String value = "";
                if(cell.getCellType() == HSSFCell.CELL_TYPE_BLANK){
                    value="";
                } else {
                    //타입별로 내용 읽기
                    switch (cell.getCellType()){
                        case HSSFCell.CELL_TYPE_FORMULA:
                            value=cell.getCellFormula();
                            break;
                        case HSSFCell.CELL_TYPE_NUMERIC:
                            value=cell.getNumericCellValue()+"";
                            break;
                        case HSSFCell.CELL_TYPE_STRING:
                            value=cell.getStringCellValue()+"";
                            break;
                        case HSSFCell.CELL_TYPE_BLANK:
                            value=cell.getBooleanCellValue()+"";
                            break;
                        case HSSFCell.CELL_TYPE_ERROR:
                            value=cell.getErrorCellValue()+"";
                            break;
                    }
                }
            }
        }
    }
}
