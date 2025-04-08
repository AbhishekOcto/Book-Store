package com.bookStore.controller;


import com.bookStore.entity.Book;
import com.bookStore.entity.MyBookList;
//import com.bookStore.repository.BookRepository;
import com.bookStore.service.BookService;
import com.bookStore.service.MyBookListService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class BookController {
    @Autowired
	private BookService service;
	
	@Autowired
	private MyBookListService myBookService;

	
	@GetMapping("/")
	public String home() {
		return "home";
	}

	@GetMapping("/book_register")
public String showForm(Model model) {
    model.addAttribute("book", new Book());
    return "book_register";
}

@PostMapping("/save")
public String saveBook(@Valid @ModelAttribute("book") Book book,
                       BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
        return "book_register"; // reload the form with error messages
    }

    service.save(book);
    return "redirect:/available_books";
}
	
	// @GetMapping("/book_register")
	// public String bookRegister() {
	// 	return "book_Register";
	// }
	
	@GetMapping("/available_books")
	public ModelAndView getAllBook() {
		List<Book>list=service.getAllBook();
//		ModelAndView m=new ModelAndView();
//		m.setViewName("bookList");
//		m.addObject("book",list);
		return new ModelAndView("bookList","book",list);
	}
	
	// @PostMapping("/save")
	// public String addBook(@ModelAttribute Book b) {
	// 	service.save(b);
	// 	return "redirect:/available_books";
	// }


	@GetMapping("/my_books")
	public String getMyBooks(Model model)
	{
		List<MyBookList>list=myBookService.getAllMyBooks();
		model.addAttribute("book",list);
		return "myBooks";
	}

	/**
	 * Get a book by its ID and save it to MyBookList database.
	 * @param id The ID of the book to be saved.
	 * @return A redirect to the available books page.
	 */
	@RequestMapping("/mylist/{id}")
	public String getMyList(@PathVariable("id") int id) {
		Book b=service.getBookById(id);
		MyBookList mb=new MyBookList(b.getId(),b.getName(),b.getAuthor(),b.getPrice());
		myBookService.saveMyBooks(mb);
		return "redirect:/available_books"; //myBooks
	}
	

	/**
	 * Get a book by its ID and store it in the model.
	 * The HTML template will then display the values of the book in a form.
	 * @param id The ID of the book to be edited.
	 * @param model The model that stores the book.
	 * @return The name of the template to be rendered.
	 */
	@RequestMapping("/editBook/{id}")
	public String editBook(@PathVariable("id") int id,Model model) {
		Book b=service.getBookById(id);
		model.addAttribute("book",b);
		return "bookEdit";
	}

	/**
	 * Delete a book by its ID.
	 * @param id The ID of the book to be deleted.
	 * @return A redirect to the available books page.
	 */
	@RequestMapping("/deleteBook/{id}")
	public String deleteBook(@PathVariable("id")int id) {
		service.deleteById(id);
		return "redirect:/available_books";
	}



}
