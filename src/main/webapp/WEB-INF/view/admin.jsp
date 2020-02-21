<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.eland.meetingRoom.entity.MeetingRoom"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%
	request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Meeting Room List</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<style>
timetable, th, td{
	position : "absolute";	
	border: 1px solid #2B8DD2;
	text-align: center;
	margin: auto;
    width: 150px;
    font-size: 18px;
    padding: 9px;
	border-radius: 4px;
}

th{
    font-weight: bold;
    vertical-align: top;
	color: white;
	background-color: #204f80;
}

input[type=text] {
  width: 10%;
  padding: 10px;
  margin: 8px 0;
  box-sizing: border-box;
  border: 2px solid #2B8DD2;
  border-radius: 4px;
}

button {
  background-color: white;
  border: 2px solid #2B8DD2;
  border-radius: 4px;
  color: #2B8DD2; 
  padding: 10px;
  text-align: center;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  -webkit-transition-duration: 0.4s; /* Safari */
  transition-duration: 0.4s;
  cursor: pointer;
}

button:hover, #room:hover {
  background-color: #2B8DD2;
  color: white;
  cursor: pointer;
}

#roomFalse:hover {
  background-color: rgb(128,128,128);
  color: white;
  cursor: pointer;
}

#room{
color: #2B8DD2; 
}

#roomFalse{
color:  rgb(128,128,128); 
}

#page{
padding-top:2%;
}

#top_button{
padding-right:28%;
}

</style>


</head>
<body>
	<div id="page">
		<div align="center" align="right">
			<h1 align="center">관리자 페이지</h1>
		</div>
		
		<div id="top_button" align="right">
			<button class="btn btn-primary" onclick="location.href='/admin/room/insert'">회의실 추가하기</button>
			<button class="btn btn-primary" onclick="location.href='/res'">예약 페이지로 가기</button>
		</div><br>
		
		<table class="table table-bordered" id="timetable" align="center"></table><br><br>
		 
	</div>
<script type="text/javascript">
	
	// usable meeting room list
	let meetingRoomTrue = new Array();
	<c:forEach var="room" items="${meetingTrue}">
		var json = new Object;
		json.id = "${room.id}";
		json.name = "${room.name}";
		json.user = "${room.user}";
		json.depart = "${room.depart}";
		json.in_use = "${room.in_use}";
		meetingRoomTrue.push(json);
	</c:forEach>
	
	let meetingRoomFalse = new Array();
	<c:forEach var="room" items="${meetingFalse}">
	var json = new Object;
		json.id = "${room.id}";
		json.name = "${room.name}";
		json.user = "${room.user}";
		json.depart = "${room.depart}";
		json.in_use = "${room.in_use}";
		meetingRoomFalse.push(json);
	</c:forEach>
	
	$(document).ready(		
		function() {			
			createTable();
	});
	
	
	function createTable(){		
	    var table = '<tr><th></th><th>회의실 이름</th><th>관리 부서</th><th>최근 수정한 사람</th><th>활성화</th><th>회의실 수정</th></tr>';
	    for (i = 0; i < meetingRoomTrue.length; i++) {
	    	table += '<tr><th>'+ (i+1) + '</th>';
	    	table += '<td>'+ meetingRoomTrue[i].name + '</td>';
    		table += '<td>'+ meetingRoomTrue[i].depart + '</td>';
    		table += '<td>'+ meetingRoomTrue[i].user + '</td>';
    		table += '<td>';
				if("true" == meetingRoomTrue[i].in_use){
	    			table+='O';
	    		}
				else{
					table+='X';
				}
    		table += '</td>';
    		table += '<td id="room" onclick="reviseMeetingRoom(' + meetingRoomTrue[i].id + ')">수정하기</td>';
    		table += '</tr>';
	    }
	    for (i = 0,j=meetingRoomTrue.length; i < meetingRoomFalse.length; i++,j++) {
	    	table += '<tr><th >'+ (j+1) + '</th>';
	    	table += '<td style="color:rgb(128,128,128)">'+ meetingRoomFalse[i].name + '</td>';
    		table += '<td style="color:rgb(128,128,128)">'+ meetingRoomFalse[i].depart + '</td>';
    		table += '<td style="color:rgb(128,128,128)">'+ meetingRoomFalse[i].user + '</td>';
    		table += '<td style="color:rgb(128,128,128)">';
				if("true" == meetingRoomFalse[i].in_use){
	    			table+='O';
	    		}
				else{
					table+='X';
				}
    		table += '</td>';
    		table += '<td id="roomFalse" onclick="reviseMeetingRoom(' + meetingRoomFalse[i].id + ')">활성화하기</td>';
    		table += '</tr>';
	    }
	    
	    
	    $('#timetable').append(table);
	}
	
	function reviseMeetingRoom(args){
		let id=args;
		$.ajax({
				type : "GET",
				url : "/admin/room/revise/" + id,
				success : function(args) {
					window.location.href = "/admin/room/revise/" + id;
				},
				error : function(e) {
					alert("error");
				}
			});
	}

</script>

</body>
</html>