<%-- Document : iniciarSesion Created on : 18 oct 2024, 5:40:00 p.m. Author : user --%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
  <meta http-equiv="Pragma" content="no-cache">
  <meta http-equiv="Expires" content="0">

  <!-- Icono de la página -->
  <link rel="icon" type="image/x-icon" href="<c:url value="${pageContext.request.contextPath}/img/icon.svg"/>">
  <!-- CSS Stylesheets -->
  <link rel="stylesheet" href="<c:url value='/style/style.css'/>">

  <script defer src="<c:url value='/script/validarLogin.js'/>"></script>

  <title>Mythify</title>
</head>

<body>
<main class="container-main">
  <img src="img/statue.png" alt="" id="statue-img"/>
  <div class="main-conent">
    <!-- <img src="" alt="" /> -->
    <h1>Iniciar Sesión</h1>

    <p class="description">
      Mythify es un blog para compartir posts con temática de mitologías
    </p>

    <form action="SVUsuario" method="post" id="form-iniciar-sesion" novalidate>
      <input type="hidden" name="action" value="iniciarSesion">
      <label for="correo">Correo</label>
      <input type="email" class="input-format" autocomplete="email" name="correo" id="correo"/>
      <div id="correoError" class="error-formulario"></div>
      <label for="contrasena">Contraseña</label>
      <input type="password" class="input-format" name="contrasenia" autocomplete="current-password" id="contrasena"/>
      <div id="contrasenaError" class="error-formulario"></div>
      <div id="usuarioError" class="error-formulario-usuario"></div>
      <button type="submit" class="btn-format">Ingresar</button>
      <p>
        ¿Aun no tienes una cuenta?
        <span><a href="registrar.jsp">Registrar cuenta </a></span>
      </p>
    </form>
  </div>
</main>
</body>

</html>