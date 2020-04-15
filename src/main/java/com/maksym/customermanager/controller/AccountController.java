package com.maksym.customermanager.controller;

import com.maksym.customermanager.model.Account;
import com.maksym.customermanager.model.Customer;
import com.maksym.customermanager.repository.AccountRepository;
import com.maksym.customermanager.repository.CustomerRepository;
import com.maksym.customermanager.util.Initializer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class AccountController extends HttpServlet {
    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;

    @Override
    public void init() {
        this.accountRepository = Initializer.getAccountRepository();
        this.customerRepository = Initializer.getCustomerRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        StringBuilder result = new StringBuilder();
        if (req.getParameter("id") != null) {
            Long id = Long.parseLong(req.getParameter("id"));
            Account account = accountRepository.get(id);
            if (account == null) {
                resp.setStatus(404);
                result.append("No account with id: " + id);
            } else {
                result.append(account.toString());
            }
            result.append("\n");
        } else {
            List<Account> accounts = accountRepository.getAll();
            for (Account account : accounts) {
                result.append(account.toString());
                result.append("\n");
            }
        }

        resp.setContentType("text/plain;charset=UTF-8");
        resp.getOutputStream().print(result.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String customerId = req.getParameter("customer-id");
        String accountData = req.getParameter("account-data");
        String balance = req.getParameter("balance");

        resp.setContentType("text/plain;charset=UTF-8");

        if (customerId == null || accountData == null || balance == null) {
            resp.setStatus(400);
            resp.getOutputStream().print("To create a new account URL must contain " +
                    "customer-id, account-data and balance parameters");
            return;
        }

        Long id = Long.parseLong(customerId);
        Customer customer = customerRepository.get(id);
        if (customer == null) {
            resp.setStatus(404);
            resp.getOutputStream().print("No customer with id:" + id);
            return;
        }
        if (customer.getAccount() != null) {
            resp.setStatus(400);
            resp.getOutputStream().print("Customer with id:" + id + " already has an account");
            return;
        }

        Account account = new Account();
        account.setAccountData(accountData);
        account.setBalance(new BigDecimal(balance));

        customer.setAccount(account);

        customerRepository.saveOrUpdate(customer);

        resp.getOutputStream().print(account.toString());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id;
        resp.setContentType("text/plain;charset=UTF-8");
        if (req.getParameter("id") != null) {
            id = Long.parseLong(req.getParameter("id"));
        } else {
            resp.setStatus(400);
            resp.getOutputStream().print("To update a account URL must contain id parameter");
            return;
        }

        Account account = accountRepository.get(id);
        if (account == null) {
            resp.setStatus(404);
            resp.getOutputStream().print("No account with id: " + id);
            return;
        }

        String accountData = req.getParameter("account-data");
        String balance = req.getParameter("balance");
        if (accountData != null) account.setAccountData(accountData);
        if (balance != null) account.setBalance(new BigDecimal(balance));

        accountRepository.saveOrUpdate(account);

        resp.getOutputStream().print(account.toString());
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean deleted = false;
        if (req.getParameter("id") != null) {
            Long id = Long.parseLong(req.getParameter("id"));
            deleted = accountRepository.remove(id);
        } else {
            resp.getOutputStream().println("Request must contain id parameter");
        }
        resp.setContentType("text/plain;charset=UTF-8");
        if (deleted) {
            resp.getOutputStream().print("Account has been deleted");
        } else {
            resp.setStatus(404);
            resp.getOutputStream().print("Account has not been deleted");
        }
    }
}
