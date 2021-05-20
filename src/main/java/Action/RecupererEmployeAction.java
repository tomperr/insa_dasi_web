/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Action;

import dasi.dasi_projet.metier.modele.Utilisateur;
import dasi.dasi_projet.metier.modele.Employe;
import dasi.dasi_projet.metier.service.Service;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author TomPC
 */
public class RecupererEmployeAction extends Action
{

    RecupererEmployeAction(Service service)
    {
        super(service);
    }

    @Override
    public void execute(HttpServletRequest request)
    {
        HttpSession session = request.getSession(true);
        
        Utilisateur user = (Utilisateur)session.getAttribute("utilisateur");
        String mail = user.getMail();
        String mdp = user.getMotdepasse();
        
        Utilisateur updated_user = this.service.authentifierUtilisateur(mail, mdp);
        if (updated_user != null) {
            if (updated_user instanceof Employe) {
                Employe emp = (Employe)updated_user;
                request.setAttribute("employe", emp);
            }
        } else {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur !");
        }

    }
    
};
