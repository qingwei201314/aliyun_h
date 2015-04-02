package im.gsj.util.controller;

import im.gsj.dao.ShopDao;
import im.gsj.entity.Shop;
import im.gsj.shop.service.ShopService;
import im.gsj.util.Constant;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 公共controller
 * @author Kevin
 *
 */
public class CommonController {
	@Resource
	private ShopService shopService;
	@Resource
	private ShopDao shopDao;
	
	/**
	 * 当有异常时，跳转到其他页面
	 * @throws IOException 
	 */
	@ExceptionHandler(Exception.class)
	public String handleIOException(Exception ex, HttpServletRequest request) throws IOException {
		ex.printStackTrace();
		
		//使头部能显示
		String phone = (String) request.getSession().getAttribute(Constant.phone);
		Shop shop = shopDao.getByPhone(phone);
		request.setAttribute("shop", shop);
		return "/error/admin/error";
	}
}
