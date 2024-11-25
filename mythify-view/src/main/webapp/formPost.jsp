<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8"/>
        <meta name="description" content=""/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
        <meta http-equiv="Pragma" content="no-cache">
        <meta http-equiv="Expires" content="0">

        <!-- Icono de la página -->
        <link rel="icon" type="image/x-icon" href="<c:url value="${pageContext.request.contextPath}/img/icon.svg"/>">
        <!-- Link footer -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <!-- CSS Stylesheets -->
        <link rel="stylesheet" href="<c:url value='/style/style.css' />">
        <!-- JavaScript -->
        <script defer src="<c:url value='/script/crearPost.js' />"></script>
        <script defer src="<c:url value='/script/validarPost.js' />"></script>

        <title>Mythify</title>
    </head>

    <body>
        <%@ include file="partials/header.jspf" %>
        <div class="main-page">
            <div class="post-container">
                <div></div>
                <%@ include file="partials/aside.jspf" %>
                <main>
                    <div class="container-crear-post">
                        <form action="<c:url value='/SVPost' />" method="post" id="form-post">
                            <div class="anclar-crear-titles">
                                <input type="hidden" name="action" value="publicarPost">

                                <div class="category-post">
                                    <h2 class="mythologies-title">Crear post</h2>
                                    <select name="category" class="select-category">
                                        <option value="" disabled selected hidden>SELECCIONAR CATEGORÍA</option>
                                        <option value="egipcia">EGIPCIA</option>
                                        <option value="griega">GRIEGA</option>
                                        <option value="azteca">AZTECA</option>
                                        <option value="maya">MAYA</option>
                                        <option value="nordica">NORDICA</option>
                                        <option value="romana">ROMANA</option>
                                    </select>
                                    <div class="error-formulario" id="categoryError"></div>
                                </div>

                                <!-- Solo visible para Administradores -->
                                <c:if test="${usuario.tipoUsuario == 'ADMINISTRADOR'}">
                                    <div class="anclar-post">
                                        <h2 class="mythologies-title" for="anclar">Anclar post</h2>
                                        <div class="container-checkbox">
                                            <input type="checkbox" name="anclar" value="true">
                                        </div>
                                    </div>
                                </c:if>

                            </div>

                            <ul class="section-post">
                                <li id="li-texto">Texto</li>
                                <li id="li-img">Imagenes</li>
                                <li id="li-link">Link</li>
                            </ul>

                            <input type="text" name="title" placeholder="Titulo" class="input-text-post"
                                   id="titleGet">
                            <div class="error-formulario" id="tituloError"></div>
                            <label for="imgGet" id="drop-area">
                                <input type="file" accept="image/jpeg, image/png" id="imgGet"
                                       name="avatar"/>
                                <div id="img-view">
                                    <p>Arrastrar o subir imagen</p>
                                </div>
                            </label>

                            <input type="text" name="link" placeholder="Link URL" class="input-text-post"
                                   id="link-url">

                            <textarea name="content" placeholder="Comentario..." class="input-area-post"
                                      id="comentGet"></textarea>
                            <div class="error-formulario" id="comentError"></div>
                            <div class="post-buttons">
                                <button type="submit" class="btn-submit">Publicar</button>
                                <a href="<c:url value='/SVPost?mythology=all' />">
                                    <button type="button" class="btn-cancel">Cancelar</button>
                                </a>
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
                                <img src="<c:url value='/img/pin-white.svg' />" alt="">
                            </div>
                            <div class="right-head-article">
                                <img src="<c:url value='/img/options-post.svg' />" alt="">
                            </div>
                        </div>
                        <h3 id="preview-titulo">Introduzca un titulo...</h3>
                        <div class="content-post">
                            <p id="preview-texto">
                                Introduzca un comentario...
                            </p>
                        </div>
                        <a href="" id="preview-link">
                            http://linkdeejemplo.com
                        </a>
                        <!-- class="contenedor-crear-post-img"> dentro va la imagen -->
                        <div class="footer-post">
                            <button class="btn-footer">
                                <img src="<c:url value='/img/heart-black.svg' />" alt="">
                                102
                            </button>
                            <button class="btn-footer">
                                <img src="<c:url value='/img/comments-black.svg' />" alt="">
                                41 comentarios
                            </button>
                        </div>
                    </article>
                </main>
            </div>
        </div>
        <%@ include file="partials/footer.jspf" %>
    </body>

</html>