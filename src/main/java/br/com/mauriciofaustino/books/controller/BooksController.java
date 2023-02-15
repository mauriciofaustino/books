package br.com.mauriciofaustino.books.controller;

import br.com.mauriciofaustino.books.entity.Book;
import br.com.mauriciofaustino.books.exception.ValidationException;
import br.com.mauriciofaustino.books.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BooksController {

    @Autowired
    private BooksService bookService;

    @RequestMapping("/")
    public String home(Model model) {
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        return "home";
    }

    @RequestMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("book", new Book());
        return "form";
    }

    @RequestMapping("/update/{id}")
    public String updateForm(@PathVariable("id") Long id, Model model) {
        Book bookToUpdate = bookService.findById(id);
        model.addAttribute("book", bookToUpdate);
        return "form-update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Book book, Model model) {
        try {
            Book bookCreated = this.bookService.update(book);
            model.addAttribute("message", "Livro de id " + bookCreated.getId() + " alterado com sucesso");
            return home(model);
        } catch (ValidationException e) {
            model.addAttribute("message", "Ocorreu um erro: " + e.getErrorMessage());
            return updateForm(book.getId(), model);
        }
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Book book, Model model) {
        try {
            Book bookCreated = this.bookService.create(book);
            model.addAttribute("message", "Livro de id " + bookCreated.getId() + " registrado com sucesso");
            return home(model);
        } catch (ValidationException e) {
            model.addAttribute("message", "Ocorreu um erro: " + e.getErrorMessage());
            return newForm(model);
        }
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Long id, Model model) {
        try {
            this.bookService.remove(id);
            model.addAttribute("message", "Livro de id " + id + " excluído com sucesso");
        } catch (ValidationException e) {
            model.addAttribute("message", "Ocorreu um erro: " + e.getErrorMessage());
        }
        return home(model);
    }

}
