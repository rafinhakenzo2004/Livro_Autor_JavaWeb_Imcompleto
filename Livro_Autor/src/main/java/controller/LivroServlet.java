package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Livro;
import persistence.GenericDao;
import persistence.LivroDao;

@WebServlet("/livro")
public class LivroServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LivroServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao");
		String codigo = request.getParameter("codigo");
		
		Livro l = new Livro();
		String erro = "";
		List<Livro> livros = new ArrayList<>();
		
		try {
			if (acao != null) {
				l.setCodigo(Integer.parseInt(codigo));
				
				GenericDao gDao = new GenericDao();
				LivroDao lDao = new LivroDao(gDao);
				
				if (acao.equalsIgnoreCase("excluir")) {
					lDao.excluir(l);
					livros = lDao.listar();
					l = null;
				} else {
					l = lDao.buscar(l);
					livros = null;
				}
			}
		} catch (SQLException | ClassNotFoundException e) {
			erro = e.getMessage();
		} finally {
			request.setAttribute("erro", erro);
			request.setAttribute("livro", l);
			request.setAttribute("livros", livros);

			RequestDispatcher dispatcher = 
					request.getRequestDispatcher(
							"livro.jsp");
			dispatcher.forward(request, response);

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String saida = "";
		String erro = "";
		List<Livro> livros = new ArrayList<Livro>();
		Livro l = new Livro();
		String cmd = "";
		try {
			String codigo = request.getParameter("codigo");
			String nome = request.getParameter("nome");
			String lingua = request.getParameter("lingua");
			String ano = request.getParameter("ano");
			cmd = request.getParameter("botao");
			
			if (!cmd.equalsIgnoreCase("Listar")) {
				l.setCodigo(Integer.parseInt(codigo));
			}
			if (cmd.equalsIgnoreCase("Inserir") || 
					cmd.equalsIgnoreCase("Atualizar")) {
				l.setNome(nome);
				l.setLingua(lingua);
				l.setAno(Integer.parseInt(ano));
			}
			
			GenericDao gDao = new GenericDao();
			LivroDao lDao = new LivroDao(gDao);
			
		
			if (cmd.equalsIgnoreCase("Inserir")) {
				saida = lDao.inserir(l);
			}
			if (cmd.equalsIgnoreCase("Atualizar")) {
				saida = lDao.atualizar(l);
			}
			if (cmd.equalsIgnoreCase("Excluir")) {
				saida = lDao.excluir(l);
			}
			if (cmd.equalsIgnoreCase("Buscar")) {
				l = lDao.buscar(l);
			}
			if (cmd.equalsIgnoreCase("Listar")) {
				livros = lDao.listar();
			}

		} catch (SQLException | ClassNotFoundException | NumberFormatException e) {
			saida = "";
			erro = e.getMessage();
			if (erro.contains("input string")) {
				erro = "Preencha os campos corretamente";
			}
		} finally {
			if (!cmd.equalsIgnoreCase("Buscar")) {
				l = null;
			}
			if (!cmd.equalsIgnoreCase("Listar")) {
				livros = null;
			}
			request.setAttribute("erro", erro);
			request.setAttribute("saida", saida);
			request.setAttribute("livro", l);
			request.setAttribute("livros", livros);

			RequestDispatcher dispatcher = 
					request.getRequestDispatcher("livro.jsp");
			dispatcher.forward(request, response);
		}
		
	}

}