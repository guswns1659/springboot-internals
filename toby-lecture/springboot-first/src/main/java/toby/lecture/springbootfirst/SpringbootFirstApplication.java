package toby.lecture.springbootfirst;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class SpringbootFirstApplication {

    public static void main(String[] args) {
        GenericWebApplicationContext context = new GenericWebApplicationContext() {
            @Override
            protected void onRefresh() {
                super.onRefresh();

                TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();

                WebServer webServer = serverFactory.getWebServer(servletContext ->
                        servletContext.addServlet("dispatcherServlet", new DispatcherServlet(this)).addMapping("/"));

                webServer.start();
            }
        };
        context.registerBean(HelloController.class);
        context.registerBean(SimpleHelloService.class);
        context.refresh();
    }
}
