<aside class="aside-mythology">
	<a href="formPost.jsp" class="a-create-post">
		<button class="btn-create-post">
			<img src="<%= request.getContextPath()%>/img/post-pic.svg" alt="Profile Picture" class="post-pic" />
			CREAR UN POST
		</button>
	</a>
	<h2 class="mythologies-title">MITOLOGÍAS</h2>
	<div class="container-mythologies">
		<button class="btn-mythology">EGIPCIA</button>
		<button class="btn-mythology">GRIEGA</button>
		<select class="btn-mythology-select" id="mythologySelect">
			<option value="" disabled selected hidden>MESOAMERICANA</option>
			<option value="azteca.html">AZTECA</option>
			<option value="maya.html">MAYA</option>
		</select>
		<button class="btn-mythology">NÓRDICA</button>
		<button class="btn-mythology">ROMANA</button>
	</div>
</aside>