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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Zaleks
 */
public class RecupererClientSerialisation extends Serialisation
{

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        JsonObject container = new JsonObject();
        
        Client user = (Client)request.getAttribute("client");
        JsonObject userObject = new JsonObject();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        
        if(user != null)
        {
            container.addProperty("status", true);
            
            userObject.addProperty("id",         user.getId());
            userObject.addProperty("nom",        user.getNom());
            userObject.addProperty("prenom",     user.getPrenom());
            userObject.addProperty("genre",      user.getGenre());
            userObject.addProperty("mail",       user.getMail());
            userObject.addProperty("telephone",  user.getTelephone());
            userObject.addProperty("adresse",    user.getAdresse());
            
            Date date_naissance = user.getDate_naissance();
            if (date_naissance != null) {
                String strDateNaissance = dateFormat.format(date_naissance);
                userObject.addProperty("date_naissance", strDateNaissance);
            } else {
                userObject.addProperty("date_naissance", (String)null);
            }
            
            JsonObject profilAstralObject = new JsonObject();
            profilAstralObject.addProperty("animal", user.getProfil_astral().getAnimal());
            profilAstralObject.addProperty("couleur", user.getProfil_astral().getCouleur());
            profilAstralObject.addProperty("signe_chinois", user.getProfil_astral().getSigne_chinois());
            profilAstralObject.addProperty("signe_zodiaque", user.getProfil_astral().getSigne_zodiaque());
            
            userObject.add("profil_astral", profilAstralObject);
            
            JsonArray consultationObjs = new JsonArray();
            for(Consultation consultation : user.getConsultations())
            {       
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
                 consultationObject.addProperty("medium_denomination", consultation.getMedium().getDenomination());
                 
                 Medium medium = consultation.getMedium();
                 if(medium instanceof Astrologue)
                     consultationObject.addProperty("medium_specialty", "Astrologue");
                 else if (medium instanceof Cartomancien)
                     consultationObject.addProperty("medium_specialty", "Cartomancien");
                 else if (medium instanceof Spirite)
                     consultationObject.addProperty("medium_specialty", "Spiririte");
                
                consultationObjs.add(consultationObject);
            }
            
            userObject.add("consultations", consultationObjs);
            container.add("client", userObject);
        }
        else
        {
            container.addProperty("status", false);
            container.addProperty("client", false);
        }
        
        // serialisation et écriture sur le flux de sortie de la reponse
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container,out);
        out.close();    
    }
    
}
