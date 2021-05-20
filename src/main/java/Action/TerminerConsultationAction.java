/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Action;

import dasi.dasi_projet.metier.modele.Client;
import dasi.dasi_projet.metier.modele.Consultation;
import dasi.dasi_projet.metier.modele.Employe;
import dasi.dasi_projet.metier.modele.ProfilAstral;
import dasi.dasi_projet.metier.modele.Utilisateur;
import dasi.dasi_projet.metier.service.Service;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author TomPC
 */
public class TerminerConsultationAction extends Action{

    public TerminerConsultationAction(Service service) {
        super(service);
    }
    @Override 
    public void execute(HttpServletRequest request){
        try{

            // getting employe
            HttpSession session = request.getSession(true);
            Utilisateur user = (Utilisateur) session.getAttribute("utilisateur");
            
            Consultation current_consultation = new Consultation();

            // sanity check
            if (user instanceof Employe) {
                Employe emp = (Employe) user;
                List<Consultation> consultations = emp.getConsultations();
                
                for (Consultation c: consultations) {
                    if (c.getDate_fin() == null) {
                        current_consultation = c;
                    }
                }
            } else {
                throw new Exception("L'utilisateur faisant la demande n'est pas connecté");
            } 
            
            // getting parameters
            
            String commentaire = request.getParameter("commentaire");
            boolean finished = this.service.terminerConsultation(current_consultation, commentaire);

            if (finished)
                request.setAttribute("finished", "true");
            else
                request.setAttribute("finished", "false");
        }
        catch(Exception ex){
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur !");
            ex.printStackTrace(); 
        } 
    }

}
