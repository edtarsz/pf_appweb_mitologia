<%-- 
    Document   : post.jsp
    Created on : 25 oct 2024, 4:00:48 p.m.
    Author     : crist
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8" />
    <meta name="description" content="" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css" />
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link href="https://fonts.googleapis.com/css2?family=EB+Garamond:ital,wght@0,400..800;1,400..800&display=swap"
          rel="stylesheet" />
    <link href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap"
          rel="stylesheet" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <script defer src="${pageContext.request.contextPath}/script/script.js"></script>
    <title>Mythify</title>
</head>

<body>
    <header class="post-header">
        <a href="${pageContext.request.contextPath}/index.jsp" class="header-svg-container">
            <img src="${pageContext.request.contextPath}/img/icon.svg" alt="Logo Mythify" class="header-svg" />
            <span class="header-text">Mythify</span>
        </a>
        <div class="search-bar-container">
            <input type="text" class="search-bar" placeholder="Buscar..." />
            <img src="${pageContext.request.contextPath}/img/search-icon.svg" alt="Buscar" class="search-icon" />
        </div>
        <div class="container-avatar-header">
            <img src="${pageContext.request.contextPath}/img/profile-pic.svg" alt="Profile Picture" class="profile-pic" />
        </div>
    </header>

    <div class="post-container">
        <aside class="left-aside">
            <button class="btn-create-post">
                <img src="${pageContext.request.contextPath}/img/post-pic.svg" alt="Profile Picture" class="post-pic" />
                CREAR UN POST
            </button>
            <div class="mythologies-list">
                <h2 class="mythologies-title">MITOLOGÍAS</h2>
                <button class="btn-mythology">EGIPCIA</button>
                <button class="btn-mythology">GRIEGA</button>
                <select class="btn-mythology-select" id="mythologySelect">
                    <option value="" disabled selected hidden>MESOAMERICANA</option>
                    <option value="azteca.jsp">AZTECA</option>
                    <option value="maya.jsp">MAYA</option>
                </select>
                <button class="btn-mythology">NÓRDICA</button>
                <button class="btn-mythology">ROMANA</button>
            </div>
        </aside>

        <main>
            <article class="article-post">
                <div class="head-article-post">
                    <div class="left-head-article">
                        <div class="container-pfp-post">
                            <img src="${pageContext.request.contextPath}/img/crab.PNG" alt="" class="pfp-post">
                        </div>
                        <span class="span-post-header">@crab • hace 4 horas</span>
                        <span class="span-post-label">EGIPCIA</span>
                    </div>
                    <div class="right-head-article">
                        <img src="${pageContext.request.contextPath}/img/options-post.svg" alt="">
                    </div>
                </div>
                <h3>Eu nemo sit Nullam</h3>
                <div class="content-post">
                    <p>
                        Lorem ipsum, dolor sit amet consectetur adipisicing elit. Incidunt
                        culpa porro, perferendis voluptate quaerat assumenda praesentium
                        dignissimos eius esse ratione quas sed voluptatum inventore
                        voluptates illo optio officiis sit harum? Reprehenderit facilis
                        quis quae consequuntur ea, animi rem, natus necessitatibus velit
                        rerum amet ex odit officiis magnam accusantium iste atque placeat
                        aliquid, sequi qui. Modi consequuntur numquam dolorum qui
                        laboriosam!
                    </p>
                    <br />
                    <p>
                        Lorem ipsum, dolor sit amet consectetur adipisicing elit. Sed quam
                        temporibus nulla facere, deleniti optio molestiae assumenda, magni
                        perferendis voluptatibus consequuntur. Quibusdam incidunt
                        exercitationem neque ex, provident atque quae ea.
                    </p>
                </div>
                <div class="footer-post">
                    <button class="btn-footer">
                        <img src="${pageContext.request.contextPath}/img/heart-black.svg" alt="">
                        1023
                    </button>
                    <button class="btn-footer">
                        <img src="${pageContext.request.contextPath}/img/comments-black.svg" alt="">
                        241 comentarios
                    </button>
                </div>
            </article>

            <div>
				<span class="order-comments">Ordenar por:</span> Mejores <img src="img/down-arrow-white.svg" alt="">
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
							<span class="span-post-header">@ramosz replied to @crab</span>
						</div>
						<div class="right-head-article">
							<img src="img/options-post.svg" alt="">
						</div>
					</div>
					<div class="content-comment-post">
						<p>
							Lorem ipsum, dolor sit amet consectetur adipisicing elit. Incidunt
							culpa porro, perferendis voluptate quaerat assumenda praesentium
							dignissimos eius esse ratione quas sed voluptatum inventore
							voluptates illo optio officiis sit harum? Reprehenderit facilis
							quis quae consequuntur ea, animi rem, natus necessitatibus velit
							rerum amet ex odit officiis magnam accusantium iste atque placeat
							aliquid, sequi qui. Modi consequuntur numquam dolorum qui
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

				<article class="second-comment-post">
					<div class="head-article-post">
						<div class="left-head-article">
							<div class="container-pfp-post">
								<img src="img/calamardo.PNG" alt="" class="pfp-post">
							</div>
							<span class="span-post-header">@bob replied to @user</span>
						</div>
						<div class="right-head-article">
							<img src="img/options-post.svg" alt="">
						</div>
					</div>
					<div class="content-comment-post">
						<p>
							Lorem ipsum, dolor sit amet consectetur adipisicing elit. Incidunt
							culpa porro, perferendis voluptate quaerat assumenda praesentium
							dignissimos eius esse ratione quas sed voluptatum inventore
							voluptates illo optio officiis sit harum? Reprehenderit facilis
							quis quae consequuntur ea, animi rem, natus necessitatibus velit
							rerum amet ex odit officiis magnam accusantium iste atque placeat
							aliquid, sequi qui. Modi consequuntur numquam dolorum qui
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
							<span class="span-post-header">@patricio replied to @crab</span>
						</div>
						<div class="right-head-article">
							<img src="img/options-post.svg" alt="">
						</div>
					</div>
					<div class="content-comment-post">
						<p>
							Lorem ipsum, dolor sit amet consectetur adipisicing elit. Incidunt
							culpa porro, perferendis voluptate quaerat assumenda praesentium
							dignissimos eius esse ratione quas sed voluptatum inventore
							voluptates illo optio officiis sit harum? Reprehenderit facilis
							quis quae consequuntur ea, animi rem, natus necessitatibus velit
							rerum amet ex odit officiis magnam accusantium iste atque placeat
							aliquid, sequi qui. Modi consequuntur numquam dolorum qui
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

