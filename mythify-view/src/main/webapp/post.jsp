<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.time.LocalDateTime"%>
<%@page import="org.itson.mythify.auxiliar.CalcularTiempo" %>
<%@page import="org.itson.mythify.entidad.Post" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8" />
        <meta name="description" content="" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
        <meta http-equiv="Pragma" content="no-cache">
        <meta http-equiv="Expires" content="0">

        <!-- CSS Stylesheets -->

        <link rel="stylesheet" href="<%= request.getContextPath()%>/style/style.css">
        
        <!-- JavaScript -->
        <script defer src="<%= request.getContextPath()%>/script/script.js"></script>

        <link rel="stylesheet" href="<c:url value='/style/style.css' />">


        <title>Mythify</title>
    </head>

    <body>
        <%@ include file="partials/header.jsp" %>
        <div class="main-page">

            <div class="post-container">
                <div></div>
                <%@ include file="partials/aside.jsp" %>
                <main>
                    <c:set var="post" value="${requestScope.post}" />
                    <article class="article-post">
                        <div class="head-article-post">
                            <div class="left-head-article">
                                <div class="container-pfp-post"></div>
                                <c:set var="fechaCreacion" value="${post.fechaHoraCreacion}" />
                                <c:set var="tiempoTranscurrido" value="${calculadorTiempo.tiempoTranscurridoDesde(post.fechaHoraCreacion)}" />
                                <span class="span-post-header">
                                    @${empty post.usuario.nombre ? 'Anonymous' : post.usuario.nombre} •
                                    ${tiempoTranscurrido}
                                </span>
                                <span class="span-post-label">${post.categoria}</span>
                                <c:if test="${post.anclado}">
                                    <img src="<c:url value='/img/pin-white.svg' />" alt="">
                                </c:if>
                            </div>
                            
                        <c:if test="${usuario.tipoUsuario == 'ADMINISTRADOR'}">
                            <div class="right-head-article">
                                <button type="button" onclick="toggleDropdown()" class="btn-option">
                                    <img src="<%= request.getContextPath()%>/img/options-post.svg" alt="Opciones" width="20">
                                </button>
                                <div class="dropdown-menu" id="dropdownMenu">
                                    <form action="SVPost?id=${post.idPost}" method="post">
                                        <input type="hidden" name="idPost" value="${post.idPost}">
                                        <input type="hidden" name="action" value="anclarPost">
                                        <button type="submit">ANCLAR</button>
                                    </form>

                                     <button onclick="mostrarFormularioEdicion()" type="button">EDITAR</button>

                                    <form action="SVPost" method="post">
                                        <input type="hidden" name="idPost" value="${post.idPost}">
                                        <input type="hidden" name="action" value="borrarPost">
                                        <button type="submit">ELIMINAR</button>
                                    </form>

                                    <!-- Formulario de edición -->
                                    <div id="editForm" class="edit-form">
                                        <form action="SVPost" method="post">
                                            <input type="hidden" name="id" value="${post.idPost}">
                                            <input type="hidden" name="action" value="editarPost">

                                            <div class="form-group">
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

                                            <div class="form-group">
                                                <label for="title">Título:</label>
                                                <input type="text" id="title" name="title" value="${post.titulo}" required>
                                            </div>

                                            <div class="form-group">
                                                <label for="content">Contenido:</label>
                                                <textarea id="content" name="content" rows="5" required>${post.contenido}</textarea>
                                            </div>

                                            <button type="submit" class="btn-submit">Guardar cambios</button>
                                            <button type="button" class="btn-cancel" onclick="ocultarFormularioEdicion()">Cancelar</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        </div>
                        <h3>${post.titulo}</h3>
                        <div class="content-post">
                            <p>${post.contenido}</p>
                        </div>
                        <div class="footer-post">
                            <button class="btn-footer">
                                <img src="<c:url value='/img/heart-black.svg' />" alt="">
                                19
                            </button>
                            <button class="btn-footer">
                                <img src="<c:url value='/img/comments-black.svg' />" alt="">
                                2 comments
                            </button>
                        </div>
                    </article>

                    <div>
                        <span class="order-comments">Ordenar por:</span> Mejores <img src="<c:url value='/img/down-arrow-white.svg' />" alt="">
                    </div>

                    <button class="add-comment">
                        <img src="<c:url value='/img/plus.svg' />" alt="" class="svg-btn">
                        Añadir un comentario
                    </button>

                    <div class="container-comments">
                        <c:forEach var="comment" items="${post.comentarios}">
                            <article class="comment-post">
                                <div class="head-article-post">
                                    <div class="left-head-article">
                                        <div class="container-pfp-post">
                                            <img src="img/bob.PNG" alt="" class="pfp-post">
                                        </div>
                                        <span class="span-post-header">@${comment.usuario.nombre} replied to @${comment.usuarioRespondido.nombre}</span>
                                    </div>
                                    <div class="right-head-article">
                                        <img src="img/options-post.svg" alt="">
                                    </div>
                                </div>

                                <div class="right-head-article">
                                    
                                        <img src="<%= request.getContextPath()%>/img/options-post.svg" alt="Opciones" width="20">

                                <div class="content-comment-post">
                                    <p>${comment.contenido}</p>

                                </div>
                                <div class="footer-comments">
                                    <div class="group-footer-btn">
                                        <button class="btn-footer">
                                            <img src="img/heart-black.svg" alt="">
                                            230
                                        </button>
                                        <button class="btn-footer">
                                            <img src="img/reply.svg" alt="">
                                            Responder
                                        </button>
                                    </div>
                                    <span class="span-post-header">hace 4 horas</span>
                                </div>
                            </article>
                        </c:forEach>

                        <div class="container-crear-post">
                            <form action="SVComentario" method="post">
                                <input type="hidden" name="action" value="comentarPost">
                                <input type="hidden" name="id" value="${post.idPost}">
                                <textarea name="contenido" placeholder="Comentario..." class="input-area-post" required></textarea>

                                <div class="post-buttons">
                                    <button type="submit" class="btn-submit">Publicar</button>
                                    <button type="button" class="btn-cancel">Cancelar</button>
                                </div>
                            </form>
                        </div>

                        <article class="second-comment-post">
                            <div class="head-article-post">
                                <div class="left-head-article">
                                    <div class="container-pfp-post">
                                        <img src="img/calamardo.PNG" alt="" class="pfp-post">
                                    </div>
                                    <span class="span-post-header">@${secondComment.usuario.nombre} replied to @${secondComment.usuarioRespondido.nombre}</span>
                                </div>
                                <div class="right-head-article">
                                    <img src="img/options-post.svg" alt="">
                                </div>
                            </div>
                            <div class="content-comment-post">
                                <p>${secondComment.contenido}</p>
                            </div>
                            <div class="footer-comments">
                                <div class="group-footer-btn">
                                    <button class="btn-footer">
                                        <img src="img/heart-black.svg" alt="">
                                        48
                                    </button>
                                    <button class="btn-footer">
                                        <img src="img/reply.svg" alt="">
                                        Responder
                                    </button>
                                </div>
                                <span class="span-post-header">hace 3 horas</span>
                            </div>
                        </article>

                    </div>
                </main>
            </div>
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
    </body>

</html>
