package lhdt.cmmn.domain;

import javax.persistence.MappedSuperclass;

import dev.hyunlab.core.vo.PpVO;
import lombok.Getter;
import lombok.Setter;

/**
 * schema=lhdt용 
 * @author gravity@daumsoft.com
 * @since 2020. 8. 13.
 *
 */
@SuppressWarnings("serial")
@MappedSuperclass
@Getter
@Setter
public abstract class LhdtDomain extends PpVO {

}
