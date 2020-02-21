<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.eland.meetingRoom.entity.MeetingRoom" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
	request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>meeting room reservation</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<style> 
input[type=text] {
  width: 20%;
  padding: 12px 20px;
  margin: 8px 0;
  box-sizing: border-box;
  border: 2px solid #2B8DD2;
  border-radius: 4px;
}

select { 
width: 20%; /* 원하는 너비설정 */ 
padding: 12px 20px; /* 여백으로 높이 설정 */ 
margin: 8px 0;
font-family: inherit; /* 폰트 상속 */ 
background: url(https://farm1.staticflickr.com/379/19928272501_4ef877c265_t.jpg) no-repeat 95% 50%; /* 네이티브 화살표 대체 */ 
box-sizing: border-box;
border: 2px solid #2B8DD2; 
border-radius: 4px; /* iOS 둥근모서리 제거 */ 
-webkit-appearance: none; /* 네이티브 외형 감추기 */ 
-moz-appearance: none; 
appearance: none; 
}

button {
  background-color: white;
  border: none;
  border-radius: 4px;
  color: #2B8DD2; 
  padding: 10px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  border: 2px solid #2B8DD2;
  -webkit-transition-duration: 0.4s; /* Safari */
  transition-duration: 0.4s;
  cursor: pointer;
}

button:hover {
  background-color: #2B8DD2;
  color: white;
}

#date{
  width: 20%;
  padding: 12px 20px;
  margin: 8px 0;
  box-sizing: border-box;
  border: 2px solid #2B8DD2;
  border-radius: 4px;
  background: url(https://farm1.staticflickr.com/379/19928272501_4ef877c265_t.jpg) no-repeat 95% 50%; /* 네이티브 화살표 대체 */
}

.caution{
	color: red;
}

</style>

</head>
<body>

	<%
		String times[] = new String[19];
		String temp;
		for (int i = 0; i < times.length; i++) {
			temp = "";
			if (i < 4) {
				temp += "0";
			}
			if (i % 2 == 0) {
				temp += String.valueOf(8 + (i / 2)) + ":00";
			} else {
				temp += String.valueOf(8 + (i / 2)) + ":30";
			}
			times[i] = temp;
		}
	%>


	<div align="center">
		<h2>회의실 예약하기</h2>			

		<div>
			<label>제목</label><br> <input type="text" id="title" name="title"><br>
			<label>예약자</label><br> <input type="text" id="userName" name="userName"><br>
		</div>
		
		<div class="dropdown">
			<label>회의실</label><br>
			<select id="roomName" name="roomName" >
				<c:forEach var="rooms" items="${rooms}">
					<option value="${rooms.name}">${rooms.name}</option>
				</c:forEach>
			</select>
		</div>

		<div>
			<label>날짜</label><br>
			<input type="text" name="date" id="date" value="${dateValue}" readonly>
		</div>
		
		<div>
			<label>시작시간</label><br> <select id="sTime" name="sTime">
				<%for (int i = 0; i< times.length; i++) {%>		
						<option value=<%=times[i]%>><%=times[i]%></option>
				<%}%>
			</select>
		</div>

		<div>
			<label>종료시간</label><br> <select id="eTime" name="eTime">
				<%for (int i = 0; i< times.length; i++) {%>		
						<option value=<%=times[i]%>><%=times[i]%></option>
				<%}%>
			</select>
		</div>

		<small id="validation" class="caution"></small><br><br>
		
		<button class="btn btn-primary" id="insert_button">확인</button>
		<button class="btn btn-primary" onClick="history.back()">취소</button>
	</div>
	
	
	<script type="text/javascript">
		$(document).ready(		
				function() {
					$.datepicker.setDefaults($.datepicker.regional['ko']);
					$('#date').datepicker({
						dateFormat: 'yy-mm-dd',
						todayHighlight: true,
						autoclose: true
				});
		});
			
				
		$("#insert_button").on('click', function() {
			
			var params = {
				"title" : $("#title").val(),
				"date" : $("#date").val(),
				"userName" : $("#userName").val(),
				"roomName" : $("#roomName").val(),
				"sTime" : $("#sTime").val(),
				"eTime" : $("#eTime").val()
			}
			$.ajax({
				type : "POST",
				url : "/res/insert",
				data : JSON.stringify(params),
				dataType : "json",
				contentType : "application/json",
				success : function(args) {
					window.location.href = "/res?date=" + $("#date").val();
				},
				error : function(e) {
					
					if("titleNull" == e.responseText){
						errorMessage = "제목을 입력해주세요.";
						$('#validation').html(errorMessage);
        	            $('#validation').show();
					}
					else if("titleLong" == e.responseText){
						errorMessage = "제목은 9글자까지 가능합니다.";
						$('#validation').html(errorMessage);
        	            $('#validation').show();
					}
					else if("userNull" == e.responseText){
						errorMessage = "예약자를 입력해주세요.";
						$('#validation').html(errorMessage);
        	            $('#validation').show();
					}
					else if("userLong" == e.responseText){
						errorMessage = "예약자는 9글자까지 가능합니다.";
						$('#validation').html(errorMessage);
        	            $('#validation').show();
					}
					else if("timeReversal" == e.responseText){
						errorMessage = "시작/종료 시간을 확인해주세요.";
						$('#validation').html(errorMessage);
        	            $('#validation').show();
					}
					else if("overlap" == e.responseText){
						errorMessage = "해당 시간에 이미 예약이 있습니다.";
						$('#validation').html(errorMessage);
        	            $('#validation').show();
					}
					else if("roomFalse" == e.responseText){
						errorMessage = "사용 불가능한 회의실입니다.";
						$('#validation').html(errorMessage);
        	            $('#validation').show();
					}
					else{
						alert("error");
					}					
				}
			});
		});
	</script>


</body>
</html>