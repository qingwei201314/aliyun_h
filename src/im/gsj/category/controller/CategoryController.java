package im.gsj.category.controller;

import im.gsj.category.service.CategoryService;
import im.gsj.dao.CategoryDao;
import im.gsj.dao.ShopDao;
import im.gsj.dao.dto.CategoryDto;
import im.gsj.entity.Category;
import im.gsj.entity.Shop;
import im.gsj.shop.service.ShopService;
import im.gsj.util.Constant;
import im.gsj.util.controller.CommonController;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/category")
public class CategoryController extends CommonController{
	@Resource
	private CategoryService categoryService;
	@Resource
	private CategoryDao categoryDao;
	@Resource
	private ShopService shopService;
	@Resource
	private ShopDao shopDao;

	@RequestMapping(value="saveCategory.do", method=RequestMethod.POST)
	public String saveCategory(@ModelAttribute("category") Category category,HttpServletRequest request,  ModelMap model) throws IOException, IllegalAccessException, InvocationTargetException{
		String returnView = "/admin/product/addProduct";
		String phone = (String)request.getSession().getAttribute(Constant.phone);
		boolean success = categoryService.save(category, phone);
		if(!success){
			//如果保存失败，跳转到原页面
			returnView = "/admin/category/addCategory";
			model.addAttribute("message", "产品类别总数不能超过5个!");
		}
		
		//查出当前商店的分类
		List<CategoryDto> categoryList = categoryService.listCategoryDto(phone);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("categoryId", category.getId());
		
		//使头部能显示
		Shop shop = shopDao.getByPhone(phone);
		model.addAttribute("shop", shop);
		return returnView;
	}
	
	@RequestMapping(value="deleteCategory.do", method=RequestMethod.GET)
	public String deleteCategory(@RequestParam("deleteCategoryId") String deleteCategoryId, HttpServletRequest request, ModelMap model) throws IOException, IllegalAccessException, InvocationTargetException{
		categoryDao.delete(deleteCategoryId);
		
		//查出当前商店的分类
		String phone = (String)request.getSession().getAttribute(Constant.phone);
		List<CategoryDto> categoryList = categoryService.listCategoryDto(phone);
		model.addAttribute("categoryList", categoryList);
		return "/admin/category/addCategory";
	}
	
	/**
	 * 跳转到类别页面
	 */
	@RequestMapping(value="addCategory.do", method=RequestMethod.GET)
	public String addCategory(HttpServletRequest request, ModelMap model) throws IllegalAccessException, InvocationTargetException, IOException {
		String phone = (String)request.getSession().getAttribute(Constant.phone);
		//查出当前商店的分类
		List<CategoryDto> categoryList = categoryService.listCategoryDto(phone);
		model.addAttribute("categoryList", categoryList);
		
		//使头部能显示
		Shop shop = shopDao.getByPhone(phone);
		model.addAttribute("shop", shop);
		return "/admin/category/addCategory";
	}
	
	/**
	 * 跳转到类别编辑页面
	 */
	@RequestMapping(value="editCategory.do", method=RequestMethod.GET)
	public String editCategory(@RequestParam("categoryId") String categoryId, HttpSession session, ModelMap model) throws IllegalAccessException, InvocationTargetException, IOException {
		String phone = (String)session.getAttribute(Constant.phone);
		//查出当前商店的分类,编辑的分类不查出来，编辑的分类另作处理
		model = categoryService.editCategory(phone,categoryId, model);

		return "/admin/category/addCategory";
	}
}
