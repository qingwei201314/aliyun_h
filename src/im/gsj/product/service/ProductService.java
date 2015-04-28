package im.gsj.product.service;

import im.gsj.category.service.CategoryService;
import im.gsj.dao.CategoryDao;
import im.gsj.dao.ImageDao;
import im.gsj.dao.ProductDao;
import im.gsj.dao.ShopDao;
import im.gsj.entity.Category;
import im.gsj.entity.Image;
import im.gsj.entity.Product;
import im.gsj.entity.Shop;
import im.gsj.image.service.ImageResult;
import im.gsj.image.service.ImageService;
import im.gsj.index.service.IndexService;
import im.gsj.product.vo.ProductVo;
import im.gsj.shop.service.ShopService;
import im.gsj.uploadify.service.Uploadify;
import im.gsj.util.Constant;
import im.gsj.util.PageHbase;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.google.gson.Gson;

@Service
public class ProductService {
	@Resource
	private ShopDao shopDao;
	@Resource
	private CategoryDao categoryDao;
	@Resource
	private ProductDao productDao;
	@Resource
	private ImageDao imageDao;
	@Resource
	private CategoryService categoryService;
	@Resource
	private Uploadify uploadify;
	@Resource
	private ImageService imageService;
	@Resource
	private IndexService indexService;
	@Resource
	private ShopService shopService;

	public void save(Product product, String phone) throws IOException {
		Shop shop = shopDao.getByPhone(phone);
		product.setShopId(shop.getId());
		if (StringUtils.isEmpty(product.getId())) {
			product.setCreateTime(new Date());
//			productDao.save(product);
			//改成存hbase
			productDao.saveHbase(product);
		} else {
			Product oldProduct = productDao.getHbase(product.getId());
			product.setCreateTime(oldProduct.getCreateTime());
			BeanUtils.copyProperties(product, oldProduct);
			productDao.updateHbase(oldProduct);
		}
	}

	/**
	 * 取得产品基本信息
	 * @throws IOException 
	 */
	public ProductVo get(String productId) throws IllegalAccessException,
			InvocationTargetException, IOException {
		Product product = productDao.getHbase(productId);
		ProductVo productVo = new ProductVo();
		BeanUtils.copyProperties(product, productVo);
		productVo.setCategoryId(product.getCategoryId());
		Category category = categoryDao.get(product.getCategoryId(), product.getShopId());
		productVo.setCategoryName(category.getName());
		List<Image> imageList = imageDao.getImageListByProductIdHbase(product
				.getId());
		productVo.setImageList(imageList);
		return productVo;
	}

	public ModelMap toEditProduct(String phone, String productId, ModelMap model) throws IOException {
		Product product = productDao.getHbase(productId);
		// 查出当前商店的分类
		List<Category> categoryList = categoryService.list(phone);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("categoryId", product.getCategoryId());
		model.addAttribute("product", product);
		
		//使头部能显示
		Shop shop = shopDao.get(product.getShopId());
		model.addAttribute("shop", shop);
		return model;
	}

	/**
	 * 将图片写到磁盘，并保存数据库记录
	 */
	public String upload(HttpServletRequest request, String widthXheight)
			throws Exception {
		String productId = request.getParameter("productId");
		String phone = (String) request.getSession().getAttribute("phone");
		String result = uploadify.upload(phone, request, widthXheight);
		ImageResult imageResult = imageService.saveImage(productId, result);
		Gson gson = new Gson();
		String json = gson.toJson(imageResult);
		return json;
	}

