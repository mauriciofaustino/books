package br.com.mauriciofaustino.books.service;

import br.com.mauriciofaustino.books.entity.Book;
import br.com.mauriciofaustino.books.exception.ValidationException;
import br.com.mauriciofaustino.books.repository.BooksRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BooksServiceTest {
    @SpyBean
    BooksService service;
    @MockBean
    BooksRepository repository;

    @Test
    public void shouldSaveABook() {
        Book bookToSave = createBook();

        Book savedBook = createBook();
        savedBook.setId(1L);
        when(repository.save(bookToSave)).thenReturn(savedBook);

        Book book = service.create(bookToSave);

        assertThat(book.getId()).isEqualTo(savedBook.getId());
    }

    @Test
    public void shouldNotSaveABookWithoutAName() {
        Book bookToSave = createBook();
        bookToSave.setName(" ");

        catchThrowableOfType(() -> service.create(bookToSave), ValidationException.class);
        verify(repository, never()).save(bookToSave);
    }

    @Test
    public void shouldNotSaveABookWithoutADescription() {
        Book bookToSave = createBook();
        bookToSave.setDescription(" ");

        catchThrowableOfType(() -> service.create(bookToSave), ValidationException.class);
        verify(repository, never()).save(bookToSave);
    }

    @Test
    public void shouldUpdateABook() {
        Book savedBook = createBook();
        savedBook.setId(1L);

        when(repository.save(savedBook)).thenReturn(savedBook);

        service.update(savedBook);

        verify(repository, times(1)).save(savedBook);

    }

    @Test
    public void shouldNotUpdateABookWithoutAnId() {
        Book bookToSave = createBook();

        catchThrowableOfType(() -> service.update(bookToSave), ValidationException.class);
        verify(repository, never()).save(bookToSave);
    }

    @Test
    public void shouldNotUpdateABookWithoutAnName() {
        Book bookToSave = createBook();
        bookToSave.setId(1L);
        bookToSave.setName(" ");

        when(repository.save(bookToSave)).thenReturn(bookToSave);

        catchThrowableOfType(() -> service.update(bookToSave), ValidationException.class);
        verify(repository, never()).save(bookToSave);
    }

    @Test
    public void shouldNotUpdateABookWithoutAnDescription() {
        Book bookToSave = createBook();
        bookToSave.setId(1L);
        bookToSave.setDescription(" ");

        when(repository.save(bookToSave)).thenReturn(bookToSave);

        catchThrowableOfType(() -> service.update(bookToSave), ValidationException.class);
        verify(repository, never()).save(bookToSave);
    }

    @Test
    public void shouldDeleteABook() {
        Long bookId = 1L;
        Book bookToRemove = createBook();
        bookToRemove.setId(bookId);
        when(repository.findById(bookId)).thenReturn(Optional.of(bookToRemove));

        service.remove(bookId);

        verify(repository).delete(bookToRemove);
    }

    private Book createBook() {
        return Book
                .builder()
                .name("Meu livro")
                .description("Um livro muito inspirador")
                .build();
    }
}
