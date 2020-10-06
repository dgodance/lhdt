package lhdt.svc.common;

import javax.persistence.MappedSuperclass;

import lhdt.cmmn.domain.CmmnDomain;
import lombok.Getter;
import lombok.Setter;

/**
 * 공통 도메인에 대하여 정의합니다
 */
@SuppressWarnings("serial")
@MappedSuperclass
@Getter
@Setter
public abstract class Domain extends CmmnDomain {
   
}
