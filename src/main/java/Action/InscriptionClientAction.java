/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Action;

import dasi.dasi_projet.metier.modele.Client;
import dasi.dasi_projet.metier.service.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author aminejuventino
 */
public class InscriptionClientAction extends Action{

    public InscriptionClientAction(Service service) {
        super(service);
    }
    @Override 
    public void execute(HttpServletRequest request){
        try{
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            char genre = request.getParameter("genre").charAt(0);
            String mail = request.getParameter("mail");
            String adresse = request.getParameter("adresse");
            String telephone = request.getParameter("telephone");
            String dateAv = request.getParameter("date");
            String mdp = request.getParameter("motDePasse");

            SimpleDateFormat dateFormatReceived = new SimpleDateFormat("yyyy-mm-dd");
            Date d1 = dateFormatReceived.parse(dateAv);
            SimpleDateFormat dateFormatOfService = new SimpleDateFormat("dd/mm/yyyy");
            String date = dateFormatOfService.format(d1);

            Client client = this.service.inscrireClient(prenom,nom,genre, mail,mdp, telephone, date, adresse);
            request.setAttribute("client",client);
        }
        catch(Exception ex){
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur !");
            ex.printStackTrace(); 
        } 
    }

}
