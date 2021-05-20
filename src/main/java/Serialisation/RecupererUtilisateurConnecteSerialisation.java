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
import dasi.dasi_projet.metier.modele.ProfilAstral;
import dasi.dasi_projet.metier.modele.Utilisateur;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author TomPC
 */
public class RecupererUtilisateurConnecteSerialisation extends Serialisation {
    
    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
         try{
            JsonObject container = new JsonObject();
            Utilisateur user = (Utilisateur) request.getAttribute("user");
            
            if ( user != null ){
                
                JsonObject userObject = new JsonObject();
                
                JsonArray consultationsContainer = new JsonArray();
                List<Consultation> consultations;
                
                container.addProperty("connected", true);
              
                if (user instanceof Employe) {
                    Employe emp = (Employe) user;
                    container.addProperty("role", "Employe");
                    userObject.addProperty("disponible", emp.isDisponible());
                    
                    consultations = emp.getConsultations(); 
                    
                } else {
                    Client client = (Client) user;
                    container.addProperty("role", "Client");
                    
                    consultations = client.getConsultations();
                    
                    JsonObject profilAstralObject = new JsonObject();
                    ProfilAstral pa = client.getProfil_astral();
                    
                    profilAstralObject.addProperty("animal", pa.getAnimal());
                    profilAstralObject.addProperty("couleur", pa.getCouleur());
                    profilAstralObject.addProperty("signe_chinois", pa.getSigne_chinois());
                    profilAstralObject.addProperty("signe_zodiaque", pa.getSigne_zodiaque());
                    
                    userObject.add("profil_astral", profilAstralObject);
                }
                
                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
                for (Consultation c: consultations) {

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
                    
                    JsonObject mediumObject = new JsonObject();
                    Medium medium = c.getMedium();
                    mediumObject.addProperty("denomination", medium.getDenomination());
                    if (medium instanceof Cartomancien) {
                        mediumObject.addProperty("specialite", "Cartomancien");
                    } else if (medium instanceof Astrologue) {
                        mediumObject.addProperty("specialite", "Astrologue");
                    } else {
                        mediumObject.addProperty("specialite", "Spirite");
                    }
                    
                    consultationObject.add("medium", mediumObject);
                    
                    consultationsContainer.add(consultationObject);
                }

                userObject.add("consultations", consultationsContainer);
                
                userObject.addProperty("id",         user.getId());
                userObject.addProperty("nom",        user.getNom());
                userObject.addProperty("prenom",     user.getPrenom());
                userObject.addProperty("genre",      user.getGenre());
                userObject.addProperty("mail",       user.getMail());
                userObject.addProperty("telephone",  user.getTelephone());
                
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
            ex.printStackTrace();
            throw new UnsupportedOperationException(ex.getMessage()); //To change body of generated methods, choose Tools | Templates.
        }
    }
}
