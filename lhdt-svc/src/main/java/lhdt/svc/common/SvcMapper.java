/**
 * 
 */
package lhdt.svc.common;

/**
 * mybatis용 mapper의 부모
 * @author gravity@daumsoft.com
 * @since 2020. 8. 10.
 *
 */
public interface SvcMapper<DOMAIN, IDTYPE> {

	Integer getTotcnt();
}
