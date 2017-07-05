<%-- 
    Document   : index
    Created on : Jul 5, 2017, 1:43:17 PM
    Author     : pepo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <link rel="stylesheet" href="css\login.css">

        <div class="login-page">
            <div class="form">
                <form class="register-form">
                    <input type="text" placeholder="name"/>
                    <input type="password" placeholder="password"/>
                    <input type="text" placeholder="email address"/>
                    <button>create</button>
                    <p class="message">Ya estas registrado? <a href="#">Sign In</a></p>
                </form>
                <form class="login-form" action="Servlet" method ="get">
                     <div style="color: #FF0000;">${errorMessage}</div>
                    <input type="text" placeholder="username" name="usuario"/>
                    <input type="password" placeholder="password" name="clave"/>
                  <button>login</button>
                    <p class="message">No estás registrado? <a href="register.html">Crea una cuenta</a></p>
                </form>
            </div>
        </div>
    </body>
</html>
