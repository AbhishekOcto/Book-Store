package com.bookStore.service;

import com.bookStore.entity.Book;
import com.bookStore.exception.BookNotFoundException;
import com.bookStore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bRepo;

    public Book save(Book b) {
        return bRepo.save(b);
    }


    public List<Book> getAllBook(){
        return bRepo.findAll();
    }

    public Book getBookById(int id) {
        return bRepo.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + id));
    }

    public void deleteById(int id) {
        if (!bRepo.existsById(id)) {
            throw new BookNotFoundException("Cannot delete. Book not found with ID: " + id);
        }
        bRepo.deleteById(id);
    }
    // public List<Book> searchBooks(String search) {
    //     return bRepo.findByNameContainingOrAuthorContaining(search, search);
    //   }


}
