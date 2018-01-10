package ifts.biblioteca;

import java.net.URI;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

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
    public Response aggiungiLibro(Libro l) {
        // Verifica che l'ISBN del libro non sia vuoto
        if(l.getISBN().length() == 0)
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("L'ISBN non può essere vuoto.")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        
        // Verifica se il libro è già presente nella collezione
        if(indiceLibro(l.getISBN()) >= 0) 
            return Response.status(Response.Status.CONFLICT)
                    .entity(l.getISBN() + " già presente.")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        
        URI uriLibro = aggiungiLibroCorretto(l);
        return Response.created(uriLibro).build();
    }
    
    private URI aggiungiLibroCorretto(Libro l) {
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
        
        // Crea la URI corrispondente al libro aggiunto 
        URI uriLibro = UriBuilder.fromResource(Biblioteca.class)
                        .path(l.getISBN())
                        .build();
        return uriLibro;
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
    public Response recuperaLibro(@PathParam("isbn") String isbn) {
        // Recupera la posizione "i" del libro con "isbn" specificato
        int i = indiceLibro(isbn);
        
        // Se il libro non è presente, restituisce 404
        if(i==-1)
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(isbn + " non presente.")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        
        // Altrimenti, restituisce il libro
        return Response.ok().entity(libri.get(i)).build();
    }
    
    @PUT
    @Path("/{isbn}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response aggiornaLibro(
            @PathParam("isbn") String isbn,
            Libro l
    ) {
        // Recupera l'indice in "libri" del libro con "isbn" indicato 
        int i = indiceLibro(isbn);
        
        // Se il libro non è presente, restituisce 404
        if(i==-1)
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(isbn + " non presente.")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        
        // Verifica che il nuovo ISBN non sia vuoto
        if(l.getISBN().length() == 0)
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("L'ISBN non può essere vuoto.")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        
        // Se entrambi i controlli precedenti sono passati, procede 
        // all'aggiornamento del libro
        this.libri.remove(i);
        URI uriLibro = aggiungiLibroCorretto(l);
        return Response.ok(uriLibro).build();
        
    }
    
    @DELETE
    @Path("/{isbn}")
    public Response eliminaLibro(@PathParam("isbn") String isbn) {
        // Recupera l'indice in "libri" del libro con "isbn" indicato 
        int i = indiceLibro(isbn);

        // Se il libro non è presente, restituisce 404
        if(i==-1)
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(isbn + " non presente.")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        
        // Altrimenti, elimina il libro
        libri.remove(i);
        return Response.ok()
                    .entity(isbn + " eliminato.")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        
    }
    
}
