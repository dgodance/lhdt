package lhdt.geospatial;

import lhdt.domain.DesignLayer;
import lhdt.domain.ShapeFileField;
import lhdt.support.LogMessageSupport;
import lombok.extern.slf4j.Slf4j;
import org.geotools.data.FeatureSource;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.dbf.DbaseFileHeader;
import org.geotools.data.shapefile.dbf.DbaseFileReader;
import org.geotools.data.shapefile.files.ShpFileType;
import org.geotools.data.shapefile.files.ShpFiles;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.locationtech.jts.geom.Geometry;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Shape file 관련 유틸
 * TODO 이름을 바꾸던, 패키지를 바꾸던
 */
@Slf4j
public class ShapeFileParser {

    // shapefile 경로
    private final String filePath;

    public ShapeFileParser(String filePath) {
        this.filePath = filePath;
    }

    public List<DesignLayer> getExtrusionModelList(String extrusionColumns) {
        List<DesignLayer> extrusionModelList = new ArrayList<>();
        List<String> columnList = Arrays.asList(extrusionColumns.trim().toLowerCase().split(","));
        try {
            ShapefileDataStore shpDataStore = new ShapefileDataStore(new File(filePath).toURI().toURL());
            shpDataStore.setCharset(StandardCharsets.UTF_8);
            String typeName = shpDataStore.getTypeNames()[0];
            FeatureSource<SimpleFeatureType, SimpleFeature> shapeFeatureSource = shpDataStore.getFeatureSource(typeName);
            FeatureCollection<SimpleFeatureType, SimpleFeature> collection = shapeFeatureSource.getFeatures();
            FeatureIterator<SimpleFeature> features = collection.features();
            while (features.hasNext()) {
                SimpleFeature feature = features.next();
                DesignLayer extrusionModel = new DesignLayer();
                for (Property attribute : feature.getProperties()) {
                    String attributeName = String.valueOf(attribute.getName()).toLowerCase();
                    if(columnList.contains(attributeName)) {
                        if(attributeName.equalsIgnoreCase(String.valueOf(DesignLayer.RequiredColumn.THE_GEOM))) {
                            extrusionModel.setTheGeom((Geometry) attribute.getValue());
                        } else if(attributeName.equalsIgnoreCase(String.valueOf(DesignLayer.RequiredColumn.ATTRIBUTES))) {
                            extrusionModel.setAttributes((String) attribute.getValue());
                        }
                    }
                }
                extrusionModelList.add(extrusionModel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return extrusionModelList;
    }

    /**
     * shape file의 필수 칼럼 검사
     *
     * @return
     */
    public Boolean fieldValidate() {
        DbaseFileReader reader = null;
        boolean fieldValid = false;
        try {
            ShpFiles shpFile = new ShpFiles(filePath);
            if (!shpFile.exists(ShpFileType.SHP)) {
                return false;
            }
            // field만 검사할 것이기 때문에 따로 인코딩은 설정하지 않음 
            reader = new DbaseFileReader(shpFile, false, Charset.defaultCharset());
            DbaseFileHeader header = reader.getHeader();
            int filedValidCount = 0;
            // 필드 카운트
            for (int iField = 0; iField < header.getNumFields(); iField++) {
                String fieldName = header.getFieldName(iField);
                if (ShapeFileField.findBy(fieldName) != null) filedValidCount++;
            }
            // 필수 칼럼이 모두 있는지 확인한 결과 리턴 
            fieldValid = filedValidCount == ShapeFileField.values().length;

            reader.close();
        } catch (MalformedURLException e) {
            LogMessageSupport.printMessage(e, "MalformedURLException ============ {}", e.getMessage());
        } catch (IOException e) {
            LogMessageSupport.printMessage(e, "IOException ============== {} ", e.getMessage());
        }

        return fieldValid;
    }
}
