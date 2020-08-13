/**
 * 
 */
package lhdt.svc.hello.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.hyunlab.core.PpMap;
import lhdt.svc.common.SvcController;
import lhdt.svc.hello.domain.Hello;
import lhdt.svc.hello.service.HelloService;

/**
 * @author gravity@daumsoft.com
 * @since 2020. 8. 13.
 *
 */
@RestController
@RequestMapping("/hello")
public class HelloController extends SvcController {

	@Autowired
	private HelloService service;
	
	@GetMapping("/")
	public ResponseEntity<Map<String, Object>> list(){
		
		//
		PpMap map =  new PpMap();
		
		//
		map.add("all", service.findAll());
		map.add("byBizKey", service.findByBizKey(Hello.builder().helloGroupId("abcd").helloGroupNo(1L).build()));
		map.add("byId", service.findById(1L));
		map.add("totcnt", service.getTotcnt());
		
		//
		return super.res(map);
	}
}
