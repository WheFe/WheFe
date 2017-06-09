<!DOCTYPE html>
<html>

<head>
	<title>회원가입</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
	<script src="https://d1p7wdleee1q2z.cloudfront.net/post/search.min.js"></script>
</head>

<style>
	body {
		padding-top: 40px;
	}

	.form-control {
		margin-bottom: 10px;
	}
</style>

<body>
	<div class="container">
		<div id="signup-box" style="margin-top:50px;" class="signupbox col-md-offset-4 col-sm-12 col-sm-offset-4 col-md-offset-4">
			<div class="row">
				<div class="col-xs-12 col-sm-12 col-md-4 well well-sm">
					<legend>회원가입</legend>
					<form action="${pageContext.request.contextPath}/login/signup?${_csrf.parameterName}=${_csrf.token}" method="post" class="form" role="form" name="form">
						<div class="row">
							<div class="col-md-8">
								<input class="form-control" name="cafe_id" placeholder="ID" type="text" required autofocus />
							</div>
							<div class="col-md-4">
								<button type="button" class="btn btn-info">ID 중복확인</button>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6 col-md-6">
								<input class="form-control" name="cafe_pw" placeholder="비밀번호" type="password" />
							</div>
							<div class="col-md-6 col-md-6">
								<input class="form-control" name="password-check" placeholder="비밀번호 확인" type="password" />
							</div>
						</div>
						<input class="form-control" name="cafe_name" placeholder="카페 이름" type="text" />
						<input class="form-control" name="cafe_address" placeholder="카페 주소" type="text" />
						<input class="form-control" name="cafe_tel" placeholder="카페 전화번호 (-생략)" type="text" />

						<!--<label for="">영업시간</label>-->
						<div class="row"></div>
						<input class="form-control postcodify_postcode5" type="text" name="cafe_open" placeholder="영업 시작시간 (00:00~24:00)" data-timepicker/>
						<input class="form-control" name="cafe_end" placeholder="영업 종료시간 (00:00~24:00)" type="text" data-timepicker/>
						<input class="form-control" name="cafe_intro" placeholder="카페 한줄 소개" type="text" />
						<input class="form-control" name="cafe_max" placeholder="카페정원 (숫자만 입력해주세요)" type="text" />

						<div class="row">
							<div class="col-xs-12 col-md-6"><input type="submit" value="회원가입" class="btn btn-primary btn-block btn-lg" tabindex="7"></div>
							<div class="col-xs-12 col-md-6">
								<a href="<c:url value="/login"/>"></a><div class="col-xs-12 col-md-6"><div class="btn btn-danger btn-block btn-lg">취소</div>
							</div>
					</form>
					</div>
				</div>
			</div>
		</div>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
		<script src="timepicker.js"></script>
</body>

</html>