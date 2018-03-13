package br.com.fabricadeprogramador.controller;

import java.awt.List;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.fabricadeprogramador.persistencia.entidade.Usuario;
import br.com.fabricadeprogramador.persistencia.jdbc.UsuarioDAO;

@WebServlet("/usuController.do")
public class UsuarioController extends HttpServlet {
	public UsuarioController() {
		System.out.println("Construtor");
	}

	@Override
	public void init() throws ServletException {
		System.out.println("Init");

		super.init();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		String acao = req.getParameter("acao");
		
		if (acao == null) {
			HttpSession sessao = req.getSession(false);
			
			if (sessao != null) {
				RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/index.jsp");
				dispatcher.forward(req, resp);
			}
		} else if (acao.equals("exc")) {
			String id = req.getParameter("id").trim();
			Usuario usu = new Usuario();
			if ((id.trim() != null) && (id.trim().length() > 0))
				usu.setId(Integer.parseInt(id));
			
			UsuarioDAO usuDao = new UsuarioDAO();
			usuDao.excluir(usu);
			
			resp.sendRedirect("usuController.do?acao=lis");
		} else if (acao.equals("lis")) {
			UsuarioDAO usuDao = new UsuarioDAO();
			 java.util.List <Usuario> lista = usuDao.buscaTodos(); 
			
			req.setAttribute("lista", lista);
			RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/listausu.jsp");
			dispatcher.forward(req, resp);
		} else if (acao.equals("alt")) {
			String id = req.getParameter("id").trim();
			
			UsuarioDAO usuDao = new UsuarioDAO();
			Usuario usu = usuDao.buscaPorId(Integer.parseInt(id));
			usu.setSenha("");
			
			req.setAttribute("usu", usu);
			RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/formusuario.jsp");
			dispatcher.forward(req, resp);
		} else if (acao.equals("cad")) {
			Usuario usu = new Usuario();
			usu.setId(0);
			usu.setNome("");
			usu.setLogin("");
			usu.setSenha("");
			
			req.setAttribute("usu", usu);
			RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/formusuario.jsp");
			dispatcher.forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id").trim();
		String nome = req.getParameter("nome");
		String login = req.getParameter("login");
		String senha = req.getParameter("senha");

		Usuario usu = new Usuario();
		usu.setNome(nome);
		usu.setLogin(login);
		
		UsuarioDAO usuDao = new UsuarioDAO();

		// Busca senha antiga do usuário se desejar não alterar a senha
		if ((senha == null) || (senha == "")){
			Usuario usuAnt = usuDao.buscaPorId(Integer.parseInt(id));
			usu.setSenha(usuAnt.getSenha());
		} else {
			usu.setSenha(senha);
		}
		
		if ((id.trim() != null) && (id.trim().length() > 0)) {
			usu.setId(Integer.parseInt(id));

			if ((nome.isEmpty()) && (login.isEmpty()) && (senha.isEmpty())) {
				usuDao.excluir(usu);
			} else {
				usuDao.salvar(usu);
			}
		} else {
			usuDao.salvar(usu);
		}

		resp.getWriter().print("<h1>Sucesso!</h1>");
	}

	@Override
	public void destroy() {
		System.out.println("Destroi");
		super.destroy();
	}
}
