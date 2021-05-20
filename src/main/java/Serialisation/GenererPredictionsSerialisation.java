/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dasi.dasi_projet.metier.modele.Client;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author TomPC
 */
public class GenererPredictionsSerialisation extends Serialisation {
   
    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            
            JsonObject container = new JsonObject();
            List<String> predictions = (List<String>) request.getAttribute("predictions");
            
            if (predictions != null) {
                container.addProperty("amour", predictions.get(0));
                container.addProperty("sante", predictions.get(1));
                container.addProperty("travail", predictions.get(2));
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
