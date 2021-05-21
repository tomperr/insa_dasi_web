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
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dasi.dasi_projet.metier.modele.Utilisateur;
import java.io.PrintWriter;

/**
 *
 * @author TomPC
 */
public class MenuNavigationSerialisation extends Serialisation {
    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject container = new JsonObject();
        String role = (String) request.getAttribute("role");

        JsonArray linksContainer = new JsonArray();
        
        switch (role) {
            case "Utilisateur": {
                JsonObject userLinkOneObject = new JsonObject();
                userLinkOneObject.addProperty("label", "Connexion");
                userLinkOneObject.addProperty("name", "connect");
                        
                JsonObject userLinkTwoObject = new JsonObject();
                userLinkTwoObject.addProperty("label", "S'inscrire");
                userLinkTwoObject.addProperty("name", "register");
                
                linksContainer.add(userLinkOneObject);
                linksContainer.add(userLinkTwoObject);
                break;
            }

            case "Employe": {
                JsonObject employeLinkOneObject = new JsonObject();
                employeLinkOneObject.addProperty("label", "Espace Employé");
                employeLinkOneObject.addProperty("name", "employee");
                        
                JsonObject employeLinkTwoObject = new JsonObject();
                employeLinkTwoObject.addProperty("label", "Déconnexion");
                employeLinkTwoObject.addProperty("name", "disconnect");
                
                linksContainer.add(employeLinkOneObject);
                linksContainer.add(employeLinkTwoObject);
                break;
            }

            case "Client": {
                JsonObject clientLinkOneObject = new JsonObject();
                clientLinkOneObject.addProperty("label", "Espace Client");
                clientLinkOneObject.addProperty("name", "customer");
                        
                JsonObject clientLinkTwoObject = new JsonObject();
                clientLinkTwoObject.addProperty("label", "Déconnexion");
                clientLinkTwoObject.addProperty("name", "disconnect");
                
                linksContainer.add(clientLinkOneObject);
                linksContainer.add(clientLinkTwoObject);
                break;
            }
        }
        
        container.add("liens", linksContainer);
        
        // serialisation et écriture sur le flux de sortie de la reponse
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container,out);
        out.close();
    }
}
