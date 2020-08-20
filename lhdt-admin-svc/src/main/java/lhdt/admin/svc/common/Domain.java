package lhdt.admin.svc.common;

import javax.persistence.MappedSuperclass;

import lhdt.ds.common.domain.DsDomain;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@MappedSuperclass
@Getter
@Setter
public abstract class Domain extends DsDomain {
   
}
