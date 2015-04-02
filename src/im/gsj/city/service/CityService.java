package im.gsj.city.service;

import im.gsj.dao.CityDao;
import im.gsj.entity.City;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
public class CityService {
	@Resource
	private CityDao cityDao;
	
	public String getByShopDistrict(Integer district) throws IOException{
		City city  = cityDao.get(district);
		City town = cityDao.get(city.getParentId());
		City province = cityDao.get(town.getParentId());
		String provinceTownCity = province.getName() + town.getName() + city.getName();
		return provinceTownCity;
	}
}