	public ModelMap deleteProduct(String phone, String productId,
			String uploadPath, ModelMap model) throws IOException {
		// 删除产品图片
		List<Image> imageList = imageDao.getImageListByProductIdHbase(productId);
		for (Image image : imageList) {
			String path = image.getPath();
			String postfix = image.getPostfix();
			productDao.deleteHbase(image.getId());
			// 删除物理文件
			try {
				String filePath = uploadPath + path + Constant.B + postfix;
				File file = new File(filePath);
				file.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				String filePath = uploadPath + path + Constant.S + postfix;
				File file = new File(filePath);
				file.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//删除产品信息
		Product product = productDao.getHbase(productId);
		String categoryId = product.getCategoryId();
		productDao.deleteHbase(product.getId());
		
		//查出页面要用到的值
		List<Category> categoryList = categoryService.list(phone);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("categoryId", categoryId);
		
		List<Product> productList =  productDao.queryListHbase(categoryId);
		model.addAttribute("productList", productList);
		
		//使头部能显示
		Shop shop = shopDao.getByPhone(phone);
		model.addAttribute("shop", shop);

		return null;
	}
	
	/**
	 * 取出产品的详细
	 */
	public ModelMap viewProduct(String productId, String startRow, boolean pre, ModelMap model) throws IllegalAccessException, InvocationTargetException, IOException{
		ProductVo productVo = viewProductWithImage(productId, startRow, pre);
		model.addAttribute("productVo", productVo);
		model.addAttribute("categoryId", productVo.getCategoryId()); //类别id,用于在头部的高亮
		
		//取出头部和尾部的参数
		model = indexService.getHeadAndFooter(productVo.getShopId(), model);
		
		return model;
	}
	
	/**
	 * 取出产品的详细(手机版)
	 */
	public ModelMap viewProductM(String productId, String startRow, boolean pre, ModelMap model) throws IllegalAccessException, InvocationTargetException, IOException{
		ProductVo productVo = viewProductWithImage(productId, startRow, pre);
		model.addAttribute("productVo", productVo);
		model.addAttribute("categoryId", productVo.getCategoryId()); //类别id,用于在头部的高亮
		
		//取出头部和尾部的参数
		model = indexService.getHeadAndFooterM(productVo.getShopId(), model);
		
		return model;
	}
	
	/**
	 * 取得产品信息以及图片信息
	 */
	public ProductVo viewProductWithImage(String productId, String startRow, boolean pre) throws IllegalAccessException,
			InvocationTargetException, IOException {
		Product product = productDao.getHbase(productId);
		ProductVo productVo = new ProductVo();
		BeanUtils.copyProperties(product, productVo);
		Category category = categoryDao.get(product.getCategoryId(), product.getShopId());
		productVo.setCategoryId(category.getId());
		productVo.setCategoryName(category.getName());
		PageHbase<Image> page = imageDao.pageImageHbase(product.getId(), startRow, pre);
		List<Image> imageList = addFormate(page.getList());
		productVo.setImageList(imageList);
		//设置是否有下一页图片信息
		productVo.setHasNext(page.getHasNext());
		productVo.setPageSize(page.getPageSize());
		return productVo;
	}
	
	/**
	 * 增加图片规格
	 */
	private List<Image> addFormate(List<Image> imageList){
		for(Image image: imageList){
			image.setPath(image.getPath() + Constant.B);
		}
		return imageList;
	}
	
	/**
	 * 根据关键字查出一页产品记录
	 * @throws IOException 
	 */
	public PageHbase<im.gsj.index.vo.ProductVo> search(String shopId, String q, String startRow, boolean next) throws IOException{
		PageHbase<Product> originalPage = productDao.searchProductHbase(shopId, q,  startRow,  next);
		PageHbase<im.gsj.index.vo.ProductVo> page  =  indexService.getFirstProductImageHbase(originalPage);
		return page;
	}
	
	/**
	 * 根据关键字查出一页产品记录
	 * @throws IOException 
	 */
	public PageHbase<im.gsj.index.vo.ProductVo> indexSearch(String q, String startRow, boolean pre, Integer pageSize) throws IOException{
		PageHbase<Product> originalPage = productDao.searchProductHbase(q, startRow, pre, pageSize);
		PageHbase<im.gsj.index.vo.ProductVo> page  =  indexService.getFirstProductImageHbase(originalPage);
		return page;
	}
	
	/**
	 * 根据关键字查出一页产品记录
	 * @throws IOException 
	 */
	public PageHbase<im.gsj.index.vo.ProductVo> indexSearchM(String q, String startRow, boolean pre, Integer pageSize) throws IOException{
		PageHbase<Product> originalPage = productDao.searchProductHbase(q, startRow, pre, pageSize);
		PageHbase<im.gsj.index.vo.ProductVo> page  =  indexService.getFirstProductImageHbaseM(originalPage);
		return page;
	}
}
