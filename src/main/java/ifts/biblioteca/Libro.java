package ifts.biblioteca;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Libro {
    
    private String isbn;
    private String titolo;
    private List<String> autori;
    private String editore;
    private String descrizione;
    
    public Libro() {}
    
    public Libro(
        String isbn,
        String titolo,
        List<String> autori,
        String editore,
        String descrizione
    ) {
        this.isbn = isbn;
        this.titolo = titolo;
        this.autori = autori;
        this.editore = editore;
        this.descrizione = descrizione;
    }
    
    @JsonProperty
    public String getISBN() { return this.isbn; }

    @JsonProperty
    public String getTitolo() { return this.titolo; }
    
    @JsonProperty
    public List<String> getAutori() { return this.autori; }
    
    @JsonProperty
    public String getEditore() { return this.editore; }
   
    @JsonProperty
    public String getDescrizione() { return this.descrizione; }

}
