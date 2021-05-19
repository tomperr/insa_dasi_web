/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Action;

import dasi.dasi_projet.metier.modele.Medium;
import dasi.dasi_projet.metier.modele.Utilisateur;
import dasi.dasi_projet.metier.service.Service;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author aminejuventino
 */
public class RecupererSessionAction extends Action {
    public RecupererSessionAction(Service service) {
        super(service);
    }
    @Override 
    public void execute(HttpServletRequest request){
        try{
            HttpSession session = request.getSession(true);
            Utilisateur user = (Utilisateur) session.getAttribute("utilisateur");
            request.setAttribute("utilisateur", user);
            
        } catch(Exception ex){
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur !");
            ex.printStackTrace(); 
        } 
    }
}
