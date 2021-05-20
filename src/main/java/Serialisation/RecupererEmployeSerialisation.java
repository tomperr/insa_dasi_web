/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dasi.dasi_projet.metier.modele.Astrologue;
import dasi.dasi_projet.metier.modele.Cartomancien;
import dasi.dasi_projet.metier.modele.Client;
import dasi.dasi_projet.metier.modele.Consultation;
import dasi.dasi_projet.metier.modele.Employe;
import dasi.dasi_projet.metier.modele.Medium;
import dasi.dasi_projet.metier.modele.Spirite;
import dasi.dasi_projet.metier.modele.Utilisateur;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author TomPC
 */
public class RecupererEmployeSerialisation extends Serialisation
{

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        JsonObject container = new JsonObject();
        Employe emp = (Employe) request.getAttribute("employe");

        if ( emp != null ){

            JsonObject userObject = new JsonObject();

            container.addProperty("version", "1.1");
            container.addProperty("connected", true);
            container.addProperty("role", "Employe");
            
            userObject.addProperty("disponible", emp.isDisponible());

            JsonArray consultationsContainer = new JsonArray();

            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
            for (Consultation c: emp.getConsultations()) {

                JsonObject consultationObject = new JsonObject();
                consultationObject.addProperty("id", c.getId());

                Date date_debut = c.getDate_debut();
                if (date_debut != null) {
                    String strDateDebut = dateFormat.format(date_debut);
                    consultationObject.addProperty("date_debut", strDateDebut);
                } else {
                    consultationObject.addProperty("date_debut", (String)null);
                }


                Date date_fin = c.getDate_fin();
                if (date_fin != null) {
                    String strDateFin = dateFormat.format(date_fin);
                    consultationObject.addProperty("date_fin", strDateFin);
                } else {
                    consultationObject.addProperty("date_fin", (String)null);
                }

                consultationObject.addProperty("commentaire", c.getCommentaire());

                consultationsContainer.add(consultationObject);
            }

            userObject.add("consultations", consultationsContainer);

            userObject.addProperty("id",         emp.getId());
            userObject.addProperty("nom",        emp.getNom());
            userObject.addProperty("prenom",     emp.getPrenom());
            userObject.addProperty("genre",      emp.getGenre());
            userObject.addProperty("mail",       emp.getMail());
            container.addProperty("telephone",   emp.getTelephone());

            container.add("user", userObject);
        }
        else{
            container.addProperty("connected", false);
        }
        // serialisation et écriture sur le flux de sortie de la reponse
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container,out);
        out.close();
    }
    
}
