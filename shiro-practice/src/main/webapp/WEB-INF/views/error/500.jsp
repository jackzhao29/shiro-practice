<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <title>系统错误</title>
    <style type="text/css">
        body,html{
            height: 100%;
            margin: 0;
            padding: 0;
        }
        #wait {
            display: -webkit-box;
            display: -webkit-flex;
            display: flex;
            -webkit-box-align: center;
            -webkit-align-items: center;
            align-items: center;
            -webkit-box-pack: center;
            -webkit-justify-content: center;
            justify-content: center;
            height:100%;
            background-color: #FAFAFA;
            color: #999;
            font-size: 14px;
        }
        </style>
</head>
<body>
    <div id='wait'>
        <p>内部系统出错，请返回重试！</p>
    </div>
</body>
</html>