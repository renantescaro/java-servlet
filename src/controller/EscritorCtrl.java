package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.ArrayList;
import java.sql.Date;

import dao.EscritorDao;
import dao.PessoaDao;
import entity.Escritor;
import entity.Pessoa;

@WebServlet(urlPatterns = "/listar")
public class EscritorCtrl extends HttpServlet{
	
	// METODO PRA RETORNAR O HTML (GET)
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse reponses) throws IOException{

		EscritorDao escritorDao = new EscritorDao();
		List<Escritor> escritores = new ArrayList<Escritor>();
		
		try {
			escritores = escritorDao.selecionarTudo();
		} catch (SQLException e) {
			escritores = null;
		}
		
		
		String acao = request.getParameter("acao");
		
		if(acao == null){

			PrintWriter html = reponses.getWriter();

			html.println("<html>");
			html.println("<head>");
				html.println("<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css'>");
			html.println("</head>");
			html.println("<body>");
			html.println("<div style=''>");
			
			html.println("<div style='text-align: center'>");
				html.println("<h1>Listagem de Autores</h1>");
			html.println("</div>");
			
			html.println("<a href='?acao=novo' class='btn btn-primary'>Novo</a>");
			
			html.println("<table class='table table-striped table-bordered'>");
				html.println("<thead>");
					html.println("<tr>");
						html.println("<th>Id</th>");
						html.println("<th>Nome</th>");
						html.println("<th>Idade</th>");
						html.println("<th>Observação</th>");
					html.println("</tr>");
				html.println("</thead>");
			
				html.println("<tbody>");
				
				escritores.forEach(escritor -> {
				
					html.println("<tr>");
						html.println("<td>");
							html.println(escritor.getId());
						html.println("</td>");
						html.println("<td>");
							html.println(escritor.getPessoa().getNome());
						html.println("</td>");
						html.println("<td>");
							html.println(escritor.getPessoa().getDataNascimento());
						html.println("</td>");
						html.println("<td>");
							html.println(escritor.getPessoa().getObservacao());
						html.println("</td>");
					html.println("</tr>");
				});
					
				html.println("</tbody>");
			html.println("</table>");
			
			html.println("</div>");
			
			html.println("<script src='https://code.jquery.com/jquery-3.4.1.slim.min.js'></script>");
			html.println("<script src='https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js'></script>");
			html.println("<script src='https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js'></script>");
			html.println("</body>");
			html.println("</html>");
			
		}else{
			
			PrintWriter html = reponses.getWriter();
			
			html.println("<html>");
			html.println("<head>");
				html.println("<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css'>");
			html.println("</head>");
			html.println("<body>");
			html.println("<div style=''>");

				html.println("<div style='text-align: center'>");
					html.println("<h1>Cadastro de Escritores</h1>");
				html.println("</div>");
				
				html.println("<form method='post' action='' >");
					html.println("<div class='form-group'>");
						html.println("<div>");
							html.println("<label>Nome</label>");
							html.println("<input name='nome' class='form-control'>");
						html.println("</div>");
						html.println("<div>");
							html.println("<label>Data Nascimento</label>");
							html.println("<input name='data_nascimento' class='form-control'>");
						html.println("</div>");
						html.println("<div>");
							html.println("<label>Observações</label>");
							html.println("<input name='observacoes' class='form-control'>");
						html.println("</div>");
					html.println("</div>");
					html.println("<a href='?' class='btn btn-danger'>Voltar</a>");
					html.println("<button type='submit' class='btn btn-primary'>Salvar</button>");
				html.println("</form>");
				
			html.println("</div>");
			
			html.println("<script src='https://code.jquery.com/jquery-3.4.1.slim.min.js'></script>");
			html.println("<script src='https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js'></script>");
			html.println("<script src='https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js'></script>");
			html.println("</body>");
			html.println("</html>");
		}
	}
	
	// metodo pra salvar e editar (POST)
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse reponses) throws IOException{
		
		String nome   = request.getParameter("nome");
		String dataNascimento  = request.getParameter("data_nascimento");
		String observacao = request.getParameter("observacao");
		java.util.Date dataTemporartia;
		java.sql.Date data = null;
		
		try{
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			dataTemporartia = (java.util.Date) sd.parse(dataNascimento);
			data = new java.sql.Date (dataTemporartia.getTime());
			
		}catch (ParseException e){
			dataTemporartia = null;
		}
		
		try{
			PessoaDao pessoaDao = new PessoaDao();
			Pessoa pessoa = new Pessoa();
			EscritorDao escritorDao = new EscritorDao();
			Escritor escritor = new Escritor();
			
			pessoa.setNome(nome);
			pessoa.setObservacao(observacao);
			pessoa.setDataNascimento(data);
			pessoa = pessoaDao.inserir(pessoa);
			
			escritor.setPessoa(pessoa);
			escritorDao.inserir(escritor);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
}
