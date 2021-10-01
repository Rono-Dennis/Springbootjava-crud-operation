package com.meeting.planner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProjectController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public String viewHomePage(){

        return "index";
    }

    @GetMapping("/list_users")
    public String viewUsersList(Model model){

        List<User> listUsers = userRepository.findAll();
        model.addAttribute("listUsers", listUsers);

        return "list_users";
    }

    @GetMapping("/add_users")
    public String addUser(Model model){

        model.addAttribute("user", new User());

        return "add_users";
    }

    @RequestMapping("/delete_user/{id}")
    public String deleteUser(@PathVariable(name = "id") int id){
        userRepository.deleteById(id);
        return "list_users";
    }

    /**
     * Create a POSTMAPPING function to save users
     */

    @PostMapping("/save_user")
    public String addNewUser(User web_user){
        userRepository.save(web_user);
        return "list_users";
    }

    /**
     * Edit users
     */

    @RequestMapping("/edit_user/{id}")
    public ModelAndView showEditUserForm(@PathVariable(name = "id") int id){

        ModelAndView mnv = new ModelAndView("edit_users");

        //User object
        User user = userRepository.getById(id);
        mnv.addObject("editUser", user);

        return mnv;
    }

}
