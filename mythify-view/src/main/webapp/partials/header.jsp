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
                    <ul>
                        <form action="SVUsuario" method="get">
                            <li>
                                <input type="hidden" name="action" value="verPerfil">
                                <button type="submit">Ver Perfil</button>
                            </li>
                        </form>
                        <form action="SVUsuario" method="get">
                            <li>
                                <input type="hidden" name="action" value="iniciarSesion">
                                <button type="submit">Actualizar Perfil</button>
                            </li>
                        </form>
                        <form action="SVUsuario" method="get">
                            <li>
                                <input type="hidden" name="action" value="cerrarSesion">
                                <button type="submit">Cerrar Sesi�n</button>
                            </li>
                        </form>
                    </ul>
                </div>
            </div>
        </div>
    </header>