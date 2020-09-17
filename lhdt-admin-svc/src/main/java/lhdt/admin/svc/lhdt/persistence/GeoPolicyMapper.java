package lhdt.admin.svc.lhdt.persistence;

import lhdt.admin.svc.lhdt.domain.GeoPolicy;
import lhdt.cmmn.config.LhdtConnMapper;

/**
 * 2D, 3D 운영 정책
 * @author jeongdae
 *
 */
@LhdtConnMapper
public interface GeoPolicyMapper {

	/**
	 * 운영 정책 정보
	 * @return
	 */
	GeoPolicy getGeoPolicy();
	
	/**
	 * 공간 정보 기본 수정
	 * @param geoPolicy
	 * @return
	 */
	int updateGeoPolicy(GeoPolicy geoPolicy);
	
	/**
	 * Geo Server 수정
	 * @param geoPolicy
	 * @return
	 */
	int updateGeoPolicyGeoServer(GeoPolicy geoPolicy);
}
