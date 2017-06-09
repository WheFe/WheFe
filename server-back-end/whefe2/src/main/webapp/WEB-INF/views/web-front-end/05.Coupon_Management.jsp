<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
<title>쿠폰 관리</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
</head>
<style>
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

.coupon-active-custom {
	background: #D9EDF7;
	border-radius: 17px;
	max-width: 1000px;
}

.coupon-expired-custom {
	background: #F5F5F5;
	border-radius: 17px;
	max-width: 1000px;
}

.coupon-expired-button {
	min-width: 220px;
	min-height: 70px;
	margin-top: 20px;
}

.btn-huge {
	padding-top: 20px;
	padding-bottom: 20px;
}

.coupon {
	margin-left: 55px;
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
					<li class="active"><a href="#">쿠폰관리</a></li>
					<li><a href="<c:url value="/management/order"/>">주문확인</a></li>
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

	<div class="container" style="margin-top: 80px">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4>
					<b>쿠폰 관리</b>
				</h4>
			</div>

			<div class="container">
				<!-- body 부분 -->
				<div class="row">
					<div class="panel-body">
						<button type="button" class="btn btn-default btn-round-lg btn-lg"
							data-toggle="modal" data-target="#addCoupon"
							style="border: 0; text-align: left">
							<h4>
								<span class="glyphicon glyphicon-plus-sign"></span><b>&nbsp;쿠폰
									추가하기</b>
							</h4>
						</button>

						<!--쿠폰 추가하기 팝업-->
						<div class="modal fade" id="addCoupon" role="dialog">
							<div class="modal-dialog modal-lg">

								<!-- Modal content-->
								<div class="modal-content">

									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">&times;</button>
										<h4 class="modal-title">쿠폰 추가하기</h4>
									</div>
									<form action="<c:url value="/management/coupon/addcoupon"/>">
										<input type="hidden" name="cafe_id"
											value="${pageContext.request.userPrincipal.name}" /> <input
											type="hidden" name="use_ox" value="1" />
										<div class="modal-body">
											<div class="container">
												<div class="row">
													<input class="form-control"
														style="max-width: 870px; margin-top: 10px"
														id="coupon_name" name="coupon_name" placeholder="쿠폰 이름"
														type="text" required autofocus />
												</div>
												<div class="row">
													<input class="form-control"
														style="max-width: 870px; margin-top: 10px"
														id="coupon_start" name="coupon_start"
														placeholder="쿠폰 시작기간 예)2017-06-01" type="text">
												</div>
												<div class="row">
													<input class="form-control"
														style="max-width: 870px; margin-top: 10px" id="coupon_end"
														name="coupon_end" placeholder="쿠폰 종료기간 예)2017-06-05" type="text">
												</div>
												<div class="row">
													<input class="form-control"
														style="max-width: 870px; margin-top: 10px"
														id="coupon_price" name="coupon_price" placeholder="할인금액"
														type="text">
												</div>

											</div>

										</div>
										<div class="modal-footer">
											<button type="submit" class="btn btn-default btn-md"
												data-toggle="modal" data-target="#addCoupon_complete">쿠폰
												추가</button>
											<!--쿠폰 추가 완료-->
											<div class="modal" id="addCoupon_complete" role="dialog">
												<div class="modal-dialog modal-sm">
													<div class="modal-content">
														<div class="modal-body">
															<div class="row" style="text-align: center">
																<b>쿠폰 추가 완료!</b>
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
				<br /> <br />

				<c:forEach var="coupon" items="${coupons}" varStatus="status">
					<div class="row coupon">
						<div class="panel panel-default coupon-expired-custom">
							<form
								action="<c:url value="/management/coupon/deletecoupon/${coupon.coupon_name}"/>">
								<input type="hidden" name="cafe_id"
									value="${pageContext.request.userPrincipal.name}" />
								<div class="panel-body">
									<div class="row">
										<div class="col-md-9">
											<div class="text-left">
												<h3>
													<ul>
														<li>쿠폰명 : ${coupon.coupon_name}
														<li>기간 : ${coupon.coupon_start} ~
															${coupon.coupon_end}
														<li>할인금액 : ${coupon.coupon_price}
													</ul>
												</h3>
											</div>
										</div>
										<div class="col-md-3">
											<button type="submit"
												class="btn btn-danger btn-lg btn-huge btn-block coupon-expired-button">
												<span style="margin-bottom: 20px margin-top:20px"
													class="glyphicon glyphicon-trash"></span>&nbsp;삭제
											</button>
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>
				</c:forEach>

			</div>
			<!--body 종료-->
		</div>
	</div>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>

</html>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
 <title>쿠폰 관리</title>
 <meta name="viewport" content="width=device-width, initial-scale=1">
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
 <meta http-equiv="content-type" content="text/html; charset=utf-8" />
</head>
<style>
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
 .coupon-active-custom {
  background: #D9EDF7;
  border-radius: 17px;
  max-width: 1000px;
 }
 .coupon-expired-custom {
  background: #F5F5F5;
  border-radius: 17px;
  max-width: 1000px;
 }
 .coupon-expired-button {
  min-width: 220px;
  min-height: 70px;
  margin-top: 20px;
 }
 .btn-huge {
  padding-top: 20px;
  padding-bottom: 20px;
 }
 .coupon {
  margin-left: 55px;
 }
</style>
<body>
 <nav class="navbar navbar-default navbar-fixed-top">
  <div class="container-fluid">
   <div class="navbar-header">
    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
         <span class="icon-bar"></span>
         <span class="icon-bar"></span>
         <span class="icon-bar"></span>
       </button>
    <a class="navbar-brand" href="#">WheFe</a>
   </div>
   <div class="collapse navbar-collapse" id="myNavbar">
    <ul class="nav navbar-nav">
     <li><a href="<c:url value="/management"/>">메뉴관리</a></li>
     <li class="active"><a href="#">쿠폰관리</a></li>
     <li><a href="<c:url value="/management/order"/>">주문확인</a></li>
    </ul>
    <ul class="nav navbar-nav navbar-right">
     <li><a href="#"><span class="glyphicon glyphicon-log-out"></span> 로그아웃</a></li>
    </ul>
   </div>
  </div>
 </nav>
 <div class="container" style="margin-top:80px">
  <div class="panel panel-default">
   <div class="panel-heading">
    <h4><b>메뉴 관리</b></h4>
   </div>
   <div class="container">
    <!-- body 부분 -->
    <div class="row">
     <div class="panel-body"><button type="button" class="btn btn-default btn-round-lg btn-lg" data-toggle="modal" data-target="#addCoupon" style="border:0; text-align:left"><h4><span class="glyphicon glyphicon-plus-sign"></span><b>&nbsp;쿠폰 추가하기</b></h4></button>
      <!--쿠폰 추가하기 팝업-->
      <div class="modal fade" id="addCoupon" role="dialog">
       <div class="modal-dialog modal-lg">
        <!-- Modal content-->
        <div class="modal-content">
         <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">쿠폰 추가하기</h4>
         </div>
         <div class="modal-body">
          <div class="container">
           <div class="row">
            <input class="form-control" style="max-width: 870px; margin-top:10px" name="category-name" placeholder="쿠폰 이름" type="text" required autofocus />
           </div>
           <div class="row">
            <input class="form-control" style="max-width: 870px; margin-top:10px" name="category-name" placeholder="쿠폰 시작기간" type="text">
           </div>
           <div class="row">
            <input class="form-control" style="max-width: 870px; margin-top:10px" name="category-name" placeholder="쿠폰 종료기간" type="text">
           </div>
           <div class="row">
            <input class="form-control" style="max-width: 870px; margin-top:10px" name="category-name" placeholder="할인금액" type="text">
           </div>
          </div>
         </div>
         <div class="modal-footer">
          <button type="button" class="btn btn-default btn-md" data-toggle="modal" data-target="#addCoupon_complete">쿠폰 추가</button>
          <!--쿠폰 추가 완료-->
          <div class="modal" id="addCoupon_complete" role="dialog">
           <div class="modal-dialog modal-sm">
            <div class="modal-content">
             <div class="modal-body">
              <div class="row" style="text-align:center">
               <b>쿠폰 추가 완료!</b>
              </div>
             </div>
             <div class="modal-footer" data-dismiss="modal">
              <button type="button" class="btn btn-default btn-md">확인</button>
             </div>
            </div>
           </div>
          </div>
          <button type="button" class="btn btn-danger btn-md" data-dismiss="modal">취소</button>
         </div>
        </div>
       </div>
      </div>
     </div>
    </div>
    <br /><br />
    <div class="row coupon">
     <div class="panel panel-default coupon-expired-custom">
      <div class="panel-body">
       <div class="row">
        <div class="col-md-9">
         <div class="text-left">
          <h3>
     <ul>
       <li>쿠폰명 : 송승민 생일 기념 쿠폰
       <li>기간 : 2017년 5월 3일 ~ 2017년 5월 7일
       <li>할인금액 : 500원
     </ul></h3>
         </div>
        </div>
        <div class="col-md-3">
         <button type="button" class="btn btn-danger btn-lg btn-huge btn-block coupon-expired-button"><span style="margin-bottom:20px margin-top:20px" class="glyphicon glyphicon-trash"></span>&nbsp;기간만료 쿠폰 삭제</button>
        </div>
       </div>
      </div>
     </div>
    </div>
    <div class="row coupon">
     <div class="panel panel-default coupon-active-custom">
      <div class="panel-body">
       <div class="row">
        <div class="col-md-9">
         <div class="text-left">
          <h3>
     <ul>
       <li>쿠폰명 : 카페 오픈 100일 기념 쿠폰
       <li>기간 : 2017년 5월 3일 ~ 2017년 6월 2일
       <li>할인금액 : 500원
     </ul></h3>
         </div>
        </div>
        <div class="col-md-3">
         <!-- 기간이 만료될 경우 삭제 버튼 삽입 -->
        </div>
       </div>
      </div>
     </div>
    </div>
   </div>
   <!--body 종료-->
  </div>
 </div>
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
 <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html> --%>