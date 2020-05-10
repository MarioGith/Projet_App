<!DOCTYPE html>
<%@page import="fr.imt.cepi.util.Liste_Search" %>
<%@ page import="fr.imt.cepi.util.Utilisateur" %>
<%
    Utilisateur user = (Utilisateur) session.getAttribute("utilisateur");
    Liste_Search liste = (Liste_Search) request.getAttribute("liste_search");
%>

<html lang="french">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="manifest" href="${pageContext.request.contextPath}/manifest.json">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-title" content="Ma Servlet">
    <meta name="description" content="Example de servlet">
    <link rel="apple-touch-icon" href="images/icons/icon-152.png">
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>

    <link href="css/my.css" rel="stylesheet">
    <link href="css/home.css" rel="stylesheet">

    <title>Accueil</title>
</head>
<body>

<header>
    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
        <figure ><a class="navbar-brand" href="#"><img class="imageHome" src="images/logo.jpg"></a><!-- Ce sera le lien vers la page d'acceuil--></figure>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">
            <ul class="navbar-nav mr-auto">
                <form action="GoHome" method="post"><input class="nav-link" type="submit" value="Accueil" name="connect"/></form>
                <form action="GoProfil" method="post"><input class="nav-link" type="submit" value="Profil" name="connect"/></form>
                <form action="GoHistorique" method="post"><input class="nav-link" type="submit" value="Historique" name="connect"/></form>
                <li class="nav-item"><a class="nav-link" href="New_Event.html">Créer un évènement</a></li>
                <li class="nav-item"><a class="nav-link" href="http://www.cercle-des-eleves.fr/evenements/" >Calendrier</a></li>

            </ul>
            <form action="Search" class="form-inline mt-2 mt-md-0">
                <input class="form-control mr-sm-2" type="text" placeholder="Search" aria-label="Search" name="recherche">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
            </form>
        </div>
    </nav>
</header>

<div id="MargePremier"></div>

<div class="jumbotron">
    <h1 class="display-2">Résultat de la recherche :</h1>
</div>

<div class="jumbotron">
    <%=liste.getEventSearch()%>
</div>

<footer class="footer">
    <nav class="navbar  navbar-expand-sm navbar-dark bg-dark " >
        <div class="navbar-collapse" >
            <ul class="navbar-nav ml-auto ">
                <li class="nav-item"><a class="nav-link" href="confidentialite.html">Politique de condidentialité</a></li>
                <li class="nav-item" ><a class="nav-link" href="#">Nous contacter</a></li>
            </ul>

        </div>
    </nav>
</footer>

</body>
</html>