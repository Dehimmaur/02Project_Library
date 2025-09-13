package telran.library.models;

import telran.library.entities.Book;
import telran.library.entities.PickRecord;
import telran.library.entities.Reader;
import telran.library.entities.enums.BooksReturnCodes;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public interface ILibrary extends Serializable {
    //sprint 1
    BooksReturnCodes addBookItem(Book book);
    BooksReturnCodes addReader(Reader reader);
    BooksReturnCodes addBookExemplars(long isbn, int amount);
    Reader getReader (int readerId);
    Book getBookItem(long isbn, int amount);

    //sprint 2
    BooksReturnCodes pickBook(long isbn, int readerId, LocalDate pickDate);
    List<Book> getBooksPickedByReader(int readerId);
    List<Reader> getReadersPickedBook(long isbn);
    List<Book> getBooksAuthor(String authorName);
    List<PickRecord> getPickedRecordsAtDates(LocalDate from, LocalDate to);
}
