package lhdt.admin.svc.cityplanning.service.impl;

import com.opencsv.CSVReader;
import lhdt.admin.svc.cityplanning.domain.CPReportDetail;
import lhdt.admin.svc.cityplanning.exception.NotSupportCsvFileException;
import lhdt.admin.svc.cityplanning.service.CPReportProc;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CPCsvReportParserServiceImpl implements CPReportProc {

    @Override
    public List<List<String>> readData(String fullPath) throws IOException {
        List<List<String>> records = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(fullPath));) {
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                if(values.length != 24)
                    throw new NotSupportCsvFileException(fullPath, values.toString(), values.length);
                records.add(Arrays.asList(values));
            }
        }
        return records;
    }

    @Override
    public List<CPReportDetail> procDataObj(List<List<String>> excelDatas) {
        List<CPReportDetail> cprds = new ArrayList<>();
        excelDatas.forEach(p -> constructCPRD(p, cprds));
        return cprds;
    }

    private void constructCPRD(List<String> p, List<CPReportDetail> cprds) {
        CPReportDetail cprd = new CPReportDetail();

        var lotId = Long.parseLong(p.get(0));
        var projectTypeString = p.get(1);
        var projectTitle = p.get(2);
        var blockCode = p.get(3);
        var lotCode = p.get(4);
        var lotArea = Long.parseLong(p.get(5));
        var landUseZoning = p.get(6);
        var landUsePlan = p.get(7);
        var lotDivideMarge = p.get(8);
        var buildingUse = p.get(9);
        var buildingUseDefined = p.get(10);
        var buildingUseRecommended = p.get(11);
        var buildingUseAllowed = p.get(12);
        var buildingUseConditional = p.get(13);
        var buildingUseForbidden = p.get(14);
        var buildingCoverageRatio = Long.parseLong(p.get(15));
        var floorAreaRatio = Long.parseLong(p.get(16));
        var floorAreaRatioStandard = Long.parseLong(p.get(17));
        var floorAreaRatioAllowed = Long.parseLong(p.get(18));
        var floorAreaRatioMaximum = Long.parseLong(p.get(19));
        var maximunBuildingHeight = Long.parseLong(p.get(20));
        var maximunBuildingFloors = Long.parseLong(p.get(21));
        var housingTypeString = p.get(22);
        var numberOfHouseholds = Long.parseLong(p.get(23));
        var reference = p.get(24);

        cprd.setLotId(lotId);
        cprd.setProjectTypeString(projectTypeString);
        cprd.setProjectTitle(projectTitle);
        cprd.setBlockCode(blockCode);
        cprd.setLotCode(lotCode);
        cprd.setLotArea(lotArea);
        cprd.setLandUseZoning(landUseZoning);
        cprd.setLandUsePlan(landUsePlan);
        cprd.setLotDivideMarge(lotDivideMarge);
        cprd.setBuildingUse(buildingUse);
        cprd.setBuildingUseDefined(buildingUseDefined);
        cprd.setBuildingUseRecommended(buildingUseRecommended);
        cprd.setBuildingUseAllowed(buildingUseAllowed);
        cprd.setBuildingUseConditional(buildingUseConditional);
        cprd.setBuildingUseForbidden(buildingUseForbidden);
        cprd.setBuildingCoverageRatio(buildingCoverageRatio);
        cprd.setFloorAreaRatio(floorAreaRatio);
        cprd.setFloorAreaRatioStandard(floorAreaRatioStandard);
        cprd.setFloorAreaRatioAllowed(floorAreaRatioAllowed);
        cprd.setFloorAreaRatioMaximum(floorAreaRatioMaximum);
        cprd.setMaximunBuildingHeight(maximunBuildingHeight);
        cprd.setMaximunBuildingFloors(maximunBuildingFloors);
        cprd.setHousingTypeString(housingTypeString);
        cprd.setNumberOfHouseholds(numberOfHouseholds);
        cprd.setReference(reference);

        cprds.add(cprd);
    }
}
