/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Action;

import dasi.dasi_projet.metier.modele.Consultation;
import dasi.dasi_projet.metier.service.Service;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Zaleks
 */
public class RecupererConsultationAction  extends Action
{

    public RecupererConsultationAction(Service service)
    {
        super(service);
    }
    

    @Override
    public void execute(HttpServletRequest request)
    {
        long idConsultation = Long.parseLong(request.getParameter("id"));
                
        try{
            Consultation consultation = service.rechercherConsultation(idConsultation);
            request.setAttribute("consultation", consultation);
        } catch(Exception ex){
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur !");
            ex.printStackTrace(); 
        } 
    }
    
    
}
