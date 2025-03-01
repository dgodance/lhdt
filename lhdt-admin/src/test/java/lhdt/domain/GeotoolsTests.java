package lhdt.domain;

import lhdt.domain.extrusionmodel.DesignLayer;
import lombok.extern.slf4j.Slf4j;
import org.geotools.data.*;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.dbf.DbaseFileHeader;
import org.geotools.data.shapefile.dbf.DbaseFileReader;
import org.geotools.data.shapefile.files.ShpFiles;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.feature.type.GeometryTypeImpl;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.MultiPolygon;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
public class GeotoolsTests {

    private JSONParser parser = new JSONParser();

    @Disabled
    void 필드_확인() throws IOException {
        ShpFiles shpFile = new ShpFiles("D:\\test\\shape\\02_과천과천_시범단지샘플_1_필지.dxf(byLay)L.shp");
        DbaseFileReader r = new DbaseFileReader(shpFile, false, Charset.forName("UTF-8"));
        DbaseFileHeader header = r.getHeader();
        int numFields = header.getNumFields();
        for (int iField = 0; iField < numFields; ++iField) {
            String fieldName = header.getFieldName(iField);
            log.info("filed name ={}", fieldName);
        }
    }

    @Disabled
    void 필드값_확인() throws IOException {
        FeatureSource<SimpleFeatureType, SimpleFeature> shapeFeatureSource = getShape();
        FeatureCollection<SimpleFeatureType, SimpleFeature> collection = shapeFeatureSource.getFeatures();
        FeatureIterator<SimpleFeature> features = collection.features();
        while (features.hasNext()) {
            SimpleFeature feature = features.next();
            log.info("feature ID ===== {}", feature.getID());
            for (Property attribute : feature.getProperties()) {
                // geometry 필드인 경우
                if (attribute.getType() instanceof GeometryTypeImpl) {
                    log.info("\t\t" + attribute.getName() + ":" + attribute.getValue());
                    // 일반 필드
                } else {
                    log.info("\t" + attribute.getName() + ":" + attribute.getValue());
                }
            }
        }
    }

    @Disabled
    void 특정필드_필터() throws IOException {
        String extrusionRequiredColumns = "the_geom, layer, angle, flnum";
        String[] columns = extrusionRequiredColumns.split(",");
        FeatureSource<SimpleFeatureType, SimpleFeature> shapeFeatureSource = getShape();
        FeatureCollection<SimpleFeatureType, SimpleFeature> collection = shapeFeatureSource.getFeatures();
        FeatureIterator<SimpleFeature> features = collection.features();
        while (features.hasNext()) {
            SimpleFeature feature = features.next();
            DesignLayer extrusionModel = new DesignLayer();
            for (Property attribute : feature.getProperties()) {
                boolean test = Arrays.stream(columns).anyMatch(f -> f.trim().equalsIgnoreCase(String.valueOf(attribute.getName())));
                log.info("anyMatch ============== {} : {}", attribute.getName(), test);
            }
        }
    }

    @Disabled
    void 특정필드_추출() throws IOException {
        List<DesignLayer> extrusionModelList = new ArrayList<>();
        String extrusionColumns = "the_geom, layer, angle, flnum";
        List<String> columnList = Arrays.asList(extrusionColumns.trim().toLowerCase().split(","));
        FeatureSource<SimpleFeatureType, SimpleFeature> shapeFeatureSource = getShape();
        FeatureCollection<SimpleFeatureType, SimpleFeature> collection = shapeFeatureSource.getFeatures();
        FeatureIterator<SimpleFeature> features = collection.features();
        while (features.hasNext()) {
            SimpleFeature feature = features.next();
            DesignLayer extrusionModel = new DesignLayer();
            for (Property attribute : feature.getProperties()) {
                String attributeName = String.valueOf(attribute.getName()).toLowerCase();
                if (columnList.contains(attributeName)) {
                    if (attributeName.equalsIgnoreCase(String.valueOf(DesignLayer.RequiredColumn.THE_GEOM))) {
                        extrusionModel.setTheGeom(attribute.getValue().toString());
                    }
//                    else if (attributeName.equalsIgnoreCase(String.valueOf(DesignLayer.RequiredColumn.ATTRIBUTES))) {
//                        extrusionModel.setAttributes(attribute.getValue().toString());
//                    }
                }
            }
            extrusionModelList.add(extrusionModel);
        }
        log.info("extrutionModelList =========== {} ", extrusionModelList);
    }

    @Disabled
    void 필드_추가() throws IOException {
    }

    @Disabled
    void 필드_삭제() throws IOException {
        FeatureSource<SimpleFeatureType, SimpleFeature> featureSource = getShape();
        FeatureCollection<SimpleFeatureType, SimpleFeature> features = featureSource.getFeatures();
        SimpleFeatureType schema = features.getSchema();
        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        builder.remove("enable_yn");
        SimpleFeatureType outType = builder.buildFeatureType();
//        SimpleFeatureSource Source = dataStore.getFeatureSource(schema.getName().getLocalPart());
        FeatureIterator<SimpleFeature> attributes = features.features();
        while (attributes.hasNext()) {
            SimpleFeature feature = attributes.next();
            log.info(feature.getID() + ": ");
            for (Property attribute : feature.getProperties()) {
                if (attribute.getType() instanceof GeometryTypeImpl) {
                    log.info("\t\t" + attribute.getName() + ":" + attribute.getValue());
                } else {
                    log.info("\t" + attribute.getName() + ":" + attribute.getValue());
                }
            }
        }
    }

