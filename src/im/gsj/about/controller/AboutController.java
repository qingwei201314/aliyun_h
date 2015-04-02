package im.gsj.about.controller;

import im.gsj.about.service.AboutService;
import im.gsj.dao.ShopDao;
import im.gsj.entity.About;
import im.gsj.entity.Shop;
import im.gsj.shop.service.ShopService;
import im.gsj.util.Constant;
import im.gsj.util.controller.CommonController;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin/about")
public class AboutController extends CommonController{
	@Resource
	private AboutService aboutService;
	@Resource
	private ShopService shopService;
	@Resource
	private ShopDao shopDao;
	
	@RequestMapping(value = "addAbout.do", method = RequestMethod.GET)
	public String addAbout(HttpSession session, ModelMap model) throws IOException {
		String toView = "/admin/about/addAbout";
		String phone = (String) session.getAttribute(Constant.phone);
		About about = aboutService.getByPhone(phone);
		if (about != null) {
			model.addAttribute("about", about);
			toView = "/admin/about/viewAbout";
		}
		
		//使头部能显示
		Shop shop = shopDao.getByPhone(phone);
		model.addAttribute("shop", shop);
		return toView;
	}

	@RequestMapping(value = "saveAbout", method = RequestMethod.POST)
	public String saveAbout(@ModelAttribute("about") About about,
			HttpSession session, ModelMap model) throws IOException {
		String phone = (String) session.getAttribute(Constant.phone);
		about = aboutService.saveAbout(about, phone);
		model.addAttribute("about", about);
		
		//使头部能显示
		Shop shop = shopDao.getByPhone(phone);
		model.addAttribute("shop", shop);
		return "/admin/about/viewAbout";
	}

	@RequestMapping(value = "editAbout.do", method = RequestMethod.GET)
	public String editAbout(HttpSession session, ModelMap model) throws IOException {
		String phone = (String) session.getAttribute(Constant.phone);
		About about = aboutService.getByPhone(phone);
		model.addAttribute("about", about);
		
		//使头部能显示
		Shop shop = shopDao.getByPhone(phone);
		model.addAttribute("shop", shop);
		return "/admin/about/addAbout";
	}
}
