package im.gsj.util;

import java.util.ArrayList;
import java.util.List;

public class Page<T> {
	private long total;
	private int pageSize = 12;
	private int pageNo = 1;
	private List<T> list = new ArrayList<T>();
	
	private long totalPage;
	//控制显示在页面上可以点的页面数
	private int displaySize = 5; 
	//在页面上显示的页数集合
	private List<Long> displayPage;

	public Page(int pageNo, int pageSize, long total) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.total = total;
	}
	
	public Page(int pageNo, long total) {
		this.pageNo = pageNo;
		this.total = total;
	}

	public Page(){
	}
	
	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public long getTotalPage() {
		if(total%pageSize == 0){
			totalPage = total/pageSize;
		}
		else{
			totalPage = total/pageSize +1;
		}
		return totalPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}
	
	public int getDisplaySize() {
		return displaySize;
	}

	public void setDisplaySize(int displaySize) {
		this.displaySize = displaySize;
	}
	
	/**
	 * 取得显示在页面上的页码集
	 */
	public List<Long> getDisplayPage() {
		displayPage = new ArrayList<Long>();
		int middle =  displaySize/2 +1;
		if(getTotalPage() <= displaySize){
			//按照总页面进行
			for(long i=1; i<= totalPage; i++ )
				displayPage.add(i);
		}
		else{
			if(pageNo <= middle){
				//取出前displaySize页码
				for(long i=1; i<= displaySize; i++)
					displayPage.add(i);
			}
			else{
				if(totalPage-pageNo >= displaySize - middle){
					//当前页面在中间
					int start = pageNo - displaySize/2; //开始位置
					int end = pageNo + displaySize/2; //结束位置
					for(long i=start; i <= end; i++)
						displayPage.add(i);
				}
				else{
					//当前页面在右边，取出最后的页码
					long start = totalPage - displaySize +1;
					for(long i= start; i <= totalPage; i++)
						displayPage.add(i);
				}
			}
		}
		return displayPage;
	}
}
