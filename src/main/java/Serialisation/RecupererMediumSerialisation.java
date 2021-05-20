/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dasi.dasi_projet.metier.modele.Astrologue;
import dasi.dasi_projet.metier.modele.Cartomancien;
import dasi.dasi_projet.metier.modele.Client;
import dasi.dasi_projet.metier.modele.Medium;
import dasi.dasi_projet.metier.modele.Spirite;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Zaleks
 */
public class RecupererMediumSerialisation extends Serialisation
{

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        Medium medium = (Medium)request.getAttribute("medium");
        
        JsonObject container = new JsonObject();
        
        JsonObject jsonMedium = new JsonObject();
        
        if(medium != null)
        {
            container.addProperty("status", true);
            
            jsonMedium.addProperty("id", medium.getId());
            jsonMedium.addProperty("denomination", medium.getDenomination());
            jsonMedium.addProperty("genre", medium.getGenre());
            jsonMedium.addProperty("presentation", medium.getPresentation());

            if (medium instanceof Astrologue) {
                Astrologue astrologue = (Astrologue)medium;
                jsonMedium.addProperty("role", "Astrologue");
                jsonMedium.addProperty("formation", astrologue.getFormation());
                jsonMedium.addProperty("promotion", astrologue.getPromotion());

            } else if (medium instanceof Cartomancien) {
                Cartomancien cartomancien = (Cartomancien)medium;
                jsonMedium.addProperty("role", "Cartomancien");

            } else {
                Spirite spirite = (Spirite)medium;
                jsonMedium.addProperty("role", "Spirite");
                jsonMedium.addProperty("support", spirite.getSupport());
            }
            
            container.add("medium", jsonMedium);
        }
        else
        {
            container.addProperty("status", false);
            container.addProperty("medium", false);
        }
        
        
        // serialisation et écriture sur le flux de sortie de la reponse
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container,out);
        out.close();    
    }
    
}
