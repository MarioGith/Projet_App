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
    <title>Changez votre mot de passe</title>
    <link href="css/login.css" rel="stylesheet" >
</head>


<body>

<% if (request.getAttribute("message")!=null) {%>
<p><%=request.getAttribute("message")%></p><%}%>


<div class="container login-container" >
    <div class="row">
        <div class="col-md-6 login-form">
            <h3>Veuillez remplir le formulaire pour changer votre mot de passe</h3>
            <form action="UpdatePwdServlet" method="post">

                <div class="form-group">
                    <input name="NewPass" type="password" class="form-control"
                           placeholder="Nouveau mot de passe" value=""/>
                    <input type="hidden"  name="token" value=<%=request.getAttribute("token")%>>
                </div>
                <div class="form-group">
                    <input type="submit" class="btnSubmit" value="OK" name="connect"/>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>