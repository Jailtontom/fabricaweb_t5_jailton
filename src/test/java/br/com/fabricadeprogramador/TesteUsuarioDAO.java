package br.com.fabricadeprogramador;

import java.util.List;

import br.com.fabricadeprogramador.persistencia.entidade.Usuario;
import br.com.fabricadeprogramador.persistencia.jdbc.UsuarioDAO;

public class TesteUsuarioDAO {

	public static void main(String[] args) {
		testeAltenticar();
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
	
	public static void testesalvar () {
		Usuario usu = new Usuario();
		//usu.setId(1);
		usu.setNome("Vimerson");
		usu.setLogin("vim");
		usu.setSenha("123");
				
		UsuarioDAO usuDAO = new UsuarioDAO();
		usuDAO.salvar(usu);
				
		System.out.println("Salvo com sucesso");
	}
	
	public static void testeBuscarPorId () {
		UsuarioDAO usuDAO = new UsuarioDAO();
		Usuario usu = usuDAO.buscaPorId(3);
		System.out.println(usu);
	}
	
	public static void testeBuscarTodos () {
		UsuarioDAO usuDAO = new UsuarioDAO();
		List<Usuario> lista = usuDAO.buscaTodos();
		for	(Usuario u : lista) {
			System.out.println(u);
		}
	}
	
	public static void testeAltenticar () {
		UsuarioDAO usuDAO = new UsuarioDAO();
		Usuario usu = new Usuario();
		usu.setLogin("jao");
		usu.setSenha("123");
		
		Usuario usuRetorno = usuDAO.autenticar(usu);
		System.out.println(usuRetorno);
	}
}
