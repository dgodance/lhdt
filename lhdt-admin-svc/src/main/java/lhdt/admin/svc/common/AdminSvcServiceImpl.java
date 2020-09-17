/**
 * 
 */
package lhdt.admin.svc.common;

import lhdt.cmmn.misc.CmmnServiceImpl;

/**
 * 모든 serviceImpl의 부모
 * @author gravity@daumsoft.com
 * @since 2020. 8. 18.
 *
 */
public class AdminSvcServiceImpl<JPA, MAPPER, DOMAIN, IDTYPE> extends CmmnServiceImpl<JPA, MAPPER, DOMAIN, IDTYPE> implements AdminSvcService<DOMAIN, IDTYPE> {

}
