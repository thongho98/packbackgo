package own.thongho.web.packbackgo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import own.thongho.web.packbackgo.bean.User;
import own.thongho.web.packbackgo.dto.SearchRequest;
import own.thongho.web.packbackgo.dto.UserLoginRequest;
import own.thongho.web.packbackgo.services.Impl.UserServiceImpl;
import own.thongho.web.packbackgo.services.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    HttpSession session;

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String register(ModelMap model) {
        model.addAttribute("newUser", new User());
        model.addAttribute("newSearch", new SearchRequest());
        return "register";
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("newUser") User user) {
        System.out.println(user);
        userService.createUser(user);
        return "redirect:/thank-you";
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(ModelMap model) {
        model.addAttribute("userLoginRequest", new UserLoginRequest());
        model.addAttribute("newSearch", new SearchRequest());
        return "login";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(@ModelAttribute("userLoginRequest") UserLoginRequest userLoginRequest, ModelMap model) {
        String token = userService.login(userLoginRequest.getUsername(),userLoginRequest.getPassword());
        session.setAttribute("usersession",token);
        model.addAttribute("username", userLoginRequest.getUsername());
        return "redirect:/";
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(ModelMap model) {
        session.invalidate();
        model.addAttribute("newSearch", new SearchRequest());
        return "index";
    }
}
