<%-- Document : main.jsp Created on : 18 oct 2024, 5:43:38 p.m. Author : user
--%> <%@page import="com.mycompany.redsocial.Entidad.Usuario"%>
<%@page import="java.util.Base64"%>
<%@page import="com.mycompany.redsocial.Controller.UsuarioDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>JSP Page</title>
    </head>
    <body>
        <a href="registrar.jsp">Registrarse</a><br>
        <a href="iniciarSesion.jsp">Iniciar Sesión</a><br>
        <input type="hidden" name="action" value="cerrarSesion">
        <a href="SVCerrarSesion">Cerrar Sesión</a>
    <center>
        <h1>Hello World!</h1>
    </center>

    <%
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        String avatarBase64 = null;
    %>
    <p>ID: <%=usuario.getId()%></p>
    <p>Usuario: <%=usuario.getNombre()%></p>
    <p>Correo: <%=usuario.getCorreo()%></p>
    <p>Avatar</p>
    <%
        avatarBase64 = Base64.getEncoder().encodeToString(usuario.getAvatar());
    %>
    <img src="data:image/png;base64,<%= avatarBase64 != null ? avatarBase64 : ""%>" alt="Avatar del Usuario" style="width: 100px; height: 100px;" />
</body>
</html>
