/**
 * 
 */
package lhdt.admin.svc.common;

import lhdt.ds.common.misc.DsServiceImpl;

/**
 * 모든 serviceImpl의 부모
 * @author gravity@daumsoft.com
 * @since 2020. 8. 18.
 *
 */
public class AdminSvcServiceImpl<JPA, MAPPER, DOMAIN, IDTYPE> extends DsServiceImpl<JPA, MAPPER, DOMAIN, IDTYPE> implements AdminSvcService<DOMAIN, IDTYPE> {

}
