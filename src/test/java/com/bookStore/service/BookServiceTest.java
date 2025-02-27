package com.bookStore.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bookStore.entity.Book;
import com.bookStore.exception.BookNotFoundException;
import com.bookStore.repository.BookRepository;
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    BookRepository bRepo;

    @InjectMocks
    BookService bService; //instead of real object, we should create a mock object in test. That's when mockito comes into picture.
    //If we are creating real bject then we need BookService Repository.
    @Test
    public void testSaveShouldSaveBookSuccessfully(){
    //     Book book = new Book();
    //    // bService.save(book);
    //     Mockito.when(bRepo.save(book)).thenReturn(book);
    //     Book savedBook = bService.save(book);
    //     //Test book == saved book; This assertion should be true then the test is passing
    Book book = new Book();
    book.setId(1);
    book.setName("Wings of Fire and Flower");
    book.setAuthor("Nobody");
    book.setPrice("20");
    Mockito.when(bRepo.save(book)).thenReturn(book);
    Book savedBook = bService.save(book);
    //Assertions.assertEquals(book.getId(), savedBook.getId());
    Assertions.assertEquals(1, savedBook.getId());
    //Assertions.assertTrue(book.getId()==1);  //test saved book == book [if we change the expectedx value, test will fail]
    }  

    @Test
    public void testGetAllBooks() {
    // Arrange
    Book book1 = new Book();
    book1.setId(1);
    book1.setName("Book 1");
    book1.setAuthor("Author 1");
    book1.setPrice("10");

    Book book2 = new Book();
    book2.setId(2);
    book2.setName("Book 2");
    book2.setAuthor("Author 2");
    book2.setPrice("20");

    Mockito.when(bRepo.findAll()).thenReturn(Arrays.asList(book1, book2));

    // Act
    List<Book> allBooks = bService.getAllBook();

    // Assert
    assertNotNull(allBooks);
    assertEquals(2, allBooks.size());
    assertTrue(allBooks.contains(book1));
    assertTrue(allBooks.contains(book2));   
    /*In this test, we:

Arrange: Create two Book objects and set up the mock repository to return a list containing these two books when findAll() is called.
Act: Call the getAllBook() method on the bService object.
Assert:
Verify that the returned list is not null.
Verify that the list contains exactly 2 books.
Verify that the list contains both book1 and book2.
Note that we're using Mockito to mock the bRepo object, so we can control the behavior of the findAll() method and test the getAllBook() method in isolation. */    
    }

    @Test
    public void testDeleteById_ExistingBook() {
    // Arrange
    int id = 1;
    Mockito.when(bRepo.existsById(id)).thenReturn(true);
    Mockito.doNothing().when(bRepo).deleteById(id);

    // Act
    bService.deleteById(id);

    // Assert
    Mockito.verify(bRepo, Mockito.times(1)).deleteById(id);
    /*Arrange: Set up the mock repository to return true for existsById() and do nothing for deleteById().
Act: Call deleteById() on the bService object.
Assert: Verify that deleteById() was called on the repository exactly once. */
    }

    @Test
    public void testDeleteById_NonExistingBook() {
    // Arrange
    int id = 1;
    Mockito.when(bRepo.existsById(id)).thenReturn(false);

    // Act and Assert
    Assertions.assertThrows(BookNotFoundException.class, () -> bService.deleteById(id));
    /*Arrange: Set up the mock repository to return false for existsById().
Act and Assert: Verify that calling deleteById() on the bService object throws a BookNotFoundException. */
    }

    @Test
    public void testDeleteById_NonExistingBook_CheckErrorMessage() {
    // Arrange
    int id = 1;
    Mockito.when(bRepo.existsById(id)).thenReturn(false);

    // Act and Assert
    try {
        bService.deleteById(id);
        fail("Expected BookNotFoundException to be thrown");
    } catch (BookNotFoundException e) {
        assertEquals("Cannot delete. Book not found with ID: 1", e.getMessage());
        /*Arrange: Set up the mock repository to return false for existsById().
Act and Assert: Verify that calling deleteById() on the bService object throws a BookNotFoundException with the expected error message.
Note that we're using Mockito to mock the bRepo object, so we can control the behavior of the existsById() and deleteById() methods and test the deleteById() method in isolation. */
        }
    }

}
