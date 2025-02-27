package com.bookStore.service;

import com.bookStore.entity.MyBookList;
import com.bookStore.exception.MyBookNotFoundException;
import com.bookStore.repository.MyBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyBookListService {
    @Autowired
    private MyBookRepository mybook;

    public void saveMyBooks(MyBookList book) {
        mybook.save(book);
    }

    public List<MyBookList> getAllMyBooks(){
        return mybook.findAll();
    }

    public void deleteById(int id) {
        if (!mybook.existsById(id)) {
            throw new MyBookNotFoundException("Book not found with ID: " + id);
        }
        mybook.deleteById(id);
    }
}
