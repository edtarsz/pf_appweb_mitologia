<!-- <link rel="icon" href="img/logo.jpeg" /> -->
<script defer src="<%= request.getContextPath()%>/script/nav.js"></script>
<%@page import="org.itson.mythify.entidad.Usuario" %>
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
            <img src="<%= request.getContextPath()%>/img/search-icon.svg" alt="Buscar" class="search-icon" />
        </div>
        <div class="container-avatar-header">
            <div class="clicking">
                <div class="click-menu">
                    <% Usuario usuario=(Usuario) request.getSession().getAttribute("usuario"); if (usuario !=null) {%>
                        <p>
                            <% if (!usuario.getPermiso().toString().equalsIgnoreCase("COMENTAR")) {%>
                                <i>
                                    <%= usuario.getNombre() + " " + usuario.getApellidoPaterno() + " " +
                                        usuario.getApellidoMaterno()%>
                                </i>
                                <% } else {%>
                                    <%= usuario.getNombre() + " " + usuario.getApellidoPaterno() + " " +
                                        usuario.getApellidoMaterno()%>
                                        <% } %>
                        </p>
                        <% } else { %>
                            <p>Usuario no autenticado</p>
                            <% }%>
                                <img src="<%= request.getContextPath()%>/img/profile-pic.svg" alt="Profile Picture"
                                    class="profile-pic" />
                </div>
                <div class="items-menu">
                    <form action="SVUsuario" method="get">
                        <ul>
                            <li>
                                <a href="">Ver perfil</a>
                                <input type="hidden" name="action" value="iniciarSesion">
                            </li>
                            <li>
                                <a href="">Actualizar Perfil</a>
                                <input type="hidden" name="action" value="iniciarSesion">
                            </li>
                            <li>
                                <a href="iniciarSesion.jsp">Cerrar Sesi�n</a>
                                <input type="hidden" name="action" value="cerrarSesion">
                            </li>
                        </ul>
                    </form>
                </div>
            </div>
        </div>
    </header>