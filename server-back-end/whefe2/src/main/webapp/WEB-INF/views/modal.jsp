<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
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
									id="category_name" name="category_name" placeholder="카테고리 이름"
									type="text" required autofocus />
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
</body>
</html>