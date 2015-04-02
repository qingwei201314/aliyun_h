package im.gsj.product.controller;

import im.gsj.index.service.IndexService;
import im.gsj.product.service.ProductService;
import im.gsj.product.vo.ProductVo;
import im.gsj.util.PageHbase;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/product")
public class ProductViewController {
	@Resource
	private ProductService productService;
	@Resource
	private IndexService indexService;
	
	/**
	 * 跳转到产品详细页面
	 */
	@RequestMapping(value="viewProduct.do", method=RequestMethod.GET)
	public String viewProduct(@RequestParam("productId") String productId,String startRow, boolean pre, ModelMap model) throws IllegalAccessException, InvocationTargetException, IOException{
		model = productService.viewProduct(productId, startRow, pre, model);
		return "/product/viewProduct";
	}
	
	/**
	 * 查看某一张图片
	 */
	@RequestMapping(value="viewImage.do", method=RequestMethod.GET)
	public String viewImage(@RequestParam("imageSrc") String imageSrc, @RequestParam("productName") String productName, ModelMap model){
		model.addAttribute("imageSrc", imageSrc);
		model.addAttribute("productName", productName);
		return "/product/viewImage";
	}
	
	/**
	 * 加载某一产品的更多图片
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
		return "/product/moreImage";
	}
	
	/**
	 * 头部搜索
	 */
	@RequestMapping(value="search", method=RequestMethod.GET)
	public String search(@RequestParam("shopId") String shopId, @RequestParam("q") String q, String startRow, boolean next, ModelMap model) throws IOException{
		PageHbase<im.gsj.index.vo.ProductVo> page =productService.search(shopId, q, startRow, next);
		model.addAttribute("page", page);
		model.addAttribute("shopId", shopId);
		model.addAttribute("q", q);
		
		//查出头部和尾部信息
		model = indexService.getHeadAndFooter(shopId, model);
		return "/product/searchResult";
	}
}
