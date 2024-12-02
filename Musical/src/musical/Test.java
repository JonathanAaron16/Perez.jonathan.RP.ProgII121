package musical;

import modelo.GestorEventos;
import modelo.GeneroMusical;
import modelo.EventoMusical;
import modelo.Evento;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

public class Test {

    public static void main(String[] args) {
        try {       
        
// Crear instancia del gestor de eventos
        GestorEventos<EventoMusical> gestor = new GestorEventos<>();
        // Crear algunos eventos musicales
        gestor.agregar(new EventoMusical(1, "Rock Fest", LocalDate.of(2024, 3, 15), "Queen Revival", GeneroMusical.ROCK));
        gestor.agregar(new EventoMusical(2, "Jazz Night", LocalDate.of(2024, 6, 20), "John Doe Quintet", GeneroMusical.JAZZ));
        gestor.agregar(new EventoMusical(3, "Pop Party", LocalDate.of(2024, 8, 5), "Taylor Tribute", GeneroMusical.POP));
        gestor.agregar(new EventoMusical(4, "Electronic Vibes", LocalDate.of(2024, 10, 12), "DJ Nova", GeneroMusical.ELECTRONICA));

// Mostrar todos los eventos
        System.out.println("Lista inicial de eventos:");
        gestor.mostrarTodos();
// Ordenar por fecha (orden natural)
        System.out.println("\nEventos ordenados por fecha:");
        gestor.ordenarNatural();
        gestor.mostrarTodos();
// Ordenar por nombre de evento
        System.out.println("\nEventos ordenados por nombre:");
        gestor.ordenarComparador((a1, a2) -> a1.getNombre().compareTo(a2.getNombre()));
        gestor.mostrarTodos();

        // Filtrar por género
        System.out.println("\nEventos de género ROCK:");
        List<Evento> eventosRock = gestor.filtrar(evento
                -> evento instanceof EventoMusical
                && ((EventoMusical) evento).getGenero() == GeneroMusical.ROCK
        );

        eventosRock.forEach(System.out::println);
// Filtrar por palabra clave en el nombre
        System.out.println("\nEventos que contienen 'Night' en el nombre:");
        List<Evento> nightEvents = gestor.filtrar(a -> a.getNombre().toLowerCase().contains("Night".toLowerCase()));
        nightEvents.forEach(System.out::println);

        System.out.println("\nEventos entre el 01/01/2024 y el 31/07/2024:");
        List<Evento> dateRangeEvents = gestor.buscarPorRango(
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 7, 31)
        );
        System.out.println(dateRangeEvents);

        // Guardar y cargar en formato binario
        System.out.println("\nGuardando y cargando eventos en binario...");
        gestor.guardarEnArchivo("src/data/eventos.dat");
         
        gestor.cargarDesdeArchivo("src/data/eventos.dat");
        gestor.mostrarTodos();
        
// Guardar y cargar en formato CSV
        System.out.println("\nGuardando y cargando eventos en CSV...");
        gestor.guardarEnCSV("src/data/evento.csv");
        
        
        gestor.cargarDesdeCSV("src/data/evento.csv");
        gestor.mostrarTodos();

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        }
        
        
       
    }

}
