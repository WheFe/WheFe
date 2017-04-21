<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>

<head>
<title>메뉴관리 (로그인 후 첫 화면)</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<meta http-equiv="content-type" content="text/html; charset=utf-8" />

</head>
<style>
/*.navbar-nav {
  font-size: 20px;
 }*/
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
							<span class="glyphicon glyphicon-user"></span>&nbsp; ${pageContext.request.userPrincipal.name}
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
					<b>메뉴 관리 (카테고리)</b>
				</h4>
			</div>
			<div class="container">
				<div class="row">
					<div class="panel-body">
						<button type="button" class="btn btn-default btn-round-lg btn-lg"
							data-toggle="modal" data-target="#addCategory"
							style="border: 0; text-align: left">
							<b><span class="glyphicon glyphicon-plus-sign"></span>&nbsp;카테고리
								추가하기</b>
						</button>

						<!--카테고리 추가하기 팝업-->
						<div class="modal fade" id="addCategory" role="dialog">
							<div class="modal-dialog modal-sm">
								<!-- Modal content-->
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">&times;</button>
										<h4 class="modal-title">카테고리 추가하기</h4>
									</div>
									<form action="<c:url value="/management/addcategory"/>">
										<div class="modal-body">
											<div class="container">
												<div class="row">
													<input class="form-control" style="max-width: 270px"
														id="category_name" name="category_name"
														placeholder="카테고리 이름" type="text" required autofocus />
												</div>
											</div>
										</div>
										<div class="modal-footer">
											<button type="submit" class="btn btn-default btn-md"
												data-toggle="modal" data-target="#addCategory_complete">카테고리
												추가</button>
											<!--카테고리 추가 완료-->
											<div class="modal" id="addCategory_complete" role="dialog">
												<div class="modal-dialog modal-sm">
													<div class="modal-content">
														<div class="modal-body">
															<div class="row" style="text-align: center">
																<b>카테고리 추가 완료!</b>
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
						<c:forEach var="category" items="${categories}" varStatus="status">	
							<%-- categories[status.index].category_name :   ${categories[status.index].category_name} --%>
							<%-- ${status.count}
							${status.index }
							${category.category_name} --%>
							<div class="row">
								<div class="col-lg-5">
									<button type="button"
										onclick="location.href='<c:url value="/management/menu/${category.category_name}"/>'"
										class="btn btn-default btn-round-lg btn-lg">
										<h4>
											<b id="categoryValue">${category.category_name}</b>
										</h4>
									</button>
								</div>


								<div class="col-lg-2">
									<button type="button" class="btn btn-default btn-lg"
										onclick="location.href='<c:url value="/management/option/${category.category_name}"/>'">옵션관리</button>
								</div>


								<div class="col-lg-2">
									<button type="button" class="btn btn-warning btn-lg"
										data-toggle="modal" data-target="#category-name-edit">
										<span style="margin-right: 5px"
											class="glyphicon glyphicon-pencil"></span>이름 수정
									</button>


									<!--카테고리 이름수정하기 팝업-->
									<div class="modal fade" id="category-name-edit" role="dialog">
										<div class="modal-dialog modal-sm">

											<!-- Modal content-->
											<div class="modal-content">


												<div class="modal-header">
													<button type="button" class="close" data-dismiss="modal">&times;</button>
													<h4 class="modal-title">카테고리 이름 수정하기</h4>
												</div>
												<form
													action="<c:url value="/management/editcategory/${category.category_name}"/>">
													<div class="modal-body">
														<div class="container">
															<div class="row">

																<input class="form-control" style="max-width: 270px"
																	id="category_name" name="category_name"
																	placeholder="카테고리 이름" type="text"
																	value="${category.category_name}" required autofocus />
															</div>
														</div>
													</div>


													<div class="modal-footer">
														<button type="submit" class="btn btn-default btn-md"
															data-toggle="modal"
															data-target="#category-name-edit-complete">카테고리
															이름 수정</button>


														<!--카테고리 추가 완료-->
														<div class="modal" id="category-name-edit-complete"
															role="dialog">
															<div class="modal-dialog modal-sm">
																<div class="modal-content">
																	<div class="modal-body">
																		<div class="row" style="text-align: center">
																			<b>카테고리 이름 수정 완료!</b>
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
										data-toggle="modal" data-target="#category-delete">
										<span style="margin-right: 5px"
											class="glyphicon glyphicon-trash"></span>삭제
									</button>
									<!--카테고리 삭제하기 팝업-->
									<div class="modal fade" id="category-delete" role="dialog">
										<div class="modal-dialog modal-sm">
											<!-- Modal content-->
											<div class="modal-content">
												<div class="modal-header">
													<h4 class="modal-title">카테고리 삭제하기</h4>
												</div>
												<form
													action="<spring:url value="/management/deletecategory/${category.category_name}"/>">
													<div class="modal-body">
														<div class="container">
															<div class="row">카테고리를 삭제하시겠습니까?</div>
														</div>
													</div>
													<div class="modal-footer">
														<button type="submit" class="btn btn-default btn-md"
															data-toggle="modal"
															data-target="#category-delete-complete">삭제하기</button>
														<!--카테고리 삭제 완료-->
														<div class="modal" id="category-delete-complete"
															role="dialog">
															<div class="modal-dialog modal-sm">
																<div class="modal-content">
																	<div class="modal-body">
																		<div class="row" style="text-align: center">
																			<b>카테고리 삭제 완료!</b>
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