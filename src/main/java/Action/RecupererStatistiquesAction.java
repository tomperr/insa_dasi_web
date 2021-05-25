/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Action;

import dasi.dasi_projet.metier.modele.Medium;
import dasi.dasi_projet.metier.service.Service;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Zaleks
 */
public class RecupererStatistiquesAction extends Action
{

    public RecupererStatistiquesAction(Service service)
    {
        super(service);
    }
    
    
    @Override
    public void execute(HttpServletRequest request)
    {   
        try
        {
            Map<Medium,Integer> stat_consultation_medium = service.statistiquesMediumsConsultations();
            List<Medium> top_medium = service.statistiquesTopMediums();
            Map<String, Integer> repartition = service.calculerRepartitionClientsParEmployes();
            
            request.setAttribute("stat_consultation_medium", stat_consultation_medium);
            request.setAttribute("top_medium", top_medium);
            request.setAttribute("repartition_client_employe", repartition);
        } catch(Exception ex){
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur !");
        }
    }
    
}
