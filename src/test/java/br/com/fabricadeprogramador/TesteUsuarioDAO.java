package br.com.fabricadeprogramador;

import br.com.fabricadeprogramador.persistencia.entidade.Usuario;
import br.com.fabricadeprogramador.persistencia.jdbc.UsuarioDAO;

public class TesteUsuarioDAO {

	public static void main(String[] args) {
		testeexcluir();
	}
	
	public static void testecadastrar () {
		// Criando usuário
		Usuario usu = new Usuario();
		usu.setNome("Jaozão");
		usu.setLogin("jzao");
		usu.setSenha("123");
				
		// Cadastrando usuário no banco de dados
		UsuarioDAO usuDAO = new UsuarioDAO();
		usuDAO.cadastrar(usu);
				
		System.out.println("Cadastrado com sucesso");
	}
	
	public static void testealterar () {
		// Alterando usuário
		Usuario usu = new Usuario();
		usu.setNome("J");
		usu.setLogin("j");
		usu.setSenha("12345678");
		usu.setId(4);
		
		// Cadastrando usuário no banco de dados
		UsuarioDAO usuDAO = new UsuarioDAO();
		usuDAO.alterar(usu);
				
		System.out.println("Alterado com sucesso");
	}
	
	public static void testeexcluir () {
		// Alterando usuário
		Usuario usu = new Usuario();
		usu.setId(5);
		
		// Cadastrando usuário no banco de dados
		UsuarioDAO usuDAO = new UsuarioDAO();
		usuDAO.excluir(usu);
				
		System.out.println("Excluído com sucesso");
	}
}
