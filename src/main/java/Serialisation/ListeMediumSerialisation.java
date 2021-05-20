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
import dasi.dasi_projet.metier.modele.Medium;
import dasi.dasi_projet.metier.modele.Spirite;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author aminejuventino
 */
public class ListeMediumSerialisation extends Serialisation{

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            
            List<Medium> mediums = (List<Medium>)request.getAttribute("mediums");
        
            JsonObject container = new JsonObject();

            JsonArray jsonListeAstrologues = new JsonArray();
            JsonArray jsonListeCartomanciens = new JsonArray();
            JsonArray jsonListeSpirites = new JsonArray();

            for (Medium m : mediums) {
                JsonObject jsonMedium = new JsonObject();
                jsonMedium.addProperty("denomination", m.getDenomination());
                jsonMedium.addProperty("presentation", m.getPresentation());
                jsonMedium.addProperty("id", m.getId());
                if (m.getGenre()  == 'M'){
                      jsonMedium.addProperty("genre", "Homme");
                }else{
                      jsonMedium.addProperty("genre", "Femme");
                }

                if (m instanceof Astrologue) {
                    Astrologue astrologue = (Astrologue) m;
                    jsonMedium.addProperty("type", "Astrologue");
                    jsonMedium.addProperty("promotion", astrologue.getPromotion());
                    jsonMedium.addProperty("formation", astrologue.getFormation());
                    jsonListeAstrologues.add(jsonMedium);
                } else if (m instanceof Cartomancien) {
                    jsonMedium.addProperty("type", "Cartomancien");
                    jsonListeCartomanciens.add(jsonMedium);
                } else {
                    Spirite spirite = (Spirite) m;
                    jsonMedium.addProperty("type", "Spirite");
                    jsonMedium.addProperty("support", spirite.getSupport());
                    jsonListeSpirites.add(jsonMedium);
                }
            }

            container.add("astrologues", jsonListeAstrologues);
            container.add("cartomanciens", jsonListeCartomanciens);
            container.add("spirites", jsonListeSpirites);

            response.setContentType("application/json;charset=UTF -8");
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
