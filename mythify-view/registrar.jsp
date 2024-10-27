<%-- Document : registrar Created on : 18 oct 2024, 5:40:47 p.m. Author : user --%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" href="style/style.css" />
        <script defer src="script/script.js"></script>
        <link rel="stylesheet" href="path/to/font-awesome/css/font-awesome.min.css" />
        <!-- <link rel="icon" href="img/logo.jpeg" /> -->
        <title>Title</title>
    </head>

    <body>
        <header>
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
                    <form action="SVUsuario" method="post" class="form-register" enctype="multipart/form-data">
                        <input type="hidden" name="action" value="registrar">
                        <div class="register-page1">
                            <div class="input-group">
                                <div class="label-input-g">
                                    <label for="nombre">Nombre</label>
                                    <input type="text" class="register-input" name="nombre" />
                                </div>
                            </div>

                            <div class="input-group">
                                <div class="label-input-g">
                                    <label for="apellidoPaterno">Primer apellido</label>
                                    <input type="text" class="register-input" name="apellidoPaterno" />
                                </div>

                                <div class="label-input-g">
                                    <label for="apellidoMaterno">Segundo apellido</label>
                                    <input type="text" class="register-input" name="apellidoMaterno" />
                                </div>
                            </div>

                            <div class="input-group">
                                <div class="label-input-g">
                                    <label for="estado">Estado</label>
                                    <input type="text" class="register-input" name="estado" />
                                </div>

                                <div class="label-input-g">
                                    <label for="ciudad">Ciudad</label>
                                    <input type="text" class="register-input" name="ciudad" />
                                </div>
                            </div>

                            <div class="input-group">
                                <div class="label-input-g">
                                    <label for="municipio">Municipio</label>
                                    <input type="text" class="register-input" name="municipio" />
                                </div>

                                <div class="label-input-g">
                                    <label for="fechaNacimiento">Fecha de nacimiento</label>
                                    <input type="date" class="register-input" name="fechaNacimiento" />
                                </div>
                            </div>
                        </div>

                        <div class="register-page2">
                            <div class="input-group">
                                <div class="label-input-g">
                                    <label for="correo">Correo</label>
                                    <input type="email" class="register-input" autocomplete="email" name="correo" />
                                </div>
                            </div>

                            <div class="input-group">
                                <div class="label-input-g">
                                    <label for="contrasena">Contraseña</label>
                                    <input type="password" class="register-input" name="contrasena" />
                                </div>

                                <div class="label-input-g">
                                    <label for="segundoApellido">Confirmar Contraseña</label>
                                    <input type="confirmarContrasena" class="register-input" name="confirmarContrasena" />
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
                                        <input type="file" accept="image/jpeg, image/png" id="file-upload" name="avatar" />
                                    </div>
                                </div>

                                <div class="input-group-register">
                                    <div class="label-input-g">
                                        <label for="telefono">Telefono</label>
                                        <input type="text" class="register-input" name="telefono" />
                                    </div>

                                    <div class="label-input-g">
                                        <label for="genero">Genero</label>
                                        <select class="register-input select-custom" id="genero" name="genero">
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
                            <button class="btn-format-black">Registrar</button>
                        </div>
                    </form>

                    <div class="radio-nav-register">
                        <button type="submit" class="arrow-register-left">
                            <?xml version="1.0" ?><svg class="feather feather-chevron-left" fill="none" height="34"
                                                       stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                                       viewBox="0 0 24 24" width="34" xmlns="http://www.w3.org/2000/svg">
                            <polyline points="15 18 9 12 15 6" />
                            </svg>
                        </button>

                        <div class="group-radio">
                            <input type="radio" name="register" id="leftRadio" checked />
                            <input type="radio" name="register" id="rightRadio" />
                        </div>

                        <button type="submit" class="arrow-register-right">
                            <?xml version="1.0" ?><svg class="feather feather-chevron-right" fill="none" height="34"
                                                       stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                                       viewBox="0 0 24 24" width="34" xmlns="http://www.w3.org/2000/svg">
                            <polyline points="9 18 15 12 9 6" />
                            </svg>
                        </button>
                    </div>
                </div>
            </div>
        </main>
    </body>

</html>