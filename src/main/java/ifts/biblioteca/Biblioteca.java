package ifts.biblioteca;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/biblioteca")
public class Biblioteca {
    
    private String descrizionePredefinita;
    private List<Libro> libri;
    
    public Biblioteca(String descrizionePredefinita) {
        this.descrizionePredefinita = descrizionePredefinita;
        this.libri = new ArrayList<Libro>();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void aggiungiLibro(Libro l) {
        // Se il libro ha "descrizione" non vuota
        if (l.getDescrizione().length() != 0)
            // lo aggiunge direttamente in "libri"
            this.libri.add(l);
        else {
            // altrimenti, utilizza la "descrizionePredefinita"
            Libro l1 = new Libro (
                l.getISBN(),
                l.getTitolo(),
                l.getAutori(),
                l.getEditore(),
                descrizionePredefinita
            );
            this.libri.add(l1);
        }
    }
    
    // Metodo privato per recuperare l'indice del libro avente "isbn"
    // specificato in "libri"
    private int indiceLibro(String isbn) {
        for(int i=0; i<this.libri.size(); i++)
            if(this.libri.get(i).getISBN().equals(isbn))
                return i;
        return -1;
    }
    
    @GET
    @Path("/{isbn}")
    @Produces(MediaType.APPLICATION_JSON)
    public Libro recuperaLibro(@PathParam("isbn") String isbn) {
        // Recupera la posizione "i" del libro con "isbn" specificato
        int i = indiceLibro(isbn);
        // Restituisce il libro, se c'è
        if(i>-1)
            return this.libri.get(i);
        // Altrimenti restituisce "null"
        return null;
    }
    
    @PUT
    @Path("/{isbn}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void aggiornaLibro(
            @PathParam("isbn") String isbn,
            Libro l
    ) {
        // Recupera l'indice in "libri" del libro con "isbn" indicato 
        int i = indiceLibro(isbn);
        // Se il libro è presente
        if (i>-1) {
            // elimina la vecchia descrizione del libro
            this.libri.remove(i);
            // e ne aggiunge la nuova
            aggiungiLibro(l);
        }
    }
    
    @DELETE
    @Path("/{isbn}")
    public void eliminaLibro(@PathParam("isbn") String isbn) {
        // Recupera l'indice in "libri" del libro con "isbn" indicato 
        int i = indiceLibro(isbn);
        // Se il libro è presente
        if (i>-1)
            // elimina la vecchia descrizione del libro
            this.libri.remove(i);
        
    }
    
}
