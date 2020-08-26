package lhdt.api;

import lhdt.common.BaseControllerTest;
import lhdt.persistence.DataMapper;
import lhdt.persistence.DesignLayerMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GeometryAPIControllerTests extends BaseControllerTest {

    @Autowired
    private DataMapper dataMapper;
    @Autowired
    private DesignLayerMapper designLayerMapper;

    @BeforeAll
    public void insert() {

    }

}