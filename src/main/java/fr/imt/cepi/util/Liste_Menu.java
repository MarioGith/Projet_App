package fr.imt.cepi.util;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Liste_Menu implements Serializable {

    private static final long serialVersionUID = 6297385302078200511L;

    public ArrayList<Evenement> liste = new ArrayList();

    public Liste_Menu(HttpServletRequest request) {
        ArrayList<Evenement> id = new ArrayList();

        Connection con = (Connection) request.getServletContext().getAttribute("DBConnection");
        PreparedStatement ps = null;
        ResultSet rs = null;
        Date utilDate = new Date();
        java.sql.Timestamp maintenant= new java.sql.Timestamp(utilDate.getTime());

        try {
            ps = con.prepareStatement(
                    "select * from tst.evenement where datec < ? order by datec DESC");
            ps.setTimestamp(1,maintenant);
            rs = ps.executeQuery();
            while(rs.next()){
                Evenement event = new Evenement(rs.getString("organisateur"),rs.getString("type_event"),rs.getInt("idevent"),rs.getString("description"),rs.getString("prix"),rs.getTimestamp("datec"), rs.getInt("id_createur"));
                liste.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String getMenu() {
        String fin="";
        for (int i=0;i<liste.size();i++) {
            fin+="<br>";
            fin+= "<div class='display-1'>"+"Ce menu a été fait par " + liste.get(i).getOrganisateur()+" le "+liste.get(i).getDate()+"<br>";
            fin+="<div class='event'>";
            fin += liste.get(i).getMenu();
            fin+="</div>";
            fin+="</div>";
            fin+="<br>";
        }
        return fin;
    }

}