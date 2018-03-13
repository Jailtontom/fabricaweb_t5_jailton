<%@page import="br.com.fabricadeprogramador.persistencia.entidade.Usuario"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%@include file="menu.jsp" %>

	<%
	Usuario usu = (Usuario)request.getAttribute("usu"); 
	%>
	<form action="usuController.do" method="post">
		Id: <input type="number" name="id" value="<%= usu.getId() %>"/>
		Nome: <input type="text" name="nome" value="<%= usu.getNome() %>"/>
		Login: <input type="text" name="login" value="<%= usu.getLogin() %>"/>
		Senha: <input type="text" name="senha" value="<%= usu.getSenha() %>"/>
		<input type="submit" value="Salvar"/>
	</form>
</body>
</html>