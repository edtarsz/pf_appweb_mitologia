<%-- 
    Document   : iniciarSesion
    Created on : 18 oct 2024, 5:40:00 p.m.
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" href="style/style.css" />
        <!-- <link rel="icon" href="img/logo.jpeg" /> -->
        <title>Title</title>
    </head>
    <body>
        <main class="container-main">
            <img src="img/statue.png" alt="" id="statue-img" />
            <div class="main-conent">
                <!-- <img src="" alt="" /> -->
                <h1>Iniciar Sesión</h1>

                <p class="description">
                    Lorem ipsum dolor sit amet consectetur adipiscing elit augue ornare
                </p>
                <form action="SVUsuario" method="post">
                    <input type="hidden" name="action" value="iniciarSesion">
                    <label for="correo">Correo</label>
                    <input
                        type="email"
                        class="input-format"
                        autocomplete="email"
                        name="correo"
                        required
                        />

                    <label for="contraseña">Contraseña</label>
                    <input type="password" class="input-format" name="contrasenia" required/>

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
