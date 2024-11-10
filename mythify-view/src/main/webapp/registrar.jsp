<%-- Document : registrar Created on : 18 oct 2024, 5:40:47 p.m. Author : user --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
        <meta http-equiv="Pragma" content="no-cache">
        <meta http-equiv="Expires" content="0">

        <!-- CSS Stylesheets -->
        <link rel="stylesheet" href="<c:url value='/style/style.css' />">

        <!-- JavaScript -->
        <script defer src="script/registrar.js"></script>

        <title>Mythify</title>
    </head>

    <body>
        <header class="register-header">
            <nav class="nav-bar">
                <h4>Logo</h4>
                <ul>
                    <li>
                        <a href="iniciarSesion.jsp">Iniciar Sesión</a>
                    </li>
                </ul>
            </nav>
        </header>
        <main class="container-register">
            <div class="main-register">
                <div class="content-register">
                    <div class="title-register">
                        <h1>Registro</h1>
                        <p>
                            Lorem ipsum dolor sit amet consectetur adipiscing elit augue
                            ornare
                        </p>
                    </div>
                    <form action="SVUsuario" method="post" class="form-register" enctype="multipart/form-data" id= "form-register-validation" novalidate>
                        <input type="hidden" name="action" value="registrar">
                        <div class="register-page1">
                            <div class="input-group">
                                <div class="label-input-g">
                                    <label for="nombre">Nombre</label>
                                    <input type="text" class="register-input" name="nombre" required/>
                                    <small>Por favor ingrese el nombre</small>
                                </div>
                            </div>

                            <div class="input-group">
                                <div class="label-input-g">
                                    <label for="apellidoPaterno">Primer apellido</label>
                                    <input type="text" class="register-input" name="apellidoPaterno" required/>
                                    <small>Por favor ingrese el apellido paterno</small>
                                </div>

                                <div class="label-input-g">
                                    <label for="apellidoMaterno">Segundo apellido</label>
                                    <input type="text" class="register-input" name="apellidoMaterno" required/>
                                    <small>Por favor ingrese el apellido materno</small>
                                </div>
                            </div>

                            <div class="input-group">
                                <div class="label-input-g">
                                    <label for="estado">Estado</label>
                                    <input type="text" class="register-input" name="estado" required/>
                                    <small>Por favor ingrese el estado</small>
                                </div>

                                <div class="label-input-g">
                                    <label for="ciudad">Ciudad</label>
                                    <input type="text" class="register-input" name="ciudad" required/>
                                    <small>Por favor ingrese la ciudad</small>
                                </div>
                            </div>

                            <div class="input-group">
                                <div class="label-input-g">
                                    <label for="municipio">Municipio</label>
                                    <input type="text" class="register-input" name="municipio" required/>
                                    <small>Por favor ingrese el municipio</small>
                                </div>

                                <div class="label-input-g">
                                    <label for="fechaNacimiento">Fecha de nacimiento</label>
                                    <input type="date" class="register-input" name="fechaNacimiento" required/>
                                    <small>Por favor ingrese la fecha de nacimiento</small>
                                </div>
                            </div>
                        </div>

                        <div class="register-page2">
                            <div class="input-group">
                                <div class="label-input-g">
                                    <label for="correo">Correo</label>
                                    <input type="email" class="register-input" autocomplete="email" name="correo" required/>
                                    <small>Por favor ingrese el correo</small>
                                </div>
                            </div>

                            <div class="input-group">
                                <div class="label-input-g">
                                    <label for="contrasena">Contraseña</label>
                                    <input type="password" class="register-input" name="contrasena" required/>
                                    <small>Por favor ingrese la constraseña</small>
                                </div>

                                <div class="label-input-g">
                                    <label for="confirmarConstraseña">Confirmar Contraseña</label>
                                    <input type="confirmarContrasena" class="register-input"
                                           name="confirmarContrasena" required/>
                                    <small>Por favor ingrese la misma contraseña nuevamente</small>
                                </div>
                            </div>

                            <div class="input-group-col">
                                <div class="label-input-g">
                                    <label for="estado">Avatar</label>
                                    <div class="container-avatar">
                                        <img src="" alt="" id="imgAvatar" />
                                    </div>
                                    <div class="container-btn-upload">
                                        <label for="file-upload" class="btn-upload">Subir avatar</label>
                                        <input type="file" accept="image/jpeg, image/png" id="file-upload"
                                               name="avatar" />
                                    </div>
                                </div>

                                <div class="input-group-register">
                                    <div class="label-input-g">
                                        <label for="telefono">Telefono</label>
                                        <input type="text" class="register-input" name="telefono" required/>
                                    </div>

                                    <div class="label-input-g">
                                        <label for="genero">Genero</label>
                                        <select class="register-input select-custom" id="genero" name="genero" required>
                                            <option value="seleccionar" disabled selected>
                                                Seleccionar género
                                            </option>
                                            <option value="masculino">Masculino</option>
                                            <option value="femenino">Femenino</option>
                                            <option value="otro">Otro</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="transition-btn">
                            <button type= "submit" class="btn-format-black">Registrar</button>
                        </div>
                    </form>

                    <div class="radio-nav-register">
                        <button type="submit" class="arrow-register-left">
                            <?xml version="1.0" ?><svg class="feather feather-chevron-left" fill="none" height="34"
                                                       stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                                       stroke-width="2" viewBox="0 0 24 24" width="34" xmlns="http://www.w3.org/2000/svg">
                            <polyline points="15 18 9 12 15 6" />
                            </svg>
                        </button>

                        <div class="group-radio">
                            <input type="radio" name="register" id="leftRadio" checked />
                            <input type="radio" name="register" id="rightRadio" />
                        </div>

                        <button type="submit" class="arrow-register-right">
                            <?xml version="1.0" ?><svg class="feather feather-chevron-right" fill="none" height="34"
                                                       stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                                       stroke-width="2" viewBox="0 0 24 24" width="34" xmlns="http://www.w3.org/2000/svg">
                            <polyline points="9 18 15 12 9 6" />
                            </svg>
                        </button>
                    </div>
                </div>
            </div>
        </main>
        <!-- Coloca el código al final del body -->
        <script>
            document.addEventListener("DOMContentLoaded", () => {
                const addForm = document.getElementById("form-register-validation");

                if (addForm) {
                    addForm.addEventListener("submit", (e) => {
                        if (!addForm.checkValidity()) {
                            e.preventDefault();
                            e.stopPropagation();
                            addForm.classList.add("was-validated");
                        }
                    });
                } else {
                    console.error("Formulario con id 'form-register-validation' no encontrado.");
                }
            });

        </script>


    </body>

</html>