package telran.library.models;

import java.util.*;

import telran.library.entities.Book;
import telran.library.entities.Reader;
import telran.library.entities.enums.BooksReturnCodes;
import static telran.library.entities.enums.BooksReturnCodes.*;

public class LibraryMaps extends AbstractLibrary {

    Map<Long, Book> books = new HashMap<>();
    Map<Integer, Reader> readers = new HashMap<>();

    @Override
    public BooksReturnCodes addBookItem(Book book) {
        if (book.getPickPeriod() < minPickPeriod)
            return PICK_PERIOD_LESS_MIN;
        if (book.getPickPeriod() > maxPickPeriod)
            return PICK_PERIOD_GREATER_MAX;
        BooksReturnCodes res = books
                .putIfAbsent(book.getIsbn(), book) ==
                null ? OK : BOOK_ITEM_EXISTS;
        return res;
    }

    @Override
    public BooksReturnCodes addReader(Reader reader) {
        if (readers.putIfAbsent(reader.getReaderId(), reader) != null) {
            return BooksReturnCodes.READER_EXISTS;
        }
        return BooksReturnCodes.OK;
    }

    @Override
    public BooksReturnCodes addBookExemplars(long isbn, int amount) {
        Book book = books.get(isbn);
        if (book == null) {
            return BooksReturnCodes.NO_BOOK_ITEM;
        }
        book.setAmount(book.getAmount() + amount);
        return BooksReturnCodes.OK;
    }

    @Override
    public Reader getReader(int readerId) {
        return readers.get(readerId);
    }

    @Override
    public Book getBookItem(long isbn, int amount) {
        Book book = books.get(isbn);
        if (book == null) {
            return null;
        }
        if (book.getAmount() < amount) {
            return null;
        }
        return book;
    }
}
