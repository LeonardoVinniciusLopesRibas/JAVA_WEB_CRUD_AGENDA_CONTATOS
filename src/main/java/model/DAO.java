package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.spi.DirStateFactory.Result;

public class DAO {

	private String driver = "org.postgresql.Driver";
	private String url = "jdbc:postgresql://localhost:5432/agenda";
	private String user = "postgres";
	private String password = "gtsnfe2010";

	private Connection conectar() {
		Connection con = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			return null;
		}
	}

	// CRUD CREATE
	public void inserirContato(JavaBeans contato) {
		String create = "INSERT INTO contatos (nome, fone, email) VALUES (?, ?, ?)";
		try {
			// abrir conexão
			Connection con = conectar();

			// preparar a query
			PreparedStatement pst = con.prepareStatement(create);

			// Substituir as interrogações, pelo conteúdo
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());

			pst.executeUpdate();

			// Encerrar conexão com o banco
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	// CRUD READ
	public ArrayList<JavaBeans> listarTodosContatos() {
		// criando um objeto para acessar a classe javabeans
		ArrayList<JavaBeans> contatos = new ArrayList<>();
		String read = "SELECT * FROM contatos ORDER BY idcontato ASC";

		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			ResultSet rs = pst.executeQuery();

			// laço abaixo será executado enquanto tiver ctt

			while (rs.next()) {
				// variáveis de apoio
				String idcontato = rs.getString(1);
				String nome = rs.getString(2);
				String fone = rs.getString(3);
				String email = rs.getString(4);

				// populando javabeans
				contatos.add(new JavaBeans(idcontato, nome, fone, email));
			}
			con.close();
			return contatos;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	// CRUD UPDATE
	// selecionar contato
	public void selecionarContato(JavaBeans contato) {
		String read_by_id = "SELECT * FROM contatos WHERE idcontato = ?";

		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read_by_id);
			pst.setInt(1, Integer.parseInt(contato.getIdcontato()));
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				// setar variáveis javabeans
				contato.setIdcontato(rs.getString(1));
				contato.setNome(rs.getString(2));
				contato.setFone(rs.getString(3));
				contato.setEmail(rs.getString(4));
			}

			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	//editaContato
	public void alterarContato(JavaBeans contato) {
		String update = "UPDATE CONTATOS SET NOME=?, FONE=?, EMAIL=? WHERE IDCONTATO = ?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(update);
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			pst.setInt(4, Integer.parseInt(contato.getIdcontato()));
			pst.executeUpdate();
			con.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	//CRUD DELETE
	
	public void deletarContato(JavaBeans contato) {
		String delete = "DELETE FROM CONTATOS WHERE ID=?";
		
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(delete);
			pst.setInt(1, Integer.parseInt(contato.getIdcontato()));
			pst.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	

	// teste de conexão

	public void testeConexao() {
		try {
			Connection con = conectar();
			System.out.println(con);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
