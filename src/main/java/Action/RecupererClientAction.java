/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Action;

import dasi.dasi_projet.metier.modele.Client;
import dasi.dasi_projet.metier.service.Service;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Zaleks
 */
public class RecupererClientAction extends Action
{

    RecupererClientAction(Service service)
    {
        super(service);
    }

    @Override
    public void execute(HttpServletRequest request)
    {
        long idClient = Long.parseLong(request.getParameter("id"));
                
        try{
            Client client = service.rechercherClient(idClient);
            request.setAttribute("client", client);
        } catch(Exception ex){
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur !");
        } 
    }
    
};