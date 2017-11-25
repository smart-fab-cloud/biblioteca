package ifts.biblioteca;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class BibliotecaConfig extends Configuration {
    
    @NotEmpty
    private String descrizionePredefinita;
    
    @JsonProperty
    public String getDescrizionePredefinita() {
        return this.descrizionePredefinita;
    }
    
}
