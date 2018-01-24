package com.pereverziev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

import javax.sql.DataSource;
import javax.validation.Valid;
import java.util.UUID;

@Configuration
@EnableWebMvc
@ComponentScan
@PropertySource("resources/dataSource.properties")
public class DispatcherConfig extends WebMvcConfigurerAdapter {

    private Environment env;

    @Autowired
    DispatcherConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(env.getProperty("jdbc.jdbcDriverClass"));
        driverManagerDataSource.setPassword(env.getProperty("jdbc.password"));
        driverManagerDataSource.setUrl(env.getProperty("jdbc.url"));
        driverManagerDataSource.setUsername(env.getProperty("jdbc.username"));
        return driverManagerDataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public ViewResolver viewResolver() {
        return new TilesViewResolver();
    }

    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setCheckRefresh(true);
        tilesConfigurer.setDefinitions("/WEB-INF/TilesDefinitions.xml");
        return tilesConfigurer;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

}

@Controller
class MainController {

    private AccountRepository repository;

    @Autowired
    MainController(AccountRepository repository) {
        this.repository = repository;
    }

    @RequestMapping("/account")
    public String accountPage() {
        return "account";
    }

    @RequestMapping("/accountRedirect")
    public String accountRedirect() {
        return "redirect:account";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registrationPageGet(Model model) {
        model.addAttribute("account", new Account());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registrationPagePost(@Valid Account account, BindingResult bindingResult, Model view) {
        if (bindingResult.hasErrors()) {
            view.addAttribute("account",account);
            return "registration";
        }
        if (repository.checkForExistingUser(account.getLogin(), account.getEmail())) {
            view.addAttribute("existingError", "User with this login or email already exists");
            return "registration";
        }
        UUID uuid = UUID.randomUUID();
        repository.addAccountToDB(account, uuid.toString());
        return "redirect:successRegistration";
    }

    @RequestMapping("/logout")
    public String logoutMethod() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        auth.setAuthenticated(false);
        return "redirect:/login";
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String loginPageGet() {
        return "login";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String loginPagePost(Model model,String username) {
        model.addAttribute("loginError","Wrong login/email or password");
        model.addAttribute("loginVal",username);
        return "login";
    }

    @RequestMapping("/confirm")
    public String confirmRequest(@RequestParam String id) {
        repository.confirmAccount(id);
        return "redirect:/login";
    }

    @RequestMapping(value = "/restorePassword", method = RequestMethod.GET)
    public String restorePasswordPageGet() {
        return "restorePassword";
    }

    @RequestMapping(value = "/restorePassword", method = RequestMethod.POST)
    public String restorePasswordPagePost(String email) {
        repository.restoreAccountPassword(email);
        return "restorePasswordInformPage";
    }

    @RequestMapping(value = "/restorePasswordLink", method = RequestMethod.GET)
    public String restorePasswordLinkPageGet(@RequestParam String id, Model model) {
        model.addAttribute("POJO", new RestorePasswordPOJO());
        return "restorePasswordLink";
    }

    @RequestMapping(value = "/restorePasswordLink", method = RequestMethod.POST)
    public String restorePasswordLinkPagePost(@RequestParam String id, @ModelAttribute("POJO") @Valid RestorePasswordPOJO helpDao, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) return "restorePasswordLink";
        if (!helpDao.checkForEquals()) {
            model.addAttribute("equalError","Passwords aren't equal");
            return "restorePasswordLink";
        }
        repository.setNewPasswordForAccountByResPassId(helpDao.getPassword1(), id);
        return "redirect:login";
    }

    @RequestMapping("/successRegistration")
    public String successRegistration() {
        return "successRegistration";
    }

    @RequestMapping("/about")
    public String aboutPage() {
        return "about";
    }

    @RequestMapping(value = "/changePassword",method = RequestMethod.GET)
    public String changePasswordPageGet(Model model) {
        model.addAttribute("POJO",new ChangePasswordPOJO());
        return "changePassword";
    }

    @RequestMapping(value = "/changePassword",method = RequestMethod.POST)
    public String changePasswordPagePost(@ModelAttribute("POJO") @Valid ChangePasswordPOJO pojo,BindingResult bindingResult,Model model) {
        if (bindingResult.hasErrors()) return "changePassword";
        String userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        String userLogin = userDetails.substring(userDetails.indexOf("Username:") + 10,userDetails.indexOf("; Password"));
        if (!pojo.checkForEqualsPasswords()) {
            model.addAttribute("equalsError","Passwords must be equals");
            return "changePassword";
        }
        if (!repository.checkForCurrentPassword(userLogin,pojo.getCurrentPassword())) {
            model.addAttribute("changeError","Current password is incorrect");
            return "changePassword";
        }
        repository.changeAccountPasswordByLogin(userLogin,pojo.getNewPassword1());
        return "redirect:/account";
    }

    @RequestMapping("/restorePasswordInformPage")
    public String restorePasswordInformPage() {
        return "restorePasswordInformPage";
    }

}
