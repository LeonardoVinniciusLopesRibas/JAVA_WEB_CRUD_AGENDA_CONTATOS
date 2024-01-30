package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import model.JavaBeans;

//@WebServlet(name = "Servlet", urlPatterns = {"/Servlet", "/main"})
@WebServlet(urlPatterns = { "/controller", "/main", "/insert", "/select", "/update", "/delete", "/report" })
public class controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DAO dao = new DAO();
	JavaBeans contato = new JavaBeans();

	public controller() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		System.out.println(action);
		if (action.equals("/main")) {
			contatos(request, response);
		} else if (action.equals("/insert")) {
			novoContato(request, response);
		} else if (action.equals("/select")) {
			listarContato(request, response);
		} else if (action.equals("/update")) {
			atualizarContato(request, response);
		} else if (action.equals("/delete")) {
			deletarContato(request, response);
		} else if (action.equals("/report")) {
			relatorioCadastrado(request, response);
		} else {
			response.sendRedirect("index.html");
		}

		// dao.testeConexao();
	}

	protected void contatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Criando um objeto que irá receber os dados javabeans
		ArrayList<JavaBeans> lista = dao.listarTodosContatos();

		// teste de recebimento da lista
		// for(int i = 0; i < lista.size(); i++) {
		// System.out.println(lista.get(i).getIdcontato());
		// System.out.println(lista.get(i).getNome());
		// System.out.println(lista.get(i).getFone());
		// System.out.println(lista.get(i).getEmail());
		// }

		// encaminhar a lista ao documento jsp
		request.setAttribute("contatos", lista);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);

	}

	protected void novoContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// teste de recebimento
		// System.out.println(request.getParameter("nome"));
		// System.out.println(request.getParameter("fone"));
		// System.out.println(request.getParameter("email"));

		// setando variáveis javabeans
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));

		// Invocar o método inserir, passando o objeto contato
		dao.inserirContato(contato);

		// redirecionar para o agenda.jsp
		response.sendRedirect("main");

	}

	// editar contato
	protected void listarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String idcontato = request.getParameter("idcontato");

		// setar a variável javabeans
		contato.setIdcontato(idcontato);

		// Executar o método selecionar contato
		dao.selecionarContato(contato);

		// teste recebimento
		// System.out.println(contato.getIdcontato());
		// System.out.println(contato.getNome());
		// System.out.println(contato.getFone());
		// System.out.println(contato.getEmail());

		// setar atributos do formulários com o cont javabeans
		request.setAttribute("idcontato", contato.getIdcontato());
		request.setAttribute("nome", contato.getNome());
		request.setAttribute("fone", contato.getFone());
		request.setAttribute("email", contato.getEmail());

		// encaminhar ao doc jsp
		RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
		rd.forward(request, response);

	}

	protected void atualizarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// System.out.println(request.getParameter("idcontato"));
		// System.out.println(request.getParameter("nome"));
		// System.out.println(request.getParameter("fone"));
		// System.out.println(request.getParameter("email"));

		// setar variáveis javabeans
		contato.setIdcontato(request.getParameter("idcontato"));
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		dao.alterarContato(contato);

		// redirecionar para o doc agenda, atualizando as alterações
		response.sendRedirect("main");
	}

	protected void deletarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// recebimento do id do contato a ser excluido
		String idcontato = request.getParameter("idcontato");

		// setar a variável javabeans
		contato.setIdcontato(idcontato);

		// teste de verificação se o id chegou
		// System.out.println(idcontato);

		// executar o método deletar contato
		dao.deletarContato(contato);

		// redirecionar ao agenda.jsp
		response.sendRedirect("main");

	}
	
	protected void relatorioCadastrado(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Document documento = new Document();
		
		try {
			//Tipo de conteúdo
			response.setContentType("application/pdf");
			
			//nome do documento
			response.addHeader("Content-Disposition", "inline; filename="+"contatos.pdf");
			
			//criar o documento
			PdfWriter.getInstance(documento, response.getOutputStream());
			
			//Abrir o documento para gerar o conteúdo
			documento.open();
			documento.add(new Paragraph("Lista de Contatos: "));
			documento.add(new Paragraph(" "));
			//criar uma tabela
			//número 3 diz quantas colunas terá a tabela
			PdfPTable tab = new PdfPTable(3);
			
			//Cabeçalho
			PdfPCell col1 = new PdfPCell(new Paragraph("Nome"));
			PdfPCell col2 = new PdfPCell(new Paragraph("Fone"));
			PdfPCell col3 = new PdfPCell(new Paragraph("Email"));
			
			//adicionando as colunas a tabela
			tab.addCell(col1);
			tab.addCell(col2);
			tab.addCell(col3);
			
			//popular tabelas com os contatos dinâmicos
			ArrayList<JavaBeans> lista = dao.listarTodosContatos();
			for (int i = 0; i < lista.size(); i++) {
				
				tab.addCell(lista.get(i).getNome());
				tab.addCell(lista.get(i).getFone());
				tab.addCell(lista.get(i).getEmail());
				
			}
			
			documento.add(tab);
			
			documento.close();
			
			
		} catch (Exception e) {
			System.out.println(e);
			documento.close();
		}
		
	}

}
