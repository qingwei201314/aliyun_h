package im.gsj.shop.service;

import im.gsj.dao.CityDao;
import im.gsj.dao.ShopDao;
import im.gsj.dao.UserDao;
import im.gsj.entity.City;
import im.gsj.entity.Shop;
import im.gsj.entity.User;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Service
public class ShopService {
	@Resource
	private UserDao userDao;
	@Resource
	private ShopDao shopDao;
	@Resource
	private CityDao cityDao;
	
	public Shop dealShop(Shop shop, String phone) throws IllegalAccessException, InvocationTargetException, IOException{
		User user = userDao.query("phone", phone);
		Shop shop_db = shopDao.getByPhone(user.getPhone());
		String id = null;
		if(shop_db==null){
			shop_db = shop;
		}
		else{
			id = shop_db.getId();
			BeanUtils.copyProperties(shop, shop_db);
		}
		shop_db.setId(id);
		shop_db.setUserId(user.getId());
		shopDao.saveOrUpdate(shop_db);
		return shop_db;
	}
	
	/**
	 * 查出进入商店页面的参数
	 */
	public ModelMap toShop(String phone, ModelMap model) throws IOException{
		Shop shop = shopDao.getByPhone(phone);
		if(shop!=null){
			model.addAttribute("shop", shop);
			
			//区
			Integer district = shop.getDistrict();
			City districtCity = cityDao.get(district);
			List<City> cityList = cityDao.queryList("parentId", districtCity.getParentId());
			model.addAttribute("cities",cityList);
			model.addAttribute("shopCity", districtCity.getId());
			
			//市
			City town = cityDao.get(districtCity.getParentId());
			List<City> townList = cityDao.queryList("parentId", town.getParentId());
			model.addAttribute("towns",townList);
			model.addAttribute("shopTown", town.getId());
			
			//查出区划信息 省
			model.addAttribute("shopProvince", town.getParentId());
		}
		//不管有没商店信息，都会存省的列表
		List<City> provinces = cityDao.getProvince();
		model.addAttribute("provinces", provinces);
		return model;
	}
}
