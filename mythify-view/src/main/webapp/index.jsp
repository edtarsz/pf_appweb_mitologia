<%-- Document : post.jsp Created on : 25 oct 2024, 4:00:48 p.m. Author : crist --%>

<%@page import="java.util.Date"%>
<%@page import="java.util.List" %>
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
        <link rel="preconnect" href="https://fonts.googleapis.com" />
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
        <link
            href="https://fonts.googleapis.com/css2?family=EB+Garamond:ital,wght@0,400..800;1,400..800&display=swap"
            rel="stylesheet" />
        <link
            href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap"
            rel="stylesheet" />
        <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

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
                    <% List<Post> posts = (List<Post>) request.getAttribute("posts"); %>
                    <% for (Post post : posts) {%>
                    <article class="article-post">
                        <a href="SVPost?id=<%= post.getIdPost()%>">
                            <div class="head-article-post">
                                <div class="left-head-article">
                                    <div class="container-pfp-post"></div>
                                    <%
                                        Date fechaCreacion = post.getFechaHoraCreacion();
                                        String tiempoTranscurrido = CalcularTiempo.tiempoTranscurridoDesde(fechaCreacion);
                                    %>
                                    <span class="span-post-header">@<%=post.getUsuario().getNombre()%> • <%= tiempoTranscurrido%>
                                    </span>
                                    <span class="span-post-label">
                                        <%= post.getCategoria()%>
                                    </span>
                                    <img src="<%= request.getContextPath()%>/img/pin-white.svg"
                                         alt="">
                                </div>
                                <div class="right-head-article">
                                    <img src="<%= request.getContextPath()%>/img/options-post.svg"
                                         alt="">
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
                        </a>
                    </article>
                    <% }%>
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
                                <img src="<%= request.getContextPath()%>/img/options-post.svg"
                                     alt="">
                            </div>
                        </div>
                        <div class="content-post">
                            <p>Lorem ipsum, dolor sit amet consectetur adipisicing elit.
                                Incidunt culpa
                                porro,
                                perferendis voluptate quaerat assumenda praesentium dignissimos
                                eius esse
                                ratione
                                quas
                                sed voluptatum inventore voluptates illo optio officiis sit
                                harum?</p>
                        </div>
                        <div class="footer-hot-post">
                            <button class="btn-footer-hot-post">
                                <img src="<%= request.getContextPath()%>/img/heart-white.svg"
                                     alt="">
                                4014
                            </button>
                            <button class="btn-footer-hot-post">
                                <img src="<%= request.getContextPath()%>/img/comments-white.svg"
                                     alt="">
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