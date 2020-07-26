package net.veniture.exercise.jira.rest;


import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import net.veniture.exercise.jira.entity.Book;
import net.veniture.exercise.jira.entity.BookService;
import net.veniture.exercise.jira.entity.BookServiceImpl;
import net.veniture.exercise.jira.settings.AppSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
@Scanned
@Path("/book")
public class BookListResource {

    private static final Logger log = LoggerFactory.getLogger(BookListResource.class);

    private AppSettings appSettings;
    private BookService bookService;

    @Autowired
    public BookListResource(BookService bookService, AppSettings appSettings) {
        this.bookService = bookService;
        this.appSettings = appSettings;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    //@Consumes({MediaType.APPLICATION_JSON})
    public Response getAvailableBooks(@QueryParam("max") Integer maxItemsToFetch, @QueryParam("offset") Integer offset){

        List<BookDTO> foundBooks = new ArrayList<>();

        for (Book foundBook : bookService.getAllBooks()) {
            if (foundBooks.size() >= appSettings.getInteger(AppSettings.BOOK_LIST_PAGE_ITEM_COUNT)){break;}
            BookDTO bookDTO = new BookDTO(foundBook);
            foundBooks.add(bookDTO);
        }

        return Response.ok(new Object(){
            public List<BookDTO> books = foundBooks;
            public String test = "Lorem ipsum dolor sit amet";
        }).build();
    }

    @POST
    public void createNewBook(@FormParam("title") String title,@FormParam("author") String author){
        bookService.addBook(author,title);
    }

    @DELETE
    @Path("/{id}")
    public void deleteBookById(@PathParam("id")int id){
        Book book = bookService.getBookById(id);
        bookService.removeBook(book);
    }
}
