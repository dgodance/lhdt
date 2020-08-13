/**
 * 
 */
package lhdt.svc.config;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.extern.slf4j.Slf4j;

/**
 * lhdt-anals접속 for jpa
 * persistence추가되면 basePackages추가하삼. TODO 나중에 일반화 예정
 * @author gravity@daumsoft.com
 * @since 2020. 8. 13.
 *
 */
@Slf4j
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		entityManagerFactoryRef = "entityManagerFactory",
		basePackages = { 
				"lhdt.svc.cityplanning.persistence"
				,"lhdt.svc.hello.persistence"
				,"lhdt.svc.landscape.persistence"
				,"lhdt.svc.lowinfo.persistence" 
				}
		)
public class JpaAnalsConfig {

	@PostConstruct
	private void init() {
		log.info("{}",this);
	}


	@Primary
	@Bean(name = "dataSource")
	@ConfigurationProperties(prefix = "app.datasource.anals")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}



	@Primary
	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(	EntityManagerFactoryBuilder builder,@Qualifier("dataSource") DataSource dataSource	) {
		return builder
				.dataSource(dataSource)
				.packages("lhdt.svc")
				.persistenceUnit("foo")
				.build();
	}


	@Primary
	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

}
