package im.gsj.shop.map.service;

import im.gsj.city.service.CityService;
import im.gsj.dao.MapDao;
import im.gsj.dao.ShopDao;
import im.gsj.entity.Map;
import im.gsj.entity.Shop;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

@Service
public class MapService {
	@Resource
	private MapDao mapDao;
	@Resource
	private ShopDao shopDao;
	@Resource
	private CityService cityService;
	
	public ModelMap addAxis(String phone, ModelMap model) throws IOException{
		Shop shop = shopDao.getByPhone(phone);
		Map map = mapDao.query("shop_id", shop.getId());
		if(map != null){
			model.addAttribute("map", map);
		}
		//如果没有标注，地图则转到填写的地址上
		else{
			Integer cityId = shop.getDistrict();
			String provinceTownCity = cityService.getByShopDistrict(cityId);
			String address = provinceTownCity + shop.getAddress();
			model.addAttribute("address", address);
		}
		
		//使头部能显示
		model.addAttribute("shop", shop);
		return model;
	}
	
	public void deleteMapByPhone(String phone, ModelMap model) throws IOException{
		Shop shop = shopDao.getByPhone(phone);
		mapDao.deleteByShop(shop.getId());
		//查出地图的默认显示位置
		Integer cityId = shop.getDistrict();
		String provinceTownCity = cityService.getByShopDistrict(cityId);
		String address = provinceTownCity + shop.getAddress();
		model.addAttribute("address", address);
		
		//使头部能显示
		model.addAttribute("shop", shop);
	}
}
