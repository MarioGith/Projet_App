<!DOCTYPE html>
<%@ page import="fr.imt.cepi.util.Utilisateur" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="manifest" href="${pageContext.request.contextPath}/manifest.json">
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
    <link href="css/my.css" rel="stylesheet">

    <title>Accueil</title>
</head>
<body>

<%
    Utilisateur user = (Utilisateur) session.getAttribute("utilisateur");
%>
<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
    <figure ><a class="navbar-brand" href="#"><img class="imageHome" src="images/logo.jpg"></a><!-- Ce sera le lien vers la page d'acceuil--></figure>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarCollapse">
        <ul class="navbar-nav mr-auto">
            <form action="GoHome" method="post"><input class="nav-link" type="submit" value="Acceuil" name="connect"/></form>
            <form action="GoProfil" method="post"><input class="nav-link" type="submit" value="Profil" name="connect"/></form>
            <li class="nav-item"><a class="nav-link" href="New_Event.html">Créer un évènement</a></li>
            <li class="nav-item"><a class="nav-link" href="#">Création Profil</a></li>
            <li class="nav-item"><a class="nav-link" href="http://www.cercle-des-eleves.fr/evenements/" >Calendrier</a></li>

        </ul>
        <form action="Search" class="form-inline mt-2 mt-md-0">
            <input class="form-control mr-sm-2" type="text" placeholder="Search" aria-label="Search" name="recherche">
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
        </form>
    </div>
</nav>
</header>

<div class="jumbotron">
    <form action="ModifyProfil" method="post">

        <h1 class="display-4">Hi : <%=user.getNom()%> </h1>
        <h2 class="display-3">Email : <%=user.getEmail()%></h2>

        <h2 class="display-3">Chambre : </h2>

        <div class="form-group">
            <input name="chambre" type="txt" class="form-control"
                   placeholder="Numéro de chambre" value=""/>
        </div>

        <h2 class="display-3">Mot de passe : </h2>

        <div class="form-group">
            <input name="password" type="txt" class="form-control"
                   placeholder="Mot de passe" value=""/>
        </div>

        <h2 class="display-3">Pp : <%=user.getPp()%></h2>

        <div class="form-group">
            <input type="file" name="pp" size="50" />
        </div>

        <div class="form-group">
            <input type="submit" class="btnSubmit" value="Modifier " name="connect"/>
        </div>

    </form>

</div>

<footer class="footer ">
    <nav class="navbar navbar-expand-md navbar-dark bottom bg-dark " >
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item"><a class="nav-link" href="#">Condition Général d'Utilisation</a></li>
                <li class="nav-item" ><a class="nav-link" href="#">Nous contacter</a></li>
            </ul>

        </div>
    </nav>
</footer>


</body>
</html>
