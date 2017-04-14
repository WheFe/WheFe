<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">

<script type="text/javascript">
	function getTextValue() {
		var txObj = document.getElementsByName("tx");
		alert(txObj.length);
		alert(txObj[0].value);
		alert(txObj[1].value);
	}

	function getPname() {
		var pid = document.getElementById("pid").innerHTML;
		alert(pid);
	}

	function AA() {
		alert(10);
		location.href = "modal.jsp"
	}

	function action_pop(url) {
		//객체생성후 데이터 할당
		var obj = new Object();
		obj.msg1 = "test1";
		obj.msg2 = "test2";
		var rtnValue = window
				.showModalDialog(url, obj,
						"dialogWidth:980px;dialogHeight:600px;status:no;help:no;location:no");
	}
	function test() {

		alert(10!!!!!!!!!);
		var objectName = new Object(); // object 선언 modal의 이름이 된다.

		objectName.message = "이건 테스트"; // modal에 넘겨줄 값을 선언할 수 있다.

		var site = "pop";

		var style = "dialogWidth:255px;dialogHeight:250px"; // 사이즈등 style을 선언

		window.showModalDialog(site, objectName, style); // modal 실행 window.showModalDialog 포인트!!

		// modal 에 넘겨줬던 값을 다시 부모창에 받아 들임   

		document.getElementById("test1").value = objectName.message;

	}
</script>
</head>
<body>

	<p id="test1">test1</p>
	<input type="text" name="tx" value="message1">
	<input type="text" name="tx" value="message2">
	<input type="button" value="alert text1" onClick="getTextValue()">
	<input type="button" value="alert text2" onClick="AA()">
	<input type="button" name="connectContract" value="alert text3" onClick="test()">
	
</body>
</html>