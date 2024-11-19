<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="description" content="" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
        <meta http-equiv="Pragma" content="no-cache">
        <meta http-equiv="Expires" content="0">

        <!<!-- Link footer -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <!-- CSS Stylesheets -->
        <link rel="stylesheet" href="<c:url value='/style/style.css' />">
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
                    <c:choose>
                        <c:when test="${not empty posts}">
                            <c:forEach var="post" items="${posts}">
                                <c:if test="${post != null && post.usuario != null && post.titulo != null && post.contenido != null}">
                                    <article class="article-post">
                                        <div class="head-article-post">
                                            <c:if test="${!post.anclado}">
                                                <a href="<c:url value='/SVPost?id=${post.idPost}'/>">
                                                </c:if>
                                                <div class="left-head-article">
                                                    <div class="container-pfp-post"></div>
                                                    <c:set var="tiempoTranscurrido" value="${calculadorTiempo.tiempoTranscurridoDesde(post.fechaHoraCreacion)}" />
                                                    <span class="span-post-header">
                                                        @${empty post.usuario.nombre ? 'Anonymous' : post.usuario.nombre} •
                                                        ${tiempoTranscurrido}
                                                    </span>
                                                    <span class="span-post-label">
                                                        ${empty post.categoria ? 'Uncategorized' : post.categoria}
                                                    </span>

                                                    <c:if test="${post.anclado}">
                                                        <img src="<c:url value='/img/pin-white.svg' />" alt="Pin icon">
                                                    </c:if>
                                                </div>
                                                <c:if test="${!post.anclado}">
                                                </a>    
                                            </c:if>
                                            <c:if test="${usuario.tipoUsuario == 'ADMINISTRADOR'}">
                                                <div class="right-head-article">
                                                    <button type="button" class="btn-option" data-post-id="${post.idPost}">
                                                        <img src="<%= request.getContextPath()%>/img/options-post.svg" alt="Opciones" width="20">
                                                    </button>

                                                    <div class="dropdown-menu" id="dropdown-${post.idPost}" style="display: none;">

                                                        <form action="SVPost?id=${post.idPost}" method="post">
                                                            <input type="hidden" name="idPost" value="${post.idPost}">
                                                            <input type="hidden" name="action" value="anclarPost">
                                                            <button type="submit">ANCLAR</button>
                                                        </form>

                                                        <a href="<c:url value='/SVPost?id=${post.idPost}&action=editarPost' />">EDITAR</a>

                                                        <form action="SVPost" method="post">
                                                            <input type="hidden" name="idPost" value="${post.idPost}">
                                                            <input type="hidden" name="action" value="borrarPost">
                                                            <button type="submit">ELIMINAR</button>
                                                        </form>
                                                    </div>
                                                </div>
                                            </c:if>

                                        </div>


                                        <c:if test="${!post.anclado}">
                                            <a href="<c:url value='/SVPost?id=${post.idPost}'/>">
                                            </c:if>
                                            <h3>${post.titulo}</h3>
                                            <div class="content-post">
                                                <p>${post.contenido}</p>
                                            </div>
                                            <c:if test="${!post.anclado}">
                                            </a>    
                                        </c:if>

                                        <div class="footer-post">
                                            <button class="btn-footer">
                                                <img src="<c:url value='/img/heart-black.svg' />" alt="Like">
                                                19
                                            </button>
                                            <c:if test="${!post.anclado}">
                                                <button class="btn-footer">
                                                    <img src="<c:url value='/img/comments-black.svg' />" alt="Comments">
                                                    2 comments
                                                </button>
                                            </c:if>
                                        </div>
                                        <c:if test="${!post.anclado}">
                                        </c:if>
                                    </article>
                                </c:if>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <p>Aún no hay posts para mostrar.</p>
                        </c:otherwise>
                    </c:choose>
                </main>
                <aside class="aside-hot">
                    <h2 class="hot-posts-title">HOT POSTS</h2>
                    <article class="hot-post">
                        <div class="head-article-hot-posts">
                            <div class="left-head-article">
                                <div class="container-pfp-post"></div>
                                <span class="span-post-header">@user</span>
                            </div>
                            <div class="right-head-article">
                                <img src="<c:url value='/img/options-post.svg' />" alt="Options">
                            </div>
                        </div>
                        <div class="content-post">
                            <p>Lorem ipsum, dolor sit amet consectetur adipisicing elit. Incidunt culpa porro, perferendis voluptate quaerat assumenda praesentium dignissimos eius esse ratione quas sed voluptatum inventore voluptates illo optio officiis sit harum?</p>
                        </div>
                        <div class="footer-hot-post">
                            <button class="btn-footer-hot-post">
                                <img src="<c:url value='/img/heart-white.svg' />" alt="Like">
                                4014
                            </button>
                            <button class="btn-footer-hot-post">
                                <img src="<c:url value='/img/comments-white.svg' />" alt="Comments">
                                409
                            </button>
                        </div>
                    </article>
                </aside>
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