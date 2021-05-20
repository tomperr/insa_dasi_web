/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Zaleks
 */
public class CommencerConsultationSerialisation extends Serialisation
{

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        JsonObject container = new JsonObject();
        
        boolean consultationOk = (boolean)request.getAttribute("consultationOk");
        
        container.addProperty("status", consultationOk);
        
        // serialisation et écriture sur le flux de sortie de la reponse
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container,out);
        out.close();   
    }
    
}
