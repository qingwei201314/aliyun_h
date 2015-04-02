package im.gsj.image.controller;

import im.gsj.dao.ProductDao;
import im.gsj.entity.Product;
import im.gsj.image.service.ImageService;
import im.gsj.util.Constant;
import im.gsj.util.Util;
import im.gsj.util.controller.CommonController;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/image")
public class ImageController extends CommonController{
	@Resource
	private ImageService imageService;
	@Resource
	private ProductDao productDao;
	
	@RequestMapping(value="addImage.do", method=RequestMethod.GET)
	public String addImage(HttpSession session, ModelMap model) throws IOException{
		String phone = (String)session.getAttribute(Constant.phone);
		model = imageService.addImage(phone, model);
		return "/admin/image/addImage";
	}

	/**
	 * 取得某一类别的产品
	 */
	@RequestMapping(value="getProductList.do", method=RequestMethod.GET)
	public String getProductList(@RequestParam("categoryId") String categoryId, ModelMap model) throws IOException {
		List<Product> productList = productDao.queryListHbase(categoryId);
		model.addAttribute("productList", productList);
		return "/admin/image/getProductList";
	}
	
	@RequestMapping(value="deleteImage.do", method=RequestMethod.GET)
	public String deleteImage(HttpServletRequest request, HttpServletResponse response, @RequestParam("imageId") String imageId) throws IOException{
		String phone = (String)request.getSession().getAttribute(Constant.phone);
		String uploadPath = ((Util)request.getServletContext().getAttribute("util")).getUpload();
		String result = imageService.deleteImage(phone, imageId, uploadPath);
		PrintWriter pw = response.getWriter();
		pw.append(result);
		return null;
	}
}
