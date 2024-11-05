<aside class="aside-mythology">
    <a href="formPost.jsp" class="a-create-post">
        <button class="btn-create-post">
            <img src="<%= request.getContextPath()%>/img/post-pic.svg" alt="Profile Picture" class="post-pic" />
            CREAR UN POST
        </button>
    </a>
    <h2 class="mythologies-title">MITOLOGÍAS</h2>
    <div class="container-mythologies">
        <form action="SVPost" method="get">
            <input type="hidden" name="mythology" value="egipcia">
            <button type="submit" class="btn-mythology">EGIPCIA</button>
        </form>

        <form action="SVPost" method="get">
            <input type="hidden" name="mythology" value="griega">
            <button type="submit" class="btn-mythology">GRIEGA</button>
        </form>

        <form action="SVPost" method="get">
            <input type="hidden" name="mythology" value="mesoamericana">
            <select class="btn-mythology-select" id="mythologySelect" name="specificMythology"
                onchange="this.form.submit()">
                <option value="" disabled selected hidden>MESOAMERICANA</option>
                <option value="azteca">AZTECA</option>
                <option value="maya">MAYA</option>
            </select>
        </form>

        <form action="SVPost" method="get">
            <input type="hidden" name="mythology" value="nordica">
            <button type="submit" class="btn-mythology">N�RDICA</button>
        </form>

        <form action="SVPost" method="get">
            <input type="hidden" name="mythology" value="romana">
            <button type="submit" class="btn-mythology">ROMANA</button>
        </form>

    </div>
</aside>