package com.pereverziev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class AccountRepository {

    private JdbcTemplate jdbcTemplate;
    private MailMail mailMail;

    @Autowired
    AccountRepository(JdbcTemplate jdbcTemplate, MailMail mailMail) {
        this.jdbcTemplate = jdbcTemplate;
        this.mailMail = mailMail;
    }

    void addAccountToDB(Account account, String confirmId) {
        jdbcTemplate.execute("INSERT INTO users(login,password,enabled,role,email,confirmid) VALUES ('" + account.getLogin() + "','"
                + account.getPassword() + "','f','USER','" + account.getEmail() + "','" + confirmId + "')");
        mailMail.sendEmail(confirmId, account.getEmail(), "Confirm your email address.",
                "Hi, your link to confirm your Facebook2 account here: localhost:8080/confirm?id=" + confirmId);
    }

    void confirmAccount(String id) {
        jdbcTemplate.execute("UPDATE users SET enabled = 't' WHERE confirmid = '" + id + "'");
    }

    void restoreAccountPassword(String email) {
        UUID uuid = UUID.randomUUID();
        jdbcTemplate.execute("UPDATE users SET respassid = '" + uuid + "' WHERE email = '" + email + "'");
        mailMail.sendEmail(uuid.toString(), email, "Restore password to your facebook2 account.",
                "Hi, your link to restore your Facebook2 account here: localhost:8080/restorePasswordLink?id=" + uuid.toString());
    }

    void setNewPasswordForAccountByResPassId(String password, String id) {
        jdbcTemplate.execute("UPDATE users SET password = '" + password + "' WHERE respassid = '" + id + "'");
    }

    void changeAccountPasswordByLogin(String login, String newPassword) {
        jdbcTemplate.update("UPDATE users SET password = '" + newPassword + "' WHERE login = '" + login + "'");
    }

    boolean checkForExistingUser(String login, String email) {
        List<String> listL = jdbcTemplate.queryForList("SELECT login FROM users WHERE login ='" + login + "'", String.class);
        List<String> listE = jdbcTemplate.queryForList("SELECT login FROM users WHERE email ='" + email + "'", String.class);
        if (listL.size() == 0 && listE.size() == 0) return false;
        return true;
    }

    boolean checkForCurrentPassword(String login,String currentPassword) {
        List<String> listP = jdbcTemplate.queryForList("SELECT password FROM users WHERE login = '" + login + "'",String.class);
        String realPassword = listP.get(0);
        if (realPassword.equals(currentPassword)) return true;
        return false;
    }

}
