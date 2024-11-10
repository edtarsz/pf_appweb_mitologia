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

        <!-- CSS Stylesheets -->
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
                    <c:choose>
                        <c:when test="${not empty posts}">
                            <c:forEach var="post" items="${posts}">
                                <c:if test="${post != null && post.usuario != null && post.titulo != null && post.contenido != null}">
                                    <article class="article-post">
                                        <c:if test="${!post.anclado}">
                                            <a href="<c:url value='/SVPost?id=${post.idPost}'/>">
                                            </c:if>
                                            <div class="head-article-post">
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
                                                <div class="right-head-article">
                                                    <img src="<c:url value='/img/options-post.svg' />" alt="Options">
                                                </div>
                                            </div>
                                            <h3>${post.titulo}</h3>
                                            <div class="content-post">
                                                <p>${post.contenido}</p>
                                            </div>
                                            <c:if test="${not empty post.link}">
                                                URL:
                                                <a href="${fn:escapeXml(post.link)}" id="preview-link">
                                                    ${fn:escapeXml(post.link)}
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
                                            </a>
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