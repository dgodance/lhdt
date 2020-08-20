package lhdt.sensorthingsapi.restapi.domain.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import lhdt.sensorthingsapi.restapi.domain.common.AbstractEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class NamedEntity <T extends NamedEntity<T>> extends AbstractEntity<T>{
	
	private String name;
	private String description;
	private Map<String, Object> properties;

	
	
	public NamedEntity(Long id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name, description, properties);
	}
	
	public void addProperty(String name, Object value) {
		if(properties == null)
			properties = new HashMap<String, Object>();
		properties.put(name, value);
		
	}
	

}
