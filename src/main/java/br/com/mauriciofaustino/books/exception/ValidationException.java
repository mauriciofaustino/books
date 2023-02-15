package br.com.mauriciofaustino.books.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class ValidationException extends RuntimeException {
    final String errorMessage;

}