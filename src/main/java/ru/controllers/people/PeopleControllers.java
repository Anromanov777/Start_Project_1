package ru.controllers.people;

import org.springframework.web.bind.annotation.*;
import ru.dao.PeopleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.model.Person;

@Controller
@RequestMapping("/people")
public class PeopleControllers {

    private final PeopleDAO peopleDAO;

    @Autowired
    public PeopleControllers(PeopleDAO peopleDAO) {
        this.peopleDAO = peopleDAO;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("people", peopleDAO.index());
        return "people/index";
    }

    @GetMapping("/new")
    public String creat(@ModelAttribute("person") Person person) {
        return "people/creat";
    }

    @PostMapping
    public String save(@ModelAttribute Person person) {
        peopleDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("{id}")
    public String read(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleDAO.getPerson(id));
        return "people/read";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleDAO.getPerson(id));
        return "people/edit";
    }

    @PatchMapping("{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        peopleDAO.update(id, person);
        System.out.println(person);
        return "redirect:/people";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") int id){
        peopleDAO.delete(id);
        return "redirect:/people";
    }

}
