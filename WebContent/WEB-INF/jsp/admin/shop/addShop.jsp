<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/commonjsp/head.jsp"%>
<link href="${util.path}/uploadify/uploadify.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${util.path}/uploadify/jquery.uploadify.min.js" ></script>
<title>用户登录</title>
</head>
<body style="padding-top: 60px;">
	<%@ include file="/commonjsp/admin/topbar.jsp"%>
	<div class="container">
		<form  action="${util.path}/admin/shop/saveShop.do" onsubmit="return beforeSubmit();" method="post">
			<fieldset>
				<div class="clearfix">
					<label for="xlInput" class="label_width">名称</label>
					<div class="div_margin">
						<input type="text" id="name" name="name" value="${shop.name}" size="30" maxlength="26" class="xlarge" />
					</div>
				</div>
				<div class="clearfix">
					<label for="xlInput" class="label_width">简称</label>
					<div class="div_margin">
						<input type="text" id="shortName" name="shortName" size="30" value="${shop.shortName}"
							maxlength="5" class="xlarge" />
					</div>
				</div>
				<div class="clearfix">
					<label for="xlInput" class="label_width">联系人</label>
					<div class="div_margin ">
						<input type="text" id="contact" name="contact" size="30" value="${shop.contact}"
							maxlength="12" class="xlarge" />
					</div>
				</div>
				<div class="clearfix">
					<label for="xlInput" class="label_width">地址</label>
					<div class="div_margin ">
						<select id="province" onchange="getCity(this.value, '#town')" style="width: 150px;">
							<option value="">请选择</option>
							<c:forEach var="province" items="${provinces}">
								<option value="${province.id}" <c:if test="${province.id == shopProvince}">selected="selected"</c:if>>${province.name}</option>
							</c:forEach>
						</select>
						<select id="town" onchange="getCity(this.value, '#city')" style="width: 150px;">
							<option value="">请选择</option>
							<c:forEach var="town" items="${towns}">
								<option value="${town.id}" <c:if test="${town.id == shopTown}">selected="selected"</c:if>>${town.name}</option>
							</c:forEach>
						</select>
						<select id="city" name="district" style="width: 150px;" >
							<option value="">请选择</option>
							<c:forEach var="city" items="${cities}">
								<option value="${city.id}" <c:if test="${city.id == shopCity}">selected="selected"</c:if>>${city.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="div_margin ">
						<input type="text" id="address" name="address" size="30" value="${shop.address}"
							maxlength="26" class="xlarge"
							style="width: 440px; margin-top: 5px;" />
					</div>
				</div>
				<div class="clearfix">
					<label for="xlInput" class="label_width">大门图片</label>
					<div class="div_margin ">
						<img id="gatePhone"
							src="${util.statics}${util.repository}${shop.gateUrl}"
							style="width: 580px; height: 290px; margin-bottom:10px; display: none;<c:if test="${shop.gateUrl!=null&& !''.equals(shop.gateUrl)}">display:block;</c:if>" />
						<input type="file" name="file_upload" id="file_upload" />
						<input type="hidden" id="gateUrl" name="gateUrl" value="${shop.gateUrl}" />
					</div>
				</div>
				<div class="actions" style="padding-left: 360px;">
					<input type="submit" value="保存" class="btn primary" />
				</div>
			</fieldset>
		</form>
		<%@ include file="/commonjsp/footer.jsp"%>
	</div>
	<script type="text/javascript">
		$("#shop").attr("class", "active");

		//根据区划id查找子类，并加载到子下拉框中
		function getCity(cityId, renderItem){
			$.ajax({
				  url: "${util.path}/city/getCityList.do",
				  data: {
			      	cityId: cityId
				  },
				  success: function(html) {
				    $(renderItem).html(html);
				  }
			});
		}

		//上传大门图片
		$(function() {
			  var phone = '${phone}';
			  var jspPaht = '${util.path}/admin/shop/upload.do?phone=${phone}&widthXheight=580x290';
		      $('#file_upload').uploadify({
		         'swf'      : '${util.path}/uploadify/uploadify.swf',
		         'uploader' : jspPaht,
		         'buttonText' : '上传图片', 
		         'width'    :  '90px',
		         'height'   :  '28px',
		         'fileTypeExts' : '*.gif; *.jpg; *.png',
		         'onUploadSuccess' : function(file, data, response) {
		         	var path = "${util.statics}${util.repository}" + data;
		        	$("#gatePhone").show();
		        	$("#gatePhone").attr("src", path); 
		        	$("#gateUrl").val(data);
					//如果有验证信息，将其去掉
		    		if($("#gateUrl").next().hasClass("LV_validation_message LV_invalid")){
		    			$("#gateUrl").next().remove();
		    		}
		          },
		         'buttonClass' : 'btn info'
		      });
		});

		//验证信息
		new LiveValidation('name').add(Validate.Presence);
		new LiveValidation('shortName').add(Validate.Presence);
		new LiveValidation('contact').add(Validate.Presence);
		new LiveValidation('address').add(Validate.Presence);
		
	    //提交前对字段进行非空验证
		function beforeSubmit() {
			var city=  notEnpty('city');
			var gateUrl = notEnpty('gateUrl');
			return city&&gateUrl;
		}
	</script>
</body>
</html>
