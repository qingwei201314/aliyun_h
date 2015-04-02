package im.gsj.uploadify.service;

import im.gsj.user.service.WidthHeight;
import im.gsj.util.Constant;
import im.gsj.util.Util;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 用于处理上传文件
 * @author Kevin
 */
@Service
public class Uploadify {
	public String upload(String phone, HttpServletRequest request, String widthXheight_s) throws Exception{
		List<WidthHeight> widthHeights = new ArrayList<WidthHeight>(); //用于存各存规格
		String[] widthXheightArray = widthXheight_s.split("_"); //分出几种规格
		for(int i=0; i < widthXheightArray.length; i++){
			String widthXheight = widthXheightArray[i];
			String[] wXh = widthXheight.split("x");
			WidthHeight widthHeight = new WidthHeight(wXh[0], wXh[1]);
			widthHeights.add(widthHeight);
		}
		String resultPath = "";
		String path = "";
	 	int length = phone.length();
	 	int count = length%4 > 0 ? length/4 +1 : length/4;
	 	for(int i=0; i< count; i++)
	 		path += "/" + phone.substring(i*4, (i*4 + 4)>length?length:i*4 + 4);
	 	//增加年份
	 	int year = Calendar.getInstance().get(Calendar.YEAR);
	 	path += "/" + year;
	 	String ymdhms = getDateFormat();
	 	String upLoadPath = ((Util)request.getServletContext().getAttribute("util")).getUpload();
	 	File filepath = new File(upLoadPath + path);
	 	if(!filepath.exists()){
	 		filepath.mkdirs();
	 	}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletContext servletContext = request.getServletContext();
		File repository = (File) servletContext.getAttribute(upLoadPath);
		factory.setRepository(repository);
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> items = upload.parseRequest(request);
		if(items != null && items.size() >0){
			for(int i=0; i< items.size(); i++){
				FileItem fileItem = items.get(i);
				if(!fileItem.isFormField()){
					File file = new File(upLoadPath + path+"/" + ymdhms + "_" + fileItem.getName());
					fileItem.write(file);
					//将图片进行缩略
					Image srcImg = ImageIO.read(file); 
					if(widthHeights != null && widthHeights.size() >0){
						for(int j =0; j< widthHeights.size() ; j++ ){
							WidthHeight widthHeight = widthHeights.get(j);
							BufferedImage buffImg = null;
							buffImg = new BufferedImage(widthHeight.getWidth(), widthHeight.getHeight(), BufferedImage.TYPE_INT_RGB);   
							buffImg.getGraphics().drawImage(srcImg.getScaledInstance(widthHeight.getWidth(), widthHeight.getHeight(), Image.SCALE_SMOOTH), 0, 0, null);
							String postfix = StringUtils.substringAfterLast(fileItem.getName(),".");
							String newpath =  path + "/" + ymdhms + "_" + widthHeight.getWidth() + "x" + widthHeight.getHeight() + "." + postfix;
							ImageIO.write(buffImg, postfix, new File(upLoadPath + newpath));
							resultPath += newpath + ",";
						}
					}
					//将原始文件删除，节省磁盘空间
					file.delete(); 
				}
			}
		}
		return resultPath;
	}
	
	/**
	 * 上传大门图片
	 * @param request
	 * @param widthXheight_s
	 * @return
	 * @throws Exception
	 */
	public String uploadGate(String phone, HttpServletRequest request, String widthHeight_s) throws Exception{
		String[] wXh = widthHeight_s.split("x");
		WidthHeight widthHeight = new WidthHeight(wXh[0], wXh[1]);
		
		String resultPath = "";
		String path = "";
	 	int length = phone.length();
	 	int count = length%4 > 0 ? length/4 +1 : length/4;
	 	for(int i=0; i< count; i++)
	 		path += "/" + phone.substring(i*4, (i*4 + 4)>length?length:i*4 + 4);
	 	
	 	path += "/gate";

	 	String upLoadPath = ((Util)request.getServletContext().getAttribute("util")).getUpload();
	 	String ymdhms = getDateFormat();
	 	//大门图片的存放路径
	 	File filepath = new File(upLoadPath + path);
	 	//如果不存在，则创建，存在则删除其子目录。大门图片只有一张
	 	if(!filepath.exists()){
	 		filepath.mkdirs();
	 	}
	 	else{
	 		File[] files = filepath.listFiles();
	 		if(files !=null && files.length >0)
	 			for(File file: files)
	 				file.delete();
	 	}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletContext servletContext = request.getServletContext();
		File repository = (File) servletContext.getAttribute(upLoadPath + path);
		factory.setRepository(repository);
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> items = upload.parseRequest(request);
		if(items != null && items.size() >0){
			for(int i=0; i< items.size(); i++){
				FileItem fileItem = items.get(i);
				if(!fileItem.isFormField()){
					File file = new File(upLoadPath + path+"/" + "_" + fileItem.getName());
					fileItem.write(file);
					Image srcImg = ImageIO.read(file); 
					BufferedImage buffImg = null;
					buffImg = new BufferedImage(widthHeight.getWidth(), widthHeight.getHeight(), BufferedImage.TYPE_INT_RGB);   
					buffImg.getGraphics().drawImage(srcImg.getScaledInstance(widthHeight.getWidth(), widthHeight.getHeight(), Image.SCALE_SMOOTH), 0, 0, null);
					String postfix = StringUtils.substringAfterLast(fileItem.getName(),".");
					String newpath =  path  + "/" + ymdhms+ "_" + widthHeight.getWidth() + "x" + widthHeight.getHeight() + "." + postfix;
					//如果已有大门图片，则先删除
					File oldFile = new File(upLoadPath +  newpath);
					
					ImageIO.write(buffImg, postfix, oldFile);
					resultPath =newpath;
					file.delete(); //将原始文件删除，节省磁盘空间
				}
			}
		}
		return resultPath;
	}
	
	/**
	 * 取得当前时间的格式
	 * @return
	 */
	private String getDateFormat(){
		String ymdhms = new StringBuffer().append(Calendar.getInstance().get(Calendar.YEAR))
			.append("-")
			.append(Calendar.getInstance().get(Calendar.MONTH) + 1)
			.append("-")
			.append(Calendar.getInstance().get(Calendar.DATE))
			.append("-")
			.append(Calendar.getInstance().get(Calendar.HOUR_OF_DAY))
			.append("-")
			.append(Calendar.getInstance().get(Calendar.MINUTE))
			.append("-")
			.append(Calendar.getInstance().get(Calendar.SECOND))
			.toString();
		return ymdhms;
	}
}
