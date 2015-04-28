package im.gsj.mobile.index.controller;

import im.gsj.index.service.IndexService;
import im.gsj.product.service.ProductService;
import im.gsj.util.PageHbase;
import im.gsj.util.Util;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/indexM")
public class IndexMController {
	@Resource
	private IndexService indexService;
	@Resource
	private ProductService productService;
	
	// 跳转到主页
	@RequestMapping(value = "/main.do", method = RequestMethod.GET)
	public String main() {
		return "/mobile/main";
	}
	
	//跳转到商店首页
	@RequestMapping(value = "/home.do", method = RequestMethod.GET)
	public String home(@RequestParam("phone") String phone, String startRow, boolean pre, ModelMap model) throws IOException {
		model = indexService.homeM(phone, startRow, pre, model);
		return "/mobile/shop/home";
	}
	
	/**
	 * 首页搜索(手机版）
	 */
	@RequestMapping(value = "search.do", method = RequestMethod.GET)
	public String search(@RequestParam("q") String q, String startRow, boolean pre, ModelMap model) throws IOException {
		String viewResult = "/mobile/shop/indexSearchResult";
		// 如果是手机号，直接跳转到商店首页
		if (q != null && q.matches("^(0|1)\\d{10,19}$")) {
			viewResult = "redirect:" + Util.path() + "/" + q;
		} else {
			PageHbase<im.gsj.index.vo.ProductVo> page = productService.indexSearchM(q, startRow, pre, 18);
			model.addAttribute("page", page);
			model.addAttribute("q", q);
		}
		return viewResult;
	}
}
