<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
   String pageForm = request.getParameter("pageForm");
%>

<c:if test="${page.hasPre || page.hasNext }">
	<div class="pagination" style="padding-left: 385px;">
		<ul>
			<li class="prev <c:if test="${page.hasPre == false}">disabled</c:if>">
				<a href="javascript:void(0)" <c:if test="${page.hasPre}">onclick="gotoPage('<%=pageForm%>', '${page.list[0].id}', true)"</c:if>>&larr; 上一页</a></li>
			<li class="next <c:if test="${page.hasNext == false}">disabled</c:if>">
				<a href="javascript:void(0)" <c:if test="${page.hasNext}">onclick="gotoPage('<%=pageForm%>', '${page.list[page.pageSize].id}', false)"</c:if>>下一页 &rarr;</a>
			</li>
		</ul>
	</div>
</c:if>

<c:if test="${page.list==null||page.list.size()<=0 }">
<div class="popover left" style="display: block;position: static;" >
	<div class="inner" style="margin: 0 auto;">
		<h3 class="title">没有相关产品。</h3>
		<div class="content">
			<p>对不起，没有符合条件的相关产品。</p>
		</div>
	</div>
</div>
</c:if>
			
<script type="text/javascript">
	//跳转到第n页面
	function gotoPage(pageForm, startRow, pre){
		var pageForm = "#" + pageForm;
		var pageNoHidden = "<input type='hidden' id='startRow' name='startRow' value='" + startRow + "' />" ;
		$("#pageNo").remove();
		$(pageForm).append(pageNoHidden);
		//是否上一页
		if(pre){
			var preHidden = "<input type='hidden' id='pre' name='pre' value='" + pre + "' />" ;
			$("#pre").remove();
			$(pageForm).append(preHidden);
		}
		$(pageForm).submit();
	}
</script>