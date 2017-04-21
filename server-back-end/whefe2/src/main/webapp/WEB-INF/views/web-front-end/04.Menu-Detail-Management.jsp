<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<title>상세 메뉴 관리</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
</head>
<style>
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

.price {
	max-width: 100px;
}
}
</style>
<body>
	<nav class="navbar navbar-default navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#myNavbar">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">Grazie</a>
			</div>

			<div class="collapse navbar-collapse" id="myNavbar">
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">메뉴관리</a></li>
					<li><a href="<c:url value="/management/coupon"/>">쿠폰관리</a></li>
					<li><a href="<c:url value="/management/order"/>">주문확인</a></li>
				</ul>

				<ul class="nav navbar-nav navbar-right">
					<li><div class="user-info"
							style="margin-top: 15px; color: #636363">
							<span class="glyphicon glyphicon-user"></span>&nbsp;
							${pageContext.request.userPrincipal.name}
						</div></li>
					<li><a href="<c:url value="/logout"/>"><span
							class="glyphicon glyphicon-log-out"></span> 로그아웃</a></li>
				</ul>
			</div>

		</div>
	</nav>

	<div class="container" style="margin-top: 80px">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4>
					<b>메뉴 관리
						<h4>
					</b>
			</div>

			<div class="container">
				<div class="row">
					<div class="panel-body">
						<button type="button" class="btn btn-default btn-round-lg btn-lg"
							onclick="location.href='<c:url value="/management"/>'"
							style="text-align: left; width: 260px; float: right; margin-right: 40px;">
							<span class="glyphicon glyphicon-arrow-left"></span>&nbsp;카테고리
							메뉴로 돌아가기
						</button>
					</div>
				</div>
				<div class="container">
					<div class="row">

						<div class="panel-body">
							<button type="button" class="btn btn-default btn-round-lg btn-lg"
								data-toggle="modal" data-target="#addMenu"
								style="border: 0; text-align: left">
								<b><span class="glyphicon glyphicon-plus-sign"></span>&nbsp;메뉴
									추가하기</b>
							</button>
							<!--메뉴 추가하기 팝업-->
							<div class="modal fade" id="addMenu" role="dialog">
								<div class="modal-dialog modal-lg">

									<!-- Modal content-->
									<div class="modal-content">

										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal">&times;</button>
											<h4 class="modal-title">메뉴 추가하기</h4>
										</div>
										<form action="<c:url value="/management/menu/addmenu"/>">
											<input type="hidden" name="category_name"
												value="${category_name}" /> <input type="hidden"
												name="cafe_id"
												value="${pageContext.request.userPrincipal.name}" />
											<div class="modal-body">
												<div class="container-fluid">
													<div class="row">
														<div class="input-group">
															<span class="input-group-addon"> </span> <input
																id="menu_name" type="text" class="form-control"
																name="menu_name" placeholder="추가할 메뉴 이름 입력">
														</div>
													</div>
													<br />
													<div class="row">
														<!--가격 관리 표 삽입-->
														<div class="table">
															<table class="table">
																<thead>
																	<tr>
																		<th></th>
																		<th>Small</th>
																		<th>Medium</th>
																		<th>Large</th>
																		<th>사이즈 없음</th>
																	</tr>
																</thead>
																<tbody>
																	<tr>
																		<th scope="row">HOT</th>
																		<td><div class="checkbox price">
																				<label><input type="checkbox" value=""><input
																					name="hot_small" type="text" class="form-control"></input></label>
																			</div></td>
																		<td><div class="checkbox price">
																				<label><input type="checkbox" value=""><input
																					name="hot_medium" type="text" class="form-control"></input></label>
																			</div></td>
																		<td><div class="checkbox price">
																				<label><input type="checkbox" value=""><input
																					name="hot_large" type="text" class="form-control"></input></label>
																			</div></td>
																		<td><div class="checkbox price">
																				<label><input type="checkbox" value=""><input
																					name="hot_none" type="text" class="form-control"></input></label>
																			</div></td>
																	</tr>
																	<tr>
																		<th scope="row">ICE</th>
																		<td><div class="checkbox price">
																				<label><input type="checkbox" value=""><input
																					name="ice_small" type="text" class="form-control"></input></label>
																			</div></td>
																		<td><div class="checkbox price">
																				<label><input type="checkbox" value=""><input
																					name="ice_medium" type="text" class="form-control"></input></label>
																			</div></td>
																		<td><div class="checkbox price">
																				<label><input type="checkbox" value=""><input
																					name="ice_large" type="text" class="form-control"></input></label>
																			</div></td>
																		<td><div class="checkbox price">
																				<label><input type="checkbox" value=""><input
																					name="ice_none" type="text" class="form-control"></input></label>
																			</div></td>
																	</tr>
																	<tr>
																		<th scope="row">단일 메뉴</th>
																		<td><div class="checkbox price">
																				<label><input type="checkbox" value=""><input
																					name="none_small" type="text" class="form-control"></input></label>
																			</div></td>
																		<td><div class="checkbox price">
																				<label><input type="checkbox" value=""><input
																					name="none_medium" type="text" class="form-control"></input></label>
																			</div></td>
																		<td><div class="checkbox price">
																				<label><input type="checkbox" value=""><input
																					name="none_large" type="text" class="form-control"></input></label>
																			</div></td>
																		<td><div class="checkbox price">
																				<label><input type="checkbox" value=""><input
																					name="none_none" type="text" class="form-control"></input></label>
																			</div></td>
																	</tr>
																</tbody>
															</table>
														</div>
													</div>
												</div>
											</div>
											<div class="modal-footer">
												<button type="submit" class="btn btn-default btn-md"
													data-toggle="modal" data-target="#addMenu_complete">메뉴
													추가</button>
												<!--메뉴 추가 완료-->
												<div class="modal" id="addMenu_complete" role="dialog">
													<div class="modal-dialog modal-sm">
														<div class="modal-content">
															<div class="modal-body">
																<div class="row" style="text-align: center">
																	<b>메뉴 추가 완료!</b>
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
							<c:forEach var="cafe_menu" items="${cafe_menu}">
								<div class="row">
									<div class="col-lg-5">
										<button type="button"
											class="btn btn-default btn-round-lg btn-lg">
											<h4>
												<b> ${cafe_menu.menu_name} </b>
											</h4>
										</button>
									</div>

									<div class="col-lg-2">
										<button type="button" class="btn btn-default btn-lg"
											data-toggle="modal" data-target="#price-management">가격관리</button>
									</div>
									<!--가격 관리 팝업-->
									<div class="modal fade" id="price-management" role="dialog">
										<div class="modal-dialog modal-lg">
											<!--Modal content-->
											<div class="modal-content">
												<div class="modal-header">
													<button type="button" class="close" data-dismiss="modal">&times;</button>
													<h4 class="modal-title">가격 수정하기</h4>
												</div>

												<div class="modal-body">
													<div class="container-fluid">
														<!--가격 관리 표 삽입-->
														<div class="table">
															<table class="table">
																<thead>
																	<tr>
																		<th></th>
																		<th>Small</th>
																		<th>Medium</th>
																		<th>Large</th>
																	</tr>
																</thead>
																<tbody>
																	<tr>
																		<th scope="row">HOT</th>
																		<td>2,200</td>
																		<td>2,500</td>
																		<td>2,800</td>
																	</tr>
																	<tr>
																		<th scope="row">ICE</th>
																		<td>2,500</td>
																		<td>2,800</td>
																		<td>3,100</td>
																	</tr>
																</tbody>
															</table>
														</div>
													</div>
												</div>

												<div class="modal-footer" data-dismiss="modal">
													<button type="button" class="btn btn-default btn-md"
														data-toggle="modal">가격 수정</button>
													<button type="button" class="btn btn-danger btn-md"
														data-toggle="modal">취소</button>
												</div>
											</div>
										</div>
									</div>



									<div class="col-lg-2">
										<button type="button" class="btn btn-warning btn-lg"
											data-toggle="modal" data-target="#menu-name-edit">
											<span style="margin-right: 5px"
												class="glyphicon glyphicon-pencil"></span>이름 수정
										</button>
										<!--메뉴 이름수정하기 팝업-->
										<div class="modal fade" id="menu-name-edit" role="dialog">
											<div class="modal-dialog modal-sm">

												<!-- Modal content-->
												<div class="modal-content">

													<div class="modal-header">
														<button type="button" class="close" data-dismiss="modal">&times;</button>
														<h4 class="modal-title">메뉴 이름 수정하기</h4>
													</div>
													<div class="modal-body">
														<div class="container">
															<div class="row">
																<input class="form-control" style="max-width: 270px"
																	name="category-name" placeholder="메뉴 이름" type="text"
																	required autofocus />
															</div>
														</div>

													</div>
													<div class="modal-footer">
														<button type="button" class="btn btn-default btn-md"
															data-toggle="modal"
															data-target="#menu-name-edit-complete">메뉴 이름 수정</button>
														<!--메뉴 추가 완료-->
														<div class="modal" id="menu-name-edit-complete"
															role="dialog">
															<div class="modal-dialog modal-sm">
																<div class="modal-content">
																	<div class="modal-body">
																		<div class="row" style="text-align: center">
																			<b>메뉴 이름 수정 완료!</b>
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

												</div>

											</div>
										</div>
									</div>
									<div class="col-lg-2">
										<button type="button" class="btn btn-danger btn-lg"
											data-toggle="modal" data-target="#menu-delete">
											<span style="margin-right: 5px"
												class="glyphicon glyphicon-trash"></span>삭제
										</button>
										<!--메뉴 삭제하기 팝업-->
										<div class="modal fade" id="menu-delete" role="dialog">
											<div class="modal-dialog modal-sm">

												<!-- Modal content-->
												<div class="modal-content">

													<div class="modal-header">
														<button type="button" class="close" data-dismiss="modal">&times;</button>
														<h4 class="modal-title">메뉴 삭제하기</h4>
													</div>
													<div class="modal-body">
														<div class="container">
															<div class="row">메뉴를 삭제하시겠습니까?</div>
														</div>

													</div>
													<div class="modal-footer">
														<button type="button" class="btn btn-default btn-md"
															data-toggle="modal" data-target="#menu-delete-complete">삭제하기</button>
														<!--메뉴 삭제 완료-->
														<div class="modal" id="menu-delete-complete" role="dialog">
															<div class="modal-dialog modal-sm">
																<div class="modal-content">
																	<div class="modal-body">
																		<div class="row" style="text-align: center">
																			<b>메뉴 삭제 완료!</b>
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
	</div>


	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>

</html>