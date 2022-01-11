package com.example.splitwise.controller.view;

import com.example.splitwise.controller.rest.RestRequestService;
import com.example.splitwise.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.security.web.server.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/account")
public class UserController {

    private final RestRequestService restResponsesService;

    @Autowired
    public UserController(RestRequestService restResponsesService) {
        this.restResponsesService = restResponsesService;
    }

    @GetMapping
    public String getUserAccount(Model model) {
        model.addAttribute("id", 1);
        model.addAttribute("edit", null);
        return "redirect:/account/1?edit=";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") int id,
                          @RequestParam(value = "edit") String edit,
                          Model model) {
        User user = restResponsesService.findUser(id);

        model.addAttribute("edit", edit);
        model.addAttribute("savedEdit", edit);
        model.addAttribute("userObject", user);
        return "/account";
    }

//    @GetMapping("/{id}/{edit}")
//    public String getUserAccountWithId(@PathVariable("id") int id, Model model) {
//        User user = restResponsesService.findUser(id);
//
//        model.addAttribute("userObject", user);
//        return "/account";
//    }
//
//    @PutMapping("/{id}/{edit}")
//    public String edit(@PathVariable("id") Integer id,
//                       @PathVariable("edit") String edit,
//                       Model model) {
//
//        User user = restResponsesService.findUser(id);
//
//        user = restResponsesService.editUserByParam(id, user);
//
//        model.addAttribute("userObject", user);
//        return "/account";
//    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") Integer id,
                         @RequestParam(value = "edit") String edit,
                         @RequestParam(value = "username", required = false) String username,
                         @RequestParam(value = "new-password", required = false) String password,
                         @RequestParam(value = "phone", required = false) String phone,
                         HttpServletResponse response,
                         Model model) {
        Cookie cookie = new Cookie("XSRF-TOKEN", null); // Not necessary, but saves bandwidth.
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
//        new DefaultCsrfToken().getToken()
        Cookie newCookie = new Cookie("XSRF-TOKEN", "4bfd1575-3ad1-4d21-96c7-4ef2d9f86721");
        newCookie.setPath("/api/account"+id+"?edit="+edit);
        newCookie.setHttpOnly(true);
        newCookie.setMaxAge(3600);
        response.addCookie(newCookie);

        User user = restResponsesService.editUser(
            id,
            (User) model.getAttribute("userObject"),
            edit);
//        if (Objects.equals(edit, "email")) {
//            user = restResponsesService.editUserByParam(id, user);
//        } else if (Objects.equals(edit, "password")) {
//            user = restResponsesService.editUserByParam(id, user);
//        } else {
//            user = restResponsesService.editUserByParam(id, user);
//        }

        model.addAttribute("userObject", user);
        model.addAttribute("savedEdit", edit);
        return "/account";
    }
}
