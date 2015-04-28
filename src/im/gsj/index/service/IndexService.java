package im.gsj.index.service;

import im.gsj.city.service.CityService;
import im.gsj.dao.CategoryDao;
import im.gsj.dao.ImageDao;
import im.gsj.dao.MapDao;
import im.gsj.dao.ProductDao;
import im.gsj.dao.ShopDao;
import im.gsj.dao.UserDao;
import im.gsj.entity.Category;
import im.gsj.entity.Image;
import im.gsj.entity.Map;
import im.gsj.entity.Product;
import im.gsj.entity.Shop;
import im.gsj.entity.User;
import im.gsj.index.vo.ProductVo;
import im.gsj.shop.service.ShopService;
import im.gsj.util.Constant;
import im.gsj.util.PageHbase;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Service
public class IndexService {
	@Resource
	private ShopService shopService;
	@Resource
	private CategoryDao categoryDao;
	@Resource
	private ProductDao productDao;
	@Resource
	private CityService cityService;
	@Resource
	private ShopDao shopDao;
	@Resource
	private MapDao mapDao;
	@Resource
	private ImageDao imageDao;
	@Resource
	private UserDao userDao;

	// 未登录商店首页
	public ModelMap home(String phone, String startRow, boolean pre, ModelMap model) throws IOException {
		Shop shop = shopDao.getByPhone(phone);
		User user = userDao.get(shop.getUserId());
		model.addAttribute("shop", shop);
		model.addAttribute("userPhone", user.getPhone());
		// 取出省市区
		Integer cityId = shop.getDistrict();
		String provinceTownCity = cityService.getByShopDistrict(cityId);
		model.addAttribute("provinceTownCity", provinceTownCity);
		List<Category> categoryList = categoryDao.getByShop(shop.getId());
		model.addAttribute("categoryList", categoryList);
		PageHbase<Product> originalPage = productDao.getNewProductHbase(shop.getId(), startRow, pre);
		PageHbase<ProductVo> page = getFirstProductImage(originalPage);
		model.addAttribute("page", page);

		// 如果有地图信息，则显示地图
		Map map = mapDao.query("shopId", shop.getId());
		model.addAttribute("map", map);
		return model;
	}

	// 未登录商店首页(手机版)
	public ModelMap homeM(String phone, String startRow, boolean pre, ModelMap model) throws IOException {
		Shop shop = shopDao.getByPhone(phone);
		User user = userDao.get(shop.getUserId());
		model.addAttribute("shop", shop);
		model.addAttribute("userPhone", user.getPhone());
		// 取出省市区
		Integer cityId = shop.getDistrict();
		String provinceTownCity = cityService.getByShopDistrict(cityId);
		model.addAttribute("provinceTownCity", provinceTownCity);
		// 头部显示的菜单
		List<Category> categoryList = categoryDao.getByShop(shop.getId());
		// 导航栏只显示三个菜单
		if (categoryList.size() > 3) {
			List<Category> categoryListFront = categoryList.subList(0, 3);
			model.addAttribute("categoryList", categoryListFront);
			List<Category> categoryListHide = categoryList.subList(3, categoryList.size());
			model.addAttribute("categoryListHide", categoryListHide);
		} else {
			model.addAttribute("categoryList", categoryList);
		}

		PageHbase<Product> originalPage = productDao.getNewProductHbase(shop.getId(), startRow, pre);
		PageHbase<ProductVo> page = getFirstProductImage(originalPage);
		model.addAttribute("page", page);

		// 如果有地图信息，则显示地图
		Map map = mapDao.query("shopId", shop.getId());
		model.addAttribute("map", map);
		return model;
	}

	/**
	 * 取得每个产品的第一张图片
	 * 
	 * @throws IOException
	 */
	public PageHbase<ProductVo> getFirstProductImage(PageHbase<Product> page) throws IOException {
		PageHbase<ProductVo> newPage = new PageHbase<ProductVo>();
		List<ProductVo> productVoList = newPage.getList();
		List<Product> productList = page.getList();
		for (Product product : productList) {
			ProductVo productVo = new ProductVo();
			BeanUtils.copyProperties(product, productVo);
			// 第一张图片
			Image image = imageDao.getFirstImage(product.getId());
			if (image != null) {
				productVo.setPath(image.getPath() + Constant.S);
				productVo.setPostfix(image.getPostfix());
			}
			productVoList.add(productVo);
		}
		return newPage;
	}

	/**
	 * 取得每个产品的第一张图片(from Hbase)
	 * 
	 * @throws IOException
	 */
	public PageHbase<ProductVo> getFirstProductImageHbase(PageHbase<Product> page) throws IOException {
		PageHbase<ProductVo> newPage = new PageHbase<ProductVo>(page.getHasPre(), page.getHasNext());
		List<ProductVo> productVoList = newPage.getList();
		List<Product> productList = page.getList();
		for (Product product : productList) {
			ProductVo productVo = new ProductVo();
			BeanUtils.copyProperties(product, productVo);
			// 取出产品的第一张图片。
			Image image = imageDao.getFirstImage(product.getId());
			if (image != null) {
				productVo.setPath(image.getPath() + Constant.S);
				productVo.setPostfix(image.getPostfix());
			}
			productVoList.add(productVo);
		}
		return newPage;
	}
	
	/**
	 * 取得每个产品的第一张图片(from Hbase , 手机网页)
	 */
	public PageHbase<ProductVo> getFirstProductImageHbaseM(PageHbase<Product> page) throws IOException {
		PageHbase<ProductVo> newPage = new PageHbase<ProductVo>(page.getHasPre(), page.getHasNext());
		List<ProductVo> productVoList = newPage.getList();
		List<Product> productList = page.getList();
		for (Product product : productList) {
			ProductVo productVo = new ProductVo();
			BeanUtils.copyProperties(product, productVo);
			//手机页面显示字数控制：7字数
			if(productVo.getName().length() >7 )
				productVo.setName(productVo.getName().substring(0, 6) + "...");
			// 取出产品的第一张图片。
			Image image = imageDao.getFirstImage(product.getId());
			if (image != null) {
				productVo.setPath(image.getPath() + Constant.S);
				productVo.setPostfix(image.getPostfix());
			}
			productVoList.add(productVo);
		}
		return newPage;
	}

	/**
	 * 取出头部和尾部
	 */
	public ModelMap getHeadAndFooter(String shopId, ModelMap model) throws IOException {
		Shop shop = shopDao.get(shopId);
		User user = userDao.get(shop.getUserId());
		model.addAttribute("shop", shop);
		model.addAttribute("userPhone", user.getPhone());
		List<Category> categoryList = categoryDao.getByShop(shop.getId());
		model.addAttribute("categoryList", categoryList);
		return model;
	}
	
	/**
	 * 取出头部和尾部(手机版)
	 */
	public ModelMap getHeadAndFooterM(String shopId, ModelMap model) throws IOException {
		Shop shop = shopDao.get(shopId);
		User user = userDao.get(shop.getUserId());
		model.addAttribute("shop", shop);
		model.addAttribute("userPhone", user.getPhone());
		List<Category> categoryList = categoryDao.getByShop(shop.getId());
		
		// 导航栏只显示三个菜单
		if (categoryList.size() > 3) {
			List<Category> categoryListFront = categoryList.subList(0, 3);
			model.addAttribute("categoryList", categoryListFront);
			List<Category> categoryListHide = categoryList.subList(3, categoryList.size());
			model.addAttribute("categoryListHide", categoryListHide);
		} else {
			model.addAttribute("categoryList", categoryList);
		}
		
		return model;
	}
}
