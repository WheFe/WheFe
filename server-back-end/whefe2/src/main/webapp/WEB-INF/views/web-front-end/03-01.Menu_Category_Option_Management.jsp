<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>

<head>
<title>카테고리 옵션 관리</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
</head>
<style>
/*.navbar-nav {
  font-size: 20px;
 }*/
.option-button {
	border-radius: 10px;
	width: 250px;
	height: 50px;
}

.btn-round {
	border-radius: 17px;
}

.btn-lg {
	border-radius: 10px;
	width: 190px;
	height: 50px;
}

.btn-round-lg {
	border-radius: 10px;
	width: 400px;
}

.btn-round-sm {
	border-radius: 15px;
}

#menu {
	margin-top: 5px;
}
</style>
<script>
	
</script>

<body>
	<nav class="navbar navbar-default navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#myNavbar">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">WheFe</a>
			</div>

			<div class="collapse navbar-collapse" id="myNavbar">
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">메뉴관리</a></li>
					<li><a href="#">쿠폰관리</a></li>
					<li><a href="#">주문확인</a></li>
				</ul>

				<ul class="nav navbar-nav navbar-right">
					<li><a href="#"><span class="glyphicon glyphicon-log-out"></span>
							로그아웃</a></li>
				</ul>
			</div>

		</div>
	</nav>


	<div class="container" style="margin-top: 80px">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4>
					<b>카테고리별 옵션관리 </b>
				</h4>
			</div>

			<div class="container">
				<div class="row">
					<div class="panel-body">
						<button type="button"
							onclick="location.href='<c:url value="/management"/>'"
							class="btn btn-default btn-round-lg btn-lg"
							style="text-align: left; width: 260px; float: right; margin-right: 40px;">
							<span class="glyphicon glyphicon-arrow-left"></span>&nbsp;카테고리
							메뉴로 돌아가기
						</button>
					</div>
				</div>
				<div class="row">
					<div class="panel-body">
						<button type="button" class="btn btn-default btn-round-lg btn-lg"
							data-toggle="modal" data-target="#addCategory"
							style="border: 0; text-align: left; vertical-align: middle">
							<b><span class="glyphicon glyphicon-plus-sign"></span>&nbsp;옵션
								추가하기</b>
						</button>
						<!--옵션 추가하기 팝업-->
						<div class="modal fade" id="addCategory" role="dialog">
							<div class="modal-dialog modal-sm">

								<!-- Modal content-->
								<div class="modal-content">
									<form action="<c:url value="/management/option/addoption"/>">
										<input type="hidden" name="category_name"
											value="${category_name}" /> ${category_name}
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal">&times;</button>
											<h4 class="modal-title">옵션 추가하기</h4>
										</div>
										<div class="modal-body">
											<div class="container">
												<div class="row">
													<input class="form-control" style="max-width: 270px"
														name="option_name" placeholder="옵션 이름" type="text"
														required autofocus /> <input class="form-control"
														style="max-width: 270px" name="option_price"
														placeholder="가격" type="text" />
												</div>
											</div>
										</div>
										<div class="modal-footer">
											<button type="submit" class="btn btn-default btn-md"
												data-toggle="modal" data-target="#addOption_complete">옵션
												추가</button>
											<!--옵션 추가 완료-->
											<div class="modal" id="addOption_complete" role="dialog">
												<div class="modal-dialog modal-sm">
													<div class="modal-content">
														<div class="modal-body">
															<div class="row" style="text-align: center">
																<b>옵션 추가 완료!</b>
															</div>
														</div>
														<div class="modal-footer" data-dismiss="modal">
															<button type="button" class="btn btn-default btn-md">확인</button>
														</div>
													</div>
												</div>
											</div>
											<button type="button" class="btn btn-danger btn-md"
												data-dismiss="modal">취소</button>
										</div>
									</form>
								</div>

							</div>
						</div>
					</div>
				</div>

				<div class="row" id="menu">
					<div class="panel-body">
						<c:forEach var="opt" items="${opt}">
							<div class="row">
								<div class="col-lg-4">
									<button type="button" class="btn btn-default option-button"
										style="margin-left: 100px">
										<h4>
											<b> ${opt.option_name} </b>
										</h4>
									</button>
								</div>

								<div class="col-lg-2">
									<button type="button" class="btn btn-default btn-lg"
										style="border: 0">
										<b> + ${opt.option_price} </b>
									</button>
								</div>

								<!--옵션 수정-->
								<div class="col-lg-2">
									<button type="button" class="btn btn-warning btn-lg"
										data-toggle="modal" data-target="#modify-name">
										<span style="margin-right: 5px"
											class="glyphicon glyphicon-pencil"></span>수정
									</button>
									<!--옵션 수정하기 팝업-->
									<div class="modal fade" id="modify-name" role="dialog">
										<div class="modal-dialog modal-sm">

											<!-- Modal content-->
											<div class="modal-content">
												<form
													action="<c:url value="/management/option/editoption/${opt.option_name}"/>">
													<input type="hidden" name="category_name"
														value="${category_name}" />
													<div class="modal-header">
														<button type="button" class="close" data-dismiss="modal">&times;</button>
														${opt.option_name}
														<h4 class="modal-title">옵션 수정하기</h4>
													</div>
													<div class="modal-body">

														<div class="container">
															<div class="row">

																<input class="form-control" style="max-width: 270px"
																	name="option_name" placeholder="옵션이름" type="text"
																	required autofocus />
															</div>
															<div class="row">
																<input class="form-control" style="max-width: 270px"
																	name="option_price" placeholder="가격" type="text" />
															</div>
														</div>

													</div>
													<div class="modal-footer">
														<button type="submit" class="btn btn-default btn-md"
															data-toggle="modal" data-target="#modify_option_complete">옵션
															수정</button>
														<!--이름 추가 완료-->
														<div class="modal" id="modify_option_complete"
															role="dialog">
															<div class="modal-dialog modal-sm">
																<div class="modal-content">
																	<div class="modal-body">
																		<div class="row" style="text-align: center">
																			<b>옵션 수정 완료!</b>
																		</div>
																	</div>
																	<div class="modal-footer" data-dismiss="modal">
																		<button type="button" class="btn btn-default btn-md">확인</button>
																	</div>
																</div>
															</div>
														</div>
														<button type="button" class="btn btn-danger btn-md"
															data-dismiss="modal">취소</button>
													</div>
												</form>
											</div>

										</div>
									</div>

								</div>

								<div class="col-lg-2">
									<button type="button" class="btn btn-danger btn-lg"
										data-toggle="modal" data-target="#delete-option">
										<span style="margin-right: 5px"
											class="glyphicon glyphicon-trash"></span>삭제
									</button>
									<!--옵션 삭제하기 팝업-->
									<div class="modal fade" id="delete-option" role="dialog">
										<div class="modal-dialog modal-sm">

											<!-- Modal content-->
											<div class="modal-content">

												<div class="modal-header">
													<button type="button" class="close" data-dismiss="modal">&times;</button>
													<h4 class="modal-title">옵션 삭제하기</h4>
												</div>
												<form
													action="<c:url value="/management/option/deleteoption/${opt.option_name}"/>">
													<input type="hidden" name="category_name"
														value="${category_name}" />
													<div class="modal-body">
														<div class="container">
															<div class="row">옵션을 삭제하시겠습니까?</div>
														</div>

													</div>
													<div class="modal-footer">
														<button type="submit" class="btn btn-default btn-md"
															data-toggle="modal" data-target="#delete_option_complete">옵션
															삭제</button>
														<!--이름 추가 완료-->
														<div class="modal" id="delete_option_complete"
															role="dialog">
															<div class="modal-dialog modal-sm">
																<div class="modal-content">
																	<div class="modal-body">
																		<div class="row" style="text-align: center">
																			<b>옵션 삭제 완료!</b>
																		</div>
																	</div>
																	<div class="modal-footer" data-dismiss="modal">
																		<button type="button" class="btn btn-default btn-md">확인</button>
																	</div>
																</div>
															</div>
														</div>
														<button type="button" class="btn btn-danger btn-md"
															data-dismiss="modal">취소</button>
													</div>
												</form>
											</div>
										</div>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</div>



	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>

</html>