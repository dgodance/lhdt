package lhdt.admin.svc.cityplanning.service;

import lhdt.admin.svc.cityplanning.domain.CPReportDetail;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * CPRpoert를 지원하는 Parser를 만들고 싶다면 해당 인터페이스를 구현하세요
 */
public interface CPReportProc {
    List<List<String>> readData(String fileName) throws IOException, InvalidFormatException;
    List<CPReportDetail> procDataObj(List<List<String>> excelDatas);
}
