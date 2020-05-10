<%@ page import="fr.imt.cepi.util.Utilisateur" %>
<%@ page import="fr.imt.cepi.util.Liste_Perso" %>
<%@ page import="fr.imt.cepi.util.Liste_Perso_Historique" %>
<!DOCTYPE html>
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

    <title>Accueil</title>
    <% Utilisateur user = (Utilisateur) session.getAttribute("utilisateur");
        Liste_Perso perso = new Liste_Perso(request);
        Liste_Perso_Historique perso_historique = new Liste_Perso_Historique(request);%>

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
<div class="container login-container">
    <div class="container">
        <div class="row my-2">
            <div class="col-lg-8 order-lg-2">
                <ul class="nav nav-tabs">
                    <li class="nav-item">
                        <a href="" data-target="#profil" data-toggle="tab" class="nav-link active">Profil</a>
                    </li>
                    <li class="nav-item">
                        <a href="" data-target="#historique" data-toggle="tab" class="nav-link">Historique</a>
                    </li>
                    <li class="nav-item">
                        <a href="" data-target="#edit" data-toggle="tab" class="nav-link">Paramètres</a>
                    </li>
                </ul>
                <div class="tab-content py-4">
                    <div class="tab-pane active" id="profil">
                        <h5 class="mb-3">Informations personnelles</h5>
                        <div class="row">
                            <div class="col-md-6">
                                <br/>
                                <h6><%=user.getNom()%></h6>

                                <br/>
                                <h6> <%=user.getNumChambre()%> </h6>
                                <br/>

                            </div>
                            <div class="col-md-12">
                                <h5 class="mt-2"><span class="fa fa-clock-o ion-clock float-right"></span> Évenements inscrits</h5>
                                <br/>
                                <table class="table table-sm table-hover table-striped">
                                    <tbody>
                                    <%=perso.getEventPerso()%>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <!--/row-->
                    </div>

                    <div class="tab-pane" id="historique">

                        <table class="table table-hover table-striped">
                            <tbody>
                            <%=perso_historique.getEventPersoHistorique()%>
                            </tbody>
                        </table>
                    </div>

                    <div class="tab-pane" id="edit">
                        <form role="form" action="ModifyProfil" method="post">

                            <div class="form-group row">
                                <label class="col-lg-3 col-form-label form-control-label">Chambre ou adresse</label>
                                <div class="col-lg-9">
                                    <input class="form-control" type="text" value="<%=user.getNumChambre()%>" name="chambre">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-lg-3 col-form-label form-control-label">Mot de passe</label>
                                <div class="col-lg-9">
                                    <input class="form-control" type="password" name="password">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-lg-3 col-form-label form-control-label"></label>
                                <div class="col-lg-9">
                                    <input type="reset" class="btn btn-secondary" value="Cancel">
                                    <input type="submit" class="btnSubmit" value="Save Changes" name="connect">
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="col-lg-4 order-lg-1 text-center">
                <%=user.getPp()%>
                <h6 class="mt-2">Changer sa photo de profil</h6>
                <label class="custom-file ">
                    <form action="ModifyPp" method="post" enctype="multipart/form-data">
                        <input type="file" name="pp" />
                        <input type="submit" class="btnSubmit" value="Modifier" name="connect"/>
                    </form>
                </label>
            </div>
        </div>
    </div>
</div>

<footer class="footer ">
    <nav class="navbar bottom navbar-expand-sm navbar-dark bg-dark " >
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