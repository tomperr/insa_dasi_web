/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import dasi.dasi_projet.metier.modele.Utilisateur;
import dasi.dasi_projet.metier.modele.Employe;
import dasi.dasi_projet.metier.service.Service;

/**
 *
 * @author TomPC
 */
public class MenuNavigationAction extends Action {
    MenuNavigationAction(Service service)
    {
        super(service);
    }

    @Override
    public void execute(HttpServletRequest request)
    {
        HttpSession session = request.getSession(true);
        Utilisateur user = (Utilisateur)session.getAttribute("utilisateur");
        
        if (user != null) {
            if (user instanceof Employe) {
                // employe
                request.setAttribute("role", "Employe");
            } else {
                // client
                request.setAttribute("role", "Client");
            }
        } else {
            // not connected
            request.setAttribute("role", "Utilisateur");
        }
    }
}
