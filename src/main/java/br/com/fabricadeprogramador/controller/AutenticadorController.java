package br.com.fabricadeprogramador.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.fabricadeprogramador.persistencia.entidade.Usuario;
import br.com.fabricadeprogramador.persistencia.jdbc.UsuarioDAO;

@WebServlet("/autenticador.do")
public class AutenticadorController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession sessao = req.getSession(false);
		
		if (sessao != null) {
			sessao.invalidate();
		}
		
		resp.sendRedirect("login.html");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// Capturando dados da tela
		String login = req.getParameter("login");
		String senha = req.getParameter("senha");
		
		// Colocando dados em objeto usuário
		Usuario usu = new Usuario();
		usu.setLogin(login);
		usu.setSenha(senha);
		
		// Consultando se usuário existe no banco
		UsuarioDAO usuDao = new UsuarioDAO();
		Usuario usuAut = usuDao.autenticar(usu);
		
		// Verificando se usuário foi encontrado
		if (usuAut != null) {
			// Colocando usuário na sessão
			HttpSession sessao = req.getSession();
			sessao.setAttribute("usuAut", usuAut);
			
			sessao.setMaxInactiveInterval(60*5);
			
			// Redirecionando usuário
			req.getRequestDispatcher("WEB-INF/index.jsp").forward(req, resp);
		} else {
			resp.getWriter().print("<script> window.alert ('Não encontrado!'); location.href='login.html'; </script>");
		}
	}
}
