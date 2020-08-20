package lhdt.sensorthingsapi.restapi.controller;
import lhdt.sensorthingsapi.restapi.domain.Things;
import lhdt.sensorthingsapi.restapi.repository.ThingsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ThingsController {
	@Autowired
	ThingsRepository thingsRepository;
	
	@GetMapping("/things")
	public List<Things> getAllThings(){
		return thingsRepository.findAll();
	}
	
	@PostMapping("/things")
	public Things createThings(@Valid @RequestBody Things thing) {
		return thingsRepository.save(thing);
	}
}
