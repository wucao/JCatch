<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="renderer" content="webkit">
  <title>后台管理系统</title>
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap.min.css">
  <link rel="stylesheet" href="//cdn.bootcss.com/font-awesome/4.4.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="//cdn.bootcss.com/ionicons/2.0.1/css/ionicons.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/admin/dist/css/AdminLTE.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/admin/dist/css/skins/skin-blue.min.css">
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
  <script src="//cdn.bootcss.com/jquery/2.1.4/jquery.min.js"></script>
  <script src="//cdn.bootcss.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
  <script src="${pageContext.request.contextPath}/resources/particleground/jquery.particleground.min.js"></script>
  <script>
    $(document).ready(function () {
      $('body').particleground({
        dotColor: '#92afbf',
        lineColor: '#92afbf'
      });
      $(window).resize(function() {
        loginBoxPosition();
      });
      loginBoxPosition();
      function loginBoxPosition() {
        $('.login-box').css({
          'margin-top': -($("body").height() + $(".login-box").height()) / 2
        });
      }
    });
  </script>
  <style type="text/css">
    html,body {
      height: 100%;
    }
    body.login-page {
      background: #5b99bd;
    }
    .login-box {
      box-shadow: 0 -15px 30px #3f90bf;
    }
    .login-box-body {
      background: transparent;
    }
    .form-control {
      background: #69a9ce;
      color: white;
      border: transparent;
    }
    .login-logo {
      font-family: 'Microsoft Yahei';
      color: #0f74ae;
      font-size: 18px;
      padding-top: 10px;
      margin-bottom: 5px;
    }
    .login-box input::-webkit-input-placeholder {
      color: white !important;
    }
    .login-box input:-moz-placeholder {
      /* FF 4-18 */
      color: white !important;
    }
    .login-box input::-moz-placeholder {
      /* FF 19+ */
      color: white !important;
    }
    .login-box input:-ms-input-placeholder {
      /* IE 10+ */
      color: white !important;
    }
  </style>
</head>
<body class="hold-transition login-page">
<div class="login-box">
  <div class="login-logo">
    后台管理系统
  </div>
  <!-- /.login-logo -->
  <div class="login-box-body">
  
    <c:if test="${param.error == true}"><p class="login-box-msg" style="color: red;">用户名或密码错误！</p></c:if>

    <form action="${pageContext.request.contextPath}/login" method="post">
      <div class="form-group has-feedback">
        <input type="text" class="form-control" placeholder="用户" name="username">
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="password" class="form-control" placeholder="密码" name="password">
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>
      <div class="row">
        <div class="col-xs-12">
          <button type="submit" class="btn btn-primary btn-block btn-flat">登录</button>
        </div>
        <!-- /.col -->
      </div>
    </form>
    
  </div>
  <!-- /.login-box-body -->
</div>
<!-- /.login-box -->

</body>
</html>