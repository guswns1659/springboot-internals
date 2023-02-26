package toby.lecture.springbootfirst;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SpringbootFirstApplication {

	public static void main(String[] args) {
		GenericApplicationContext context = new GenericApplicationContext();
		context.registerBean(HelloController.class);
		context.registerBean(SimpleHelloService.class);
		context.refresh();

		TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();

		WebServer webServer = serverFactory.getWebServer(servletContext -> servletContext.addServlet("hello", new HttpServlet() {
			@Override
			protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

				if (req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())) {
					String name = req.getParameter("name");

					HelloController helloController = context.getBean(HelloController.class);
					String res = helloController.hello(name);

					resp.setStatus(HttpStatus.OK.value());
					resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
					resp.getWriter().println(res);
				}  else {
					resp.setStatus(HttpStatus.NOT_FOUND.value());
				}
			}
		}).addMapping("/"));

		webServer.start();
	}
}
