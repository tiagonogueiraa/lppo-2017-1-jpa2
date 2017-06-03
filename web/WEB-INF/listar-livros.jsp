<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : listar-livros.jsp
    Created on : 02/06/2017, 22:10:47
    Author     : alunoces
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Listar livros</h1>
        <table>
            <th>
            <th>ID</th>
            <th>Titulo</th>
            <th>Autor</th>
            <th>Ano</th>
        </th>
        
        <c:forEach var="livro" items="${livros}">
        
        <tr>
        <td>${livro.id}</td>
        <td>${livro.titulo}</td>
        <td>${livro.autor}</td>
        <td>${livro.ano}</td>
        </tr>
        
        </c:forEach>
        </table>
    </body>
</html>
