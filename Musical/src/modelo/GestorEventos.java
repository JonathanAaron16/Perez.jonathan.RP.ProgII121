package modelo;

import modelo.EventoMusical;
import modelo.Evento;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class GestorEventos<T extends Evento> {

    private List<Evento> inventario;

    public GestorEventos() {
        this.inventario = new ArrayList<>();
    }

    // Agregar evento
    public void agregar(Evento evento) {
        if (evento == null) {
            throw new IllegalArgumentException("No se puede agregar un evento nulo.");
        }
        inventario.add(evento);
    }

    // Obtener evento por índice
    public Evento obtener(int indice) {
        if (indice < 0 || indice >= inventario.size()) {
            throw new IndexOutOfBoundsException("El indice está fuera del rango : " + indice);
        }
        return inventario.get(indice);
    }

    // Eliminar evento por índice
    public void eliminar(int indice) {
        if (indice < 0 || indice >= inventario.size()) {
            throw new IndexOutOfBoundsException("El indice está fuera del rango: " + indice);
        }
        inventario.remove(indice);
    }

    public void mostrarTodos() {
        if (inventario.isEmpty()) {
            System.out.println("No hay eventos en el inventario.");
            return;
        }
        System.out.println("Lista de eventos:");
        for (Evento evento : inventario) {
            System.out.println(evento.toString());
        }
    }

    public List<Evento> filtrar(Predicate<Evento> criterio) {
        List<Evento> toReturn = new ArrayList<>();
        for (Evento evento : inventario) {
            if (criterio.test(evento)) {
                toReturn.add(evento);
            }
        }
        return toReturn;
    }

    public void ordenarNatural() {
        inventario.sort((e1, e2) -> {
            if (e1 instanceof EventoMusical && e2 instanceof EventoMusical) {
                return ((EventoMusical) e1).compareTo((EventoMusical) e2);
            }
            return 0; // No realizar cambios si no son del mismo tipo
        });

    }

    public void ordenarComparador(Comparator<Evento> comparator) {
        inventario.sort(comparator);
    }

    public List<Evento> buscarPorRango(LocalDate inicio, LocalDate fin) {
        List<Evento> eventosEnRango = new ArrayList<>();
        for (Evento evento : inventario) {
            if (!evento.getFecha().isBefore(inicio) && !evento.getFecha().isAfter(fin)) {
                eventosEnRango.add(evento);
            }
        }
        return eventosEnRango;
    }

    // Guardar en archivo binario
    public void guardarEnArchivo(String ruta) throws IOException {
        try (ObjectOutputStream archivo = new ObjectOutputStream(new FileOutputStream(ruta))) {
            archivo.writeObject(inventario);
        }
    }

    // Cargar desde archivo binario
    public void cargarDesdeArchivo(String ruta) throws IOException, ClassNotFoundException {
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(ruta))) {
            inventario = (List<Evento>) (List<T>) input.readObject();
        }
    }

    public void guardarEnCSV(String path) {
        File archivo = new File(path);
        try {
            if (archivo.exists()) {
                System.out.println("El archivo ya exite");
            } else {
                archivo.createNewFile();
            }
        } catch (IOException ex) {
            System.out.println("Ocurrio un errror");

        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            bw.write("id, nombre, especie, alimentacion \n");
            for (Evento evento : inventario) {
                bw.write(((EventoMusical) evento).toCSV() + "\n");
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public List<Evento> cargarDesdeCSV(String path) {
        List<Evento> toReturn = new ArrayList<>();

        try (BufferedReader bf = new BufferedReader(new FileReader(path))) {

            String linea;

            bf.readLine();
            while ((linea = bf.readLine()) != null) {
                if (linea.endsWith("\n")) {
                    linea = linea.substring(linea.length() - 1);
                }
                String[] datos = linea.split(",");
                
                int id = Integer.parseInt(datos[0]);
                String nombre = datos[1];
                LocalDate fecha = LocalDate.parse(datos[2]);
                String artista = datos[3];
                GeneroMusical genero = GeneroMusical.valueOf(datos[4]);
                Evento e = new EventoMusical(id, nombre, fecha, artista, genero);
                toReturn.add(e);

            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException("problema al cargar empleado");

        }
        return toReturn;
    }
    
    
  

}
