<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="model.JavaBeans"%>
<%@ page import="java.util.ArrayList"%>
<%
ArrayList<JavaBeans> lista = (ArrayList<JavaBeans>) request.getAttribute("contatos");
//for(int i = 0; i < lista.size(); i++){
//	out.println(lista.get(i).getIdcontato());
//	out.println(lista.get(i).getNome());
//	out.println(lista.get(i).getFone());
//	out.println(lista.get(i).getEmail());
//}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AGENDA DE CONTATOS</title>
<link rel="icon" href="imagens/icone_telefone.png">
<link rel="stylesheet" href="style.css">
</head>
<body>
	<h1>AGENDA DE CONTATOS</h1>
	<a href="novo.html" class="button-one">Novo Contato</a>
	<a href="report" class="button-one">Relatório</a>
	<table id="tabela">

		<thead>

			<tr>

				<th>Id</th>
				<th>Nome</th>
				<th>Telefone</th>
				<th>E-mail</th>
				<th>Opções</th>

			</tr>

		</thead>
		<tbody>

			<%
			for (int i = 0; i < lista.size(); i++) {
			%>
			<tr>

				<td><%=lista.get(i).getIdcontato()%></td>
				<td><%=lista.get(i).getNome()%></td>
				<td><%=lista.get(i).getFone()%></td>
				<td><%=lista.get(i).getEmail()%></td>
				<td><a href="select?idcontato=<%=lista.get(i).getIdcontato()%>"
					class="button-one">Editar</a>
					<a href="javascript: confirmar(<%=lista.get(i).getIdcontato()%>)" class="button-delete">Editar</a>
					</td>
					

			</tr>
			<%
			}
			%>
		</tbody>

	</table>
	
	<script src="scripts/confirma.js"></script>
</body>
</html>