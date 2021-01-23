<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximun-scale=1.0, 
								   minimum-scale=1.0, user-scalable=no" />
	<meta http-equiv="X-UA-Compatible" content="IE=EDGE, chrome=1" />
	<title>COMMENT</title>
	<link rel="stylesheet" type="text/css" href="/include/css/reply.css" />
	<script type="text/javascript" src="/include/js/common.js"></script>
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
	<script type="text/javascript">
	
		$(document).ready(function(){
			/* 기본 댓글 목록 불러오기 */
			var b_num = "<c:out value='${detail.b_num}' />"; 
			// c:out 태그 >> 변수를 가져온다. 
			// boardDetail.jsp 과 reply.jsp는 한페이제 보여지지만 물리적으로는 다른 페이지라 변수를 불러올 때 이렇게 써줘야 불러오는건가?
			listAll(b_num)
			
			/* *댓글 내용 저장 이벤트*/
			$('#replyInsert').on('click',function(){
				// 작성자 이름에 대한 입력 여부 검사
				if(!chkSubmit($("#r_name"),"이름을")){
					return;
				}else if(!chkSubmit($("#r_pwd"),"패스워드를")){
					return;
				}else if(!chkSubmit($("#r_content"),"내용을")){
					return;
				}else{
					
					var insertUrl = "/replies/replyInsert.shj";
					/* 글 저장을 위한 POST방식의 Ajax 연동처리 */
					$.ajax({
						url : insertUrl,
						type : "POST",
						headers :{
							"Content-Type":"application/json",
							"X-HTTP-Method-Override":"POST"
						},
						dataType : "text",
						data : JSON.stringify({
							b_num:b_num,
							r_name:$('#r_name').val(),
							r_pwd:$("#r_pwd").val(),
							r_content:$('#r_content').val()
						}),
						error : function(){
							alert("시스템 오류입니다. 관리자에게 문의하세요");
						},
						success : function(res){
							if(res == "SUCCESS"){
								alert("댓글이 등록되었습니다.");
								dataReset();
								
								listAll(b_num); // 댓글이 등록되었기 때문에 초기화해서 다시 화면에 출력
							}
						}
						
					}); // end of ajax
				} // end of else
				
			}); // replyInsert
			
			// on() 함수 : 동적으로 추가할 요소에 대한 이벤트를 미리 정의해 놓는 함수이다.
			// 새로 추가될 요소의 이벤트 이기때문에 이 이벤트가 정의되는 시점에서는 대상이 존재하지 않을 것이기 때문에
			// 이 이벤트는 document 객체에 설정해야한다.
			/* 수정 버튼 클릭시 수정 폼 출력 */
			$(document).on('click', '.update_form', function(){
				$(".reset_btn").click();
				var conText = $(this).parents("li").children().eq(1).html();
				
				console.log("conText >>> : " + conText);
				$(this).parents("li").find("input[type='button']").hide();
				$(this).parents("li").children().eq(0).html();
				
				var conArea = $(this).parents("li").children().eq(1);
				
				conArea.html("");
				
				var data = "<textarea name='content' id='content'>"+conText+"</textarea>";
				data += "<input type='button' class='update_btn' value='수정완료'>";
				data += "<input type='button' class='reset_btn' value='수정취소'>";
				
				conArea.html(data);
			});
			
			/* 초기화 버튼 */
			$(document).on('click', '.reset_btn', function(){
				var conText = $(this).parents("li").find("textarea").html();
				$(this).parents("li").find("input[type='button']").show();
				var conArea = $(this).parents("li").children().eq(1);
				conArea.html(conText);
			})
			
			/* 글 수정을 위한 Ajax 연동 처리 */
			$(document).on('click','.update_btn', function(){
				var r_num = $(this).parents("li").attr('data-num');
				var r_content = $('#content').val();
				if(!chkSubmit($('#content'), "댓글 내용을")){
					return;
				}else{
					
					$.ajax({
						url:'/replies.'+r_num+".shj",
						type:'PUT',
						headers:{
							"Content-type":"application/json",
							"X-HTTP-Method-Override":"PUT"
						},
						data:JSON.Stringify({
							r_content:r_content
						}),
						dataType:'text',
						success:function(result){
							console.log("result >>> : " + result);
							if(result == "SUCCESS"){
								alert("수정 되었습니다.");
								listAll(b_num);
							}
						}
					});
				}
			}); // end of .update_btn

			/* 글 삭제를 위한 Ajax 연동처리 */
			$(document).on('click','.delete_btn', function(){
				var r_num = $(this).parents("li").attr("data-num");
				
				console.log("r_num: " + r_num);
				
				if (confirm("선택하신 댓글을 삭제하시겠습니까?")) {
					
					$.ajax({
						type : 'delete',
						url : '/replies/' + r_num+".do",
						headers : {
							"Content-Type" : "application/json",
							"X-HTTP-Method-Override" : "DELETE"
						},
					dataType : 'text',
					success : function(result) {
						console.log("result: " + result);
						if (result == 'SUCCESS') {
							alert("삭제 되었습니다.");
							listAll(b_num);
							}
						}	
					});
				}
			}); // end of .delete_btn
			
			
		}); // end of document ready()
	
		// 리스트 요청함수
		function listAll(b_num){
			$('#comment_list').html(""); //이름을/ 동적요소가 들어갈 ul태그의 id값
			
			var url = "/replies/all/" + b_num + ".shj";
			$.getJSON(url, function(data){
				
				console.log(data.length);
				
				$(data).each(function(){
					var r_num = this.r_num;
					var r_name = this.r_name;
					var r_content = this.r_content;
					var r_date = this.r_date;
					addNewItem(r_num, r_name, r_content, r_date);
				});
			}).fail(function(){
				alert("댓글 목록을 불러오는데 실패 하였습니다. 잠시후 다시 시도해 주세요")
			})
		}// end of listAll()
		
		/* 새로운 글을 화면에 추가하기 위한 함수 */
		function addNewItem(r_num, r_name, r_content, r_date){
			// 새로운 글이 추갈 li 태그 객체
			var new_li = $("<li>");
			new_li.attr("data-num", r_num);
			new_li.addClass("comment_item");
			
			// 작성자 정보가 지정될 <p> 태그
			var writer_p = $("<p>");
			writer_p.addClass("writer");
			
			// 작성자 정보의 이름
			var name_span = $("<span>");
			name_span.addClass("name");
			name_span.html(r_name + "님");
			
			// 작성일시
			var date_span = $("<span>");
			date_span.html(" / " + r_date + " ");
			
			// 수정하기 버튼
			var up_input = $("<input>");
			up_input.attr({"type" : "button", "value" : "수정하기"});
			up_input.addClass("update_form");
			
			// 삭제하기 버튼
			var del_input = $("<input>");
			del_input.attr({"type" : "button", "value" : "삭제하기"});
			del_input.addClass("delete_btn");
			
			// 내용
			var content_p = $("<p>");
			content_p.addClass("con");
			content_p.html(r_content);
			
			// 조립하기
			writer_p.append(name_span).append(date_span).append(up_input).append(del_input)
			new_li.append(writer_p).append(content_p);
			$("#comment_list").append(new_li);

		} // addNewItem()
		
		/* INPUT 태그들에 대한 초기화 함수 */
		function dataReset(){
			$('#r_name').val("");
			$('#r_pwd').val('');
			$('#r_content').val('');
		}
		
		
	</script>
	</head>
	<body>
		<div id="replyContainer">
			<h1></h1>
			<div id="comment_write">
				<form id="comment_form">
					<div>
						<label for="r_name">작성자</label> <!-- for?? -->
						<input type="text" name="r_name" id="r_name">
						<label for="r_pwd">비밀번호</label>
						<input type="password" name="r_pwd" id="r_pwd">
						<input type="button" id="replyInsert" value="저장하기"	>
					</div>
					<div>
						<label for="r_content">댓글 내용</label>
						<textarea name="r_content" id="r_content"></textarea>
					</div>
				</form>
			</div>
			<ul id="comment_list">
				<!-- 여기에 동적 생성요소가 들어가게됩니다. -->
			</ul>
		</div>
	</body>
</html>