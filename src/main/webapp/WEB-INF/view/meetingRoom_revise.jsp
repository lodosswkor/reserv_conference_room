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
<title>Revise Meeting Room</title>
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

lable{
vertical-align: middle;
}

/* #checks{ */
/* align:center; */
/* vertical-align:middle; */
/* } */

/* input[type=radio] { */
/* 	height: 20px; */
/* 	width: 20px; */
/* 	border-radius: 50%; */
/* 	background-color: #2B8DD2; */
/* } */

button {
	border: none;
	border-radius: 4px;
	color: #2B8DD2;
	background-color: white;
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

.caution {
	color: red;
}

input[type="radio"] {
    display:none;
}

input[type="radio"] + label {
    color:black;
}

input[type="radio"] + label span {
    display:inline-block;
    width:12px;
    height:12px;
    margin:-2px 10px 0 0;
    vertical-align:middle;
    background: #c8c8c8;
    border-radius: 50%;
    cursor:pointer;
}

input[type="radio"]:checked + label span {
	width:12px;
    height:12px;
	border-radius: 50%;
    background: #2B8DD2;
}

</style>

</head>
<body>

	<div align="center">
		<h2>회의실 수정/삭제</h2>			
	
		<div>
			<label>회의실 이름</label><br> <input type="text" id="name" name="name" value="${roomResponse.name}"><br>
			<label>사용자 이름</label><br> <input type="text" id="user" name="user" value="${roomResponse.user}"><br>
			<label>관리 부서</label><br> <input type="text" id="depart" name="depart" value="${roomResponse.depart}"><br>
			<input type="hidden" id="id" name="id" value="${roomResponse.id}"><br>
		</div>
		
		<div class="checks">
			<input type="radio" name="in_use" id="c1" value="true" checked="checked"/>
			<label for="c1"><span></span>활성화</label>
			<input type="radio" name="in_use" id="c2" value="false"/>
			<label for="c2"><span></span>비활성화</label>
		</div>
		
		<small id="validation" class="caution"></small><br><br>
		
		<div>
			<button class="btn btn-primary" id="edit_button" name="edit_button">수정</button>
			<button class="btn btn-primary" id="delete_button" name="delete_button">삭제</button>
			<button class="btn btn-primary" onClick="history.back()">취소</button>
		</div>
		
	</div>

	<script>

	$("#delete_button").on('click', function() {
		var deleteConfirm = confirm("삭제 하시겠습니까?");
		if (deleteConfirm) {
			var params = {
				"id" : $("#id").val()
			}
			$.ajax({
				type : "DELETE",
				url : "/admin/room?id=" + $("#id").val(),
				dataType : "text",
				success : function(args) {
					window.location.href = "/admin";
				},
				error : function(e) {
					alert("error");
				}
			});
		}
	});
	
	$("#edit_button").on('click', function() {
		var editConfirm = confirm("수정 하시겠습니까?");
		if (editConfirm) {
			var params = {
				"id" : $("#id").val(),
				"name" : $("#name").val(),
				"user" : $("#user").val(),
				"depart" : $("#depart").val(),
				"in_use" : $("input[name=in_use]:checked").val()
			}
			$.ajax({
				type : "PUT",
				url : "/admin/room/revise?id=" + $("#id").val(),
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
		}
	});
	</script>

</body>
</html>