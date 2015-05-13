package im.gsj.about.service;

import im.gsj.dao.AboutDao;
import im.gsj.dao.ShopDao;
import im.gsj.entity.About;
import im.gsj.entity.Shop;
import im.gsj.index.service.IndexService;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Service
public class AboutService {
	@Resource
	private AboutDao aboutDao;
	@Resource
	private ShopDao shopDao;
	@Resource
	private IndexService indexService;
	
	/**
	 * 保存about记录
	 */
	public About saveAbout(About about, String phone) throws IOException{
		Shop shop = shopDao.getByPhone(phone);
		about.setShopId(shop.getId());
			if(StringUtils.isEmpty(about.getId())){
				aboutDao.save(about);
			}
			else{
				aboutDao.update(about);
			}
			return about;
	}
	
	public About getByPhone(String phone) throws IOException{
		Shop shop = shopDao.getByPhone(phone);
		About about = aboutDao.query("shopId", shop.getId());
		return about;
	}
	
	/**
	 * 查出跳转到《关于我们》所需要的参数
	 * @throws IOException 
	 */
	public ModelMap aboutUs(String phone, ModelMap model) throws IOException{
		Shop shop = shopDao.getByPhone(phone);
		About about = aboutDao.query("shopId", shop.getId());
		model.addAttribute("about", about);
		model = indexService.getHeadAndFooter(shop.getId(), model);
		return model;
	}
	
	/**
	 * 查出跳转到《关于我们》所需要的参数(手机版)
	 */
	public ModelMap aboutUsM(String phone, ModelMap model) throws IOException{
		Shop shop = shopDao.getByPhone(phone);
		About about = aboutDao.query("shopId", shop.getId());
		model.addAttribute("about", about);
		model = indexService.getHeadAndFooterM(shop.getId(), model);
		return model;
	}
}
