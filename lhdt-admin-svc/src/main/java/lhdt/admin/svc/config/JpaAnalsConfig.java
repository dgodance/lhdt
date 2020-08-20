/**
 * 
 */
package lhdt.admin.svc.config;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.lang.builder.ToStringBuilder;
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
				"lhdt.admin.svc.hello.persistence",
//				"lhdt.admin.svc.cityplanning.persistence",
//				"lhdt.admin.svc.landscape.persistence",
				"lhdt.admin.svc.lowinfo.persistence"
				}
		)
public class JpaAnalsConfig {

	@PostConstruct
	private void init() {
		log.info("{}",ToStringBuilder.reflectionToString(this));
	}


	@Primary
	@Bean(name = "dataSource")
	@ConfigurationProperties(prefix = "app.datasource.anals")
	public DataSource dataSource() {
		DataSource ds = DataSourceBuilder.create().build();
		
		//
		log.info("<<.dataSource - {}", ToStringBuilder.reflectionToString(ds));
		return ds;
	}



	@Primary
	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(	EntityManagerFactoryBuilder builder,@Qualifier("dataSource") DataSource dataSource	) {
		LocalContainerEntityManagerFactoryBean bean = builder
				.dataSource(dataSource)
				.packages("lhdt.admin.svc.hello", "lhdt.admin.svc.lowinfo")
				.persistenceUnit("foo")
				.build();
		
		//
		log.info("<<.entityManagerFactory - {}", ToStringBuilder.reflectionToString(bean));
		return bean;
	}


	@Primary
	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
		PlatformTransactionManager manager = new JpaTransactionManager(entityManagerFactory);
		
		//
		log.info("<<.transactionManager - {}", ToStringBuilder.reflectionToString(manager));
		return manager;
	}

}
