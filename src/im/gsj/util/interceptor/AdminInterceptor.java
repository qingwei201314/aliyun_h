package im.gsj.util.interceptor;

import im.gsj.util.Constant;
import im.gsj.util.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AdminInterceptor implements HandlerInterceptor{
	/**
	 * 用于验证用户是否是登录状态
	 */
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String phone = (String)request.getSession().getAttribute(Constant.phone);
		if(StringUtils.isNotEmpty(phone)){
			return true;
		}
		else{
			//如果用户未登录，则跳转到登录页面
			String path = ((Util)request.getServletContext().getAttribute("util")).getPath();
			path += "/user/login.do";
			response.sendRedirect(path);
		}
		
		return false;
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
