package im.gsj.city.controller;

import im.gsj.dao.CityDao;
import im.gsj.entity.City;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/city")
public class CityController {
	@Resource
	private CityDao cityDao;
	
	@RequestMapping(value="getCityList.do", method=RequestMethod.GET)
	public String getCityList(@RequestParam("cityId") Integer cityId,  ModelMap model) throws IOException{
		List<City> cityList = cityDao.queryList("parentId", cityId);
		model.addAttribute("cityList",cityList);
		return "/admin/city/getCityList";
	}
}
