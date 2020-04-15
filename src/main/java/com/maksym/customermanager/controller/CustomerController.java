package com.maksym.customermanager.controller;

import com.maksym.customermanager.model.Customer;
import com.maksym.customermanager.repository.CustomerRepository;
import com.maksym.customermanager.util.Initializer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CustomerController extends HttpServlet {

    private CustomerRepository customerRepository;

    @Override
    public void init() {
        customerRepository = Initializer.getCustomerRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        StringBuilder result = new StringBuilder();
        if (req.getParameter("id") != null) {
            Long id = Long.parseLong(req.getParameter("id"));
            Customer customer = customerRepository.get(id);
            if (customer == null) {
                resp.setStatus(404);
                result.append("No customer with id: " + id);
            } else {
                result.append(customer.toString());
            }
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String firsName = req.getParameter("firstname");
        String lastName = req.getParameter("lastname");
        String specialty = req.getParameter("specialty");

        resp.setContentType("text/plain;charset=UTF-8");

        if (firsName == null || lastName == null || specialty == null) {
            resp.setStatus(400);
            resp.getOutputStream().print("To create a new customer URL must contain " +
                    "firstname, lastname and specialty parameters");
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
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id;
        resp.setContentType("text/plain;charset=UTF-8");
        if (req.getParameter("id") != null) {
            id = Long.parseLong(req.getParameter("id"));
        } else {
            resp.setStatus(400);
            resp.getOutputStream().print("To update a customer URL must contain id parameter");
            return;
        }

        Customer customer = customerRepository.get(id);
        if (customer == null) {
            resp.setStatus(404);
            resp.getOutputStream().print("No customer with id: " + id);
            return;
        }

        String firsName = req.getParameter("firstname");
        String lastName = req.getParameter("lastname");
        String specialty = req.getParameter("specialty");
        if (firsName != null) customer.setFirsName(firsName);
        if (lastName != null) customer.setLastName(lastName);
        if (specialty != null) customer.setSpecialty(specialty);

        customerRepository.saveOrUpdate(customer);

        resp.getOutputStream().print(customer.toString());
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean deleted = false;
        if (req.getParameter("id") != null) {
            Long id = Long.parseLong(req.getParameter("id"));
            deleted = customerRepository.remove(id);
        } else {
            resp.getOutputStream().println("Request must contain id parameter");
        }
        resp.setContentType("text/plain;charset=UTF-8");
        if (deleted) {
            resp.getOutputStream().print("Customer has been deleted");
        } else {
            resp.setStatus(404);
            resp.getOutputStream().print("Customer has not been deleted");
        }
    }

}
