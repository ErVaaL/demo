package com.example.demo.controls;

import com.example.demo.objects.Fox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MyFoxController {
    @GetMapping(value = "/welcome")
    public String getWelcomeView() {return "welcome";}

    private final MyRestService service;

    @Autowired
    public MyFoxController(MyRestService service){ this.service = service;}

    @GetMapping(value = "/index")
    public String getIndexView(Model model){
        model.addAttribute("foxes", service.getAllAnimals());
        return "index";
    }

    @GetMapping(value = "/addFoxToBase")
    public String getFoxView(Model model){
        model.addAttribute("fox", new Fox("",0));
        return "addFoxToBase";
    }
    @PostMapping(value = "/addFoxToBase")
    public String addFox(Fox fox, Model model, RedirectAttributes redirectAttributes){
        if(fox.getTails() < 1){
            model.addAttribute("errorMessage", "fox has to have tail");
            return "addFoxToBase";
        }
        service.addAnimal(fox);
        redirectAttributes.addFlashAttribute("successMessage", "added successfully");
        return "redirect:/index";
    }

}
