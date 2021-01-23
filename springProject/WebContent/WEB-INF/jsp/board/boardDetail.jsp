<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 상세 보기</title>
	<link rel="stylesheet" type="text/css" href="/include/css/board.css" />
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script type="text/javascript" src="/include/js/common.js"></script>
	<script type="text/javascript">
	
		var butChk = 0; // 수정버튼과 삭제버튼을 구별하기 위한 변수
		
		$(document).ready(function(){
			
			$('#pwdChk').hide(); // 버튼을 숨깁니다.
			
			/* 첨부파일 이미지 보여주기 위한 속성 추가 */
			var file = "<c:out value='${detail.b_file}' />";
			if(file!=""){
				$('#fileImage').attr({
					src:"/uploadStorage/${detail.b_file}",
					width:"450px",
					height:"200px"
				});
			}
			
			/* 수정 버튼 클릭시 처리 이벤트 */
			$('#updateForm').on('click',function(){
				$('#pwdChk').show(); // 버튼을 보여줍니다.
				$('#msg').text("작성시 입력한 비밀번호를 입력해 주세요.").css("color","#000099");
				
				butChk = 1;
			});
			
			/* 삭제 버튼 클릭시 처리 이벤트 */
			$('#deleteForm').on('click',function(){
				$('#pwdChk').show(); // 버튼을 보여줍니다.
				$('#msg').text("작성시 입력한 비밀번호를 입력해 주세요.").css("color","#000099");
				
				butChk = 2;
			});
			
			/* 비밀번호 확인 버튼 클릭시 처리 이벤트 */
			$("#pwdBut").on('click',function(){
				pwdConfirm();
			});
			
			/* 목록 버튼 클릭시 처리 이벤트*/
			$('#boardList').on('click',function(){
				location.href="/board/boardList.shj";
			});
			
		});
		
		function pwdConfirm(){
			if(!chkSubmit($('#b_pwd'),"비밀번호를")){
				return;
			}else{
				
				let pwdURL = "/board/pwdConfirm.shj";
				let method = "POST";
				
				/*let param = {
					b_pwd : $('#b_pwd').val(),
					b_num : $('#b_num').val()
				};*/
				
				/*  .serialize() : 입력된 모든 Elements을 문자열의 데이터에 serialize 한다.
				.serialize()를 이용하면 일일이 값을 입력하지 않아도 된다.
				*/
				$.ajax({
					url : pwdURL,
					type : method,
					data : $('#f_pwd').serialize(),
					success : whenSuccess,
					error : whenError
				})
				
				function whenSuccess(resData){
					var goUrl = "";
					if(resData == 0){
						$('#msg').text("작성시 입력한 비밀번호가 일치하지 않습니다.").css("color",'red');
						$('#b_pwd').select(); // .select() : 
					}else if(resData == 1){ // 일치한 경우
						$('#msg').text("");
						if(butChk==1){
							goUrl = "/board/updateForm.shj";
						}else if(butChk==2){
							goUrl = "/board/boardDelete.shj";
						}
						$('#f_data').attr("action",goUrl);
						$('#f_data').submit();
					}
				} 
				function whenError(e){
					alert("시스템 오류입니다. 관리자에게 문의하세요" + e);
				}
				
			}
		}// end of pwdConfirm()
	
	
	</script>
</head>
<body>
	<div id="boardTit"><h3>글 상세</h3></div>
	<form name="f_data" id="f_data" method="POST">
		<input type="hidden" name="b_num" value="${detail.b_num}">
		<input type="hidden" name="b_file" id="b_file" value="${detail.b_file}">
	</form>
	<%-- === 비밀번호 확인 버튼 및 버튼  추가 시작 === --%>
	<table id="boardPwdBut">
		<tr>
			<td id="btd1"><!-- 호오.. 한행에만 form태그를 사용하여 데이터를 넘길 수도 있네. 꼭 데이터를 넘기는 기준이 페이지일 필요는 없네 -->
				<div id="pwdChk">
					<form name="f_pwd" id="f_pwd">
						<input type="hidden" name="b_num" id="b_num" value="${detail.b_num}">
						<label for="b_pwd" id="l_pwd">비밀번호 : </label>
						<input type="password" name="b_pwd" id="b_pwd">
						<input type="button" id="pwdBut" value="확인">
						<span id="msg"></span>
					</form>
				</div>
			</td>
			<td id="btd2">
				<input type="button" value="수정" id="updateForm">
				<input type="button" value="삭제" id="deleteForm">
				<input type="button" value="목록" id="boardList">
			</td>
		</tr>
	</table>
	<%-- === 비밀번호 확인 버튼 및 버튼 추가 종료 === --%>
	<%-- === 상세 정보 보여주기 시작 === --%>
	<div id="boardDetail">
		<table>
			<colgroup>
				<col width="25%" />
				<col width="25%" />
				<col width="25%" />
				<col width="25%" />
			</colgroup>
			<tbody>
				<tr>
					<td class="ac">작성자</td>
					<td>${detail.b_name}</td>
					<td class="ac">작성일</td>
					<td>${detail.b_updatedate}</td>
				</tr>
				<tr>
					<td class="ac">제목</td>
					<td colspan="3">${detail.b_title}</td>
				</tr>
				<tr class="ctr">
					<td class="ac">내용</td>
					<td colspan="3">${detail.b_content}</td>
				</tr>
				<tr class="ctr">
					<td class="ac">첨부파일 파일</td>
					<td colspan="3"><img id="fileImage" /></td>
				</tr>
			</tbody>
		</table>
	</div>	
	<%-- === 상세 정보 보여주기 종료 === --%>
	<jsp:include page="reply.jsp"></jsp:include>
</body>
</html>