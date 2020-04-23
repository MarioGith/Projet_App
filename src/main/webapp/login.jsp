<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link rel="manifest" href="manifest.json">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-title" content="Ma Servlet">
	<meta name="description" content="Example de servlet">
	<link rel="apple-touch-icon" href="images/icons/icon-152.png">
    <link
            href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
            rel="stylesheet" id="bootstrap-css">
    <script
            src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script
            src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <title>Connection</title>
    <link href="css/login.css" rel="stylesheet" >
</head>


<body>

<% if (request.getAttribute("message")!=null) {%>
<p><%=request.getAttribute("message")%></p><%}%>

<div class="container login-container" >
    <div class="row">
        <div class="col-md-6 login-form">
            <h3>Connection</h3>
            <form action="Login" method="post">

                <div class="form-group">
                    <input name="email" type="email" class="form-control"
                           placeholder="Email" value=""/>
                </div>
                <div class="form-group">
                    <input name="password" type="password" class="form-control"
                           placeholder="Mot de passe" value=""/>
                </div>
                <div class="form-group">
                    <input type="submit" class="btnSubmit" value="Se connecter" name="connect"/>
                </div>
                <div class="form-group">
                    <a href="Register" class="ForgetPwd">Pas encore enregistré ?</a>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>