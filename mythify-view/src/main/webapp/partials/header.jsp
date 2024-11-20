<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script defer src="<%= request.getContextPath()%>/script/nav.js"></script>
<header class="post-header">
    <form action="SVPost" method="get">
        <input type="hidden" name="mythology" value="all">
        <button type="submit" class="header-svg-container" style="background: none; border: none; cursor: pointer;">
            <img src="${pageContext.request.contextPath}/img/icon.svg" alt="Logo Mythify" class="header-svg" />
            <span class="header-text">Mythify</span>
        </button>
    </form>
    <div class="search-bar-container">
        <input type="text" class="search-bar" placeholder="Buscar..." />
        <img src="${pageContext.request.contextPath}/img/search-icon.svg" alt="Buscar" class="search-icon" />
    </div>
    <div class="container-avatar-header">
        <div class="clicking">
            <div class="click-menu">
                <c:choose>
                    <c:when test="${not empty sessionScope.usuario}">
                        <p>
                            <c:choose>
                                <c:when test="${sessionScope.usuario.tipoUsuario eq 'ADMINISTRADOR'}">
                                    <i>${sessionScope.usuario.nombre} ${sessionScope.usuario.apellidoPaterno} ${sessionScope.usuario.apellidoMaterno}</i>
                                </c:when>
                                <c:otherwise>
                                    ${sessionScope.usuario.nombre} ${sessionScope.usuario.apellidoPaterno} ${sessionScope.usuario.apellidoMaterno}
                                </c:otherwise>
                            </c:choose>
                        </p>
                    </c:when>
                    <c:otherwise>
                        <p>Usuario no autenticado</p>
                    </c:otherwise>
                </c:choose>
                <img src="${pageContext.request.contextPath}/img/profile-pic.svg" alt="Profile Picture" class="profile-pic" />
            </div>
            <div class="items-menu">
                <ul>
                    <li>
                        <form action="SVUsuario" method="get">
                            <input type="hidden" name="action" value="verPerfil">
                            <button type="submit">Ver Perfil</button>
                        </form>
                    </li>
                    <li>
                        <form action="SVUsuario" method="get">
                            <input type="hidden" name="action" value="iniciarSesion">
                            <button type="submit">Actualizar Perfil</button>
                        </form>
                    </li>
                    <li>
                        <form action="SVUsuario" method="get">
                            <input type="hidden" name="action" value="cerrarSesion">
                            <button type="submit">Cerrar Sesión</button>
                        </form>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</header>