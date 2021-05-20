/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Action;

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
public class GenererPredictionsAction extends Action {
    public GenererPredictionsAction(Service service) {
        super(service);
    }
    @Override 
    public void execute(HttpServletRequest request){
        try{
            
            // getPredictions
            // (String couleur, String animal, int amour, int sante, int travail)
            
            // TODO : get employe
            HttpSession session = request.getSession(true);
            Utilisateur user = (Utilisateur) session.getAttribute("utilisateur");
            
            ProfilAstral pa = new ProfilAstral();

            // sanity check
            if (user instanceof Employe) {
                Employe emp = (Employe) user;
                List<Consultation> consultations = emp.getConsultations();
                
                for (Consultation c: consultations) {
                    if (c.getDate_fin() == null) {
                        // current
                        pa = c.getClient().getProfil_astral();
                    }
                }
            } else {
                throw new Exception("L'utilisateur faisant la demande n'est pas connecté");
            } 
            
            // GET PARAMETERS 
            int amour = Integer.parseInt(request.getParameter("amour"));
            int sante = Integer.parseInt(request.getParameter("sante"));
            int travail = Integer.parseInt(request.getParameter("travail"));
            
            String couleur = pa.getCouleur();
            String animal = pa.getAnimal();
            
            List<String> predictions = service.genererPredictions(couleur, animal, amour, sante, travail);
            
            if (predictions == null){
                Logger.getAnonymousLogger().log(Level.SEVERE, "Non trouvée!");
            } else {
                request.setAttribute("predictions", predictions);
            }
        }
        catch(Exception ex){
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur !");
            ex.printStackTrace(); 
        } 
    }
}
