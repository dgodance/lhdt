/**
 * 
 */
package lhdt.admin.svc.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lhdt.admin.svc.common.interceptor.SessionCheckInterceptor;
import lhdt.ds.common.interceptor.DsCsrfInterceptor;
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
	private DsCsrfInterceptor csrfInterceptor;
	
	@Autowired
	private SessionCheckInterceptor sessionCheckInterceptor;
	
	@Value("${app.login.uri}")
	private String loginUri;
	
//	@Autowired
//	private ConfigInterceptor configInterceptor;
	
	@PostConstruct
	private void init() {
		log.info("{}", this);
	}
	
	
	/**
	 * exclude path patterns
	 * @return
	 */
	private List<String> excludes(){
		List<String> patterns = new ArrayList<>();
		
		//
		patterns.add("/**");
		patterns.add("/error/**");
		patterns.add("/sign/**");
		patterns.add("/images/**");
		patterns.add("/js/**");
		patterns.add("/externlib/**");
		patterns.add("/css/**");
		patterns.add("/assets/**");
		
		//
		return patterns;
	}
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//
		registry.addInterceptor(sessionCheckInterceptor)
			.addPathPatterns("/**")
			.excludePathPatterns(excludes());

		//
		registry.addInterceptor(localeInterceptor)
			.addPathPatterns("/**")
			.excludePathPatterns(excludes());

		//
		registry.addInterceptor(miscInterceptor)
			.addPathPatterns("/**")
			.excludePathPatterns(excludes());

		//
		registry.addInterceptor(csrfInterceptor)
		.addPathPatterns("/**")
		.excludePathPatterns(excludes());
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*");
	}

//	@Bean
//	public FilterRegistrationBean hiddenHttpMethodFilter() {
//		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new HiddenHttpMethodFilter());
//		filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
//		return filterRegistrationBean;
//	}
	
	
//	@Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//		registry.addViewController("/").setViewName("forward:" + loginUri);
//        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
//    }
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("classpath:static/css/");
		registry.addResourceHandler("/externlib/**").addResourceLocations("classpath:static/externlib/");
		registry.addResourceHandler("/images/**").addResourceLocations("classpath:static/images/");
		registry.addResourceHandler("/js/**").addResourceLocations("classpath:static/js/");
		registry.addResourceHandler("/assets/**").addResourceLocations("classpath:assets/");
		
	}
}
