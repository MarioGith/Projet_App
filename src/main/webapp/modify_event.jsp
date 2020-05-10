<!DOCTYPE html>
<%@page import="fr.imt.cepi.util.Evenement" %>
<html lang="french">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link rel="manifest" href="manifest.json">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-title" content="Ma Servlet">
	<meta name="description" content="Example de servlet">
	<link rel="apple-touch-icon" href="images/icons/icon-152.png">
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>

    <link href="css/my.css" rel="stylesheet">
    <title>Modification event</title>
</head>
<body>
<!--<%/*String idevent = request.getParameter("var");*/%>-->
<%
    Evenement event = (Evenement) request.getAttribute("evenement");
%>
<div class="row my-2">
    <div class="container login-container">
        <div class="row">
            <div class="col-md-6 login-form">
                <h3>Modifier l'Event </h3>
                <form action="Modify_Event" method="post" enctype="multipart/form-data">
                    <div class="form-group">
                        <input name="organisateur" type="text" class="form-control" value=<%=event.getOrganisateur()%>>
                    </div>
                    <div class="form-group">
                        <input type="hidden"  name="idevent" value=<%=event.getId()%>>
                    </div>
                    <div class="form-group">
                        <input name="typeevent" type="text" class="form-control" value=<%=event.getType_event()%>>
                    </div>
                    <div class="form-group">
                        <input name="description" type="text" class="form-control " value=<%=event.getDescription()%>>
                    </div>
                    <div class="form-group">
                        <input name="prix" type="text" class="form-control" value=<%=event.getPrix()%>>
                    </div>
                    <div class="form-group">
                        <input type="submit" class="btnSubmit" value="Modifier" name="modify"/>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="container login-container">
        <div class="row">
            <div class="col-md-6 login-form">
                <h3>Modifier la date et l'heure </h3>
                <form action="Modify_Date_Event" method="post" enctype="multipart/form-data">
                    <h6>vous devez modifier la date et l'horraire</h6>
                    <div class="form-group">
                        <input type="hidden"  name="idevent" value=<%=event.getId()%>>
                    </div>
                    <div class="form-group">
                        <input name="horaire" type="time" class="form-control"/>
                    </div>
                    <div class="form-group">
                        <input name="date" type="date" class="form-control"/>
                    </div>
                    <div class="form-group">
                        <input type="submit" class="btnSubmit" value="Modifier" name="modify"/>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="container login-container">
        <div class="row">
            <div class="col-md-6 login-form">
                <h3>Modifier l'image de l'evenement </h3>
                <form action="Modify_Image_Event" method="post" enctype="multipart/form-data">
                    <div class="form-group">
                        <input type="hidden"  name="idevent" value=<%=event.getId()%>>
                    </div>
                    <div class="form-group">
                        <input type="file" name="image_pre" size="50" />
                    </div>
                    <div class="form-group">
                        <input type="submit" class="btnSubmit" value="Modifier" name="modify"/>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="container login-container">
        <div class="row">
            <div class="col-md-6 login-form">
                <h3>Modifier le menu </h3>
                <form action="Modify_Menu_Event" method="post" enctype="multipart/form-data">
                    <div class="form-group">
                        <input type="hidden"  name="idevent" value=<%=event.getId()%>>
                    </div>
                    <div class="form-group">
                        <input type="file"name="menu" size="50" />
                    </div>
                    <div class="form-group">
                        <input type="submit" class="btnSubmit" value="Modifier" name="modify"/>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<form action="ShowEvent" method="post">
    <div class="form-group">
        <input type="hidden"  name="idevent" value=<%=event.getId()%>>
    </div>
    <div class="form-group">
        <input type="submit" class="btnSubmit" value="Retour" name="modify"/>
    </div>
</form>
</body>
</html>