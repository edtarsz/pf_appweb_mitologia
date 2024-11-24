<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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

        <!-- Icono de la página -->
        <link rel="icon" type="image/x-icon" href="<c:url value="${pageContext.request.contextPath}/img/icon.svg"/>">
        <!-- Link footer -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
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

                                        <form action="SVPost" method="post">
                                            <input type="hidden" name="idPost" value="${post.idPost}">
                                            <input type="hidden" name="action" value="borrarPost">
                                            <button type="submit">ELIMINAR</button>
                                        </form>
                                    </div>
                                </div>
                            </c:if>

                        </div>

                        <h3>${post.titulo}</h3>
                        <div class="content-post">
                            <p>${post.contenido}</p>
                        </div>

                        <c:if test="${not empty post.link}">
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
                    </article>
                    <!-- Formulario de edición -->
                    <div id="editForm" class="edit-form">
                        <form action="SVPost" method="post">
                            <input type="hidden" name="id" value="${post.idPost}">
                            <input type="hidden" name="action" value="editarPost">

                            <div class="form-group">
                                <select name="category" class="select-category">
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
                                <textarea id="content" name="content" rows="5" class="input-area-post input-area-post-editar" required>${post.contenido}</textarea>
                            </div>

                            <div class="post-buttons">
                                <button type="submit" class="btn-submit">Confirmar</button>
                                <a href="<c:url value='/SVPost?mythology=all' />">
                                    <button type="button" class="btn-cancel">Cancelar</button>
                                </a>
                            </div>
                        </form>
                    </div>
                </main>
            </div>
        </div>
        <%@ include file="partials/footer.jsp" %>
    </body>

</html>