package lhdt.sensorthingsapi.restapi.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Things")
@NamedQueries({
    @NamedQuery(name = "Things.findAll", query = "SELECT t FROM Things t")
})

@Getter
@Setter
public class Things implements Serializable{
	
	 private static final long serialVersionUID = 1L;
	 
	 @Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	 private Long id;
	 private Long datastreams;
	 private Long hist_locations;
	 private Long location;
	 private String name;
	 private String description;
	 private String properties;
	 
	 public Things() {
		 
	 }
	 
	
	

}
