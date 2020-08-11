/**
 * 
 */
package lhdt.anals.hello.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lhdt.anals.common.AnalsServiceImpl;
import lhdt.anals.hello.domain.Hello;
import lhdt.anals.hello.persistence.HelloMapper;
import lhdt.anals.hello.persistence.HelloRepository;
import lhdt.anals.hello.service.HelloService;

/**
 * 안녕 service impl
 * @author gravity@daumsoft.com
 *
 */
@Service("helloService")
public class HelloServiceImpl extends AnalsServiceImpl<HelloRepository, HelloMapper, Hello, Long> implements HelloService {


	/**
	 * jpa
	 */
	@Autowired
	private HelloRepository jpaRepo;
	
	/**
	 * mybatis
	 */
	@Autowired
	private HelloMapper mapper;
	
	
	@PostConstruct
	private void init() {
		super.set(jpaRepo, mapper, Hello.builder().build());
	}

	


}
