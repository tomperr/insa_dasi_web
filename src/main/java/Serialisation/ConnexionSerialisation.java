/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dasi.dasi_projet.metier.modele.Employe;
import dasi.dasi_projet.metier.modele.Client;
import dasi.dasi_projet.metier.modele.Utilisateur;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author aminejuventino
 */
public class ConnexionSerialisation extends Serialisation{
    
    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
         try{
            JsonObject container = new JsonObject();
            Utilisateur utilisateur= (Utilisateur) request.getAttribute("utilisateur");
            if ( utilisateur != null ){
               container.addProperty("connexion", true);
               if (utilisateur instanceof Employe) {
                   container.addProperty("role", "Employe");
               } else {
                   container.addProperty("role", "Client");
               }
            }
            else{
                container.addProperty("connexion", false);
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
