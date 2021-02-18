package controller;

import model.User;
import service.IUserService;
import service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserServlet", urlPatterns = "/users")
public class UserServlet extends HttpServlet {
    private final IUserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "edit":
                showFormEdit(req, resp);
                break;
            case "delete":
                deleteUser(req, resp);
                break;
            case "create":
                showFormCreate(req, resp);
                break;
            case "search":
                showSearchCountryForm(req, resp);
                break;
            case "permission":
                addUserPermission(req, resp);
                break;
            default:
                showAllUser(req, resp);
                break;
        }
    }

    private void addUserPermission(HttpServletRequest req, HttpServletResponse resp) {
        User user = new User("kien", "kienhoang@gmail.com", "vn");
        int[] permision = {1, 2, 4};
        userService.addUserTransaction(user, permision);
    }

    private void showSearchCountryForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("search.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void showFormCreate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("create.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        userService.deleteUser(id);
        List<User> list = userService.findAll();
        req.setAttribute("list", list);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("userlist.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void showFormEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        User user = this.userService.findByID(id);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("edit.jsp");
        req.setAttribute("user", user);
        requestDispatcher.forward(req, resp);
    }

    private void showAllUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("userlist.jsp");
        List<User> userList = userService.findAll();
        req.setAttribute("list", userList);
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "edit":
                editUser(req, resp);
                break;
            case "create":
                createUser(req, resp);
                break;
            case "search":
                searchByCountry(req, resp);
                break;

        }
    }

    private void searchByCountry(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String country = req.getParameter("country");
        List<User> result = userService.findByCountry(country);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("search.jsp");
        req.setAttribute("list", result);
        requestDispatcher.forward(req, resp);

    }

    private void createUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String country = req.getParameter("country");
        User newUser = new User(name, email, country);
        userService.createUser(newUser);
        resp.sendRedirect("/users");

    }

    private void editUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String country = req.getParameter("country");
        User editUser = new User(id, name, email, country);
        this.userService.update(editUser);
        resp.sendRedirect("/users"); //thao tac xong dieu huong ve` users
    }
}
