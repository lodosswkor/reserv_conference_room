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
<title>reservation list</title>
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
	border-radius: 4px;
}

th{
    width: 135px;
    padding: 8.5px;
    font-size: 15px;
    font-weight: bold;
    vertical-align: middle;
	color: white;
	background-color: #204f80;
}

button {
  background-color: white;
  border: 2px solid #2B8DD2;
  border-radius: 4px;
  color: #2B8DD2; 
  padding: 10px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  -webkit-transition-duration: 0.4s; /* Safari */
  transition-duration: 0.4s;
  cursor: pointer;
}

button:hover {
  background-color: #2B8DD2;
  color: white;
}

img{
	display:inline;
	cursor: pointer;
	width:2.5em;
	height:2.5em;
	vertical-align:middle;
	margin: auto;
}

input[type=text] {
  width: 10%;
  padding: 10px;
  margin: 8px 0;
  box-sizing: border-box;
  border: 2px solid #2B8DD2;
  border-radius: 4px;
}

h1{
display:inline;
font-size: 3em;
vertical-align:middle;
margin: auto;
}

#buttons{
padding-right:4%;
}
#date{
background: url(https://farm1.staticflickr.com/379/19928272501_4ef877c265_t.jpg) no-repeat 95% 50%; /* 네이티브 화살표 대체 */
cursor: pointer;
}
#child{
position:relative;
display:table-cell;
text-align:center;
vertical-align:middle;
}
</style>


</head>
<body>
	<div>
		<div align="center">
			<img id="left_button" src="../resources/left.jpg"> &emsp;&emsp;
			<h1 align="center">${date_value}</h1> &emsp;&emsp;
			<img id="right_button" src="../resources/right.jpg">
		</div>
		
		<div id="buttons" align="right">
			<button class="btn btn-primary" id="reservation_button">회의실 예약하기</button>
			Date: <input type="text" name="date" id="date" value="${date_value}" readonly>
			<button class="btn btn-primary" id="date_button" name="date_button">날짜로 조회하기</button><br>	
		</div>
		<table class="table table-bordered" id="timetable" align="center"></table>
		<div id="show-reservation"></div>
	</div>
