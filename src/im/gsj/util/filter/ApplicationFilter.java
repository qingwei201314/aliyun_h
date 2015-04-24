package im.gsj.util.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.CharacterEncodingFilter;

public class ApplicationFilter extends CharacterEncodingFilter{
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String servletPath = request.getServletPath();
		if(servletPath !=null &&servletPath.matches("^/\\d{11,20}$")){
			servletPath = servletPath.substring(1, servletPath.length());
			RequestDispatcher dispatcher = null;
			if(request.getHeader("user-agent").indexOf("Mobile") > 0){
				//跳转到手机网页
				dispatcher = request.getRequestDispatcher("/indexM/home.do?phone="+servletPath + "&pageNo=1");
			}
			else{
				//跳转到pc网页
				dispatcher = request.getRequestDispatcher("/index/home.do?phone="+servletPath + "&pageNo=1");
			}
			
			dispatcher.forward(request, response);
		}
		else{
			super.doFilterInternal(request, response, filterChain);
		}
	}
}
