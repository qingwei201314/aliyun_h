<div class="alert-message block-message error fade in"
	style="display: none;" data-alert="alert">
	<a class="close" href="#">&#215;</a>
	<div id="message" style="text-align: center;">${message}</div>
</div>
<script type="text/javascript">
	var alertMessage = $(".alert-message");
	var message = $("#message");
	if (message.html() != '') {
		alertMessage.show();
		alertMessage.alert();
	}
</script>