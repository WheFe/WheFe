<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
<script type="text/javascript">
	/* $(document).ready(function() {
		$("#checkBoxId").change(function() {
			if ($("#checkBoxId").is(":checked")) {
				alert("체크박스 체크했음!");

			} else {
				alert("체크박스 체크 해제!");
			}
		});
	}); */
	$(document).ready(function() {
		$("#checkBoxId").change(function(event) {
			if ($("#checkBoxId").is(":checked")) {
				alert("체크박스 체크했음!");
				$("#textId").removeAttr("disabled");

			} else {
				alert("체크박스 체크 해제!");
				$("#textId").attr("disabled","disabled");
			}
		});
	});
</script>
</head>
<body>
	<input type="checkbox" id="checkBoxId" />
	<input type="text" id="textId" disabled="disabled"/>
	<h1 class="welcome">Hello</h1>
</body>
</html>