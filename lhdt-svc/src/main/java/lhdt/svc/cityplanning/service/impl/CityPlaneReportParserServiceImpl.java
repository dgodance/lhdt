package lhdt.svc.cityplanning.service.impl;

import java.util.ArrayList;
import java.util.List;

import lhdt.svc.cityplanning.domain.CityPlanReportDetail;
import lhdt.svc.cityplanning.model.UpDownString;
import lhdt.svc.cityplanning.types.UpDownType;

public class CityPlaneReportParserServiceImpl {

    @Override
    protected List<CityPlanReportDetail> procExcelDataObj(ArrayList<List<String>> excelDatas) {
        List<CityPlanReportDetail> cprds = new ArrayList<>();
        excelDatas.forEach(p -> constructCPRD(p, cprds));
        return cprds;
    }

    private void constructCPRD(List<String> p, List<CityPlanReportDetail> cprds) {
        CityPlanReportDetail cprd = new CityPlanReportDetail();
        var row0 = parseRowA(p.get(0));
        var row1 = parseRowB(p.get(1));
        var row2 = parseRowC(p.get(2));
        var row3 = parseRowD(p.get(3));
        var row4 = parseRowE(p.get(4));
        var row5 = parseRowF(p.get(5));
        var row6 = parseRowG(p.get(6));
        var row7 = parseRowH(p.get(7));
        var row8 = parseRowI(p.get(8));
        var row9 = parseRowJ(p.get(9));
        var row10 = parseRowK(p.get(10));

        cprd.setDrawingId(row0.get(0));
        cprd.setHouseholdId(row0.get(1));
        cprd.setArea(Long.valueOf(row1));
        cprd.setAllowableUse(row2);
        cprd.setNotAllowableUse(row3);
        cprd.setBuildingToLandRatio(Long.valueOf(row4.getRatio()));
        cprd.setFloorAreaRatio(Long.valueOf(row5));
        cprd.setAreaMaxHeight(Long.valueOf(row6));
        cprd.setFloorMaxHeight(Long.valueOf(row7.getRatio()));
        cprd.setAprartmentScale(Long.valueOf(row8));
        cprd.setNumOfApartmentHouse(Long.valueOf(row9));
        cprd.setSalesType(row10);

        cprds.add(cprd);
    }

    /**
     * 구분 -> 도면번호, 가구번호
     * @param s
     * @return
     */
    private List<String> parseRowA(String s) {
        List<String> strs = new ArrayList<>();
        String drawingId = "";
        String householdId = "";
        boolean flag = false;
        for ( char c : s.toCharArray() ) {
            if(c == '(' && flag == false) {
                flag = true;
                continue;
            } else if (c == ')') {
                continue;
            }
            if(flag) {
                householdId += c;
            } else {
                drawingId += c;
            }
        }
        strs.add(drawingId);
        strs.add(householdId);

        if(drawingId.equals(""))
            throw new RuntimeException();

        if(householdId.equals(""))
            throw new RuntimeException();

        return strs;
    }

    /**
     * 면적
     * @param s
     * @return
     */
    private Integer parseRowB(String s) {
        return string2Int(s);
    }

    /**
     * 허용용도
     * @param s
     * @return
     */
    private String parseRowC(String s) {
        return s;
    }

    /**
     * 불허용도
     * @param s
     * @return
     */
    private String parseRowD(String s) {
        return s;
    }

    /**
     * 건폐율
     * @param s
     * @return
     */
    private UpDownString parseRowE(String s) {
        return upDownStringParser(s);
    }

    /**
     * 용적률
     * @param s
     * @return
     */
    private Integer parseRowF(String s) {
        return string2Int(s);
    }

    /**
     * 최고높이
     * @param s
     * @return
     */
    private Integer parseRowG(String s) {
        if(s.equals(""))
            return 0;
        else
            return string2Int(s);
    }

    /**
     * 최고층수
     * @param s
     * @return
     */
    private UpDownString parseRowH(String s) {
        return upDownStringParser(s);

    }

    /**
     * 공동주택규모
     * @param s
     * @return
     */
    private Integer parseRowI(String s) {
        return string2Int(s);
    }

    /**
     * 공동주택세대수
     * @param s
     * @return
     */
    private Integer parseRowJ(String s) {
        return string2Int(s);
    }

    /**
     * 분양형태
     * @param s
     * @return
     */
    private String parseRowK(String s) {
        return s;
    }

    /**
     * 50%이하, 20층이하 => 20(Inteager), 이하(Enum) 파싱
     * @param s
     * @return
     */
    private UpDownString upDownStringParser(String s) {
        UpDownType udt;
        if(s.contains("이상")) {
            udt = UpDownType.이상;
        } else if(s.contains("이하")) {
            udt = UpDownType.이하;
        } else {
            udt = null;
        }
        var intStr = string2Int(s);

        if(udt == null)
            throw new NullPointerException();

        return UpDownString.builder().udt(udt).ratio(intStr).build();
    }

    private Integer string2Int(String s) {
        String intStr = s.replaceAll("[^0-9]", "");
        return Integer.parseInt(intStr);
    }
}
