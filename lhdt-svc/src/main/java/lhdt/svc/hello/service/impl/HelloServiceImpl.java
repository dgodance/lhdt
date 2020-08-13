/**
 * 
 */
package lhdt.svc.hello.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lhdt.svc.common.SvcServiceImpl;
import lhdt.svc.hello.domain.Hello;
import lhdt.svc.hello.persistence.HelloMapper;
import lhdt.svc.hello.persistence.HelloRepository;
import lhdt.svc.hello.service.HelloService;

/**
 * @author gravity@daumsoft.com
 * @since 2020. 8. 13.
 *
 */
@Service("helloService")
public class HelloServiceImpl extends SvcServiceImpl<HelloRepository, HelloMapper, Hello, Long> implements HelloService {
	@Autowired
	private HelloRepository jpaRepo;
	
	@Autowired
	private HelloMapper mapper;
	
	@Autowired
	private void init() {
		super.set(jpaRepo, mapper, Hello.builder().build());
	}
	

}
