<!DOCTYPE html>
<%@page import="fr.imt.cepi.util.Utilisateur" %>
<%@ page import="fr.imt.cepi.util.Liste_Event" %>
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
    Liste_Event liste = (Liste_Event) request.getAttribute("liste");
%>



<div class="jumbotron">
    <h1 class="display-4">Bonjour <%=user.getNom()%>
    </h1>
</div>

<div class="jumbotron">
    <h1 class="display-4">Liste evenement : </h1>
    <h3 class="display-3"> <%=liste.getEvent()%> </h3>
</div>

<div class="bouton">
    <a href="New_Event.html">Creer un event</a>
</div>

</body>
</html>
