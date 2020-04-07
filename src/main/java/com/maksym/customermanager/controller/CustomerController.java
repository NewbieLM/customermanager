package com.maksym.customermanager.controller;

import com.maksym.customermanager.model.Customer;
import com.maksym.customermanager.repository.CustomerRepository;
import com.maksym.customermanager.util.Initializer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CustomerController extends HttpServlet {

    private CustomerRepository customerRepository;

    @Override
    public void init() throws ServletException {
        customerRepository = Initializer.getCustomerRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //    System.out.println("path - " + req.getParameter("id"));
        //  System.out.println("url - " + req.getRequestURL());

        StringBuilder result = new StringBuilder();
        if (req.getParameter("id") != null) {
            Long id = Long.parseLong(req.getParameter("id"));
            result.append(customerRepository.get(id).toString());
            result.append("\n");
        } else {
            List<Customer> customers = customerRepository.getAll();
            for (Customer customer : customers) {
                result.append(customer.toString());
                result.append("\n");
            }
        }

        resp.setContentType("text/plain;charset=UTF-8");
        resp.getOutputStream().print(result.toString());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firsName = req.getParameter("firstname");
        String lastName = req.getParameter("lastname");
        String specialty = req.getParameter("specialty");

        resp.setContentType("text/plain;charset=UTF-8");

        if (firsName == null || lastName == null || specialty == null) {
            resp.getOutputStream().print("To create a new customer URL must contain " +
                    "firstname, lastname and speciaty parameters");
            return;
        }
        Customer customer = new Customer();
        customer.setFirsName(firsName);
        customer.setLastName(lastName);
        customer.setSpecialty(specialty);

        customerRepository.saveOrUpdate(customer);

        resp.getOutputStream().print(customer.toString());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id;
        resp.setContentType("text/plain;charset=UTF-8");
        if (req.getParameter("id") != null) {
            id = Long.parseLong(req.getParameter("id"));
        } else {
            resp.getOutputStream().print("To create a new customer URL must contain id parameter");
            return;
        }

        Customer customer = customerRepository.get(id);
        if (customer == null){
            resp.getOutputStream().print("No customer with id: " + id);
            return;
        }

        String firsName = req.getParameter("firstname");
        String lastName = req.getParameter("lastname");
        String specialty = req.getParameter("specialty");
        if (firsName != null) customer.setFirsName(firsName);
        if (lastName != null) customer.setLastName(lastName);
        if (specialty != null) customer.setSpecialty(specialty);

        resp.getOutputStream().print(customer.toString());
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean deleted = false;
        if (req.getParameter("id") != null) {
            Long id = Long.parseLong(req.getParameter("id"));
            deleted = customerRepository.remove(id);
        }
        resp.setContentType("text/plain;charset=UTF-8");
        resp.getOutputStream().print(deleted);
    }

}
