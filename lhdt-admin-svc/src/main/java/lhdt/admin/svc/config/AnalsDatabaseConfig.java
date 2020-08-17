/**
 * 
 */
package lhdt.admin.svc.config;

import javax.sql.DataSource;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.extern.slf4j.Slf4j;

/**
 * lhdt-anals접속관련 for mybatis
 * @author gravity@daumsoft.com
 *
 */
@Slf4j
@Configuration
@MapperScan(value="lhdt.admin.svc", annotationClass = AnalsConnMapper.class, sqlSessionFactoryRef = "analsSqlSessionFactory")
@EnableTransactionManagement
public class AnalsDatabaseConfig {
	
//	@Bean
//	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//
//		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//		vendorAdapter.setGenerateDdl(true);
//
//		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
//		factory.setJpaVendorAdapter(vendorAdapter);
//		factory.setPackagesToScan("lhdt.svc");
//		factory.setDataSource(analsDataSource());
//		return factory;
//	}
	
//	@Bean
//	public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
//	    JpaTransactionManager transactionManager = new JpaTransactionManager();
//	    transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
//	 
//	    return transactionManager;
//	}
	 
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
	    return new PersistenceExceptionTranslationPostProcessor();
	}
	
	
	/**
	 * TODO  각  접속정보 암호화 필요
	 * @return
	 */
//	@Bean(name="analsDataSource")
//	@Primary
//	@ConfigurationProperties(prefix="app.datasource.anals")
//	public DataSource analsDataSource() {
//		DriverManagerDataSource dmds = DataSourceBuilder.create().type(DriverManagerDataSource.class).build();
//		
//		//
//		log.info("<< {}", ToStringBuilder.reflectionToString(dmds));
//		return dmds;
//	}
	
	@Bean(name = "analsSqlSessionFactory")
	@Primary
	public SqlSessionFactory analsSqlSessionFactory(@Qualifier("dataSource") DataSource dataSource, ApplicationContext context) throws Exception{
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		
		//
		sqlSessionFactoryBean.setMapperLocations(context.getResources("classpath*:mybatis/admin/svc/**/*.xml"));
		sqlSessionFactoryBean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("mybatis-config.xml"));

		
		//
		SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
		
		//
		log.info("<< {}", ToStringBuilder.reflectionToString(sqlSessionFactory));
		return sqlSessionFactory;
	}
	
	@Bean(name = "analsSqlSessionTemplate")
	@Primary
	public SqlSessionTemplate analsSqlSessionTemplate(SqlSessionFactory analsSqlSessionFactory) throws Exception{
		SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(analsSqlSessionFactory);
		
		//
		log.info("<< {}", ToStringBuilder.reflectionToString(sqlSessionTemplate));
		return sqlSessionTemplate;
	}
}
