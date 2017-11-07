/*
 */
package cz.fai.blog.web;

import cz.fai.blog.dto.AuthorDto;
import cz.fai.blog.manager.AuthorManager;
import static cz.fai.blog.util.JsonUtil.readJson;
import static cz.fai.blog.util.JsonUtil.writeJson;
import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author František Špaček
 */
@WebServlet(name = "AdminServlet", urlPatterns = "/admin")
public class AdminServlet extends HttpServlet {

    private AuthorManager authorManager;

    @Inject
    public void setAuthorManager(AuthorManager authorManager) {
        this.authorManager = authorManager;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        final AuthorDto dto = readJson(req, AuthorDto.class);
        writeJson(resp, authorManager.createAuthor(dto));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        writeJson(resp, authorManager.getAuthors());
    }

}
