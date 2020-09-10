/**
 * 
 */
package lhdt.admin.svc.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * lhdt접속 for jpa
 * @author gravity@daumsoft.com
 * @since 2020. 8. 13.
 *
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		entityManagerFactoryRef = "lhdtEntityManagerFactory",
		transactionManagerRef = "lhdtTransactionManager",
		basePackages = { "lhdt.admin.svc.lhdt.userinfo.persistence" }
		)
public class JpaLhdtConfig {

	@Bean(name = "lhdtDataSource")
	@ConfigurationProperties(prefix = "app.datasource.lhdt")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "lhdtEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean 
	lhdtEntityManagerFactory(
			EntityManagerFactoryBuilder builder,
			@Qualifier("lhdtDataSource") DataSource dataSource
			) {
		return
				builder
				.dataSource(dataSource)
				.packages("lhdt.admin.svc.lhdt")
				.persistenceUnit("bar")
				.build();
	}
	@Bean(name = "lhdtTransactionManager")
	public PlatformTransactionManager lhdtTransactionManager(
			@Qualifier("lhdtEntityManagerFactory") EntityManagerFactory
			lhdtEntityManagerFactory
			) {
		return new JpaTransactionManager(lhdtEntityManagerFactory);
	}
}
