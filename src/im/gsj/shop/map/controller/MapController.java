package im.gsj.shop.map.controller;

import im.gsj.dao.MapDao;
import im.gsj.dao.ShopDao;
import im.gsj.entity.Map;
import im.gsj.entity.Shop;
import im.gsj.shop.map.service.MapService;
import im.gsj.util.Constant;
import im.gsj.util.controller.CommonController;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/shop/map")
public class MapController extends CommonController{
	@Resource
	private MapDao mapDao;
	@Resource
	private MapService mapService;
	@Resource
	private ShopDao shopDao;
	
	@RequestMapping(value="addAxis.do", method=RequestMethod.GET)
	public String addAxis(HttpSession session, ModelMap model) throws IOException{
		String phone = (String)session.getAttribute(Constant.phone);
		model = mapService.addAxis(phone,model);
		return "/admin/shop/map/addAxis";
	}
	
	@RequestMapping(value="reAxis.do", method=RequestMethod.GET)
	public String reAxis(HttpSession session, ModelMap model) throws IOException{
		String phone = (String)session.getAttribute(Constant.phone);
		mapService.deleteMapByPhone(phone, model);
		return "/admin/shop/map/addAxis";
	}
	
	@RequestMapping(value="saveMap.do", method=RequestMethod.GET)
	public String saveMap(@RequestParam("longitude") Float longitude, @RequestParam("latitude") Float latitude, HttpSession session) throws IOException{
		String phone = (String)session.getAttribute(Constant.phone);
		Shop shop = shopDao.getByPhone(phone);
		Map map = new Map();
		map.setShopId(shop.getId());
		map.setLongitude(longitude);
		map.setLatitude(latitude);
		mapDao.save(map);
		return null;
	}
}