    @Disabled
    void shape파일_인코딩_설정() throws IOException {
        FeatureSource<SimpleFeatureType, SimpleFeature> featureSource = getShape();
        FeatureCollection<SimpleFeatureType, SimpleFeature> result = featureSource.getFeatures();
        System.out.println("Geographical elements [records]:" + result.size() + "individual");
        System.out.println("==================================");
        try (FeatureIterator<SimpleFeature> features = result.features()) {
            while (features.hasNext()) {
                SimpleFeature feature = features.next();
                log.info(feature.getID() + ": ");
                for (Property attribute : feature.getProperties()) {
                    if (attribute.getType() instanceof GeometryTypeImpl) {
                        log.info("\t\t" + attribute.getName() + ":" + attribute.getValue());
                    } else {
                        log.info("\t" + attribute.getName() + ":" + attribute.getValue());
                    }
                }
            }
        }
    }

    @Disabled
    void 인코딩판별() {

    }

    @Disabled
    void 좌표계_변경() {

    }

    @Disabled
    void shape스키마_이름변경_DB_insert() throws IOException {
        // db init
        Map<String, Object> params = new HashMap<>();
        params.put("dbtype", "postgis");
        params.put("host", "localhost");
        params.put("port", 15432);
        params.put("schema", "public");
        params.put("database", "lhdt");
        params.put("user", "postgres");
        params.put("passwd", "postgres");
        DataStore dataStore = DataStoreFinder.getDataStore(params);
        log.info("dataStore ===================== {} ", dataStore);
        FeatureSource<SimpleFeatureType, SimpleFeature> shapeFeatureSource = getShape();
        FeatureCollection<SimpleFeatureType, SimpleFeature> features = shapeFeatureSource.getFeatures();
        SimpleFeatureType schema = features.getSchema();
        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        // layerKey 로 rename
        builder.setName("test_table");
        builder.setSuperType((SimpleFeatureType) schema.getSuper());
        builder.addAll(schema.getAttributeDescriptors());
        schema = builder.buildFeatureType();
        log.info("schema ============== {} ", schema);

        try {
            /*
             * Write the features
             */
            Transaction transaction = new DefaultTransaction("shape");

            String[] typeNames = dataStore.getTypeNames();
            //first check if we need to create table
            boolean exists = false;
            String schemaName = schema.getName().getLocalPart();
            log.info("schemaName ============== {} ", schemaName);
            for (String name : typeNames) {
                log.info("typeName ============  {} ", name);
                if (schemaName.equalsIgnoreCase(name)) {
                    exists = true;
                    break;
                }
            }
            // 테이블이 없다면 생성
            if (!exists) {
                dataStore.createSchema(schema);
            }
            //we should probably check the schema matches the existing table.
            SimpleFeatureSource featureSource = dataStore.getFeatureSource(schema.getName().getLocalPart());
            if (featureSource instanceof SimpleFeatureStore) {
                SimpleFeatureStore featureStore = (SimpleFeatureStore) featureSource;

                featureStore.setTransaction(transaction);
                try {
                    featureStore.addFeatures(features);
                    transaction.commit();
                } catch (Exception problem) {
                    problem.printStackTrace();
                    transaction.rollback();
                } finally {
                    transaction.close();
                }
                dataStore.dispose();
            } else {
                dataStore.dispose();
                log.error("Database not writable");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Disabled
    void sqlExport() {

    }

    @Test
    void ShapeExportLocationJSON() throws IOException {
        String SHAPE_ID = "ID";
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        FeatureSource<SimpleFeatureType, SimpleFeature> shapeFeatureSource = getShape();
        FeatureCollection<SimpleFeatureType, SimpleFeature> collection = shapeFeatureSource.getFeatures();
        FeatureIterator<SimpleFeature> features = collection.features();
        while (features.hasNext()) {
            SimpleFeature feature = features.next();
            for (Property attribute : feature.getProperties()) {
                var attributeName = attribute.getName().toString();
                // geometry 필드인 경우
                if (attribute.getType() instanceof GeometryTypeImpl) {
                    log.info("\t\t" + attribute.getName() + ":" + attribute.getValue());
                    MultiPolygon multiPolygon = (MultiPolygon) attribute.getValue();
                    log.info("location ====================== {} ", multiPolygon.getCentroid());
                    jsonObject.put("location", multiPolygon.getCentroid().toString());
                    // 일반 필드
                } else if (attributeName.equalsIgnoreCase(SHAPE_ID)) {
                    log.info("\t" + attribute.getName() + ":" + attribute.getValue());
                    jsonObject.put("id", attribute.getValue());
                }
                jsonArray.add(jsonObject);
            }
        }

//        log.info("jsonArray ============= {} ", jsonArray);

        FileWriter file = new FileWriter("D:\\shapeLocationJson.json", true);
        file.write(jsonArray.toJSONString());
        file.flush();
        file.close();
    }

    /**
     * shape feature 정보 리턴
     *
     * @return
     * @throws IOException
     */
    private FeatureSource<SimpleFeatureType, SimpleFeature> getShape() throws IOException {
        // shape load
        ShapefileDataStore shpDataStore = new ShapefileDataStore(new File("D:\\data\\lh\\incheon_gy_shape\\인천계양_도로명주소연계 완료.shp").toURI().toURL());
        shpDataStore.setCharset(StandardCharsets.UTF_8);
        String typeName = shpDataStore.getTypeNames()[0];
        log.info("shp[Layer) Name: {} ", typeName);

        return shpDataStore.getFeatureSource(typeName);
    }
}
