<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
  <title>가격 수정하기</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <meta http-equiv="content-type" content="text/html; charset=utf-8" />
</head>
<script>
  function moneyCheck(obj) {
    var v = obj.value;
    v = parseInt(v / 10) * 10;
    obj.value = v;
  }

  function validate(evt) {
    var theEvent = evt || window.event;
    var key = theEvent.keyCode || theEvent.which;
    key = String.fromCharCode(key);
    var regex = /[0-9]|\./;
    if (!regex.test(key)) {
      theEvent.returnValue = false;
      if (theEvent.preventDefault) theEvent.preventDefault();
    }
  }
</script>
<style>
  /*body{
  background: url('http://i66.tinypic.com/14y98ag.jpg') fixed;
}*/

  body {
    background: url('http://i67.tinypic.com/2ldeel3.jpg') fixed;
  }


  .navbar {
    /*opacity : 0.7;*/
  }

  .option-button {
    border-radius: 10px;
    width: 250px;
    height: 50px;
  }

  .btn-round {
    border-radius: 17px;
  }

  .btn-lg {
    border-radius: 10px;
    width: 190px;
    height: 50px;
  }

  .btn-round-lg {
    border-radius: 10px;
    width: 400px;
  }

  .btn-round-sm {
    border-radius: 15px;
  }

  #menu {
    margin-top: 5px;
  }

  .panel {
    /*opacity:0.7;*/
    background-color: #00ff0000;
  }

  .panel>.panel-body {
    /*opacity : 0.5;*/
    /*background-color:black;*/
  }

  .panel>.panel-heading {
    background-image: none;
    background-color: #222222;
    /*background-color : black;*/
    color: white;
    opacity: 1.0;
  }
</style>


<body>
  <nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
      <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
        <a class="navbar-brand" href="#">Grazie</a>
      </div>

      <div class="collapse navbar-collapse" id="myNavbar">
        <ul class="nav navbar-nav">
          <li class="active"><a href="#">메뉴관리</a></li>
          <li><a href="#">쿠폰관리</a></li>
          <li><a href="#">주문확인</a></li>
        </ul>

        <ul class="nav navbar-nav navbar-right">
          <li>
            <div class="user-info" style="margin-top:15px;color:#636363"><a href="#"><span class="glyphicon glyphicon-user"></span>&nbsp;주승권</a></div>
          </li>
          <li><a href="#"><span class="glyphicon glyphicon-log-out"></span> 로그아웃</a></li>
        </ul>
      </div>
    </div>
  </nav>


  <div class="container" style="margin-top:80px">
    <div class="panel panel-default">
      <div class="panel-heading">
        <h4><b>가격 수정하기<h4></b>
     </div>
     <div class="panel-body">
       <!--가격 관리 표 삽입-->
       <div class="table">
         <table class="table">
           <thead>
             <tr>
               <th></th>
               <th>Small</th>
               <th>Medium</th>
               <th>Large</th>
               <th>None</th>
             </tr>
           </thead>
           <tbody>
             <tr>
               <th scope="row">HOT</th>
               <td>
                 <div class="price">
                   <input type="text" id="hot_small_box" name="hot_small" class="form-control" onchange="getNumber(this);" onkeyup="getNumber(this);" onblur="value2(this)"/></div>
               </td>
               <td>
                 <div class="price">
                   <input type="text" id="hot_small_box" name="hot_small" class="form-control" onkeypress='validate(event)' /></div>
               </td>
               <td>
                 <div class="price">
                   <input type="text" id="hot_small_box" name="hot_small" class="form-control" onkeypress='validate(event)' /></div>
               </td>
               <td>
                 <div class="price">
                   <input type="text" id="hot_small_box" name="hot_small" class="form-control" onkeypress='validate(event)' /></div>
               </td>
             </tr>
             <tr>
               <th scope="row">ICE</th>
               <td>
                 <div class="price">
                   <input type="text" id="hot_small_box" name="hot_small" class="form-control" onkeypress='validate(event)' /></div>
               </td>
               <td>
                 <div class="price">
                   <input type="text" id="hot_small_box" name="hot_small" class="form-control" onkeypress='validate(event)' /></div>
               </td>
               <td>
                 <div class="price">
                   <input type="text" id="hot_small_box" name="hot_small" class="form-control" onkeypress='validate(event)' /></div>
               </td>
               <td>
                 <div class="price">
                   <input type="text" id="hot_small_box" name="hot_small" class="form-control" onkeypress='validate(event)' /></div>
               </td>
             </tr>
             <tr>
               <th scope="row">None</th>
               <td>
                 <div class="price">
                   <input type="text" id="hot_small_box" name="hot_small" class="form-control" onkeypress='validate(event)' /></div>
               </td>
               <td>
                 <div class="price">
                   <input type="text" id="hot_small_box" name="hot_small" class="form-control" onkeypress='validate(event)' /></div>
               </td>
               <td>
                 <div class="price">
                   <input type="text" id="hot_small_box" name="hot_small" class="form-control" onkeypress='validate(event)' /></div>
               </td>
               <td>
                 <div class="price">
                   <input type="text" id="hot_small_box" name="hot_small" class="form-control" onkeypress='validate(event)' /></div>
               </td>
             </tr>
           </tbody>
         </table>
       </div>
       <div class="row">
         <button type="button" class="btn btn-info btn-md" data-toggle="modal" style="margin-left:950px">가격 수정</button>
         <button type="button" class="btn btn-danger btn-md" data-toggle="modal">취소</button>
       </div>
     </div>
   </div>
 </div>



 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
 <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>

</html>