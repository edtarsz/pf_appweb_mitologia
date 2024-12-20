<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.time.LocalDateTime" %>
<%@page import="org.itson.mythify.auxiliar.CalcularTiempo" %>
<%@page import="org.itson.mythify.entidad.Post" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
        <link rel="stylesheet" href="<%= request.getContextPath()%>/style/style.css">
        <!-- JavaScript -->
        <script defer src="<%= request.getContextPath()%>/script/script.js"></script>
        <script defer src="<%= request.getContextPath()%>/script/comment.js"></script>

        <link rel="stylesheet" href="<c:url value='/style/style.css' />">

        <title>Mythify</title>
        <script defer src="<c:url value='/script/validarComentario.js' />"></script>
    </head>

    <body>
        <%@ include file="partials/header.jspf" %>
        <div class="main-page">

            <div class="post-container">
                <div></div>
                <%@ include file="partials/aside.jspf" %>
                <main>
                    <c:set var="post" value="${requestScope.post}"/>
                    <article class="article-post">
                        <div class="head-article-post">
                            <div class="left-head-article">
                                <img src="${pageContext.request.contextPath}/imgUsers/${post.usuario.avatar}" alt="Profile Picture"
                                     class="profile-pic"/>

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
                            <c:if test="${usuario.tipoUsuario == 'ADMINISTRADOR'}">
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
                                    </div>
                                </div>
                            </c:if>

                        </div>

                        <h3>${post.titulo}</h3>
                        <div class="content-post">
                            <p>${post.contenido}</p>
                        </div>

                        <c:if test="${post.imagen != null}">
                            <div id="img-view">
                                <img src="imgPosts/${post.imagen}" alt="alt"/>
                            </div>
                        </c:if>

                        <c:if test="${not empty post.link}">
                            <a href="${fn:escapeXml(post.link)}" id="preview-link">
                                ${fn:escapeXml(post.link)}
                            </a>
                        </c:if>

                        <c:set var="postLikeados" value="${sessionScope.postsLikeados}"/>
                        <div class="footer-post">
                            <form action="SVPost" method="post">
                                <input type="hidden" name="isView" value="true">
                                <input type="hidden" name="idPost" value="${post.idPost}">
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

                    <div>
                        <span class="order-comments">Ordenar por:</span> Mejores <img src="<c:url value='/img/down-arrow-white.svg' />"
                                                                                      alt="">
                    </div>

                    <button class="add-comment">
                        <img src="<c:url value='/img/plus.svg' />" alt="" class="svg-btn">
                        Añadir un comentario
                    </button>

                    <!-- Formulario de edición -->
                    <div id="commentForm" class="edit-form">
                        <form action="SVComentario" method="post" id="form-comment">
                            <input type="hidden" name="action" value="comentarPost">
                            <input type="hidden" name="id" value="${post.idPost}">

                            <div class="form-group">
                                <label for="content">Contenido:</label>
                                <textarea name="contenido" rows="5" class="input-area-post input-area-post-editar" id="content"></textarea>
                                <div class="error-formulario" id="comentarioError"></div>
                            </div>

                            <div class="post-buttons">
                                <a href="<c:url value='/SVPost?id=${post.idPost}'/>">
                                    <button type="submit" class="btn-submit">Confirmar</button>
                                </a>
                                <button type="submit" class="btn-cancel" id="cancel-comment">Cancelar</button>
                            </div>
                        </form>
                    </div>

                    <!-- Listado de comentarios -->
                    <c:forEach var="commentPadre" items="${comentarios}">
                        <c:if test="${commentPadre.comentarioPadre == null}">
                            <!-- Comentario principal -->
                            <article class="article-post">
                                <div class="head-article-post">
                                    <div class="left-head-article">
                                        <img src="${pageContext.request.contextPath}/imgUsers/${commentPadre.usuario.avatar}"
                                             alt="Profile Picture" class="profile-pic"/>

                                        <c:set var="tiempoTranscurrido"
                                               value="${calculadorTiempo.tiempoTranscurridoDesde(commentPadre.fechaHora)}"/>
                                        <span class="span-post-header">
                                            @${empty commentPadre.usuario.nombre ? 'Anonymous' : commentPadre.usuario.nombre} •
                                            ${tiempoTranscurrido}
                                        </span>
                                    </div>

                                    <c:if test="${usuario.tipoUsuario == 'ADMINISTRADOR'}">
                                        <div class="right-head-article">
                                            <button type="button" class="btn-option" data-comentario-id="${commentPadre.idComentario}">
                                                <img src="<%= request.getContextPath()%>/img/options-post.svg" alt="Opciones" width="20">
                                            </button>
                                            <div class="dropdown-menu" id="dropdown-comentario-${commentPadre.idComentario}"
                                                 style="display: none;">
                                                <form action="SVComentario" method="post">
                                                    <input type="hidden" name="idPost" value="${post.idPost}">
                                                    <input type="hidden" name="idComentario" value="${commentPadre.idComentario}">
                                                    <input type="hidden" name="action" value="eliminarComentario">
                                                    <button type="submit">ELIMINAR</button>
                                                </form>
                                            </div>
                                        </div>
                                    </c:if>

                                </div>

                                <div class="post-comment-content">
                                    <p>${commentPadre.contenido}</p>
                                </div>

                                <c:set var="comentarioLikeados" value="${sessionScope.comentariosLikeados}"/>
                                <div class="footer-post">
                                    <form action="SVComentario" method="post">
                                        <input type="hidden" name="isView" value="true">
                                        <input type="hidden" name="idPost" value="${post.idPost}">
                                        <input type="hidden" name="idComentario" value="${commentPadre.idComentario}">

                                        <%-- Usar la misma estructura de verificación que en los posts --%>
                                        <c:set var="comentarioLikeados" value="${sessionScope.comentariosLikeados}"/>
                                        <c:choose>
                                            <c:when test="${fn:contains(comentarioLikeados, commentPadre)}">
                                                <input type="hidden" name="action" value="desLikearComentario">
                                                <button class="btn-footer">
                                                    <img src="<c:url value='/img/heart-red.svg' />" alt="Dislike">
                                                    &#8203
                                                </button>
                                            </c:when>
                                            <c:otherwise>
                                                <input type="hidden" name="action" value="likearComentario">
                                                <button class="btn-footer">
                                                    <img src="<c:url value='/img/heart-black.svg' />" alt="Like">
                                                    &#8203
                                                </button>
                                            </c:otherwise>
                                        </c:choose>
                                    </form>
                                    <button class="btn-footer reply-button" data-comment-id="${commentPadre.idComentario}">
                                        <img src="<c:url value='/img/comments-black.svg' />" alt="Comments">
                                        Responder
                                    </button>
                                </div>
                            </article>

                            <!-- Formulario de respuesta para este comentario padre -->
                            <div id="replyForm-${commentPadre.idComentario}" class="edit-form reply-form" style="display: none;">
                                <form action="SVComentario" method="post">
                                    <input type="hidden" name="action" value="responderComentario">
                                    <input type="hidden" name="idComentarioPadre" value="${commentPadre.idComentario}">
                                    <input type="hidden" name="idPost" value="${post.idPost}">

                                    <div class="form-group">
                                        <label for="content-${commentPadre.idComentario}">Responder a
                                            @${empty commentPadre.usuario.nombre ? 'Anonymous' : commentPadre.usuario.nombre}:</label>
                                        <textarea id="content-${commentPadre.idComentario}" name="contenido" rows="5"
                                                  class="input-area-post input-area-post-editar" required></textarea>
                                    </div>

                                    <div class="post-buttons">
                                        <button type="submit" class="btn-submit">Responder</button>
                                        <button type="button" class="btn-cancel" data-comment-id="${commentPadre.idComentario}">Cancelar
                                        </button>
                                    </div>
                                </form>
                            </div>

                            <!-- Comentarios hijo (respuestas) con sangría -->
                            <div class="replies-container" style="margin-left: 2rem;">
                                <c:forEach var="commentHijo" items="${comentarios}">
                                    <c:if test="${commentHijo.comentarioPadre.idComentario == commentPadre.idComentario}">
                                        <article class="article-post">
                                            <div class="head-article-post">
                                                <div class="left-head-article">
                                                    <img src="${pageContext.request.contextPath}/imgUsers/${commentHijo.usuario.avatar}"
                                                         alt="Profile Picture" class="profile-pic"/>

                                                    <c:set var="tiempoTranscurrido"
                                                           value="${calculadorTiempo.tiempoTranscurridoDesde(commentHijo.fechaHora)}"/>
                                                    <span class="span-post-header">
                                                        @${empty commentHijo.usuario.nombre ? 'Anonymous' : commentHijo.usuario.nombre} •
                                                        ${tiempoTranscurrido}
                                                    </span>
                                                </div>

                                                <c:if test="${usuario.tipoUsuario == 'ADMINISTRADOR'}">
                                                    <div class="right-head-article">
                                                        <button type="button" class="btn-option" data-comentario-id="${commentHijo.idComentario}">
                                                            <img src="<%= request.getContextPath()%>/img/options-post.svg" alt="Opciones" width="20">
                                                        </button>
                                                        <div class="dropdown-menu" id="dropdown-comentario-${commentHijo.idComentario}"
                                                             style="display: none;">
                                                            <form action="SVComentario" method="post">
                                                                <input type="hidden" name="idPost" value="${post.idPost}">
                                                                <input type="hidden" name="idComentario" value="${commentHijo.idComentario}">
                                                                <input type="hidden" name="action" value="eliminarComentario">
                                                                <button type="submit">ELIMINAR</button>
                                                            </form>
                                                        </div>
                                                    </div>
                                                </c:if>
                                            </div>

                                            <div class="post-comment-content">
                                                <p>${commentHijo.contenido}</p>
                                            </div>

                                            <c:set var="isLikedHijo" value="false"/>
                                            <c:forEach var="comentarioLikeado" items="${comentarioLikeados}">
                                                <c:if test="${comentarioLikeado.idComentario == commentHijo.idComentario}">
                                                    <c:set var="isLikedHijo" value="true"/>
                                                </c:if>
                                            </c:forEach>

                                            <div class="footer-post">
                                                <form action="SVComentario" method="post">
                                                    <input type="hidden" name="isView" value="true">
                                                    <input type="hidden" name="idPost" value="${post.idPost}">
                                                    <input type="hidden" name="idComentario" value="${commentHijo.idComentario}">

                                                    <c:choose>
                                                        <c:when test="${fn:contains(comentarioLikeados, commentHijo)}">
                                                            <input type="hidden" name="action" value="desLikearComentario">
                                                            <button class="btn-footer">
                                                                <img src="<c:url value='/img/heart-red.svg' />" alt="Dislike">
                                                                &#8203
                                                            </button>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input type="hidden" name="action" value="likearComentario">
                                                            <button class="btn-footer">
                                                                <img src="<c:url value='/img/heart-black.svg' />" alt="Like">
                                                                &#8203
                                                            </button>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </form>
                                            </div>
                                        </article>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </c:if>
                    </c:forEach>
                </main>
            </div>
        </div>
        <%@ include file="partials/footer.jspf" %>
    </body>

</html>
