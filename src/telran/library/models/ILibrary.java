package telran.library.models;

import telran.library.entities.Book;
import telran.library.entities.Reader;
import telran.library.entities.enums.BooksReturnCodes;

import java.io.Serializable;

public interface ILibrary extends Serializable {
//sprint 1
    BooksReturnCodes addBookItem(Book book);
    BooksReturnCodes addReader(Reader reader);
    BooksReturnCodes addBookExemplars(long isbn, int amount);
    Reader getReader (int readerId);
    Book getBookItem(long isbn, int amount);

}
