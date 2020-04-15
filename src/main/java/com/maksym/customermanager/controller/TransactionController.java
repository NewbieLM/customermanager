package com.maksym.customermanager.controller;

import com.maksym.customermanager.model.Account;
import com.maksym.customermanager.model.Transaction;
import com.maksym.customermanager.repository.AccountRepository;
import com.maksym.customermanager.repository.TransactionRepository;
import com.maksym.customermanager.util.Initializer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class TransactionController extends HttpServlet {
    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;

    @Override
    public void init() {
        transactionRepository = Initializer.getTransactionRepository();
        accountRepository = Initializer.getAccountRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        StringBuilder result = new StringBuilder();
        if (req.getParameter("id") != null) {
            Long id = Long.parseLong(req.getParameter("id"));
            Transaction transaction = transactionRepository.get(id);
            if (transaction == null) {
                resp.setStatus(404);
                result.append("No transaction with id: " + id);
            } else {
                result.append(transaction.toString());
            }
            result.append("\n");
        } else {
            List<Transaction> transactions = transactionRepository.getAll();
            for (Transaction transaction : transactions) {
                result.append(transaction.toString());
                result.append("\n");
            }
        }

        resp.setContentType("text/plain;charset=UTF-8");
        resp.getOutputStream().print(result.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String accountId = req.getParameter("account-id");
        String amount = req.getParameter("amount");

        resp.setContentType("text/plain;charset=UTF-8");

        if (accountId == null || amount == null) {
            resp.setStatus(400);
            resp.getOutputStream().print("To add a new transaction URL must contain " +
                    "account-id and amount parameters");
            return;
        }

        Account account = accountRepository.get(Long.parseLong(accountId));
        if (account == null) {
            resp.setStatus(404);
            resp.getOutputStream().print("No account with id:" + accountId);
            return;
        }

        BigDecimal transactionAmount = new BigDecimal(amount);
        BigDecimal updatedBalance = account.getBalance().add(transactionAmount);
        if (updatedBalance.compareTo(new BigDecimal(0)) < 0) {
            resp.setStatus(400);
            resp.getOutputStream().print("Not enough money for the transaction");
            return;
        }

        account.setBalance(updatedBalance);
        Transaction transaction = new Transaction(transactionAmount, account);

        transactionRepository.saveOrUpdate(transaction);

        resp.getOutputStream().print(transaction.toString());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String amount = req.getParameter("amount");
        resp.setContentType("text/plain;charset=UTF-8");
        if (req.getParameter("id") == null || amount == null) {
            resp.setStatus(400);
            resp.getOutputStream().print("To update a transaction URL must contain id and amount parameters");
            return;
        }

        Long id = Long.parseLong(req.getParameter("id"));
        Transaction transaction = transactionRepository.get(id);
        if (transaction == null) {
            resp.setStatus(404);
            resp.getOutputStream().print("No transaction with id: " + id);
            return;
        }

        BigDecimal updatedTransactionAmount = new BigDecimal(amount);
        BigDecimal accountBalance = transaction.getAccount().getBalance();
        BigDecimal updatedBalance = accountBalance.subtract(transaction.getAmount()).add(updatedTransactionAmount);

        if (updatedBalance.compareTo(new BigDecimal(0)) < 0) {
            resp.setStatus(400);
            resp.getOutputStream().print("Not enough money for the transaction");
            return;
        }

        transaction.setAmount(updatedTransactionAmount);
        transaction.getAccount().setBalance(updatedBalance);

        transactionRepository.saveOrUpdate(transaction);

        resp.getOutputStream().print(transaction.toString());
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean deleted;
        resp.setContentType("text/plain;charset=UTF-8");

        if (req.getParameter("id") == null) {
            resp.setStatus(400);
            resp.getOutputStream().println("To delete a transaction URL must contain id parameter");
        }

        Long id = Long.parseLong(req.getParameter("id"));
        Transaction transaction = transactionRepository.get(id);
        if (transaction == null) {
            resp.setStatus(404);
            resp.getOutputStream().println("No transaction with id: " + id);
            return;
        }


        BigDecimal accountBalance = transaction.getAccount().getBalance();
        BigDecimal updatedBalance = accountBalance.subtract(transaction.getAmount());

        if (updatedBalance.compareTo(new BigDecimal(0)) < 0) {
            resp.setStatus(400);
            resp.getOutputStream().println("Not enough money for the transaction");
            return;
        }

        Account account = transaction.getAccount();
        account.setBalance(updatedBalance);
        accountRepository.saveOrUpdate(account);

        deleted = transactionRepository.remove(id);

        resp.getOutputStream().print(deleted);
    }
}
