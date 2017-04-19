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
								id="category_name" name="category_name" placeholder="카테고리 이름"
								type="text" value="${category.category_name}" required autofocus />
						</div>
					</div>
				</div>


				<div class="modal-footer">
					<button type="submit" class="btn btn-default btn-md"
						data-toggle="modal" data-target="#category-name-edit-complete">카테고리
						이름 수정</button>


					<!--카테고리 추가 완료-->
					<div class="modal" id="category-name-edit-complete" role="dialog">
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