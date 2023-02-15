package br.com.mauriciofaustino.books.repository;

import br.com.mauriciofaustino.books.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepository extends JpaRepository<Book, Long> {

}
