package im.gsj.about.controller;

import java.io.IOException;

import im.gsj.about.service.AboutService;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/about")
public class AboutViewController {
	@Resource
	private AboutService aboutService;

	/**
	 * 跳转到《我于我们》页面
	 * @throws IOException 
	 */
	@RequestMapping(value="aboutUs.do", method=RequestMethod.GET)
	public String aboutUs(@RequestParam("phone") String phone, ModelMap model) throws IOException{
		model = aboutService.aboutUs(phone, model);
		return "/about/aboutUs";
	}
}
