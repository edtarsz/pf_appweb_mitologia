/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package org.itson.mythify.filtro;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author user
 */
@WebFilter("/*")
public class FiltroAutenticacion implements Filter {

    private static final boolean DEBUG = true;
    private static final String[] URLPUBLICAS = {"registrar.jsp", "iniciarSesion.jsp", "/style/style.css", "/img/statue.png", "/script/registrar.js", "/SVUsuario"};

    private final FilterConfig filterConfig = null;

    public FiltroAutenticacion() {
    }

    /*
        Verifica que exista una sesión activa y que el usuario esté identificado
     */
    private boolean isLogged(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        boolean logged = (session != null && session.getAttribute("usuario") != null);
        return logged;
    }

    private boolean isURLPrivate(String url) {
        for (String path : URLPUBLICAS) {
            if (url.endsWith(path)) {
                return false;
            }
        }
        return true;
    }

    private String getURL(HttpServletRequest httpServletRequest) {
        String URI = httpServletRequest.getRequestURI();
        String path = URI.substring(httpServletRequest.getContextPath().length());

        return path;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        // Agregar headers para prevenir caché
        httpServletResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        httpServletResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0
        httpServletResponse.setHeader("Expires", "0"); // Proxies

        String path = this.getURL(httpServletRequest);
        boolean isURLPrivate = this.isURLPrivate(path);
        boolean isLogged = this.isLogged(httpServletRequest);

        if (!isLogged && isURLPrivate) {
            if (!path.endsWith("iniciarSesion.jsp")) {
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/iniciarSesion.jsp");
                return;
            }
        }
        chain.doFilter(request, response);
    }
}