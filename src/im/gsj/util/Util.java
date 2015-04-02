package im.gsj.util;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class Util {
	private static Document document;
	// 工程url中的目录
	private static String path;
	//静态文件的工程路径
	private static String statics;
	// 上传路径
	private static String upload;
	//存放图片的工程路径
	private static String repository;
	//一页的记录数(用于显示的记录数为12,再加1条记录用于翻页，故为13)
	public final static int pageSize=13;

	public Util() throws DocumentException {
		if (document == null) {
			SAXReader saxReader = new SAXReader();
			String path = StringUtils.substringBefore(Util.class
					.getResource("").getPath(), "im/gsj");
			document = saxReader.read(new File(path + "config.xml"));
		}
		path = document.selectSingleNode("//root//path").getStringValue();
		statics = document.selectSingleNode("//root//statics").getStringValue();
		upload = document.selectSingleNode("//root//upload").getStringValue();
		repository = document.selectSingleNode("//root//repository").getStringValue();
	}

	public String getPath() {
		return path;
	}

	public static String path() {
		return path;
	}

	public String getUpload() {
		return upload;
	}

	public static String upload() {
		return upload;
	}

	public String getRepository() {
		return repository;
	}
	
	public static String repository() {
		return repository;
	}
	
	public String getStatics() {
		return statics;
	}
	
	public static String statics() {
		return statics;
	}
}
