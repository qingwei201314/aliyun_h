package im.gsj.category.controller;

import im.gsj.category.service.CategoryService;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/category")
public class CategoryViewController {
	@Resource
	private CategoryService categoryService;

	/**
	 * 取得某一商店某一类别的所有产品
	 */
	@RequestMapping(value = "listProduct.do", method = RequestMethod.GET)
	public String listProduct(@RequestParam("shopId") String shopId, @RequestParam("categoryId") String categoryId,
			 String startRow, boolean pre, ModelMap model) throws IOException {
		model = categoryService.listProduct(shopId, categoryId, startRow, pre, model);
		return "/category/listProduct";
	}

	@RequestMapping(value = "moreImage.do", method = RequestMethod.GET)
	public String moreImage(@RequestParam("categoryId") String categoryId, String startRow, boolean pre, ModelMap model) throws IOException {
		model = categoryService.moreImage(categoryId,  startRow, pre,  model);
		return "/category/moreImage";
	}
}
