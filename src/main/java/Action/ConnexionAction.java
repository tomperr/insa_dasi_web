/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Action;

import dasi.dasi_projet.metier.modele.Utilisateur;
import dasi.dasi_projet.metier.service.Service;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author aminejuventino
 */
public class ConnexionAction extends Action {
    public ConnexionAction(Service service) {
        super(service);
    }
    @Override 
    public void execute(HttpServletRequest request){
        try{
            HttpSession session = request.getSession(true); 
            String login= request.getParameter("login");
            String mdp =  request.getParameter("password");
            
            /* TODO output your page here. You may use following sample code. */
            Utilisateur utilisateur = service.authentifierUtilisateur(login,mdp);
            if (utilisateur ==null){
                Logger.getAnonymousLogger().log(Level.SEVERE, "Non trouvée!");
            } else {
                request.setAttribute("utilisateur", utilisateur);
                session.setAttribute("utilisateur", utilisateur);
            }
        }
        catch(Exception ex){
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur !");
            ex.printStackTrace(); 
        } 
    }
}
