package hellojpa;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("BO")
public class Book extends Item{

    private String author;
    private String isbn;
}