<%-- Document : formPost Created on : 3 nov 2024, 2:41:01 p.m. Author : user --%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8" />
        <meta name="description" content="" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />

        <!-- CSS Stylesheets -->
        <link rel="stylesheet" href="<%= request.getContextPath()%>/style/style.css">
        <link rel="preconnect" href="https://fonts.googleapis.com" />
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
        <link
            href="https://fonts.googleapis.com/css2?family=EB+Garamond:ital,wght@0,400..800;1,400..800&display=swap"
            rel="stylesheet" />
        <link
            href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap"
            rel="stylesheet" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

        <!-- JavaScript -->
        <script defer src="<%= request.getContextPath()%>/script/script.js"></script>

        <title>Mythify</title>
    </head>

    <body>
        <%@ include file="partials/header.jsp" %>
        <div class="main-page">
            <div class="post-container">
                <div></div>
                <%@ include file="partials/aside.jsp" %>
                <main>
                    <div class="container-crear-post">
                        <h2 class="mythologies-title">Crear post</h2>
                        <form action="SVPost" method="post">

                            <input type="hidden" name="action" value="publicarPost">

                            <div class="category-post">
                                <select name="category" class="select-category" required>
                                    <option value="" disabled selected>SELECCIONAR CATEGORÍA</option>
                                    <option value="egipcia">EGIPCIA</option>
                                    <option value="griega">GRIEGA</option>
                                    <option value="azteca">AZTECA</option>
                                    <option value="maya">MAYA</option>
                                    <option value="nordica">NORDICA</option>
                                    <option value="romana">ROMANA</option>
                                </select>
                            </div>

                            <ul class="section-post">
                                <li id="li-texto">Texto</li>
                                <li id="li-img">Imagenes</li>
                                <li id="li-link">Link</li>
                            </ul>

                            <input type="text" name="title" placeholder="Titulo" class="input-text-post"
                                   id="titleGet" required>

                            <label for="imgGet" id="drop-area">
                                <input type="file" accept="image/jpeg, image/png" id="imgGet"
                                       name="avatar" />
                                <div id="img-view">
                                    <p>Arrastrar o subir imagen</p>
                                </div>
                            </label>

                            <input type="text" name="link" placeholder="Link URL" class="input-text-post"
                                   id="link-url">

                            <textarea name="content" placeholder="Comentario..." class="input-area-post"
                                      id="comentGet" required></textarea>

                            <div class="post-buttons">
                                <button type="submit" class="btn-submit">Publicar</button>
                                <button type="button" class="btn-cancel">Cancelar</button>
                            </div>
                        </form>

                    </div>
                    <h2 class="mythologies-title">Preview</h2>
                    <article class="article-post">
                        <div class="head-article-post">
                            <div class="left-head-article">
                                <div class="container-pfp-post"></div>
                                <span class="span-post-header">@user • hace 2 horas</span>
                                <span class="span-post-label">MÉXICO</span>
                                <img src="<%= request.getContextPath()%>/img/pin-white.svg" alt="">
                            </div>
                            <div class="right-head-article">
                                <img src="<%= request.getContextPath()%>/img/options-post.svg" alt="">
                            </div>
                        </div>
                        <h3 id="preview-titulo">Introduzca un titulo...</h3>
                        <div class="content-post">
                            <p id="preview-texto">
                                Introduzca un comentario...
                            </p>
                        </div>
                        <!-- <div class="contenedor-crear-post-img">
                            <img src="" alt="">
                        </div> -->
                        <div class="footer-post">
                            <button class="btn-footer">
                                <img src="${pageContext.request.contextPath}/img/heart-black.svg" alt="">
                                102
                            </button>
                            <button class="btn-footer">
                                <img src="${pageContext.request.contextPath}/img/comments-black.svg" alt="">
                                41 comentarios
                            </button>
                        </div>
                    </article>
                </main>
            </div>
            <footer>
                <div class="footer-content">
                    <div class="social-icons">
                        <a href="#"><i class="fab fa-facebook"></i></a>
                        <a href="#"><i class="fab fa-instagram"></i></a>
                        <a href="#"><i class="fab fa-twitter"></i></a>
                        <a href="#"><i class="fab fa-twitch"></i></a>
                        <a href="#"><i class="fab fa-discord"></i></a>
                    </div>
                    <p>Copyright 2024 © Mythify Team</p>
                </div>
            </footer>
        </div>
    </body>

</html>