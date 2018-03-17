package com.airlenet.crawler.heritrix;

import com.airlenet.core.SpringContext;
import com.airlenet.web.WebSpringContext;
import com.noelios.restlet.ext.servlet.ServerServlet;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * @author airlenet
 * @version 2018-01-01
 */
@Order(2)
public class RestletWebApplicationInitializer implements WebApplicationInitializer {
    private final String SERVLET_NAME="RestletServlet";
    public void onStartup(ServletContext servletContext) throws ServletException {
        ServerServlet servletContainer = servletContext.createServlet(com.noelios.restlet.ext.spring.SpringServerServlet.class);
        ServletRegistration.Dynamic dynamic = servletContext.addServlet(SERVLET_NAME, servletContainer);
        dynamic.setInitParameter("org.restlet.application","engineApplication");
        dynamic.setInitParameter("org.restlet.component","heritrixComponent");
        dynamic.setLoadOnStartup(1);
        dynamic.addMapping("/heritrix3/*");
    }
}
