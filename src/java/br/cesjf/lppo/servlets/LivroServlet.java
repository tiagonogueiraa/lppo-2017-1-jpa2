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
@WebServlet(name = "LivroServlet", urlPatterns = {"/editar.html", "/excluir.html"})
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
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getServletPath().contains("/editar.html")) {
            doEditarPost(request, response);
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
            
            response.sendRedirect("listar.html");
        }
    }
}
