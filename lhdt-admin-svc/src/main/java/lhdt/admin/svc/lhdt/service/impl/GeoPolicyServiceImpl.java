package lhdt.admin.svc.lhdt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lhdt.admin.svc.lhdt.domain.GeoPolicy;
import lhdt.admin.svc.lhdt.persistence.GeoPolicyMapper;
import lhdt.admin.svc.lhdt.service.GeoPolicyService;

/**
 * 2D, 3D 운영 정책
 * @author jeongdae
 *
 */
@Service
public class GeoPolicyServiceImpl implements GeoPolicyService {

	@Autowired
	private GeoPolicyMapper geoPolicyMapper;
	
	/**
	 * 운영 정책 정보
	 * @return
	 */
	@Transactional(readOnly=true)
	public GeoPolicy getGeoPolicy() {
		return geoPolicyMapper.getGeoPolicy();
	}
	
	/**
	 * 공간 정보 기본 수정
	 * @param geoPolicy
	 * @return
	 */
	@Transactional
	public int updateGeoPolicy(GeoPolicy geoPolicy) {
		return geoPolicyMapper.updateGeoPolicy(geoPolicy);
	}
	
	/**
	 * Geo Server 수정
	 * @param geoPolicy
	 * @return
	 */
	@Transactional
	public int updateGeoPolicyGeoServer(GeoPolicy geoPolicy) {
		return geoPolicyMapper.updateGeoPolicyGeoServer(geoPolicy);
	}
}
