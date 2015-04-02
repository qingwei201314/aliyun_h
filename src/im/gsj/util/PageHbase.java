package im.gsj.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Hbase分页对象
 */
public class PageHbase<T> {
	private int pageSize = 12;
	private List<T> list = new ArrayList<T>();
	private int orgPageSize;
	//下一页第一条记录
	private T nextRow;
	//是否有上一页, 有:true; 否：false
	private boolean hasPre = false;
	//是否有下一页，有：true; 否：false
	private boolean hasNext = false;

	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public PageHbase(){
	}
	public PageHbase(boolean hasPre, boolean hasNext){
		this.hasPre = hasPre;
		this.hasNext = hasNext;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public T getNextRow() {
		if(list != null && list.size() >pageSize ){
			nextRow = list.get(pageSize + 1);
		}
		return nextRow;
	}
	public int getOrgPageSize() {
		return pageSize + 1;
	}
	public boolean getHasPre() {
		return hasPre;
	}
	public void setHasPre(boolean hasPre) {
		this.hasPre = hasPre;
	}
	public boolean getHasNext() {
		return hasNext;
	}
	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}
}
