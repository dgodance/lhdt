package lhdt.persistence;

import org.springframework.stereotype.Repository;

import lhdt.domain.policy.GeoPolicy;

/**
 * 2D, 3D 운영 정책
 * @author jeongdae
 *
 */
@Repository
public interface GeoPolicyMapper {

	/**
	 * 운영 정책 정보
	 * @return
	 */
	GeoPolicy getGeoPolicy();
}
