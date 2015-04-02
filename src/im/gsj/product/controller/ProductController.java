package im.gsj.product.controller;

import im.gsj.category.service.CategoryService;
import im.gsj.dao.ProductDao;
import im.gsj.dao.ShopDao;
import im.gsj.entity.Category;
import im.gsj.entity.Product;
import im.gsj.entity.Shop;
import im.gsj.product.service.ProductService;
import im.gsj.product.vo.ProductVo;
import im.gsj.shop.service.ShopService;
import im.gsj.util.Constant;
import im.gsj.util.Util;
import im.gsj.util.controller.CommonController;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/product")
public class ProductController extends CommonController{
	@Resource
	private ProductService productService;
	@Resource
	private ProductDao productDao;
	@Resource
	private CategoryService categoryService;
	@Resource
	private ShopService shopService;
	@Resource
	private ShopDao shopDao;

	/**
	 * 跳转到增加产品页面
	 * @throws IOException 
	 */
	@RequestMapping(value="addProduct.do", method=RequestMethod.GET)
	public String addProduct(@RequestParam("categoryId") String categoryId, HttpSession session, ModelMap model) throws IOException {
		String phone = (String)session.getAttribute(Constant.phone);
		//查出当前商店的分类
		List<Category> categoryList = categoryService.list(phone);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("categoryId", categoryId);
		
		//使头部能显示
		Shop shop = shopDao.getByPhone(phone);
		model.addAttribute("shop", shop);
		return "/admin/product/addProduct";
	}
	
	/**
	 * 保存产品信息
	 * @throws IOException 
	 */
	@RequestMapping(value="saveProduct.do", method=RequestMethod.POST)
	public String saveProduct(@ModelAttribute("product") Product product, HttpSession session, ModelMap model) throws IllegalAccessException, InvocationTargetException, IOException  {
		String phone = (String)session.getAttribute(Constant.phone);
		productService.save(product, phone);
		ProductVo productVo = productService.get(product.getId());
		model.addAttribute("productVo", productVo);
		
		//使头部能显示
		Shop shop = shopDao.getByPhone(phone);
		model.addAttribute("shop", shop);
		return "/admin/product/addProductImage";
	}
	
	@RequestMapping(value="addProductImage.do", method=RequestMethod.GET)
	public String addProductImage(@RequestParam("productId") String productId, ModelMap model) throws IllegalAccessException, InvocationTargetException, IOException{
		ProductVo productVo = productService.get(productId);
		model.addAttribute("productVo", productVo);
		
		//使头部能显示
		Shop shop = shopDao.get(productVo.getShopId());
		model.addAttribute("shop", shop);
		return "/admin/product/addProductImage";
	}

	@RequestMapping(value="editProduct.do", method=RequestMethod.GET)
	public String editProduct(@RequestParam("productId") String productId, HttpSession session, ModelMap model) throws IOException {
		String phone = (String)session.getAttribute(Constant.phone);
		model =productService.toEditProduct(phone, productId, model);
		return "/admin/product/addProduct";
	}
	
	/**
	 * 上传产品图片
	 */
	@RequestMapping(value = "upload.do", method = RequestMethod.POST)
	public String upload(HttpServletRequest request, HttpServletResponse response) throws Exception{
			String widthXheight = request.getParameter("widthXheight");
			String json = productService.upload(request, widthXheight);
			PrintWriter pw = response.getWriter();
			pw.append(json);
			return null;
	}

	/**
	 * 删除产品
	 */
	@RequestMapping(value="deleteProduct.do", method=RequestMethod.GET)
	public String deleteProduct(@RequestParam("productId") String productId,  HttpServletRequest request, ModelMap model) throws IOException {
		String uploadPath = ((Util)request.getServletContext().getAttribute("util")).getUpload();
		String phone = (String)request.getSession().getAttribute(Constant.phone);
		model = productService.deleteProduct(phone, productId, uploadPath, model);
		return "/admin/image/addImage";
	}
}
