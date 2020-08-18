/**
 * 
 */
package lhdt.admin.svc.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lhdt.admin.svc.common.interceptor.SessionCheckInterceptor;
import lhdt.ds.common.interceptor.DsLocaleInterceptor;
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
	
	@Autowired
	private DsLocaleInterceptor localeInterceptor;
	
	@Autowired
	private SessionCheckInterceptor sessionCheckInterceptor;
	
//	@Autowired
//	private ConfigInterceptor configInterceptor;
	
	@PostConstruct
	private void init() {
		log.info("{}", this);
	}
	
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//
		registry.addInterceptor(sessionCheckInterceptor)
			.addPathPatterns("/**")
			.excludePathPatterns("/", "/error", "/sign/**", "/images/**", "/js/**", "/externlib/**", "/css/**");

		//
		registry.addInterceptor(localeInterceptor)
			.addPathPatterns("/**")
			.excludePathPatterns("/", "/error", "/images/**", "/js/**", "/externlib/**", "/css/**");

		//
		registry.addInterceptor(miscInterceptor)
			.addPathPatterns("/**")
			.excludePathPatterns("/", "/error", "/images/**", "/js/**", "/externlib/**", "/css/**");
		
		//
//		registry.addInterceptor(configInterceptor)
//			.addPathPatterns("/**")
//			.excludePathPatterns("/f4d/**",	"/sign/**", "/cache/reload", "/guide/**", "/css/**", "/externlib/**", "favicon*", "/images/**", "/js/**");
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*");
	}
	
	
	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("forward:/sign/signin");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("classpath:static/css/");
		registry.addResourceHandler("/externlib/**").addResourceLocations("classpath:static/externlib/");
		registry.addResourceHandler("/images/**").addResourceLocations("classpath:static/images/");
		registry.addResourceHandler("/js/**").addResourceLocations("classpath:static/js/");
		
	}
}
