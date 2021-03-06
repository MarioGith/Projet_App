<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="manifest" href="/manifest.json">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-title" content="Ma Servlet">
    <meta name="description" content="Example de servlet">
    <link rel="apple-touch-icon" href="images/icons/icon-152.png">
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
            rel="stylesheet" id="bootstrap-css">
    <script
            src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script
            src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link href="css/login.css" rel="stylesheet">
    <title>Page d'enregistrement</title>


</head>
<body>
<% if (request.getAttribute("message")!=null) {%>
<p><%=request.getAttribute("message")%></p><%}%>

<div class="container login-container">
    <div class="row">
        <div class="col-md-6 login-form">
            <h3>Remplissez tous les champs pour vous enregistrer</h3>
            <form action="Register" method="post">
                <div class="form-group">
                    <input name="email" type="email" class="form-control"
                           placeholder="Email" value=""/>
                </div>
                <div class="form-group">
                    <input name="password" type="password" class="form-control"
                           placeholder="Mot de passe" value=""/>
                </div>
                <div class="form-group">
                    <input name="nom" type="txt" class="form-control"
                           placeholder="Nom" value=""/>
                </div>
                <div class="form-group">
                    <input name="chambre" type="txt" class="form-control"
                           placeholder="Numéro de chambre ou adresse" value=""/>
                </div>
                <div class="form-group">
                    <input type="submit" class="btnSubmit" value="Enregistrer"/>
                </div>
                <div class="form-group">
                    <a href="Login" class="ForgetPwd">Déjà enregistré ?</a>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>