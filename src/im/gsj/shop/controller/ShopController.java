package im.gsj.shop.controller;

import im.gsj.category.service.CategoryService;
import im.gsj.dao.ShopDao;
import im.gsj.dao.UserDao;
import im.gsj.dao.dto.CategoryDto;
import im.gsj.entity.Shop;
import im.gsj.entity.User;
import im.gsj.shop.service.ShopService;
import im.gsj.uploadify.service.Uploadify;
import im.gsj.user.service.UserService;
import im.gsj.util.Constant;
import im.gsj.util.controller.CommonController;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/shop")
public class ShopController extends CommonController{
	@Resource
	private ShopService shopService;
	@Resource
	private CategoryService categoryService;
	@Resource
	private Uploadify uploadify;
	@Resource
	private UserService userService;
	@Resource
	private UserDao userDao;
	@Resource
	private ShopDao shopDao;
	
	/**
	 * 上传大门图片
	 */
	@RequestMapping(value = "upload.do", method = RequestMethod.POST)
	public String upload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String phone = (String)request.getSession().getAttribute(Constant.phone);
		String widthXheight = request.getParameter("widthXheight");
		String result = uploadify.uploadGate(phone,request,widthXheight);
		PrintWriter pw = response.getWriter();
		pw.append(result);
		return null;
	}
	
	@RequestMapping(value = "saveShop.do", method = RequestMethod.POST)
	public String saveShop(@ModelAttribute("shop") Shop shop, HttpSession session, ModelMap model) throws IllegalAccessException, InvocationTargetException, IOException {
		String phone = (String)session.getAttribute(Constant.phone);
		shop = shopService.dealShop(shop, phone);
		
		//查出当前商店的分类
		List<CategoryDto> categoryList = categoryService.listCategoryDto(phone);
		model.addAttribute("categoryList", categoryList);
		
		return "/admin/category/addCategory";
	}
	
	/**
	 * 跳转到商店信息页面
	 */
	@RequestMapping(value = "addShop.do", method = RequestMethod.GET)
	public String addShop(HttpSession session, ModelMap model) throws IllegalAccessException, InvocationTargetException, IOException {
		String phone = (String)session.getAttribute(Constant.phone);
		model = shopService.toShop(phone, model);
		return "/admin/shop/addShop";
	}
	
	/**
	 * 新增用户
	 */
	@RequestMapping(value="addUser.do", method=RequestMethod.POST)
	public String addUser(@ModelAttribute("user") User user, ModelMap model) throws IOException{
		String viewResult = "";
		int result = userService.addUser(user);
		if(result == 1){
			//保存成功
			model.addAttribute("displayMessage", "恭喜，新增用户成功。");
			viewResult = "/admin/user/success";
		}
		else{
			model.addAttribute("user", user);
			//用户已存在
			viewResult = "/admin/user/adminModPassword";
		}
		return viewResult;
	}
	
	/**
	 * 管理员修改某一用户的密码
	 * @throws IOException 
	 */
	@RequestMapping(value="adminUpdatePassword.do", method=RequestMethod.POST)
	public String adminUpdatePassword(@RequestParam("phone") String phone, @RequestParam("newpassword") String newpassword, @RequestParam("repassword") String repassword, ModelMap model) throws IOException{
		String viewResult = "";
		if(newpassword.equals(repassword)){
			//如果密码相同，则更新
			userService.updateUser(phone, repassword);
			model.addAttribute("displayMessage", "恭喜，密码修改成功。");
			viewResult= "/admin/user/success";
		}
		else{
			User user = new User();
			user.setPhone(phone);
			model.addAttribute("user", user);
			model.addAttribute("message", "对不起，新密码与确认新密码不一致.");
			viewResult= "/admin/user/adminModPassword";
		}
		return viewResult;
	}
	
	@RequestMapping(value="modPassword.do")
	public String modPassword(HttpSession session, ModelMap model) throws IOException{
		String phone = (String)session.getAttribute(Constant.phone);
		//使头部能显示
		Shop shop = shopDao.getByPhone(phone);
		model.addAttribute("shop", shop);
		return "/admin/user/modPassword";
	}
	
	/**
	 * 修改密码
	 * @throws IOException 
	 */
	@RequestMapping(value="updatePassword.do", method=RequestMethod.POST)
	public String updatePassword(HttpSession session, @RequestParam("password") String password, 
				@RequestParam("newpassword") String newpassword, 
				@RequestParam("repassword") String repassword, ModelMap model) throws IOException{
		String phone = (String)session.getAttribute(Constant.phone);
		User user = userDao.query("phone", phone);
		if(newpassword.equals(repassword) && user.getPassword().equals(password)){
			//如果密码相同，则更新
			userService.updateUser(phone, repassword);
			//使头部能显示
			Shop shop = shopDao.getByPhone(phone);
			model.addAttribute("shop", shop);
			model.addAttribute("message", "恭喜，密码修改成功。");
		}
		else if(!newpassword.equals(repassword)){
			model.addAttribute("message", "对不起，新密码与确认新密码不一致.");
		}
		else{
			model.addAttribute("message", "对不起，原密码不正确.");
		}
		return "/admin/user/modPassword";
	}
}
