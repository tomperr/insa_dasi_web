/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Action;

import Serialisation.ConnexionSerialisation;
import Serialisation.InscriptionClientSerialisation;
import Serialisation.ListeMediumSerialisation;
import Serialisation.RecupererSessionSerialisation;
import Serialisation.Serialisation;
import dasi.dasi_projet.dao.JpaUtil;
import dasi.dasi_projet.metier.service.Service;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author aminejuventino
 */
@WebServlet(name = "ActionServlet", urlPatterns = {"/ActionServlet"})
public class ActionServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String todo = request.getParameter("todo");
        Action action = null;
        Serialisation serialisation = null;
        Service service = new Service();
        switch(todo){
            
            case "inscription" :{
                action = new InscriptionClientAction(service);
                serialisation = new InscriptionClientSerialisation();
                break;
            }
            
            case "connexion" :{
                action = new ConnexionAction(service);
                serialisation = new ConnexionSerialisation();
                break;
            }
            
            case "listerMediumsAccueil":{
                action = new ListeMediumAction(service);
                serialisation = new ListeMediumSerialisation();
                break;
            }
           
            case "testSession": {
                action = new RecupererSessionAction(service);
                serialisation = new RecupererSessionSerialisation();
                break;
            }
            
        }
        if (action != null && serialisation != null){
            action.execute(request);
            serialisation.serialiser(request,response);
        }
        else{
            response.sendError(400,"Pas d'action ou de serialisation pour traiter cette requete");
        }
    }

  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
 
    
  @Override
  public void init() throws ServletException {
    super.init();
    JpaUtil.init();
  }

  @Override
  public void destroy() {
    JpaUtil.destroy();
    super.destroy();
  }

}
