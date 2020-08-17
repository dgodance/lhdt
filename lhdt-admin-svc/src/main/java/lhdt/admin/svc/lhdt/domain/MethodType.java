package lhdt.admin.svc.lhdt.domain;

import lombok.Getter;
import lombok.Setter;

public enum MethodType {

	INSERT("등록"),
	UPDATE("수정");
	
	private @Setter @Getter String method;
	
	MethodType(String method) {
		this.method = method;
	}
	
}