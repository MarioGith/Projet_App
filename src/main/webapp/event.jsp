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

<div class="jumbotron">
    <h1 class="display-4">Event : <%=event.getType_event()%> <%=event.getOrganisateur()%> </h1>
    <h2 class="display-3">Description : <%=event.getDescription()%></h2>
    <h2 class="display-3">Prix : <%=event.getPrix()%></h2>
</div>

<%if(event.getIdcreateur() == user.getId()){%>
    <div class="bouton">
        <a href="modify_event.html">Modifier un event</a>
    </div>
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
