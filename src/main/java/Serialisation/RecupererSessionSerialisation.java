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
import dasi.dasi_projet.metier.modele.Client;
import dasi.dasi_projet.metier.modele.Consultation;
import dasi.dasi_projet.metier.modele.Employe;
import dasi.dasi_projet.metier.modele.Utilisateur;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author TomPC
 */
public class RecupererSessionSerialisation extends Serialisation {
    
    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
         try{
            JsonObject container = new JsonObject();
            Utilisateur user = (Utilisateur) request.getAttribute("utilisateur");
            
            if ( user != null ){
                
                JsonObject userObject = new JsonObject();
                
                container.addProperty("connected", true);
                
                if (user instanceof Employe) {
                    Employe emp = (Employe) user;
                    container.addProperty("role", "Employe");
                    userObject.addProperty("disponible", emp.isDisponible());
                    
                    JsonArray consultationsContainer = new JsonArray();
                    
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
                    for (Consultation c: emp.getConsultations()) {
                        
                        JsonObject consultationObject = new JsonObject();
                        consultationObject.addProperty("id", c.getId());
                        String strDateDebut = dateFormat.format(c.getDate_debut());  
                        consultationObject.addProperty("date_debut", strDateDebut);
                        String strDateFin = dateFormat.format(c.getDate_fin());  
                        consultationObject.addProperty("date_fin", strDateFin);
                        consultationObject.addProperty("commentaire", c.getCommentaire());
                        
                        consultationsContainer.add(consultationObject);
                    }
                    
                    userObject.add("consultations", consultationsContainer);
                    
                } else {
                    Client client = (Client) user;
                    container.addProperty("role", "Client");
                    
                    JsonArray consultationsContainer = new JsonArray();
                    
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
                    for (Consultation c: client.getConsultations()) {
                        
                        JsonObject consultationObject = new JsonObject();
                        consultationObject.addProperty("id", c.getId());
                        String strDateDebut = dateFormat.format(c.getDate_debut());  
                        consultationObject.addProperty("date_debut", strDateDebut);
                        String strDateFin = dateFormat.format(c.getDate_fin());  
                        consultationObject.addProperty("date_fin", strDateFin);
                        consultationObject.addProperty("commentaire", c.getCommentaire());
                        
                        consultationsContainer.add(consultationObject);
                    }
                    
                    userObject.add("consultations", consultationsContainer);
                }
                
                userObject.addProperty("id",         user.getId());
                userObject.addProperty("nom",        user.getNom());
                userObject.addProperty("prenom",     user.getPrenom());
                userObject.addProperty("genre",      user.getGenre());
                userObject.addProperty("mail",       user.getMail());
                container.addProperty("telephone",  user.getTelephone());
                
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
        catch(Exception ex){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
}
