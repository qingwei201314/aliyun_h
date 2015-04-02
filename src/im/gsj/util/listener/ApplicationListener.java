package im.gsj.util.listener;

import im.gsj.util.Util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.dom4j.DocumentException;

public class ApplicationListener implements ServletContextListener{

	public void contextInitialized(ServletContextEvent paramServletContextEvent) {
		try {
			Util util = new Util();
			paramServletContextEvent.getServletContext().setAttribute("util", util);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
	}

	public void contextDestroyed(ServletContextEvent paramServletContextEvent) {
		
	}
}
