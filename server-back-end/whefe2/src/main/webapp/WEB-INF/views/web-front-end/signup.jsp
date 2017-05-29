<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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
<!-- <script>
	$("#cafe_id").keyup(function() {
		$.ajax({
			url : "/login/duplicationCheck",
			type : "post",
			data : $("#form").serialize(),
			success : function(data) {
				if (data.length > 0) {
				document.getElementById("duplicateResult").value = "이미 해당 아이디로 가입된 회원가 있습니다.";
				} else {
					if ($("#cafe_id").val().length < 5) {
					document.getElementById("duplicateResult").value = "아이디를 5자 이상 입력해주세요.";
					} else {
						document.getElementById("duplicateResult").value = "사용 가능한 아이디입니다.";
					}
				}
			},
			error : function(error) {
				alert(error.statusText);
			}
		});

		return false;
	});
</script> -->
	<div class="container">
		<div id="signup-box" style="margin-top:50px;" class="signupbox col-md-offset-4 col-sm-12 col-sm-offset-4">
			<div class="row">
				<div class="col-xs-12 col-sm-12 col-md-4 well well-sm">
					<legend>회원가입</legend>
					<form action="${pageContext.request.contextPath}/login/signup?${_csrf.parameterName}=${_csrf.token}" method="post" id="form" name="form" class="form" >
						<input class="form-control" id="cafe_id" name="cafe_id" placeholder="ID" type="text" required autofocus />
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
						<div class="row">
							<div class="col-md-6 col-md-6">
								<input class="form-control" name="cafe_open" placeholder="영업 시작시간" type="text" />
							</div>
							<div class="col-md-6 col-md-6">
								<input class="form-control" name="cafe_end" placeholder="영업 종료시간" type="text" />
							</div>
						</div>
						<input class="form-control" name="cafe_max" placeholder="카페정원" type="text" />

						<div class="row">
							<div class="col-xs-12 col-md-6"><input type="submit" value="회원가입" class="btn btn-primary btn-block btn-lg" tabindex="7"></div>
							<a href="<c:url value="/login"/>"></a><div class="col-xs-12 col-md-6"><div class="btn btn-danger btn-block btn-lg">취소</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>

</html>