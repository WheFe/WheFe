<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>

<head>
<title>회원가입</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
<script src="https://d1p7wdleee1q2z.cloudfront.net/post/search.min.js"></script>
</head>

<script>
function validate(evt) {
var theEvent = evt || window.event;
var key = theEvent.keyCode || theEvent.which;
key = String.fromCharCode( key );
var regex = /[0-9]|\./;
if( !regex.test(key) ) {
	theEvent.returnValue = false;
	if(theEvent.preventDefault) theEvent.preventDefault();
}
}
</script>
<style>
body {
	padding-top: 40px;
}

.form-control {
	margin-bottom: 10px;
}

.image-preview-input {
	position: relative;
	overflow: hidden;
	margin: 0px;
	color: #333;
	background-color: #fff;
	border-color: #ccc;
}

.image-preview-input input[type=file] {
	position: absolute;
	top: 0;
	right: 0;
	margin: 0;
	padding: 0;
	font-size: 20px;
	cursor: pointer;
	opacity: 0;
	filter: alpha(opacity = 0);
}

.image-preview-input-title {
	margin-left: 2px;
}

body {
	background:
		url('http://www.publicdomainpictures.net/pictures/70000/velka/old-white-background.jpg')
		fixed;
	background-size: cover;
	padding: 0;
	margin: 0;
}

.panel {
	opacity: 0.9;
}

.bottom {
	margin-bottom: 9px;
}
</style>

<body>
	<div class="container">
		<div class="panel">
			<div id="signup-box" style="margin-top: 50px;"
				class="signupbox col-md-offset-4 col-sm-12 col-sm-offset-4 col-md-offset-4">
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-4 well well-sm">
						<legend>회원가입</legend>
						<form action="${pageContext.request.contextPath}/login/signup"
							method="post" name="form" class="form" role="form"
							enctype="multipart/form-data">
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
							<div class="row">
								<div class="col-md-8">
									<input class="form-control" name="cafe_id" placeholder="ID"
										type="text" required autofocus />
								</div>
								<div class="col-md-4">
									<button type="button" class="btn btn-info">ID 중복확인</button>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 col-md-6">
									<input class="form-control" name="cafe_pw" placeholder="비밀번호"
										type="password" />
								</div>
								<div class="col-md-6 col-md-6">
									<input class="form-control" name="password-check"
										placeholder="비밀번호 확인" type="password" />
								</div>
							</div>
							<input class="form-control" name="cafe_name" placeholder="카페 이름"
								type="text" /> <input class="form-control" name="cafe_address"
								placeholder="카페 주소" type="text" /> <input class="form-control"
								name="cafe_tel" placeholder="카페 전화번호 (-생략)" onkeypress='validate(event)' type="text"
								onchange="getNumber(this);" onkeyup="getNumber(this);"
							 />

							<!--<label for="">영업시간</label>-->
							<div class="row"></div>
							<input class="form-control postcodify_postcode5" type="text"
								name="cafe_open" onkeypress='validate(event)' placeholder="영업 시작시간 (00:00~24:00)"
								data-timepicker /> <input class="form-control" name="cafe_end"
								placeholder="영업 종료시간 (00:00~24:00)" onkeypress='validate(event)' type="text" data-timepicker />
							<input class="form-control" name="cafe_intro"
								placeholder="카페 한줄 소개" type="text" />
							<div class="input-group image-preview bottom">
								<input placeholder="" type="text"
									class="form-control image-preview-filename" disabled="disabled">
								<span class="input-group-btn">
									<button type="button"
										class="btn btn-default image-preview-clear"
										style="display: none;">
										<span class="glyphicon glyphicon-remove"></span> Clear
									</button>
									<div class="btn btn-default image-preview-input">
										<span class="glyphicon glyphicon-folder-open"></span> <span
											class="image-preview-input-title">Browse</span> <input
											type="file" accept="image/png, image/jpeg, image/gif"
											name="cafe_image" />
									</div>
								</span>
							</div>
							<input class="form-control" name="cafe_max"
								placeholder="카페정원 (숫자만 입력해주세요)" onkeypress='validate(event)' type="text" />

							<div class="row">
								<div class="col-xs-12 col-md-6">
									<input type="submit" value="회원가입"
										class="btn btn-primary btn-block btn-lg" tabindex="7">
								</div>
								<div class="col-xs-12 col-md-6">
									<a href="<c:url value="/login"/>"><div
											class="btn btn-danger btn-block btn-lg">취소</div></a>
								</div>
							</div>

						</form>
					</div>
				</div>
			</div>
		</div>
		<script
			src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
		<script
			src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
		<script src="<c:url value="/resources/js/timepicker.js"/>"></script>
</body>

</html>
