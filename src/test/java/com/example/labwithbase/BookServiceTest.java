package com.example.labwithbase;

import com.example.labwithbase.services.BookService;
import com.example.labwithbase.services.CreationService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BookServiceTest {
    @Test
    void findBookByJenreTest(){
        DataLoader loader = new DataLoader();
        BookService service = new BookService();
        assertEquals(service.findBookByJenre(loader, "fairy").get(0).getName(),
                "The Hobbit, or There and Back Again");

    }

    @Test
    void findAllCreationsTest(){
        DataLoader loader = new DataLoader();
        CreationService service = new CreationService();
        assertEquals(service.findAllCreations(loader).get(1).getName(),"A Study in Scarlet");
    }
}
