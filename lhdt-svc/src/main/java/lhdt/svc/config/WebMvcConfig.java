/**
 * 
 */
package lhdt.svc.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lhdt.ds.common.interceptor.DsMiscInterceptor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gravity@daumsoft.com
 *
 */
@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	@Autowired
	private DsMiscInterceptor miscInterceptor;
	
	@PostConstruct
	private void init() {
		log.info("{}", this);
	}
	
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		//
		registry.addInterceptor(miscInterceptor)
			.addPathPatterns("/**")
			.excludePathPatterns("/", "/error", "/static/**", "/images/**", "/css/**", "/js/**");
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/data/**").addResourceLocations("classpath:data/");
	}
}
