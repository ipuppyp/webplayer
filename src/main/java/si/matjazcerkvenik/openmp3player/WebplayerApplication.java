package si.matjazcerkvenik.openmp3player;

import java.util.EnumSet;

import javax.faces.webapp.FacesServlet;
import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.ocpsoft.rewrite.servlet.RewriteFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import si.matjazcerkvenik.openmp3player.backend.OContext;

@SpringBootApplication
@SpringBootConfiguration
public class WebplayerApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		OContext.getInstance();
		SpringApplication.run(WebplayerApplication.class, args);
	}

	@Override
    public void onStartup(ServletContext servletContext) throws ServletException {
		System.out.println("rt");
        servletContext.setInitParameter("javax.faces.FACELETS_LIBRARIES", "/WEB-INF/omp3p.taglib.xml");
        super.onStartup(servletContext);
    }
	
	
	@Bean
	public ServletRegistrationBean servletRegistrationBean(ServletContext servletContext) {
		servletContext.setInitParameter("javax.faces.FACELETS_LIBRARIES", "/WEB-INF/omp3p.taglib.xml");
		FacesServlet servlet = new FacesServlet();
		return new ServletRegistrationBean(servlet, "*.xhtml");
	}

	@Bean
	public FilterRegistrationBean rewriteFilter() {
		FilterRegistrationBean rwFilter = new FilterRegistrationBean(new RewriteFilter());
		rwFilter.setDispatcherTypes(
				EnumSet.of(DispatcherType.FORWARD, DispatcherType.REQUEST, DispatcherType.ASYNC, DispatcherType.ERROR));
		rwFilter.addUrlPatterns("/*");
		return rwFilter;
	}
}
