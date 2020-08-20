package lhdt.sensorthingsapi.restapi.repository;

import org.springframework.data.jpa.repository.*;

import lhdt.sensorthingsapi.restapi.domain.Things;

public interface ThingsRepository extends JpaRepository<Things, Long>{
	
}
