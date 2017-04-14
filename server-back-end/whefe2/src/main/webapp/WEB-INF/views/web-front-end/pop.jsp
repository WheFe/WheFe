<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<script type="text/javascript">

	function init() {

		// 부모페이지의 값을 불러들이기 위함 window.dialogArguments 포인트!

		var opener = window.dialogArguments;

		var message = opener.message; // 부모창에서 object에 넣었던 값

		alert(message);

		opener.message = "test OK!!!"; // object 값을 변경

		//window.close();

	}
</script>
</head>
<body>

</body>
</html>