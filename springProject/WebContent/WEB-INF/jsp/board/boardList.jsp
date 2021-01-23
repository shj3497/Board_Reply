<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tag" uri="/WEB-INF/tld/custom_tag.tld" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 목록</title>
<link rel="stylesheet" type="text/css" href="/include/css/board.css" />
<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="/include/js/common.js"></script>
<script type="text/javascript">

	// 주석에도 c태그를 달면 에러가남
	$(document).ready(function(){
		
		/* 검색 후 검색 대상과 검색 단어 출력 */
		if("<c:out value = '${data.keyword}' />" != ""){ // c:out은 문자열로 만드는것 >> ${data.keyword} != "" 과 같은 의미인듯
			$('#keyword').val("<c:out value = '${data.keyword}'/>"); // 여기도 c:out태그로 val()안을 문자열로 만들어버림
			$('#search').val("<c:out value='${data.search}' />");
		}
		
		/* 한 페이지에 보여줄 레코드 수 조회 후 선택한 값 그대로 유지하기위한 설정 */
		if("<c:out value='${data.pageSize}' />" != ""){
			$('#pageSize').val("<c:out value='${data.pageSize}' />");
		}
		
		
		/* 검색 대상이 변경될 때마다 처리 이벤트 */
		$('#search').on('change',function(){
			if($("#search").val() == "all"){
				$('#keyword').val('전체 데이터 조회합니다.');
			}else if($('#search').val() != "all"){
				$('#keyword').val(''); // search가 all이 아닌경우 왜 keyword의 val()을 널로하는거지..
				$('#keyword').focus();
			}
		});
		
		/* 검색 버튼 클릭시 처리 이벤트 */
		$('#searchData').on('click',function(){
			if($('#search').val() != "all"){
				if(!chkSubmit($('#keyword'), "검색어를")){
					return;
				}
				
				goPage(1); // 검색버튼 클릭시 검색의 결과를 다시 1페이지 부터 보여준다는 소리
			}
		});
		
		/* 한 페이지에 보여줄 레코드 수 변경될 때 마다 처리 이벤트 */
		$('#pageSize').on('change',function(){
			goPage(1);
		})
		
		
		/* 글쓰기 버튼 클릭시 처리 이벤트*/
		$('#writeForm').on('click',function(){
			location.href="/board/writeForm.shj";
		});
		
		/* 제목 클릭시 상세 페이지 이동을 위한 처리 이벤트 */
		// 제목의 id값은 중복될테니까 생성시 공통된 class명으로 지정을 해주고 class속성에 이벤트를 넣어준다. 
		$('.goDetail').on('click',function(){
			// b_num = 제목의 부모 태그인 tr의 속성 data-num의 val 값 = ${board.b_num}
			var b_num = $(this).parents("tr").attr("data-num"); // data-num : 사용자가 만든 속성인것 같은 느낌
			
			// hidden태그에 선택한 글 제목의 data-num의 값인 b_num 을 넘긴다..
			$('#b_num').val(b_num);
			/* 상세페이지로 이동하기위해 form 추가 (id : detailForm) */
			// 상세페이지 이동시 
			$('#detailForm').attr({
				"method":"get",
				"action":"/board/boardDetail.shj"
			});
			$('#detailForm').submit();
		});
	});
	
	/* 정렬 버튼 클릭 시 처리 함수*/
	function setOrder(order_by){
		$('#order_by').val(order_by); // 히든태그로 설정된 #order_by에 val()을 설정
		if($('#order_sc').val() == 'DESC'){ // desc이면 asc로 바꿔주겠다. 버튼을 클릭한거니까
			$('#order_sc').val('ASC');
		}else{
			$('#order_sc').val('DESC');
		}
		goPage(1); // 정렬시에 1페이지로 이동하여 보여준다는 뜻이네
	}
	
	/* 검색과 한 페이지에 보여줄 레코드 수 처리 및 페이징을 위한 실질적인 처리 함수 */
	function goPage(page){
		if($('#search').val()=="all"){
			$('#keyword').val('');
		}
		$('#page').val(page);
		$('#f_search').attr({
			"method":"GET",
			"action":"/board/boardList.shj"
		});
		$('#f_search').submit();
	}
	
	
