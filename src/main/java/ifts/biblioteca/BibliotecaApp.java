package ifts.biblioteca;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class BibliotecaApp extends Application<BibliotecaConfig> {
    
    public static void main(String[] args) throws Exception {
        new BibliotecaApp().run(args);
    }
    
    @Override
    public void run (BibliotecaConfig configuration, Environment environment) {
        final Biblioteca risorsaBiblioteca = 
            new Biblioteca(configuration.getDescrizionePredefinita());
        environment.jersey().register(risorsaBiblioteca);
    }

}
