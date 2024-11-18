<%-- 
    Document   : exception
    Created on : 17 nov 2024, 19:39:01
    Author     : crist
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
       <meta charset="UTF-8" />
        <meta name="description" content="" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
        <meta http-equiv="Pragma" content="no-cache">
        <meta http-equiv="Expires" content="0">

        <!-- CSS Stylesheets -->
        <link rel="stylesheet" href="<c:url value='/style/style.css' />">
        <!-- JavaScript -->
        <script defer src="<%= request.getContextPath()%>/script/script.js"></script>
        <title>Error :(</title>
    </head>
    <body>
         <h1>Uh oh!!</h1>
        <p>Ha ocurrido un error interno. El equipo de Mythify esta trabajando en ello.</p>
    </body>
    
   
</html>
