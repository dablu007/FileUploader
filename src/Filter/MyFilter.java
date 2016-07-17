package Filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//@WebFilter(urlPatterns={"/*"})
public class MyFilter implements Filter {

	private ServletContext context;

	public MyFilter() {
		// TODO Auto-generated constructor stub
	}

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		response.setContentType("text/html");

		HttpServletRequest request1 = (HttpServletRequest) request;
		HttpServletResponse response1 = (HttpServletResponse) response;
		HttpSession session = request1.getSession(false);

		String uri = request1.getRequestURI();
		// (If session is not set)
		if ((session == null || session.getAttribute("users") == null) && (!uri.endsWith("login.jsp") && !uri.endsWith("record/authenticate"))) {
			request1.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request1, response1);
		}
		// (if session is set)
		else {
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		this.context = fConfig.getServletContext();
	}

}