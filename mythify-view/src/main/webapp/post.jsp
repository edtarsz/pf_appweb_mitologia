<%-- Document : post.jsp Created on : 25 oct 2024, 4:00:48 p.m. Author : crist --%>

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

        <!-- CSS Stylesheets -->
        <link rel="stylesheet" href="<%= request.getContextPath()%>/style/style.css">
        
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
                    <% Post post = (Post) request.getAttribute("post"); %>
                    <article class="article-post">
                        <div class="head-article-post">
                            <div class="left-head-article">
                                <div class="container-pfp-post"></div>
                                <% LocalDateTime fechaCreacion = post.getFechaHoraCreacion();
                                    String tiempoTranscurrido = CalcularTiempo.tiempoTranscurridoDesde(fechaCreacion);
                                %>
                                <span class="span-post-header">@<%=post.getUsuario().getNombre()%> • <%=tiempoTranscurrido%>
                                </span>
                                <span class="span-post-label">
                                    <%= post.getCategoria()%>
                                </span>
                                <% if (post.isAnclado() == true) {%>
                                <img src="<%= request.getContextPath()%>/img/pin-white.svg"
                                     alt="">
                                <% }%>
                            </div>
                            <div class="right-head-article">
                                <button type="button" onclick="toggleDropdown()" class="btn-option">
                                    <img src="<%= request.getContextPath()%>/img/options-post.svg" alt="Opciones" width="20">
                                </button>
                                <div class="dropdown-menu" id="dropdownMenu">
                                    <form action="SVPost" method="get"> 
                                        <input type="hidden" name="options" value="anclarPost">    
                                        <button type="submit">ANCLAR</button>
                                    </form> 
                                    <form action="SVPost" method="get"> 
                                        <input type="hidden" name="options" value="editarPost">  
                                        <button type="submit">EDITAR</button>
                                    </form> 
                                    <form action="SVPost" method="get"> 
                                        <input type="hidden" name="options" value="borrarPost">  
                                        <button type="submit">ELIMINAR</button>
                                    </form> 
                                </div>

                            </div>
                        </div>
                        <h3>
                            <%= post.getTitulo()%>
                        </h3>
                        <div class="content-post">
                            <p>
                                <%= post.getContenido()%>
                            </p>
                        </div>
                        <div class="footer-post">
                            <button class="btn-footer">
                                <img src="<%= request.getContextPath()%>/img/heart-black.svg"
                                     alt="">
                                19
                            </button>
                            <button class="btn-footer">
                                <img src="<%= request.getContextPath()%>/img/comments-black.svg"
                                     alt="">
                                2 comments
                            </button>
                        </div>
                    </article>
                    <div>
                        <span class="order-comments">Ordenar por:</span> Mejores <img
                            src="img/down-arrow-white.svg" alt="">
                    </div>
                    <button class="add-comment">
                        <img src="img/plus.svg" alt="" class="svg-btn">
                        Añadir un comentario
                    </button>

                    <div class="container-comments">
                        <article class="comment-post">
                            <div class="head-article-post">
                                <div class="left-head-article">
                                    <div class="container-pfp-post">
                                        <img src="img/bob.PNG" alt="" class="pfp-post">
                                    </div>
                                    <span class="span-post-header">@ramosz replied to
                                        @crab</span>
                                </div>
                                <div class="right-head-article">
                                    
                                        <img src="<%= request.getContextPath()%>/img/options-post.svg" alt="Opciones" width="20">
                                    
                                </div>
                            </div>
                            <div class="content-comment-post">
                                <p>
                                    Lorem ipsum, dolor sit amet consectetur adipisicing
                                    elit. Incidunt
                                    culpa porro, perferendis voluptate quaerat assumenda
                                    praesentium
                                    dignissimos eius esse ratione quas sed voluptatum
                                    inventore
                                    voluptates illo optio officiis sit harum? Reprehenderit
                                    facilis
                                    quis quae consequuntur ea, animi rem, natus
                                    necessitatibus velit
                                    rerum amet ex odit officiis magnam accusantium iste
                                    atque placeat
                                    aliquid, sequi qui. Modi consequuntur numquam dolorum
                                    qui
                                    laboriosam!
                                </p>
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

                        <div class="container-crear-post">
                            <form action="SVComentario" method="post">
                                <input type="hidden" name="action" value="comentarPost">

                                <input type="hidden" name="id"
                                       value="<%= post.getIdPost()%>">

                                <textarea name="contenido" placeholder="Comentario..."
                                          class="input-area-post" required></textarea>

                                <div class="post-buttons">
                                    <button type="submit"
                                            class="btn-submit">Publicar</button>
                                    <button type="button"
                                            class="btn-cancel">Cancelar</button>
                                </div>
                            </form>
                        </div>

                        <article class="second-comment-post">
                            <div class="head-article-post">
                                <div class="left-head-article">
                                    <div class="container-pfp-post">
                                        <img src="img/calamardo.PNG" alt=""
                                             class="pfp-post">
                                    </div>
                                    <span class="span-post-header">@bob replied to
                                        @user</span>
                                </div>
                                <div class="right-head-article">
                                    <img src="img/options-post.svg" alt="">
                                </div>
                            </div>
                            <div class="content-comment-post">
                                <p>
                                    Lorem ipsum, dolor sit amet consectetur adipisicing
                                    elit. Incidunt
                                    culpa porro, perferendis voluptate quaerat assumenda
                                    praesentium
                                    dignissimos eius esse ratione quas sed voluptatum
                                    inventore
                                    voluptates illo optio officiis sit harum? Reprehenderit
                                    facilis
                                    quis quae consequuntur ea, animi rem, natus
                                    necessitatibus velit
                                    rerum amet ex odit officiis magnam accusantium iste
                                    atque placeat
                                    aliquid, sequi qui. Modi consequuntur numquam dolorum
                                    qui
                                    laboriosam!
                                </p>
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

                        <article class="comment-post">
                            <div class="head-article-post">
                                <div class="left-head-article">
                                    <div class="container-pfp-post">
                                        <img src="img/patricio.PNG" alt="" class="pfp-post">
                                    </div>
                                    <span class="span-post-header">@patricio replied to
                                        @crab</span>
                                </div>
                                <div class="right-head-article">
                                    <img src="img/options-post.svg" alt="">
                                </div>
                            </div>
                            <div class="content-comment-post">
                                <p>
                                    Lorem ipsum, dolor sit amet consectetur adipisicing
                                    elit. Incidunt
                                    culpa porro, perferendis voluptate quaerat assumenda
                                    praesentium
                                    dignissimos eius esse ratione quas sed voluptatum
                                    inventore
                                    voluptates illo optio officiis sit harum? Reprehenderit
                                    facilis
                                    quis quae consequuntur ea, animi rem, natus
                                    necessitatibus velit
                                    rerum amet ex odit officiis magnam accusantium iste
                                    atque placeat
                                    aliquid, sequi qui. Modi consequuntur numquam dolorum
                                    qui
                                    laboriosam!
                                </p>
                            </div>
                            <div class="footer-comments">
                                <div class="group-footer-btn">
                                    <button class="btn-footer">
                                        <img src="img/heart-black.svg" alt="">
                                        57
                                    </button>
                                    <button class="btn-footer">
                                        <img src="img/reply.svg" alt="">
                                        Responder
                                    </button>
                                </div>
                                <span class="span-post-header">hace 2 horas</span>
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