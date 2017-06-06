/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lppo.servlets;

import br.cesjf.lppo.Livro;
import br.cesjf.lppo.dao.LivroJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import javax.transaction.UserTransaction;
import javax.xml.rpc.Call;

/**
 *
 * @author alunoces
 */
@WebServlet(name = "LivroServlet", urlPatterns = {"/editar.html", "/excluir.html", "/criar.html", "/listar.html"})
public class LivroServlet extends HttpServlet {

    @PersistenceUnit(unitName = "lppo-2017-1-jpa2PU")
    EntityManagerFactory emf;

    @Resource(name = "java:comp/UserTransaction")
    UserTransaction ut;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getServletPath().contains("/editar.html")) {
            doEditarGet(request, response);
        } else if (request.getServletPath().contains("/excluir.html")) {
            doExcluirGet(request, response);
        } else if (request.getServletPath().contains(("/listar.html"))) {
            doListarGet(request, response);
        } else if (request.getServletPath().contains(("criar.html"))){
            doCriarGet(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getServletPath().contains("/editar.html")) {
            doEditarPost(request, response);
        } else if (request.getServletPath().contains("/criar.html")) {
            doCriarPost(request, response);
        }

    }

    private void doEditarGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            LivroJpaController dao = new LivroJpaController(ut, emf);

            //quero do banco o que tem id, que é o que está na requisicao
            Long id = Long.parseLong(request.getParameter("id"));
            Livro livro = dao.findLivro(id);
            request.setAttribute("livro", livro);
            request.getRequestDispatcher("WEB-INF/livro-editar.jsp").forward(request, response);

        } catch (Exception e) {
            response.sendRedirect("listar.html");

        }
    }

    private void doEditarPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            LivroJpaController dao = new LivroJpaController(ut, emf);

            //quero do banco o que tem id, que é o que está na requisicao
            Long id = Long.parseLong(request.getParameter("id"));
            Livro livro = dao.findLivro(id);
            //agora ele vai preencher os dados, (no caso atualizando)
            livro.setTitulo(request.getParameter("titulo"));
            livro.setAutor(request.getParameter("autor"));
            livro.setAno(Integer.parseInt(request.getParameter("ano")));

            dao.edit(livro);

            response.sendRedirect("listar.html");

        } catch (Exception e) {
            response.sendRedirect("listar.html");

        }
    }

    private void doExcluirGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            LivroJpaController dao = new LivroJpaController(ut, emf);

            //quero do banco o que tem id, que é o que está na requisicao
            Long id = Long.parseLong(request.getParameter("id"));
            dao.destroy(id);

        } catch (Exception ex) {
            
        }
            response.sendRedirect("listar.html");
    }

    private void doListarGet(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException {
        try {
             List<Livro> livros = new ArrayList<>();

        LivroJpaController dao = new LivroJpaController(ut, emf);
        
        livros = dao.findLivroEntities();

        request.setAttribute("livros", livros);
        request.getRequestDispatcher("WEB-INF/listar-livros.jsp").forward(request, response);
        }
        catch (Exception ex){
            
        }
}

    private void doCriarGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
        try {
             request.getRequestDispatcher("WEB-INF/novo-livro.jsp").forward(request, response);
        } catch (Exception ex){
            
        }
       
    }

    private void doCriarPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        Livro livro1 = new Livro();
        livro1.setTitulo(request.getParameter("titulo"));
        livro1.setAno(Integer.parseInt(request.getParameter("ano")));
        livro1.setAutor(request.getParameter("autor"));

        LivroJpaController dao = new LivroJpaController(ut, emf);
        
        try {
            dao.create(livro1);
            response.sendRedirect("listar.html");
        } catch (Exception ex) {
            Logger.getLogger(LivroServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}
