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
<title>Insert Meeting Room</title>
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


input[type=radio] {
  height: 20px;
  width: 20px;
  border-radius: 50%;
}

input[type=radio]:checked {
  background-color: #2B8DD2;
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

.caution{
	color: red;
}
</style>

</head>
<body>


	<div align="center">
		<h2>회의실 추가하기</h2>	
		<div>
			<label>회의실 이름</label><br> <input type="text" id="name" name="name"><br>
			<label>사용자 이름</label><br> <input type="text" id="user" name="user"><br>
			<label>관리 부서</label><br> <input type="text" id="depart" name="depart" placeholder="미입력 시 '자율'로 저장됩니다."><br>
		</div>
		<small id="validation" class="caution"></small><br><br>
		<div>
			<button class="btn btn-primary" id="insert_button">확인</button>
			<button class="btn btn-primary" onClick="history.back()">취소</button>
		</div>
	</div>
	
	<script>
		$("#insert_button").on('click', function() {
			var params = {
				"name" : $("#name").val(),
				"user" : $("#user").val(),
				"depart" : $("#depart").val()
			}
			$.ajax({
				type : "POST",
				url : "/admin/room/insert",
				data : JSON.stringify(params),
				dataType : "text",
				contentType : "application/json",
				success : function(args) {
					window.location.href = "/admin";
				},
				error : function(e) {
					if("nameNull" == e.responseText){
						errorMessage = "회의실 이름을 입력해주세요.";
						$('#validation').html(errorMessage);
        	            $('#validation').show();
					}					
					else if("nameOverlap" == e.responseText){
						errorMessage = "중복된 회의실 이름입니다.";
						$('#validation').html(errorMessage);
        	            $('#validation').show();
					}
					else if("userNull" == e.responseText){
						errorMessage = "사용자 이름을 입력해주세요.";
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