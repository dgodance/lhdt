package lhdt.sensorthingsapi.restapi.domain.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractEntity<T extends AbstractEntity<T>> {

	private Long id;
	private String selfLink;
	
	public AbstractEntity(Long id) {
		this.id = id;
	}
	

}
