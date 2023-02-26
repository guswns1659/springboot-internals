package toby.lecture.springbootfirst;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
@ComponentScan
public class SpringbootFirstApplication {

    public static void main(String[] args) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext() {
            @Override
            protected void onRefresh() {
                super.onRefresh();

                TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();

                WebServer webServer = serverFactory.getWebServer(servletContext ->
                        servletContext.addServlet("dispatcherServlet", new DispatcherServlet(this)).addMapping("/"));

                webServer.start();
            }
        };
        context.register(SpringbootFirstApplication.class);
        context.refresh();
    }
}
