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
import dasi.dasi_projet.metier.modele.Medium;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Zaleks
 */
public class RecupererStatistiquesSerialisation extends Serialisation
{

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        JsonObject container = new JsonObject();
        
        Map<Medium,Integer> stat_consultation_medium = (Map<Medium,Integer>)request.getAttribute("stat_consultation_medium");
        List<Medium> top_medium = (List<Medium>)request.getAttribute("top_medium");
        Map<String, Integer> repartition_client_employe = (Map<String,Integer>)request.getAttribute("repartition_client_employe");
        
        if(stat_consultation_medium != null && top_medium != null && repartition_client_employe != null)
        {
            container.addProperty("status", true);
            
            JsonArray stat_consultationObj = new JsonArray();
            for (Map.Entry<Medium,Integer> entry : stat_consultation_medium.entrySet())
            {
                JsonObject entryObj = new JsonObject();
                entryObj.addProperty("medium_denomination", entry.getKey().getDenomination());
                entryObj.addProperty("value", entry.getValue());
                
                stat_consultationObj.add(entryObj);
            }
            container.add("stat_consultation_medium", stat_consultationObj);
            
            JsonArray top_mediumObj = new JsonArray();
            for(Medium med : top_medium)
            {
                top_mediumObj.add(med.getDenomination());
            }
            container.add("top_medium", top_mediumObj);
            
            JsonArray repartitionObj = new JsonArray();
            for (Map.Entry<String,Integer> entry : repartition_client_employe.entrySet())
            {
                JsonObject entryObj = new JsonObject();
                entryObj.addProperty("employe", entry.getKey());
                entryObj.addProperty("value", entry.getValue());
                
                repartitionObj.add(entryObj);
            }
            container.add("repartition_client_employe", repartitionObj);
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
