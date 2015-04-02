package im.gsj.user.controller;

import im.gsj.dao.CityDao;
import im.gsj.dao.UserDao;
import im.gsj.entity.User;
import im.gsj.shop.service.ShopService;
import im.gsj.user.service.UserService;
import im.gsj.util.Constant;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	private UserDao userDao;
	@Resource
	private UserService userService;
	@Resource
	private ShopService shopService;
	@Resource 
	private CityDao cityDao;

	@RequestMapping(value = "login.do", method = RequestMethod.GET)
	public String login(HttpSession session, ModelMap model) throws IOException {
		String viewResult;
		String phone = (String)session.getAttribute(Constant.phone);
		if(StringUtils.isEmpty(phone)){
			viewResult = "/user/login";
		}
		else{
			if("admin".equals(phone)){
				//如果是admin,则跳转到登录页面
				viewResult = "/user/login";
			}
			else{
				//如果已登录，查出商店信息
				model = shopService.toShop(phone, model);
				viewResult = "/admin/shop/addShop";
			}
		}
		
		return viewResult;
	}

	/**
	 * 用户登录
	 * @throws IOException 
	 */
	@RequestMapping(value = "loginValidate.do", method = RequestMethod.POST)
	public String loginValidate(@ModelAttribute("user") User user, HttpSession session, ModelMap model) throws IOException {
		String resultPath = "/user/login";
		boolean pass = userService.login(user);
		if (pass) {
			session.setAttribute(Constant.phone, user.getPhone());
			
			if("admin".equals(user.getPhone())){
				//如果是管理员
				resultPath = "/admin/user/addUser";
			}
			else{
				//查出商店信息
				model = shopService.toShop(user.getPhone(), model);
				resultPath = "/admin/shop/addShop";
			}
		} else {
			model.addAttribute("message", "电话或密码错误!");
		}
		return resultPath;
	}

	/**
	 * 退出登录
	 */
	@RequestMapping(value="logout.do", method=RequestMethod.GET)
	public String logout(HttpSession session){
		session.removeAttribute(Constant.phone);
		return "/user/login";
	}
}
