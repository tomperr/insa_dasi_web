/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dasi.dasi_projet.metier.modele.Utilisateur;
import java.io.IOException;
import java.io.PrintWriter;
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
public class ProfilUtilisateurSerialisation extends Serialisation
{

    @Override
    public void serialiser(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        JsonObject container = new JsonObject();
        
        Utilisateur user = (Utilisateur)request.getAttribute("user");
        JsonObject userObject = new JsonObject();
        if(user != null)
        {
            container.addProperty("authentification", true);
            
            userObject.addProperty("id",         user.getId());
            userObject.addProperty("nom",        user.getNom());
            userObject.addProperty("prenom",     user.getPrenom());
            userObject.addProperty("genre",      user.getGenre());
            userObject.addProperty("mail",       user.getMail());
            userObject.addProperty("telephone",  user.getTelephone());
            
            container.add("utilisateur", userObject);
        }
        else
        {
            container.addProperty("authentification", false);
            container.addProperty("utilisateur", false);
        }
        
        PrintWriter out = this.getWriter(response);
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        gson.toJson(container, out);
        out.close();
    }
    
}
