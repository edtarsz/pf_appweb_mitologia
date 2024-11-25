<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
                    <h2>Mis Publicaciones</h2>
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

                                                    <c:choose>
                                                        <c:when test="${not empty post.fechaHoraEdicion}">
                                                            <c:set var="tiempoTranscurrido"
                                                                   value="${calculadorTiempo.tiempoTranscurridoDesde(post.fechaHoraEdicion)}"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:set var="tiempoTranscurrido"
                                                                   value="${calculadorTiempo.tiempoTranscurridoDesde(post.fechaHoraCreacion)}"/>
                                                        </c:otherwise>
                                                    </c:choose>

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
                                            <div class="right-head-article">
                                                <button type="button" class="btn-option" data-post-id="${post.idPost}">
                                                    <img src="<%= request.getContextPath()%>/img/options-post.svg" alt="Opciones" width="20">
                                                </button>

                                                <div class="dropdown-menu" id="dropdown-${post.idPost}" style="display: none;">
                                                    <form action="SVPost?id=${post.idPost}" method="post">
                                                        <input type="hidden" name="idPost" value="${post.idPost}">

                                                        <c:choose>
                                                            <c:when test="${post.anclado}">
                                                                <input type="hidden" name="action" value="desAnclarPost">
                                                                <button type="submit">DESANCLAR</button>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input type="hidden" name="action" value="anclarPost">
                                                                <button type="submit">ANCLAR</button>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </form>

                                                    <a href="<c:url value='/SVPost?id=${post.idPost}&action=editarPost' />">EDITAR</a>

                                                    <form action="SVPost" method="post">
                                                        <input type="hidden" name="idPost" value="${post.idPost}">
                                                        <input type="hidden" name="action" value="borrarPost">
                                                        <button type="submit">ELIMINAR</button>
                                                    </form>
                                                </div>
                                            </div>
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

                                        <c:if test="${not empty post.link}">
                                            <a href="${fn:escapeXml(post.link)}" id="preview-link">
                                                ${fn:escapeXml(post.link)}
                                            </a>
                                        </c:if>

                                        <c:set var="postLikeados" value="${sessionScope.postsLikeados}"/>
                                        <div class="footer-post">
                                            <form action="SVPost" method="post">
                                                <input type="hidden" name="idPost" value="${post.idPost}">
                                                <input type="hidden" name="isView" value="false">
                                                <c:choose>
                                                    <%-- Verificamos si el post actual está en la lista de posts likeados --%>
                                                    <c:when test="${fn:contains(postLikeados, post)}">
                                                        <input type="hidden" name="action" value="desLikearPost">
                                                        <button class="btn-footer">
                                                            <img src="<c:url value='/img/heart-red.svg' />" alt="Dislike">
                                                            ${post.cantLikes}
                                                        </button>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <input type="hidden" name="action" value="likearPost">
                                                        <button class="btn-footer">
                                                            <img src="<c:url value='/img/heart-black.svg' />" alt="Like">
                                                            ${post.cantLikes}
                                                        </button>
                                                    </c:otherwise>
                                                </c:choose>
                                            </form>
                                        </div>
                                    </article>
                                </c:if>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <p>No has publicado nada todavía.</p>
                        </c:otherwise>
                    </c:choose>
                </main>
            </div>
        </div>
        <%@ include file="partials/footer.jsp" %>
    </body>
</html>