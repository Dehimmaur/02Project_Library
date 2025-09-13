package telran.library.models;

import java.time.LocalDate;
import java.util.*;

import telran.library.entities.Book;
import telran.library.entities.PickRecord;
import telran.library.entities.Reader;
import telran.library.entities.enums.BooksReturnCodes;
import static telran.library.entities.enums.BooksReturnCodes.*;

public class LibraryMaps extends AbstractLibrary {

    Map<Long, Book> books = new HashMap<>();
    Map<Integer, Reader> readers = new HashMap<>();

    // Sprint2
    private Map<Integer, List<PickRecord>> readersRecords = new HashMap<>();
    private Map<Long, List<PickRecord>> booksRecords = new HashMap<>();
    private Map<LocalDate, List<PickRecord>> records = new HashMap<>();

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

    @Override
    public BooksReturnCodes pickBook(long isbn, int readerId, LocalDate pickDate) {
        Book book = books.get(isbn);
        if (book == null) {
            return BooksReturnCodes.NO_BOOK_ITEM;
        }
        Reader reader = readers.get(readerId);
        if (reader == null) {
            return BooksReturnCodes.NO_READER_EXISTS;
        }
        if (book.getAmount() - book.getAmountInUse() <= 0) {
            return BooksReturnCodes.NO_BOOK_ITEM_EXIST;
        }

        book.setAmountInUse(book.getAmountInUse() + 1);
        PickRecord record = new PickRecord(isbn, readerId, pickDate);

        readersRecords.computeIfAbsent(readerId, k -> new ArrayList<>()).add(record);
        booksRecords.computeIfAbsent(isbn, k -> new ArrayList<>()).add(record);
        records.computeIfAbsent(pickDate, k -> new ArrayList<>()).add(record);

        return BooksReturnCodes.OK;
    }

    @Override
    public List<Book> getBooksPickedByReader(int readerId) {
        return readersRecords.getOrDefault(readerId, List.of())
                .stream()
                .map(r -> books.get(r.getIsbn()))
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public List<Reader> getReadersPickedBook(long isbn) {
        return booksRecords.getOrDefault(isbn, List.of())
                .stream()
                .map(r -> readers.get(r.getReaderId()))
                .filter(Objects::nonNull)
                .distinct()
                .toList();
    }

    @Override
    public List<Book> getBooksAuthor(String authorName) {
        return books.values().stream()
                .filter(b -> b.getAuthor().equalsIgnoreCase(authorName))
                .toList();
    }

    @Override
    public List<PickRecord> getPickedRecordsAtDates(LocalDate from, LocalDate to) {
        return records.entrySet().stream()
                .filter(e -> !e.getKey().isBefore(from) && !e.getKey().isAfter(to))
                .flatMap(e -> e.getValue().stream())
                .toList();
    }
}
