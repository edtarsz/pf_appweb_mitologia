<script defer src="${pageContext.request.contextPath}/script/aside.js"></script>
<aside class="aside-mythology">
    <a href="formPost.jsp" class="a-create-post">
        <button class="btn-create-post">
            <img src="${pageContext.request.contextPath}/img/post-pic.svg" alt="Profile Picture" class="post-pic" />
            CREAR UN POST
        </button>
    </a>
    <h2 class="mythologies-title">MITOLOG�AS</h2>
    <div class="container-mythologies">
        <form action="SVPost" method="get">
            <input type="hidden" name="mythology" value="egipcia">
            <button type="submit" class="btn-mythology">EGIPCIA</button>
        </form>

        <form action="SVPost" method="get">
            <input type="hidden" name="mythology" value="griega">
            <button type="submit" class="btn-mythology">GRIEGA</button>
        </form>

        <div>
            <button type="submit" class="btn-mythology" id="btn-mesoamerica">MESOAMERICANA</button>
            <div class="container-list-mythologies">
                <form action="SVPost" method="get">
                    <input type="hidden" name="mythology" value="maya">
                    <button type="submit" class="btn-select-mythology">MAYA</button>
                </form>
                <hr>
                <form action="SVPost" method="get">
                    <input type="hidden" name="mythology" value="azteca">
                    <button type="submit" class="btn-select-mythology">AZTECA</button>
                </form>
            </div>
        </div>

        <form action="SVPost" method="get">
            <input type="hidden" name="mythology" value="nordica">
            <button type="submit" class="btn-mythology">N?RDICA</button>
        </form>

        <form action="SVPost" method="get">
            <input type="hidden" name="mythology" value="romana">
            <button type="submit" class="btn-mythology">ROMANA</button>
        </form>

    </div>
</aside>