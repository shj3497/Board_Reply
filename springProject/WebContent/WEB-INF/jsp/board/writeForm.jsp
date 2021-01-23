<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기 화면</title>
<link rel="stylesheet" type="text/css" href="/include/css/board.css" />
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript" src="/include/js/common.js"></script>
<script type="text/javascript">

	$(document).ready(function(){
		/* 저장 버튼 클릭시 처리 이벤트 */
		$('#boardInsert').on('click',function(){
			// 입력값 체크 >> null이면 false를 반환하기로 했으니까 !false true로 된다.
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
				/* 배열내의 값을 찾아서 인덱스를 반환(요소가 없을 경우 -1반환) >> 없을경우 true가 되서 if()문 실행
				   jQuery.inArray(찾을 값, 검색 대상의 배열) */
				// .pop() 배열에서 마지막요소를 반환한다.
				// 파일 이름에서 .을 기준으로 split('.')함수를 사용하여 쪼개줬고, pop()함수로 마지막요소(확장자)를 반환한다.
				var ext = $('#file').val().split('.').pop().toLowerCase();
				if(jQuery.inArray(ext, ['gif','png','jpg','jpeg']) == -1){
					// 첨부파일은 이미지파일만 가능하도록 확장자에 대한 유효성 체크
					alert("gif, png, jpg, jpeg 파일만 업로드 할 수 있습니다.");
					return;
				}
				$('#f_writeForm').attr({
					"method":"POST",
					"action":"/board/boardInsert.shj"
				});
				$("#f_writeForm").submit();				
			}
			
		});
		
		/* 목록 버튼 클릭시 처리 이벤트 */
		$('#boardList').on('click',function(){
			location.href="/board/boardList";
		});
	});

</script>
</head>
<body>
	<div id="boardTit"><h3>글쓰기</h3></div>
	<form id="f_writeForm" name="f_writeForm" enctype="multipart/form-data">
		<table id="boardWrite">
			<tr>
				<td>작성자</td>
				<td><input type="text" name="b_name" id="b_name"></td>
			</tr>
			<tr>
				<td>글제목</td>
				<td><input type="text" name="b_title" id="b_title"></td>
			</tr>
			<tr>
				<td>내용</td>
				<td>
					<textarea name="b_content" id="b_content" rows="10" cols="70"></textarea>
				</td>
			</tr>
			<tr>
				<td>첨부파일</td>
				<td><input type="file" name="file" id="file"></td><!-- name="file", id="file" -->
			</tr>
			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="b_pwd" id="b_pwd"></td>
			</tr>
		</table>
	</form>
	<div id="boardBut">
		<input type="button" value="저장" class="but" id="boardInsert">
		<input type="button" value="목록" class="but" id="boardList">
	</div>
</body>
</html>