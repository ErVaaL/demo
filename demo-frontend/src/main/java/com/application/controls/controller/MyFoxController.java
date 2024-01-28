package com.application.controls.controller;

import com.application.controls.service.MyRestService;
import com.application.objects.Fox;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class MyFoxController {
    @GetMapping(value = "/welcome")
    public String getWelcomeView() {return "welcome";}

    private final MyRestService service;

    @GetMapping(value = "/index")
    public String index(Model model){
        model.addAttribute("foxes", service.getAllAnimals());
        return "index";
    }
    @GetMapping(value = "/error")
    public String getErrorMessage(Model model){

        return "error";
    }

    @GetMapping(value = "/addFoxToBase")
    public String getFoxView(Model model){
        model.addAttribute("fox", new Fox("",0));
        return "addFoxToBase";
    }
    @PostMapping(value = "/addFoxToBase")
    public String addFox( Model model, RedirectAttributes redirectAttributes, @ModelAttribute Fox fox){
        if(fox.getTails() < 1){
            model.addAttribute("errorMessage", "fox has to have tail");
            return "addFoxToBase";
        }
        service.addAnimal(fox);
        redirectAttributes.addFlashAttribute("successMessage", "added successfully");
        return "redirect:/index";
    }
    @GetMapping(value = "/editFox/{id}")
    public String editFox(Model model, @PathVariable("id") String id){
        Fox fox = service.getFoxById(Long.parseLong(id));
        model.addAttribute("fox", fox);
        return "editFox";
    }
    @PostMapping(value = "/editFox/{id}")
    public String editFox(Model model, RedirectAttributes redirectAttributes, @ModelAttribute Fox fox){
        if(fox.getTails() < 1 || fox.getName().trim() == null){
            model.addAttribute("errorMessage", "Neither of fields can be null");
            return "editFox";
        }
        service.updateAnimal(fox);
        redirectAttributes.addFlashAttribute("successMessage", "Fox edited");
        return "redirect:/index";
    }
    @GetMapping(value = "/deleteFox/{id}")
    public String deleteFox(Model model, @PathVariable("id") String id){
        var fox = service.getFoxById(Long.parseLong(id));
        model.addAttribute("fox", fox);
        return "deleteFox";
    }
    @PostMapping(value = "/deleteFox/{id}")
    public String deleteFox(Model model, RedirectAttributes redirectAttributes, @PathVariable("id") Long id){
        if(service.getFoxById(id) == null){
            model.addAttribute("errorMessage", "No such fox in database");
            return "/deleteFox";
        }
        service.deleteAnimal(id);
        model.addAttribute("successMessage", "Fox has been deleted");
        return "redirect:/index";
    }
}
