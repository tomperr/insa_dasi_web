/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serialisation;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author aminejuventino
 */
public abstract class Serialisation {
    
    protected PrintWriter getWriter(HttpServletResponse response) throws IOException{
        response.setContentType("application/json;charset=UTF-8");
        return response.getWriter();
    }
    
    public abstract void serialiser(HttpServletRequest request,HttpServletResponse response) throws IOException;

}
