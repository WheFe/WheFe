<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<title>주문 확인</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
</head>
<style>
.btn-round {
	border-radius: 17px;
}

.btn-round-sm {
	border-radius: 15px;
}

.well-large {
	height: 150px;
	width: 800px;
	border-radius: 17px;
}

.coupon-expired-custom {
	background: #F5F5F5;
}

.btn-huge {
	padding-top: 20px;
	padding-bottom: 20px;
}

.panel-outer {
	/*min-width: 200px;*/
	height: 480px;
	/*overflow-y: scroll;*/
}
/*
 .panel-inner {
  width: 815px;
  height: 400px;
  overflow-y: scroll;
 }*/
.ready-button {
	background: #337AB7;
	color: white;
	font-size: 15px;
	width: 100px;
	height: 50px;
	text-align: center;
	margin-top: 10px;
	position: absolute;
}

#congestion-box {
	top: 330px;
	width: 120px;
	height: 60px;
	margin-left: 20px;
	background-color: green;
	color: white;
	text-align: center;
	position: absolute;
}

body {
	background: url('http://i67.tinypic.com/2ldeel3.jpg') fixed;
}

.panel {
	/*opacity:0.7;*/
	background-color: #00ff0000;
}

.panel>.panel-body {
	/*opacity : 0.5;*/
	/*background-color:black;*/
	
}

.panel>.panel-heading {
	background-image: none;
	background-color: #222222;
	/*background-color : black;*/
	color: white;
	opacity: 1.0;
}
</style>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#myNavbar">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="<c:url value='/management'/>">Grazie</a>
			</div>
			<div class="collapse navbar-collapse" id="myNavbar">
				<ul class="nav navbar-nav">
					<li><a href="<c:url value="/management"/>">메뉴관리</a></li>
					<li><a href="<c:url value="/management/coupon"/>">쿠폰관리</a></li>
					<li class="active"><a href="#">주문확인</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><div class="user-info"
							style="margin-top: 15px; color: #636363">
							<a
								href="<c:url value="/cafeinfo?cafe_id=${pageContext.request.userPrincipal.name}"/>">
								<span class="glyphicon glyphicon-user"></span>&nbsp;
								${pageContext.request.userPrincipal.name}
							</a>
						</div></li>
					<li><a href="<c:url value="/logout"/>"><span
							class="glyphicon glyphicon-log-out"></span> 로그아웃</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<div class="container-fluid" style="margin-top: 80px">
		<div class="panel panel-default panel-outer">
			<div class="panel-heading">
				<h4>
					<b>주문확인
						<h4>
					</b>
			</div>
			<div class="panel-body">
				<div id="panel-body">
					<div class="row">
						<!-- 혼잡도 박스-->
						<div class="col-lg-1">
							<div class="panel panel-primary" id="congestion-box">
								<h3>&nbsp;${cafe_info.cafe_curr }&nbsp;&nbsp;/&nbsp;&nbsp;${cafe_info.cafe_max }&nbsp;</h3>
							</div>
						</div>
						<!-- 주문 목록-->
						<div class="col-lg-offset-1 col-lg-5">
							<h3>
								<div style="text-align: center">주문 목록</div>
							</h3>
							<div class="panel panel-default panel-inner"
								style="height: 330px; overflow-y: scroll">
								<div class="container-fluid">
									<div class="row">
										<c:forEach var="order" items="${orders}" varStatus="status">
											<div class="panel panel-default coupon-expired-custom">
												<div class="panel-body">
													<!--주문 한개-->
													<div class="row">
														<div class="col-md-8">
															<div class="text-left">
																<h5>
																	<ul>
																		<li>메뉴명 : ${order.menu_name}
																			(${order.hotIceNone})
																		<li>사이즈 : ${order.menu_size }
																		<li>추가 옵션 : ${order.option_info }
																		<li>고객ID : ${order.customer_id }
																	</ul>
																</h5>
															</div>
														</div>
														<div class="col-md-4">
															<a
																href="<spring:url value="/management/order/${order.orderlist_id}"/>">
																<button type="button"
																	class="btn btn-default btn-lg btn-huge btn-block ready-button">메뉴
																	완성</button>
															</a>
														</div>
													</div>
													<!--주문 한개 끝-->
												</div>
											</div>
										</c:forEach>
									</div>
								</div>
							</div>
						</div>
						<!-- 완성 목록-->
						<div class="col-lg-5">
							<h3>
								<div style="text-align: center">완성 메뉴</div>
							</h3>
							<div class="panel panel-default panel-inner"
								style="height: 330px; overflow-y: scroll;">
								<div class="container-fluid">
									<div class="row">
										<c:forEach var="completeOrder" items="${completeOrders}"
											varStatus="status">
											<div class="panel panel-default coupon-expired-custom">
												<div class="panel-body">
													<!--주문 한개-->
													<div class="row">
														<div class="col-md-8">
															<div class="text-left">
																<h5>
																	<ul>
																		<li>메뉴명 : ${completeOrder.menu_name}
																			(${completeOrder.hotIceNone})
																		<li>사이즈 : ${completeOrder.menu_size }
																		<li>추가 옵션 : ${completeOrder.option_info }
																		<li>고객ID : ${completeOrder.customer_id }
																	</ul>
																</h5>
															</div>
														</div>
														<div class="col-md-4">
															<a
																href="<spring:url value="/management/order/deleteOrder/${completeOrder.orderlist_id}"/>">
																<button type="button"
																	class="btn btn-default btn-lg btn-huge btn-block ready-button">수령
																	확인</button>
															</a>
														</div>
													</div>
													<!--주문 한개 끝-->
												</div>
											</div>
										</c:forEach>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<script language="javascript">
				function autoRefresh_div() {
					var currentLocation = window.location;
					$("#panel-body").load(currentLocation + ' #panel-body');
				}
				setInterval('autoRefresh_div()', 3000);
				
				function autoRefresh_div2() {
					var currentLocation = window.location;
					$("#congestion-box").load(currentLocation + ' #congestion-box');
				}
				setInterval('autoRefresh_div2()', 3000);
			</script>
		</div>
	</div>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>