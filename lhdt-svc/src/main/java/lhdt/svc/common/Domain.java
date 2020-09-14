package lhdt.svc.common;

import javax.persistence.MappedSuperclass;

import lhdt.cmmn.domain.CmmnDomain;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@MappedSuperclass
@Getter
@Setter
public abstract class Domain extends CmmnDomain {
   
}
