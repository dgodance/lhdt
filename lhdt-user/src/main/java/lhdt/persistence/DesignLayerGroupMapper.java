package lhdt.persistence;

import lhdt.domain.extrusionmodel.DesignLayerGroup;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesignLayerGroupMapper {

	/**
     * 디자인 레이어 그룹 목록
     * @return
     */
    List<DesignLayerGroup> getListDesignLayerGroup();

    /**
     * 디자인 레이어 정보 조회
     * @param designLayerGroupId
     * @return
     */
    DesignLayerGroup getDesignLayerGroup(Integer designLayerGroupId);

    /**
     * 디자인 레이어 그룹 등록
     * @param designLayerGroup
     * @return
     */
    int insertDesignLayerGroup(DesignLayerGroup designLayerGroup);

    /**
     * 디자인 레이어 그룹 삭제
     * @return
     */
    int deleteAllDesignLayerGroup();
}
