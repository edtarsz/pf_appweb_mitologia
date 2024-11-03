/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package org.itson.mythify.Servlet;

import java.io.IOException;

import org.itson.mythify.controller.post.FacadePostBO;
import org.itson.mythify.controller.post.IFacadePostBO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author user
 */
public class SVPost extends HttpServlet {

    private IFacadePostBO postBO;

    @Override
    public void init() throws ServletException {
        super.init();
        postBO = new FacadePostBO();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "publicarPost" ->
                    publicarPost(request, response);
                case "editarPost" ->
                    editarPost(request, response);
                case "borrarPost" ->
                    borrarPost(request, response);
                default ->
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private void publicarPost(HttpServletRequest request, HttpServletResponse response) {

    }

    private void editarPost(HttpServletRequest request, HttpServletResponse response) {

    }

    private void borrarPost(HttpServletRequest request, HttpServletResponse response) {

    }

}
