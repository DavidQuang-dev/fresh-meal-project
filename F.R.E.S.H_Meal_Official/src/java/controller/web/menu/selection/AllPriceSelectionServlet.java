/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.web.menu.selection;

import dao.CategoryDAO;
import dao.ProductDAO;
import dao.Weekly_PlanDAO;
import dto.Category;
import dto.Product;
import dto.Weekly_Plan;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ADMIN
 */
public class AllPriceSelectionServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final String MENU_PAGE = "view/jsp/home/MenuView.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = MENU_PAGE;
        try {
            String categoryName = request.getParameter("categoryName");
            String weeklyPlanId = request.getParameter("weeklyPlanId");
            CategoryDAO cdao = new CategoryDAO();
            Weekly_PlanDAO wpdao = new Weekly_PlanDAO();
            ProductDAO pdao = new ProductDAO();
            ArrayList<Category> listOfCategory = cdao.getCategory();
            ArrayList<Weekly_Plan> listOfWeeklyPlan = wpdao.getWeekly_PlanIsActive();
            if (listOfCategory != null) {
                request.setAttribute("ListOfCategory", listOfCategory);
            } else {
                request.setAttribute("Web_Category_Menu_Page_Error", "Cann't Find Any Category!");
            }
            if (listOfWeeklyPlan != null) {
                request.setAttribute("ListOfWeeklyPlan", listOfWeeklyPlan);
            } else {
                request.setAttribute("Web_Weekly_Plan_Menu_Page_Error", "Cann't Find Any Weekly Plan!");
            }
            if (!categoryName.isEmpty()) {
                ArrayList<Product> listOfFilterCategoryProduct = pdao.getProductInCategoryWithAllPriceSelection(categoryName, Integer.parseInt(weeklyPlanId));
                if (listOfFilterCategoryProduct != null) {
                    request.setAttribute("ListOfFilterCategoryProduct", listOfFilterCategoryProduct);
                } else {
                    request.setAttribute("Error", "Cann't Find Any Product");
                }
                request.setAttribute("CategoryName", categoryName);
                request.setAttribute("WeeklyPlanID", weeklyPlanId);
            } else {
                if (!weeklyPlanId.isEmpty()) {
                    ArrayList<Product> listOfFilterCategoryProduct = pdao.getProductWithAllPriceSelection(Integer.parseInt(weeklyPlanId));
                    if (listOfFilterCategoryProduct != null) {
                        request.setAttribute("ListOfFilterCategoryProduct", listOfFilterCategoryProduct);
                    } else {
                        request.setAttribute("Error", "Cann't Find Any Product");
                    }
                    request.setAttribute("WeeklyPlanID", weeklyPlanId);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
