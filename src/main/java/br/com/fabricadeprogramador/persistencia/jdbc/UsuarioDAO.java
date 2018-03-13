package br.com.fabricadeprogramador.persistencia.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fabricadeprogramador.persistencia.entidade.Usuario;

public class UsuarioDAO {
	private Connection con = ConexaoFactory.getConnection();
	
	public void cadastrar(Usuario usu) {
		String sql = "insert into usuario (nome, login, senha) values (?, ?, md5(?))";
		
		try {
			PreparedStatement preparador = con.prepareStatement(sql);
			preparador.setString(1, usu.getNome());
			preparador.setString(2, usu.getLogin());
			preparador.setString(3, usu.getSenha());
			preparador.execute();
			preparador.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void alterar(Usuario usu) {
		String sql = "update usuario set nome=?, login=?, senha=md5(?) where id=?";

		try {
			PreparedStatement preparador = con.prepareStatement(sql);
			preparador.setString(1, usu.getNome());
			preparador.setString(2, usu.getLogin());
			preparador.setString(3, usu.getSenha());
			preparador.setInt(4, usu.getId());
			preparador.execute();
			preparador.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void excluir(Usuario usu) {
		String sql = "delete from usuario where id=?";

		try (PreparedStatement preparador = con.prepareStatement(sql)) {
			preparador.setInt(1, usu.getId());
			preparador.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void salvar (Usuario usu) {
		if (usu.getId()!=null && usu.getId() != 0) {
			alterar(usu);
		}else {
			cadastrar(usu);
		}
	}
	
	/*
	 * Busca de um registro pelo id do usuário
	 * @param id é um inteiro que representa o número do usuário a ser buscado
	 * @return usuário quando encontra e nulo quando não encontra
	 * */
	public Usuario buscaPorId (Integer id) {
		Usuario usuRetorno = null;
		
		String sql = "select * from usuario where id = ?";
		
		try {
			PreparedStatement preparador = con.prepareStatement(sql);
			preparador.setInt(1, id);
			
			// Retorno da consulta em result set
			ResultSet res = preparador.executeQuery();
			
			// Se tem registro
			if (res.next()) {
				// Instancia o objeto usuário
				usuRetorno = new Usuario();
				usuRetorno.setId(res.getInt("id"));
				usuRetorno.setNome(res.getString("nome"));
				usuRetorno.setLogin(res.getString("login"));
				usuRetorno.setSenha(res.getString("senha"));
				
				return usuRetorno;
			}
			
			preparador.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return null;
	}
	
	/* 
	 * Realiza a busca de todos os registros da tabela de usuário
	 * @return uma lista de usuários
	 * */
	public List<Usuario> buscaTodos () {
		String sql = "select * from usuario order by id";
		
		// Cria lista de usuários
		List<Usuario> lista = new ArrayList<Usuario>();
		
		try {
			PreparedStatement preparador = con.prepareStatement(sql);
			
			// Retorno da consulta em result set
			ResultSet res = preparador.executeQuery();
			
			// Se tem registro
			while (res.next()) {
				// Instancia o objeto usuário
				Usuario usu = new Usuario();
				usu.setId(res.getInt("id"));
				usu.setNome(res.getString("nome"));
				usu.setLogin(res.getString("login"));
				usu.setSenha(res.getString("senha"));
				
				// Adicionando usuário na lista
				lista.add(usu);
			}
			
			preparador.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return lista;
	}
	
	public Usuario autenticar (Usuario usuConsulta) {
		String sql = "select * from usuario where login = ? and senha = md5(?)";
		
		try (PreparedStatement preparador = con.prepareStatement(sql)) {
			
			preparador.setString(1, usuConsulta.getLogin());
			preparador.setString(2, usuConsulta.getSenha());
			
			// Retorno da consulta em result set
			ResultSet res = preparador.executeQuery();
			
			if (res.next()) {
				Usuario usu = new Usuario();
				usu.setId(res.getInt("id"));
				usu.setNome(res.getString("nome"));
				usu.setLogin(res.getString("login"));
				usu.setSenha(res.getString("senha"));
				
				return usu;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
