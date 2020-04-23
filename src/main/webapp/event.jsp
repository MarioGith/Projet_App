<!DOCTYPE html>
<%@page import="fr.imt.cepi.util.Utilisateur" %>
<%@page import="fr.imt.cepi.util.Evenement" %>
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


<% if (request.getAttribute("message")!=null) {%>
<p><%=request.getAttribute("message")%></p><%}%>

<%
    Utilisateur user = (Utilisateur) session.getAttribute("utilisateur");
    Evenement event = (Evenement) request.getAttribute("evenement");
%>
<header>
    <!-- Section pour accéder à toutes les pages -->
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
            <form class="form-inline mt-2 mt-md-0" action="Search">
                <input class="form-control mr-sm-2" type="text" placeholder="Search" aria-label="Search" name="recherche">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
            </form>
        </div>
    </nav>
</header>

<div class="jumbotron">
    <h1 class="display-4">Event : <%=event.getType_event()%> <%=event.getOrganisateur()%> </h1>
    <h2 class="display-3">Description : <%=event.getDescription()%></h2>
    <h2 class="display-3">Prix : <%=event.getPrix()%></h2>
</div>

<%if(event.getIdcreateur() == user.getId()){%>
    <!--<div class="bouton">
        <a href="modify_event.jsp?var=<%=event.getId()%>">Modifier un event</a>
    </div>-->
    <form action="Modify_Event1" method="post">
        <input type="hidden" name="idevent" value= <%=event.getId()%> />
        <button name="Modify" type="submit">
            Modifier l'evenement
        </button>
    </form>
<%}%>

<%
    String id = String.valueOf(event.getId());
%>

<form action="eventRegister" method="post">
    <div class="form-group">
        <input type="hidden" name="idevenement" value= <%= id %> />
        <input type="submit" class="btnSubmit" value="Interesser" name="interesser"/>
    </div>
</form>

<form action="eventUnregister" method="post">
    <div class="form-group">
        <input type="hidden" name="idevenement" value= <%= id %> />
        <input type="submit" class="btnSubmit" value="Plus interesser" name="plusInteresser"/>
    </div>
</form>

</body>
</html>
