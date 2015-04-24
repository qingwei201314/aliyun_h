package im.gsj.mobile.index.controller;

import im.gsj.index.service.IndexService;

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
}
