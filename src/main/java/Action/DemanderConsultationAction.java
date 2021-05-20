/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Action;

import dasi.dasi_projet.metier.modele.Client;
import dasi.dasi_projet.metier.modele.Consultation;
import dasi.dasi_projet.metier.modele.Medium;
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
public class DemanderConsultationAction extends Action{

    public DemanderConsultationAction(Service service) {
        super(service);
    }

    @Override
    public void execute(HttpServletRequest request) {
        try{
            HttpSession session = request.getSession(true); 
            long id= Long.parseLong(request.getParameter("id"));
            
            Medium medium = this.service.rechercherMediumParId(id);
            Client client = (Client) session.getAttribute("utilisateur");
            /* TODO output your page here. You may use following sample code. */
            Consultation consultation = service.demanderConsultation(client,medium);
            if (consultation ==null){
                request.setAttribute("ConsultationCree", false)  ;
            }
            else {
                request.setAttribute("ConsultationCree", true)  ;
            }
        }
        catch(Exception ex){
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur !");
            ex.printStackTrace(); 
        } 
    }
}
