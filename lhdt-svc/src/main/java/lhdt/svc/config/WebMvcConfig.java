/**
 * 
 */
package lhdt.svc.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lhdt.svc.common.interceptor.MiscInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 * @author gravity@daumsoft.com
 *
 */
@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	@Autowired
	private MiscInterceptor miscInterceptor;

	@Autowired
	private ApplicationContext applicationContext;

	
	@PostConstruct
	private void init() {
		log.info("{}", this);
	}
	
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		//
		registry.addInterceptor(miscInterceptor)
			.addPathPatterns("/**")
			.excludePathPatterns("/", "/error", "/static/**");
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
