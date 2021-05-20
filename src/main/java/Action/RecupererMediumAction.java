/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Action;

import dasi.dasi_projet.metier.modele.Medium;
import dasi.dasi_projet.metier.service.Service;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Zaleks
 */
public class RecupererMediumAction extends Action
{

    public RecupererMediumAction(Service service)
    {
        super(service);
    }
    
    @Override
    public void execute(HttpServletRequest request)
    {
        long idMedium = Long.parseLong(request.getParameter("id"));
                
        try{
            Medium medium = service.rechercherMediumParId(idMedium);
            request.setAttribute("medium", medium);
        } catch(Exception ex){
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur !");
        } 
    }
    
}
