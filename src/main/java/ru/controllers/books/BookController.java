package ru.controllers.books;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.dao.BookDAO;
import ru.dao.PeopleDAO;
import ru.model.Book;
import ru.util.PersonValid;


@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDAO bookDAO;
    private final PeopleDAO peopleDAO;

    @Autowired
    public BookController(PersonValid personValid, BookDAO bookDAO, PeopleDAO peopleDAO) {
        this.bookDAO = bookDAO;
        this.peopleDAO = peopleDAO;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("/new")
    public String create(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping
    public String saveBook(@ModelAttribute @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "/books/new";
        bookDAO.saveBook(book.getName(), book.getAuthor());
        return "redirect:/books";
    }

    @GetMapping("{id}")
    public String book(@PathVariable("id") int id, Model model) {
        Book book = bookDAO.getBook(id);
        model.addAttribute("book", book);
        if (book.getId_person() != null) {
            model.addAttribute("person", peopleDAO.getPerson(book.getId_person()));
        } else {
            model.addAttribute("people", peopleDAO.index());
        }
        return "books/read";
    }

    @GetMapping("{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.getBook(id));
        return "books/edit";
    }

    @PatchMapping("{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute Book book) {
        bookDAO.update(id, book);
        return "redirect:/books";
    }

    @PatchMapping("{id}/free")
    public String update(@PathVariable("id") int id) {
        bookDAO.free(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("{id}/catch")
    public String cat(@PathVariable("id") int idBook, @RequestParam("idPerson") int idPerson) {
        bookDAO.setPerson(idBook, idPerson);
        return "redirect:/books/" + idBook;
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }
}
