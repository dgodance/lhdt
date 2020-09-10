package lhdt.admin.svc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan({"lhdt.ds.common", "lhdt.admin.svc"})
@SpringBootApplication
public class LhdtAdminSvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(LhdtAdminSvcApplication.class, args);
	}

}
