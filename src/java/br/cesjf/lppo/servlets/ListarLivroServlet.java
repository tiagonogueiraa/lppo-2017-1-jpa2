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
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

/**
 *
 * @author alunoces
 */
@WebServlet(name = "ListarLivroServlet", urlPatterns = {"/Listar.html"})
public class ListarLivroServlet extends HttpServlet {

    @PersistenceUnit(unitName = "lppo-2017-1-jpa2PU")
    EntityManagerFactory emf;

    @Resource(name = "java:comp/UserTransaction")
    UserTransaction ut;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Livro> livros = new ArrayList<>();

        LivroJpaController dao = new LivroJpaController(ut, emf);
        
        livros = dao.findLivroEntities();

        request.setAttribute("livros", livros);
        request.getRequestDispatcher("WEB-INF/listar-livros.jsp").forward(request, response);
    }

}
