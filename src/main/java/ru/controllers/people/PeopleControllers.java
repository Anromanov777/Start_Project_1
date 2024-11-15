package ru.controllers.people;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.dao.PeopleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.model.Book;
import ru.model.Person;
import ru.util.PersonValid;

@Controller
@RequestMapping("/people")
public class PeopleControllers {
    private final PersonValid personValid;
    private final PeopleDAO peopleDAO;

    @Autowired
    public PeopleControllers(PersonValid personValid, PeopleDAO peopleDAO) {
        this.personValid = personValid;
        this.peopleDAO = peopleDAO;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("people", peopleDAO.index());
        return "people/index";
    }

    @GetMapping("/new")
    public String creat(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping
    public String save(@ModelAttribute Person person, BindingResult bindingResult) {
        personValid.validate(person, bindingResult);
        if (bindingResult.hasErrors()) return "people/new";
        peopleDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("{id}")
    public String read(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleDAO.getPerson(id));
        model.addAttribute("list", peopleDAO.checkFree(id));
        return "people/read";
    }

    @GetMapping("/{id}/edit")
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
    public String delete(@PathVariable("id") int id) {
        peopleDAO.delete(id);
        return "redirect:/people";
    }

}
