<%-- Document : registrar Created on : 18 oct 2024, 5:40:47 p.m. Author : user --%>
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
  <link rel="stylesheet" href="<c:url value='/style/style.css' />">
  <!-- JavaScript -->
  <script defer src="<c:url value='/script/validarRegistro.js' />"></script>
  <script defer src="<c:url value='/script/registrar.js' />"></script>


  <title>Mythify</title>
</head>

<body>
<header class="register-header">
  <nav class="nav-bar">
    <a href="iniciarSesion.jsp">
      <img src="${pageContext.request.contextPath}/img/icon.svg" alt="Logo Mythify" class="header-svg"/>
    </a>
    <ul>
      <li>
        <a href="iniciarSesion.jsp">Iniciar Sesión</a>
      </li>
    </ul>
  </nav>
</header>
<main class="container-register">
  <%
    // Este log imprimirá en la consola del servidor la ruta generada
    System.out.println(request.getContextPath() + "/script/validarRegistro.js");
  %>
  <div class="main-register">
    <div class="content-register">
      <div class="title-register">
        <h1>Registro</h1>
        <p>
          Por favor ingrese sus datos para registrarse en Mythify
        </p>
      </div>
      <form action="SVUsuario" method="post" class="form-register" enctype="multipart/form-data"
            id="form-register-validation" novalidate>
        <input type="hidden" name="action" value="registrar">
        <div class="register-page1">
          <div class="input-group">
            <div class="label-input-g">
              <label for="nombre">Nombre</label>
              <input type="text" class="register-input" name="nombre" id="nombre" autocomplete="name"/>
              <div id="nombreError" class="error-formulario"></div>
            </div>
          </div>
          <div class="input-group">
            <div class="label-input-g">
              <label for="apellidoPaterno">Primer apellido</label>
              <input type="text" class="register-input" name="apellidoPaterno" id="apellidoPaterno"
                     autocomplete="family-name"/>
              <div id="apellidoPaternoError" class="error-formulario"></div>
            </div>

            <div class="label-input-g">
              <label for="apellidoMaterno">Segundo apellido</label>
              <input type="text" class="register-input" name="apellidoMaterno" id="apellidoMaterno"
                     autocomplete="family-name"/>
              <div id="apellidoMaternoError" class="error-formulario"></div>
            </div>
          </div>

          <div class="input-group">
            <div class="label-input-g">
              <label for="estado">Estado</label>
              <input type="text" class="register-input" name="estado" id="estado" autocomplete="address-level1"/>
              <div id="estadoError" class="error-form"></div>
            </div>
            <div class="label-input-g">
              <label for="ciudad">Ciudad</label>
              <input type="text" class="register-input" name="ciudad" id="ciudad" autocomplete="address-level2"/>
              <div id="ciudadError" class="error-form"></div>
            </div>
          </div>

          <div class="input-group">
            <div class="label-input-g">
              <label for="municipio">Municipio</label>
              <input type="text" class="register-input" name="municipio" id="municipio" autocomplete="address-level3"/>
              <div id="municipioError" class="error-form"></div>
            </div>

            <div class="label-input-g">
              <label for="fechaNacimiento">Fecha de nacimiento</label>
              <input type="date" class="register-input" name="fechaNacimiento" id="fechaNacimiento"
                     autocomplete="bday"/>
              <div id="fechaNacimientoError" class="error-form "></div>
            </div>
          </div>
        </div>

        <div class="register-page2">
          <div class="input-group">
            <div class="label-input-g">
              <label for="correo">Correo</label>
              <input type="email" class="register-input" name="correo" id="correo" autocomplete="email"/>
              <div id="correoError" class="error-formulario"></div>
            </div>
          </div>

          <div class="input-group">
            <div class="label-input-g">
              <label for="contrasena">Contraseña</label>
              <input type="password" class="register-input" name="contrasena" id="contrasena"
                     autocomplete="new-password"/>
              <div id="contrasenaError" class="error-formulario"></div>
            </div>

            <div class="label-input-g">
              <label for="confirmarConstrasena">Confirmar Contraseña</label>
              <input type="password" class="register-input"
                     name="confirmarContrasena" id="confirmarConstrasena" autocomplete="new-password"/>
              <div id="confirmarConstrasenaError" class="error-formulario"></div>
            </div>
          </div>

          <div class="input-group-col">
            <div class="label-input-g">
              <label for="estado">Avatar</label>
              <div class="container-avatar">
                <img src="" alt="" id="imgAvatar"/>
              </div>
              <div class="container-btn-upload">
                <label for="file-upload" class="btn-upload">Subir avatar</label>
                <input type="file" accept="image/jpeg, image/png" id="file-upload"
                       name="avatar"/>
                <div id="avatarError" class="error-form "></div>
              </div>
            </div>

            <div class="input-group-register">
              <div class="label-input-g">
                <label for="telefono">Telefono</label>
                <input type="tel" class="register-input" name="telefono" id="telefono" autocomplete="tel"/>
                <div id="telefonoError" class="error-formulario"></div>
              </div>

              <div class="label-input-g">
                <label for="genero">Genero</label>
                <select class="register-input select-custom" id="genero" name="genero">
                  <option value="seleccionar" disabled selected hidden>
                    Seleccionar género
                  </option>
                  <option value="masculino">Masculino</option>
                  <option value="femenino">Femenino</option>
                  <option value="otro">Otro</option>
                </select>
                <div id="generoError" class="error-formulario"></div>
              </div>
            </div>
          </div>
        </div>

        <div class="transition-btn">
          <button type="submit" class="btn-format-black" id="btn-registrar">Registrar</button>
        </div>
      </form>

      <div class="radio-nav-register">
        <button type="submit" class="arrow-register-left">
          <?xml version="1.0" ?>
          <svg class="feather feather-chevron-left" fill="none" height="34"
               stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
               stroke-width="2" viewBox="0 0 24 24" width="34" xmlns="http://www.w3.org/2000/svg">
            <polyline points="15 18 9 12 15 6"></polyline>
          </svg>
        </button>

        <div class="group-radio">
          <input type="radio" name="register" id="leftRadio" checked/>
          <input type="radio" name="register" id="rightRadio"/>
        </div>

        <button type="submit" class="arrow-register-right" id="btn-right">
          <?xml version="1.0" ?>
          <svg class="feather feather-chevron-right" fill="none" height="34"
               stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
               stroke-width="2" viewBox="0 0 24 24" width="34" xmlns="http://www.w3.org/2000/svg">
            <polyline points="9 18 15 12 9 6"></polyline>
          </svg>
        </button>
      </div>
    </div>
  </div>
</main>
<!-- Coloca el código al final del body -->
<%--        <script>--%>
<%--            document.addEventListener("DOMContentLoaded", () => {--%>
<%--                const addForm = document.getElementById("form-register-validation");--%>

<%--                if (addForm) {--%>
<%--                    addForm.addEventListener("submit", (e) => {--%>
<%--                        if (!addForm.checkValidity()) {--%>
<%--                            e.preventDefault();--%>
<%--                            e.stopPropagation();--%>
<%--                            addForm.classList.add("was-validated");--%>
<%--                        }--%>
<%--                    });--%>
<%--                } else {--%>
<%--                    console.error("Formulario con id 'form-register-validation' no encontrado.");--%>
<%--                }--%>
<%--            });--%>

<%--        </script>--%>


</body>

</html>