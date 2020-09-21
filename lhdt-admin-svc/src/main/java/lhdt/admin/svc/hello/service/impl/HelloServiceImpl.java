/**
 * 
 */
package lhdt.admin.svc.hello.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lhdt.admin.svc.common.AdminSvcServiceImpl;
import lhdt.admin.svc.hello.domain.Hello;
import lhdt.admin.svc.hello.persistence.HelloMapper;
import lhdt.admin.svc.hello.persistence.HelloRepository;
import lhdt.admin.svc.hello.service.HelloService;

/**
 * @author gravity
 * @since 2020. 8. 14.
 *
 */
@Service("helloService")
public class HelloServiceImpl extends AdminSvcServiceImpl<HelloRepository, HelloMapper, Hello, Long> implements HelloService {
	@Autowired
	private HelloRepository jpaRepo;
	
	@Autowired
	private HelloMapper mapper;
	
	@PostConstruct
	private void init() {
		super.set(jpaRepo, mapper, Hello.builder().build());
	}
}
