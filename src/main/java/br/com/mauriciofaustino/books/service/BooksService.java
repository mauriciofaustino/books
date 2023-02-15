package br.com.mauriciofaustino.books.service;

import br.com.mauriciofaustino.books.entity.Book;
import br.com.mauriciofaustino.books.exception.ValidationException;
import br.com.mauriciofaustino.books.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BooksService {
    @Autowired
    private BooksRepository booksRepository;

    public Book create(Book book) {
        validate(book);
        return booksRepository.save(book);
    }

    private void validate(Book book) {
        if(book.getName() == null || "".equals(book.getName().trim())) {
            throw new ValidationException("Preencha um nome");
        }
        if(book.getDescription() == null || "".equals(book.getDescription().trim())) {
            throw new ValidationException("Preencha uma descrição");
        }
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public void remove(Long id) {
        Book bookToDelete = findById(id);
        booksRepository.delete(bookToDelete);
    }

    public Book findById(Long id) {
        return booksRepository
                .findById(id)
                .orElseThrow(() -> new ValidationException("Livro de id "+ id+" não encontrado"));
    }

    public Book update(Book book) {
        if (book.getId() == null)
            throw new ValidationException("O id do livro está nulo");
        validate(book);
        return booksRepository.save(book);
    }
}
