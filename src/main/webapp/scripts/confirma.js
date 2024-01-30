/**
 * 
 * Confirmação de exclusão
 */

 function confirmar(idcontato){
	 let resposta = confirm("Confirma a exclusão deste contato? ")
	 
	 if(resposta === true){
		 //alert(idcontato)
		 window.location.href = "delete?idcontato="+idcontato
	 }
 }