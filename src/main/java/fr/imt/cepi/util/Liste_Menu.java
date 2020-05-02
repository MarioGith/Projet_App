package fr.imt.cepi.util;

import javax.servlet.http.HttpServletRequest;
import javax.sound.midi.MidiEvent;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Liste_Menu implements Serializable {

    private static final long serialVersionUID = 6297385302078200511L;

    public ArrayList<Integer> liste = new ArrayList();

    public Liste_Menu(HttpServletRequest request) {
        ArrayList<Integer> id = new ArrayList();

        Connection con = (Connection) request.getServletContext().getAttribute("DBConnection");
        PreparedStatement ps = null;
        ResultSet rs = null;
        Date utilDate = new Date();
        java.sql.Timestamp maintenant= new java.sql.Timestamp(utilDate.getTime());

        try {
            ps = con.prepareStatement(
                    "select idevent from tst.evenement where datec < ? order by datec DESC");
            ps.setTimestamp(1,maintenant);
            rs = ps.executeQuery();
            while(rs.next()){
                liste.add(rs.getInt("idevent"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String getMenu() {
        String fin="";
        for (int i=0;i<liste.size();i++) {
            fin+="<br>";
            fin+="<div class='event'>";
            fin += "<img src=\"eventMenuImage?id=" + liste.get(i) + "\""+"class=\"logoevent rounded mx-auto d-block\" alt=\"...\">";
             fin+="</div>";
            fin+="<br>";
        }
        return fin;
    }

}