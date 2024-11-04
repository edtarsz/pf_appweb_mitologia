<%@page import="org.itson.mythify.entidad.Usuario" %>
    <header class="post-header">
        <a href="${pageContext.request.contextPath}/index.jsp" class="header-svg-container">
            <img src="${pageContext.request.contextPath}/img/icon.svg" alt="Logo Mythify" class="header-svg" />
            <span class="header-text">Mythify</span>
        </a>
        <div class="search-bar-container">
            <input type="text" class="search-bar" placeholder="Buscar..." />
            <img src="<%= request.getContextPath()%>/img/search-icon.svg" alt="Buscar" class="search-icon" />
        </div>
        <div class="container-avatar-header">
            <div class="clicking">
                <div class="click-menu">
                    <% Usuario usuario=(Usuario) request.getSession().getAttribute("usuario"); if (usuario !=null) {%>
                        <p>
                            <%= usuario.getNombre() + " " + usuario.getApellidoPaterno() + " " +
                                usuario.getApellidoMaterno()%>
                        </p>
                        <% } else { %>
                            <p>Usuario no autenticado</p>
                            <% }%>
                                <img src="<%= request.getContextPath()%>/img/profile-pic.svg" alt="Profile Picture"
                                    class="profile-pic" />
                </div>
                <div class="items-menu">
                    <form action="SVUsuario" method="post">
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