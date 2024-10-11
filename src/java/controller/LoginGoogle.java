package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dal.AccountDAO;
import dal.GoogleAccountDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.GoogleAccount;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

public class LoginGoogle extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processLogin(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processLogin(request, response);
    }

    private void processLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("code");
        if (code == null || code.isEmpty()) {
            redirectToLoginWithError(request, response, "Authorization code is missing.");
            return;
        }

        try {
            String accessToken = getToken(code);
            GoogleAccount user = getUserInfo(accessToken);
            handleGoogleAccount(user, request, response);
        } catch (Exception e) {
            redirectToLoginWithError(request, response, "Failed to authenticate with Google.");
        }
    }

    /**
     * Get credential from Google method
     *
     * @param code
     * @return
     * @throws IOException
     */
    private String getToken(String code) throws IOException {
        String response = Request.Post(Constants.GOOGLE_LINK_GET_TOKEN)
                .bodyForm(Form.form()
                        .add("client_id", Constants.GOOGLE_CLIENT_ID)
                        .add("client_secret", Constants.GOOGLE_CLIENT_SECRET)
                        .add("redirect_uri", Constants.GOOGLE_REDIRECT_URI)
                        .add("code", code)
                        .add("grant_type", Constants.GOOGLE_GRANT_TYPE).build())
                .execute().returnContent().asString();

        JsonObject jsonObject = new Gson().fromJson(response, JsonObject.class);
        return jsonObject.get("access_token").getAsString();
    }

    /**
     * Get user information from Google method
     *
     * @param accessToken
     * @return
     * @throws IOException
     */
    private GoogleAccount getUserInfo(String accessToken) throws IOException {
        String userInfoUrl = Constants.GOOGLE_LINK_GET_USER_INFO + accessToken;
        String response = Request.Get(userInfoUrl).execute().returnContent().asString();
        return new Gson().fromJson(response, GoogleAccount.class);
    }

    private void handleGoogleAccount(GoogleAccount user, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        GoogleAccountDAO googleAccountDAO = new GoogleAccountDAO();
        boolean isAdded = googleAccountDAO.addGoogleAccount(user.getEmail());

        if (isAdded) {
            System.out.println("Account added successfully.");
        } else {
            System.out.println("Account already exists.");
        }

        GoogleAccount account = googleAccountDAO.getAccount(user.getEmail());
        if (account == null) {
            redirectToLoginWithError(request, response, "Account not found.");
            return;
        }
        
        createSessionAndRedirect(request, response, account);
    }

    /**
     * Initialize session and redirect user to home page method.
     *
     * @param request
     * @param response
     * @param account
     * @throws IOException
     */
    private void createSessionAndRedirect(HttpServletRequest request, HttpServletResponse response, GoogleAccount account)
            throws IOException {
        HttpSession session = request.getSession();
        Account userAccount = new Account();
        userAccount.setID_Account(account.getID_Account());
        userAccount.setEmail(account.getEmail());
        userAccount.setUsername(account.getUsername());

        session.setAttribute("account", userAccount);
        session.setAttribute("ID_Account", account.getID_Account());
        response.sendRedirect("home.jsp");

    }

    /**
     * Redirect to login and display error method
     * 
     * @param request
     * @param response
     * @param errorMessage
     * @throws ServletException
     * @throws IOException 
     */
    private void redirectToLoginWithError(HttpServletRequest request, HttpServletResponse response, String errorMessage)
            throws ServletException, IOException {
        request.setAttribute("errorMessage", errorMessage);
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Google Login Controller Servlet";
    }
}
