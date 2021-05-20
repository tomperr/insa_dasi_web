/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dasi.dasi_projet.metier.modele.Consultation;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Zaleks
 */
public class RecupererConsultationSerialisation  extends Serialisation
{

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        JsonObject container = new JsonObject();
        Consultation consultation = (Consultation) request.getAttribute("consultation");
        if ( consultation != null )
        {
           container.addProperty("status", true);
            
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                    
           JsonObject consultationObject = new JsonObject();
            consultationObject.addProperty("id", consultation.getId());
            
            Date date_debut = consultation.getDate_debut();
            if (date_debut != null) {
             String strDateDebut = dateFormat.format(date_debut);
              consultationObject.addProperty("date_debut", strDateDebut);
            } else {
              consultationObject.addProperty("date_debut", (String)null);
            }
            
            Date date_fin = consultation.getDate_fin();
            if (date_fin != null) {
             String strDateFin = dateFormat.format(date_fin);
              consultationObject.addProperty("date_fin", strDateFin);
            } else {
              consultationObject.addProperty("date_fin", (String)null);
            }
            
            consultationObject.addProperty("commentaire", consultation.getCommentaire());
            consultationObject.addProperty("client_id", consultation.getClient().getId());
            consultationObject.addProperty("employe_id", consultation.getEmploye().getId());
            consultationObject.addProperty("medium_id", consultation.getMedium().getId());
            
            container.add("consultation", consultationObject);
        }
        else
        {
            container.addProperty("status", false);
        }
        // serialisation et écriture sur le flux de sortie de la reponse
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container,out);
        out.close();    
    }
    
}