<script type="text/javascript">
	
	// time array list
	const time = new Array();
	var temp;
	for(i=0;i<18;i++){ 
     	temp = "";
     	var _i = 8+(i/2);
		if(i<3){
			temp = "0";
		}
		if(i%2==0){
			temp = temp + parseInt(_i) + ":00 ~ " + temp + parseInt(_i) + ":30";
		}
    	else{
    		if(i == 3){
    			temp = "0" + temp + parseInt(_i) + ":30 ~ " + temp + parseInt(_i+1) + ":00";	
    		}
    		else{
    			temp = temp + parseInt(_i) + ":30 ~ " + temp + parseInt(_i+1) + ":00";
    		}	
    	}
		time.push(temp);
	}
	
	// usable meeting room list
	let meetingRooms = new Array();
	<c:forEach var="room" items="${rooms}">
		var json = new Object;
		json.id = "${room.id}";
		json.name = "${room.name}";
		json.user = "${room.user}";
		json.depart = "${room.depart}";
		json.in_use = "${room.in_use}";
		meetingRooms.push(json);
	</c:forEach>
	
	$(document).ready(		
		function() {
			$.datepicker.setDefaults($.datepicker.regional['ko']);
			$('#date').datepicker({
				dateFormat: 'yy-mm-dd',
				todayHighlight: true,
				autoclose: true
			});
			
			createTable();
			createReservation();
	});
	
	// yyyy-mm-dd 형식의 문자열 리턴
	Date.prototype.yyyymmdd_dash = function() {
	  var mm = this.getMonth();
	  var dd = this.getDate();
	 
	  return [this.getFullYear(), '-',
	          (mm>9 ? '' : '0') + mm , '-',
	          (dd>9 ? '' : '0') + dd
	         ].join('');
	};
	
	$("#reservation_button").on('click', function() {
		$.ajax({
			type : "GET",
			url : "/res/insert/"+"${date_value}",
			contentType : "application/json",
			success : function(args) {					
					window.location.href = "/res/insert/"+"${date_value}";
			},
			error : function(e) {
				alert("error");
			}
		});		
	});
	
	$("#date_button").on('click', function() {
		$.ajax({
			type : "GET",
			url : "/res?date=" + $("#date").val(),
			success : function(args) {
				window.location.href = "/res?date=" + $("#date").val();
			},
			error : function(e) {
				alert("error");
			}
		});
	});
	$("#left_button").on('click', function() {
		let arr = "${date_value}".split('-');
		let date_obj = new Date(arr[0],arr[1],arr[2]);
		date_obj.setDate(date_obj.getDate() -1);
		let prev_day = date_obj.yyyymmdd_dash();
		
		$.ajax({
			type : "GET",
			url : "/res?date=" + prev_day,
			success : function(args) {
				window.location.href = "/res?date=" + prev_day;
			},
			error : function(e) {
				alert("error");
			}
		});
		
	});
	$("#right_button").on('click', function() {
		let arr = "${date_value}".split('-');
		let date_obj = new Date(arr[0],arr[1],arr[2]);
		date_obj.setDate(date_obj.getDate() +1);
		let next_day = date_obj.yyyymmdd_dash();
		
		$.ajax({
			type : "GET",
			url : "/res?date=" + next_day,
			success : function(args) {
				window.location.href = "/res?date=" + next_day;
			},
			error : function(e) {
				alert("error");
			}
		});
	});
	
	function createTable(){		
	    var table = '<tr><th></th>';
	    for (i = 0; i < meetingRooms.length; i++) {
    		table += '<th id="room' + meetingRooms[i].id + '">' 
    		+ meetingRooms[i].name + '<br>('+ meetingRooms[i].depart  +')</th>';
	    }
    		
	    table += '</tr>';
	    for(i = 0; i < time.length;i++){
	    	table+='<tr><th id="time' + (i+1) + '">'+ time[i] + '</th>';
	    	for (j = 0; j < meetingRooms.length; j++) {
	    		table += '<td></td>';
	    	}
	    	table += '</tr>';
	    }
	    
	    $('#timetable').append(table);
	}
	
	function createReservation(){
		
		var dateReservation = new Array();
		<c:forEach var="res" items="${dateResResponses}">
			var json = new Object;
			json.id = "${res.id}";
			json.roomId = "${res.roomId}";
			json.roomName = "${res.roomName}";
			json.title = "${res.title}";
			json.userName = "${res.userName}";
			json.sTime = "${res.sTime}";
			json.eTime = "${res.eTime}";
			json.date = "${res.date}";

			dateReservation.push(json);
		</c:forEach>
		
		for(i=0;i<dateReservation.length;i++){
			let startTimeSplit = dateReservation[i].sTime.split(':');
			const startHour = parseInt(startTimeSplit[0]);
			const startMin = startTimeSplit[1];		// '00' or '30'
			
			let endTimeSplit = dateReservation[i].eTime.split(':');
			const endHour = parseInt(endTimeSplit[0]);
			const endMin = endTimeSplit[1];		// '00' or '30'
			
			// calculate start, end idx;
			let startIdx = (startHour-8)*2 + 1;
			if(startTimeSplit[1] == '30'){
				startIdx +=1;
			}
			
			let endIdx = (endHour-8)*2 + 1;
			if(endTimeSplit[1] == '30'){
				endIdx +=1;
			}
			
			createReservationBox(dateReservation[i], startIdx, endIdx-1);
		}
	}
	
	function createReservationBox(arg, sIdx, eIdx) {
 	    const thRect = $('#room' + arg.roomId)[0].getBoundingClientRect();
		
 		const td_sRect = $('#time' + sIdx)[0].getBoundingClientRect();
 		const td_eRect = $('#time' + eIdx)[0].getBoundingClientRect();
		
		const div = '<div id="reservation' + arg.id + '"><div id="child"><strong>'+arg.title+'</strong><br>' + arg.userName + '</div></div>';
		$('#show-reservation').append(div);
	    
	    $('#reservation' + arg.id).css({
	    	"position": "absolute",
	    	"display" : "table",
	        "top": td_sRect.top + 1,
	        "left": thRect.left + 1,
	        "width": thRect.width - 1,
	        "height": td_eRect.bottom - td_sRect.top,
	        "border-radius" : "4px",
	        "color": "white",
	        "backgroundColor": getRandomBackgroundColor()
	    });
	    
		$('#reservation' + arg.id).hover(function() {
			$(this).css({
				"border-style" : "double",
				"border-radius" : "4px",
				"cursor" : "pointer"
				});
			}, function() {
				$(this).css({
					"border-style" : "none"
				});
		});
		
		$('#reservation' + arg.id).on('click', function() {
			$.ajax({
				type : "GET",
				url : "/res/revise/" + arg.id,
				contentType : "application/json",
				success : function(args) {
					window.location.href = "/res/revise/" + arg.id;
				},
				error : function(e) {
					alert("error");
				}
			});	
		});
	}

	function getRandomBackgroundColor() {
		let x = 100+Math.floor(Math.random() * 140);
		let y = 100+Math.floor(Math.random() * 140);
		let z = 100+Math.floor(Math.random() * 140);
		return 'rgb(' + x + ',' + y + ',' + z + ')';
	}
</script>

</body>
</html>