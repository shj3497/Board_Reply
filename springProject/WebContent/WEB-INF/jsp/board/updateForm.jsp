<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 수정 화면</title>
<link rel="stylesheet" type="text/css" href="/include/css/board.css" />
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript" src="/include/js/common.js"></script>
<script type="text/javascript">

	$(document).ready(function(){
		/* 수정 버튼 클릭시 처리 이벤트 */
		$("#boardUpdate").on('click',function(){
			// 입력값 체크
			if(!chkSubmit($('#b_name'), "이름을")){
				return;
			}else if(!chkSubmit($('#b_title'), "제목을")){
				return;
			}else if(!chkSubmit($('#b_content'), "작성할 내용을")){
				return;
			}else if(!chkSubmit($('#file'), "첨부 파일을")){
				return;
			}else if(!chkSubmit($('#b_pwd'), "비밀번호를")){
				return;
			}else{
				if($("#file").val().indexOf(".") > -1){
					var ext = $('#file').val().split(".").pop().toLowerCase();
					if(jQuery.inArray(ext, ['gif','png','jpg','jpeg']) == -1){
						alert("gif, png, jpg, jpeg 파일만 업로드 할 수 있습니다.");
						return;
					}
				}
				
				console.log("기본파일명 : " + $('#b_file').val());
				$('#f_writeForm').attr({
					"method":"POST",
					"action":"/board/boardUpdate.shj"
				});
				$('#f_writeForm').submit();
				
			}
		});// end of boardUpdate
		
		/* 목록 법튼 클릭시 처리 이벤트*/
		$('#boardList').on('click',function(){
			location.href="/board/boardList.shj";
		});
		
	});

</script>
</head>
<body>
	<div id="boardTit"><h3>글수정</h3></div>
	<form id="f_writeForm" name="f_writeForm" enctype="multipart/form-data">
		<input type="hidden" id="b_num" name="b_num" value="${updateData.b_num }">
		<input type="hidden" id="b_file" name="b_file" value="${updateData.b_file }">
		<table id="boardWrite">
			<tr>
				<td>작성자</td>
				<td><input type="text" name="b_name" id="b_name" value="${updateData.b_name }"></td>
			</tr>
			<tr>
				<td>글제목</td>
				<td><input type="text" name="b_title" id="b_title" value="${updateData.b_title }"></td>
			</tr>
			<tr>
				<td>내용</td>
				<td><textarea name="b_content" id="b_content" rows="10" cols="70">${updateData.b_content}</textarea></td>
			</tr>
			<tr>
				<td>첨부파일</td>
				<td><input type="file" name="file" id="file"></td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td>
					<input type="password" name="b_pwd" id="b_pwd">
					<label>수정할 비밀번호를 입력해주세요</label>
				</td>
			</tr>
		</table>
	</form>
	<!-- 버튼은 form태그에 안넣는구만 -->
	<div id="boardBut">
		<input type="button" value="수정" class="but" id="boardUpdate">
		<input type="button" value="목록" class="but" id="boardList">
	</div>
</body>
</html>