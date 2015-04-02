package im.gsj.category.service;

import im.gsj.dao.CategoryDao;
import im.gsj.dao.ImageDao;
import im.gsj.dao.ProductDao;
import im.gsj.dao.ShopDao;
import im.gsj.dao.dto.CategoryDto;
import im.gsj.dao.dto.ImageDto;
import im.gsj.entity.Category;
import im.gsj.entity.Image;
import im.gsj.entity.Product;
import im.gsj.entity.Shop;
import im.gsj.index.service.IndexService;
import im.gsj.index.vo.ProductVo;
import im.gsj.util.Constant;
import im.gsj.util.PageHbase;
import im.gsj.util.Util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Service
public class CategoryService {
	@Resource
	private CategoryDao categoryDao;
	@Resource
	private ShopDao shopDao;
	@Resource
	private ProductDao productDao;
	@Resource
	private ImageDao imageDao;
	@Resource
	private IndexService indexService;
	
	/**
	 * 取得当前店的所有分类
	 * @throws IOException 
	 */
	public List<Category> list(String phone) throws IOException {
		Shop shop =shopDao.getByPhone(phone);
		List<Category> categoryList = categoryDao.getByShop(shop.getId());
		return categoryList;
	}
	
	public List<CategoryDto> listCategoryDto(String phone) throws IOException, IllegalAccessException, InvocationTargetException {
		Shop shop =shopDao.getByPhone(phone);
		List<CategoryDto> categoryList = categoryDao.getCategoryByShop(shop.getId());
		return categoryList;
	}
	
	public boolean save(Category category, String phone) throws IOException {
		boolean success = true;
		Shop shop =shopDao.getByPhone(phone);
	
		Long categoryCount = categoryDao.getCategoryCount(shop.getId());
		if(categoryCount >=5){
			//如果类别数大于5
			success = false;
		}
		else{
			//保存类别
			category.setShopId(shop.getId());
			if(StringUtils.isEmpty(category.getId())){
				categoryDao.save(category);
			}
			else{
				categoryDao.update(category);
			}
		}
		return success;
	}
	
	/**
	 * 查出某一类别的产品
	 */
	public ModelMap listProduct(String shopId, String categoryId, String startRow, boolean pre, ModelMap model) throws IOException{
		model.addAttribute("categoryId", categoryId);
		List<Product> productList = productDao.queryListHbase(categoryId);
		List<ProductVo> productVoList = getFirstProductImage(productList);
		model.addAttribute("productVoList", productVoList);
		PageHbase<Image> page = imageDao.pageHbase(categoryId, startRow, pre);
		PageHbase<ImageDto> dtoPage = new PageHbase<ImageDto>();
		for(Image image: page.getList()){
			ImageDto imageDto =new ImageDto();
			BeanUtils.copyProperties(image, imageDto);
			Product product = productDao.getHbase(image.getProductId());
			imageDto.setName(product.getName());
			imageDto.setPath(imageDto.getPath() + Constant.B);
			dtoPage.getList().add(imageDto);
		}
		List<ImageDto> imageDtoList = dtoPage.getList();
		model.addAttribute("imageDtoList", imageDtoList);
		model.addAttribute("pageSize", dtoPage.getPageSize());
		//取出头部和尾部的值
		model = indexService.getHeadAndFooter(shopId, model);
		return model;
	}
	
	/**
	 * 取得每个产品的第一张图片
	 * @throws IOException 
	 */
	private List<ProductVo> getFirstProductImage(List<Product> productList) throws IOException{
		 List<ProductVo> productVoList = new ArrayList<ProductVo>();
		for(Product product: productList){
			ProductVo productVo =new ProductVo();
			BeanUtils.copyProperties(product, productVo);
			//第一张图片
			Image image= imageDao.getFirstImage(product.getId());
			if(image!=null){
				productVo.setPath(image.getPath()+ Constant.S);
				productVo.setPostfix(image.getPostfix());
			}
			productVoList.add(productVo);
		}
		return productVoList;
	} 
	
	/**
	 * 查出更多图片
	 */
	public ModelMap moreImage(String categoryId, String startRow, boolean pre, ModelMap model) throws IOException{
		List<Image> imageList = imageDao.pageByCategoryHbase(categoryId,startRow, pre);
		List<ImageDto> imageDtoList = new ArrayList<ImageDto>();
		for(Image image: imageList){
			ImageDto imageDto = new ImageDto();
			BeanUtils.copyProperties(image, imageDto);
			imageDto.setPath(imageDto.getPath() + Constant.B);
			//填入产品名称
			Product product = productDao.getHbase(imageDto.getProductId());
			imageDto.setName(product.getName());
			imageDtoList.add(imageDto);
		}
		model.addAttribute("imageDtoList", imageDtoList);
		model.addAttribute("pageSize", Util.pageSize);
		//用于标识最后一个id
		String lastId= "";
		if(imageDtoList != null && imageDtoList.size() >= Util.pageSize ){
			lastId = imageDtoList.get(Util.pageSize -1).getId();
		}
		return model;
	}
	
	/**
	 * 查出某一商店的所有类别，并将当前类别取出
	 */
	public ModelMap editCategory(String phone, String categoryId, ModelMap model) throws IOException, IllegalAccessException, InvocationTargetException {
		Shop shop =shopDao.getByPhone(phone);
		List<CategoryDto> categoryList = categoryDao.getByShopExceptCurrent(shop.getId(), categoryId);
		model.addAttribute("categoryList", categoryList);
		
		//当前的选中项
		Category category = categoryDao.get(categoryId, shop.getId());
		model.addAttribute("category", category);
		
		//使头部能显示
		model.addAttribute("shop", shop);
		return model;
	}
}
