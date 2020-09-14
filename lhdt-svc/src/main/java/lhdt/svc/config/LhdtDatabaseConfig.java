/**
 * 
 */
package lhdt.svc.config;

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
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lhdt.cmmn.config.LhdtConnMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * lhdt접속 for mybatis
 * @author gravity@daumsoft.com
 *
 */
@Slf4j
@Configuration
@MapperScan(value="lhdt.svc.lhdt", annotationClass = LhdtConnMapper.class, sqlSessionFactoryRef = "lhdtSqlSessionFactory")
@EnableTransactionManagement
public class LhdtDatabaseConfig {
//	
//	@Bean
//	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//
//		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//		vendorAdapter.setGenerateDdl(true);
//
//		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
//		factory.setJpaVendorAdapter(vendorAdapter);
//		factory.setPackagesToScan("lhdt.lhdt");
//		factory.setDataSource(lhdtDataSource());
//		return factory;
//	}
//	
//	@Bean
//	public PlatformTransactionManager transactionManager() {
//	    JpaTransactionManager transactionManager = new JpaTransactionManager();
//	    transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
//	 
//	    return transactionManager;
//	}
//	 
//	@Bean
//	public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
//	    return new PersistenceExceptionTranslationPostProcessor();
//	}
	
	
//	/**
//	 * TODO  각  접속정보 암호화 필요
//	 * @return
//	 */
//	@Bean(name="lhdtDataSource")
//	@ConfigurationProperties(prefix="app.datasource.lhdt")
//	public DataSource lhdtDataSource() {
//		DriverManagerDataSource dmds = DataSourceBuilder.create().type(DriverManagerDataSource.class).build();
//		
//		//
//		log.info("<< {}", ToStringBuilder.reflectionToString(dmds));
//		return dmds;
//	}
	
	@Bean(name = "lhdtSqlSessionFactory")
	public SqlSessionFactory lhdtSqlSessionFactory(@Qualifier("lhdtDataSource") DataSource lhdtDataSource, ApplicationContext context) throws Exception{
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(lhdtDataSource);
		
		//
		sqlSessionFactoryBean.setMapperLocations(context.getResources("classpath*:mybatis/lhdt/**/*.xml"));
		
		//
		SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
		
		//
		log.info("<< {}", ToStringBuilder.reflectionToString(sqlSessionFactory));
		return sqlSessionFactory;
	}
	
	@Bean(name = "lhdtSqlSessionTemplate")
	public SqlSessionTemplate lhdtSqlSessionTemplate(SqlSessionFactory lhdtSqlSessionFactory) throws Exception{
		SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(lhdtSqlSessionFactory);
		
		//
		log.info("<< {}", ToStringBuilder.reflectionToString(sqlSessionTemplate));
		return sqlSessionTemplate;
	}
}