</script>
</head>
<body>
	<div id="boardContainer">
		<div id="boardTit"><h3>글 목록</h3></div>
		<%-- 상세 페이지 이동을 위한 form --%>
		<form name="detailForm" id="detailForm">
			<input type="hidden" name="b_num" id="b_num">
			<input type="hidden" name="page" value="${data.page}">
			<input type="hidden" name="pageSize" value="${data.pageSize}">
		</form>
		<%-- === 검색기능 시작 === --%>
		<div id="boardSearch">
			<form id="f_search" name="f_search">
				<input type="hidden" id="page" name="page" value="${data.page}">
				<input type="hidden" id="order_by" name="order_by" value="${data.order_by}">
				<input type="hidden" id="order_sc" name="order_sc" value="${data.order_sc}">
				<table summary="검색">
					<colgroup>
						<col width="70%" />
						<col width="30%" />
					</colgroup>
					<tr>
						<td id="btd1">
							<label>검색조건</label>
							<select id="search" name="search">
								<option value="all">전체</option>
								<option value="b_title">제목</option>
								<option value="b_content">내용</option>
								<option value="b_name">작성자</option>
							</select>
							<input type="text" name="keyword" id="keyword" placeholder="검색어를 입력하세요">
							<input type="button" id="searchData" value="검색">
						</td>
						<td id="btd2">한페이지에
							<select id="pageSize" name="pageSize">
								<option value="10">10줄</option>
								<option value="20">20줄</option>
								<option value="30">30줄</option>
								<option value="50">50줄</option>
								<option value="70">70줄</option>
								<option value="100">100줄</option>
							</select>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<%-- === 검색기능 종료 === --%>
		<%-- === 리스트 시작 === --%>
		<div id="boardList">
			<table summary="게시판 리스트">
				<colgroup>
					<col width="10%" />
					<col width="62%" />
					<col width="15%" />
					<col width="13%" />
				</colgroup>
				<thead>
					<tr>
						<th>
							<!-- script문에서 정의한 함수를 html에서 불러와서 쓰네 -->
							<a href="javascript:setOrder('b_num');">글 번호
								<!-- 여기는 그냥 data를 받아온 값에 대하여  ▲, ▼ 을 판단하는거고 default는 c:otherwise 로 ▲ 설정 되어있는듯? -->
								<c:choose>
									<c:when test="${data.order_by == 'b_num' and data.order_sc == 'ASC' }">▲</c:when>
									<c:when test="${data.order_by == 'b_num' and data.order_sc == 'DESC' }">▼</c:when>
									<c:otherwise>▲</c:otherwise>
								</c:choose>
							</a>
						</th>
						<th>글 제목</th>
						<th>
							<a href="javascript:setOrder('b_updatedate');">작성일
								<c:choose>
									<c:when test="${data.order_by == 'b_updatedate' and data.order_sc == 'ASC' }">▲</c:when>
									<c:when test="${data.order_by == 'b_updatedate' and data.order_sc == 'DESC' }">▼</c:when>
									<c:otherwise>▲</c:otherwise>
								</c:choose>
							</a>
						</th>
						<th class="borcle">작성자</th>
					</tr>
				</thead>
				<tbody>
				<!-- 데이터 출력 -->
					<c:choose>
						<c:when test="${not empty boardList}" >
							<c:forEach var="board" items="${boardList}" varStatus="status">
							 	<!-- data-num 속성을 이용하여 현재 게시물의 글 번호를 저장한다. -->
							 	<!--  input hidden에 데이터를 넣는게 아닌 HTML의 data- 속성을 이용한다. 이해가 잘 안됨 -->
								<tr align="center" data-num="${board.b_num}">
									<td>${count - (status.count -1) }</td>
									<td align="left"><span class="goDetail">${board.b_title}</span></td>
									<td>${board.b_updatedate}</td>
									<td>${board.b_name}</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="4" align="center">등록된 게시물이 존재하지 않습니다.</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
		<%-- === 리스트 종료 === --%>
		
		
		<%-- === 글쓰기 버튼 출력 시작 === --%>
		<div id="boardBut">
			<input type="button" value="글쓰기" id="writeForm">
		</div>
		<%-- === 글쓰기 버튼 종료 === --%>
		<%-- === 페이지 네비게이션 시작 === --%>
		<div id="boardPage">
			<tag:paging page="${param.page}" total = "${total}" list_size = "${data.pageSize}" />
		</div>
	</div>
</body>
</html>