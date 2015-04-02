package im.gsj.image.service;

import im.gsj.category.service.CategoryService;
import im.gsj.dao.ImageDao;
import im.gsj.dao.ProductDao;
import im.gsj.dao.ShopDao;
import im.gsj.dao.UserDao;
import im.gsj.entity.Category;
import im.gsj.entity.Image;
import im.gsj.entity.Product;
import im.gsj.entity.Shop;
import im.gsj.entity.User;
import im.gsj.shop.service.ShopService;
import im.gsj.util.Constant;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Service
public class ImageService {
	@Resource
	private ImageDao imageDao;
	@Resource
	private CategoryService categoryService;
	@Resource
	private ProductDao productDao;
	@Resource
	private ShopService shopService;
	@Resource
	private ShopDao shopDao;
	@Resource
	private UserDao userDao;
	
	/**
	 * 保存图片的数据库记录
	 */
	public ImageResult saveImage(String product_id, String url) throws IOException{
		url = StringUtils.substringBefore(url, ",");
		String path = StringUtils.substringBeforeLast(url, "_");
		String postfix= "." + StringUtils.substringAfter(url, ".");
		Image image = new Image(product_id,path);
		image.setPostfix(postfix);
		Product product = productDao.getHbase(product_id);
		imageDao.saveHbase(product.getCategoryId(), product.getId(), image);
		String resut = path + Constant.S + postfix;
		ImageResult imageResult =new ImageResult();
		imageResult.setImageId(image.getId());
		imageResult.setResut(resut);
		return imageResult;
	}
	
	/**
	 * 查出跳转到添加图片页面的各参数
	 * @throws IOException 
	 */
	public ModelMap addImage(String phone, ModelMap model) throws IOException{
		List<Category> categoryList = categoryService.list(phone);
		model.addAttribute("categoryList", categoryList);
		if(categoryList!=null && categoryList.size()>0){
			Category category= categoryList.get(0);
			List<Product> productList =  productDao.queryListHbase(category.getId());
			model.addAttribute("productList", productList);
		}
		
		//使头部能显示
		Shop shop = shopDao.getByPhone(phone);
		model.addAttribute("shop", shop);
		return model;
	}
	
	/**
	 * 删除图片：先删除数据库记录，再删除物理文件
	 */
	public String deleteImage(String phone , String imageId, String uploadPath) throws IOException{
		String result = "failure";
		Image image = imageDao.getHbase(imageId);
		Product product = productDao.getHbase(image.getProductId());
		Shop shop = shopDao.get(product.getShopId());
		//如果是当前用户
		User user = userDao.get(shop.getUserId());
		if(phone.equals(user.getPhone())){
			productDao.deleteHbase(image.getId());
			//删除物理文件
			String path = image.getPath();
			String postfix = image.getPostfix();
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
			result= "success";
		}
		return result;
	}
}
