package modelo;

import modelo.Evento;
import java.time.LocalDate;

public class EventoMusical extends Evento implements Comparable<EventoMusical> {

    private String artista;
    private GeneroMusical genero;

    public EventoMusical(int id, String nombre, LocalDate fecha,String artista, GeneroMusical genero) {
        super(id, nombre, fecha);
        this.artista = artista;
        this.genero = genero;
    }

    public String getArtista() {
        return artista;
    }

    public GeneroMusical getGenero() {
        return genero;
    }
    
    
        
    @Override
    public int compareTo(EventoMusical o) {
        return getFecha().compareTo(o.getFecha());
    }

    public String toCSV() {
        return getId() + "," + getNombre() + "," + getFecha() + "," + artista + "," + genero;
    }

    public static EventoMusical fromCSV(String csv) {
        String[] datos = csv.split(",");
        int id = Integer.parseInt(datos[0]);
        String nombre = datos[1];
        LocalDate fecha = LocalDate.parse(datos[2]);
        String artista = datos[3];
        GeneroMusical genero = GeneroMusical.valueOf(datos[4]);
        return new EventoMusical(id, nombre, fecha,artista, genero);

    }

   

}
