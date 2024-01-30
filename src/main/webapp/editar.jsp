<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AGENDA DE CONTATOS</title>
<link rel="icon" href="imagens/icone_telefone.png">
<link rel="stylesheet" href="style.css">
</head>
<body>

	<h1>Editar novo contato</h1>
	<form name="frmContato" action="update">

		<table>
			<tr>
				<td><input type="text" name="idcontato" id="id_input" readonly value="<%out.print(request.getAttribute("idcontato"));%>"></td>
			</tr>
			<tr>
				<td><input type="text" name="nome" class="generic_input" value="<%out.print(request.getAttribute("nome"));%>"></td>
			</tr>
			<tr>
				<td><input type="text" name="fone" class="email_input" value="<%out.print(request.getAttribute("fone"));%>"></td>
			</tr>
			<tr>
				<td><input type="text" name="email" class="generic_input" value="<%out.print(request.getAttribute("email"));%>"></td>
			</tr>
		</table>
		<input type="button" value="SALVAR" class="button-one"
			onclick="validar()">

	</form>

	<script src="scripts/validador.js"></script>
</body>
</html>