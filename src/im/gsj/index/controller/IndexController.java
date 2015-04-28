package im.gsj.index.controller;

import im.gsj.index.service.IndexService;
import im.gsj.product.service.ProductService;
import im.gsj.util.PageHbase;
import im.gsj.util.Util;

import java.io.IOException;
import java.util.Calendar;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/index")
public class IndexController {
	@Resource
	private IndexService indexService;
	@Resource
	private ProductService productService;

	@RequestMapping(value = "/index.do", method = RequestMethod.GET)
	public String index(HttpServletRequest request) {
		String header = request.getHeader("user-agent");
		
		if(header.indexOf("Mobile") > 0){
			//如果是手机浏览器
			return "/mobile/index";
		}else{
			//如果是pc浏览器
			return "/index";
		}
	}

	// 跳转到主页
	@RequestMapping(value = "/main.do", method = RequestMethod.GET)
	public String main() {
		return "/main";
	}

	@RequestMapping(value = "/home.do", method = RequestMethod.GET)
	public String home(@RequestParam("phone") String phone, String startRow, boolean pre, ModelMap model) throws IOException {
		model = indexService.home(phone, startRow, pre, model);
		return "/shop/home";
	}

	/**
	 * 首页搜索
	 */
	@RequestMapping(value = "search.do", method = RequestMethod.GET)
	public String search(@RequestParam("q") String q, String startRow, boolean pre, ModelMap model) throws IOException {
		String viewResult = "/shop/indexSearchResult";
		// 如果是手机号，直接跳转到商店首页
		if (q != null && q.matches("^(0|1)\\d{10,19}$")) {
			viewResult = "redirect:" + Util.path() + "/" + q;
		} else {
			PageHbase<im.gsj.index.vo.ProductVo> page = productService.indexSearch(q, startRow, pre, null);
			model.addAttribute("page", page);
			model.addAttribute("q", q);
		}
		return viewResult;
	}

	/**
	 * 跳转到注册页面
	 */
	@RequestMapping(value = "register.do", method = RequestMethod.GET)
	public String register(ModelMap model) {
		Calendar calender = Calendar.getInstance();
		int year = calender.get(Calendar.YEAR);
		String start = String.valueOf(year) + "年" + (calender.get(Calendar.MONTH) + 1) + "月" + calender.get(Calendar.DATE);
		model.addAttribute("start", start);
		calender.set(Calendar.YEAR, year + 1);
		String end = String.valueOf(calender.get(Calendar.YEAR)) + "年" + (calender.get(Calendar.MONTH) + 1) + "月" + calender.get(Calendar.DATE);
		model.addAttribute("end", end);
		return "/register";
	}
}
