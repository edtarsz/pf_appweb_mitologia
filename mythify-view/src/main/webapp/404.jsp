<%-- 
    Document   : 404
    Created on : 17 nov 2024, 18:24:38
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
        <title>Not found</title>
    </head>

    <body>
                    <h1>No se encontro la pagina</h1>
                    <p>El recurso solicitado no fue encontrado dentro del servidor.</p>
             
       
    </body>
</html>
