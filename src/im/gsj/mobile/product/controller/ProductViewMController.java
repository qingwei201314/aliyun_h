package im.gsj.mobile.product.controller;

import im.gsj.product.service.ProductService;
import im.gsj.product.vo.ProductVo;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/productM")
public class ProductViewMController {
	@Resource
	private ProductService productService;
	
	/**
	 * 跳转到产品详细页面
	 */
	@RequestMapping(value="viewProduct.do", method=RequestMethod.GET)
	public String viewProduct(@RequestParam("productId") String productId,String startRow, boolean pre, ModelMap model) throws IllegalAccessException, InvocationTargetException, IOException{
		model = productService.viewProductM(productId, startRow, pre, model);
		return "/mobile/product/viewProduct";
	}
	
	/**
	 * 查看某一张图片
	 */
	@RequestMapping(value="viewImage.do", method=RequestMethod.GET)
	public String viewImage(@RequestParam("imageSrc") String imageSrc, @RequestParam("productName") String productName, ModelMap model){
		model.addAttribute("imageSrc", imageSrc);
		model.addAttribute("productName", productName);
		return "/mobile/product/viewImage";
	}
	
	/**
	 * 加载某一产品的更多图片(手机版)
	 */
	@RequestMapping(value="moreImage.do", method = RequestMethod.GET)
	public String moreImage(@RequestParam("productId") String productId,  String startRow, boolean next, ModelMap model) throws IllegalAccessException, InvocationTargetException, IOException{
		ProductVo productVo = productService.viewProductWithImage(productId, startRow, next);
		model.addAttribute("productVo", productVo);
		//用于标识最后一个id
		String lastId= "";
		if(productVo.getImageList() != null && productVo.getImageList().size() >= productVo.getPageSize() + 1 ){
			lastId = productVo.getImageList().get(productVo.getPageSize()).getId();
		}
		return "/mobile/product/moreImage";
	}
}
