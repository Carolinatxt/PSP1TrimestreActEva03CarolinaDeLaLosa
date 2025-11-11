import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Clase principal que lanza la simulación concurrente de la cocina.
 * Genera aleatoriamente pedidos y cocineros, y coordina su ejecución.
 */
public class Cocina {
    public static void main(String[] args) {

        Random random = new Random();

        // Lista de platos disponibles (25 platos distintos)
        String[] platosDisponibles = {
            "Pizza Margarita", "Lasaña Boloñesa", "Sushi Variado", "Paella Mixta", "Tacos de Pollo",
            "Ensalada César", "Hamburguesa Doble", "Canelones de Espinacas", "Sopa de Tomate", "Pollo al Curry",
            "Burritos de Ternera", "Filete con Patatas", "Gazpacho Andaluz", "Pasta Carbonara", "Raviolis de Queso",
            "Huevos Rotos con Jamón", "Croquetas Caseras", "Pulpo a la Gallega", "Tarta de Queso", "Arroz con Leche",
            "Empanadillas de Atún", "Paella de Verduras", "Tallarines con Marisco", "Pizza Barbacoa", "Costillas BBQ"
        };

        // Número aleatorio de pedidos (6–10)
        int numPedidos = random.nextInt(5) + 6;
        System.out.println("Se generarán " + numPedidos + " pedidos aleatorios.\n");

        // Crear la lista de pedidos
        List<Pedido> pedidos = new ArrayList<>();
        for (int i = 1; i <= numPedidos; i++) {
            String nombrePlato = platosDisponibles[random.nextInt(platosDisponibles.length)];
            pedidos.add(new Pedido(i, nombrePlato));
        }

        // Crear el candado compartido
        Object lock = new Object();

        // Número aleatorio de cocineros (3–5)
        int numCocineros = random.nextInt(3) + 3;
        System.out.println("Se asignarán " + numCocineros + " cocineros a la cocina.\n");

        
        // Crear y lanzar todos los cocineros
        // Se usa una lista para poder luego hacer join() y esperar a que terminen.
        List<Cocinero> cocineros = new ArrayList<>();

        for (int i = 1; i <= numCocineros; i++) {
            Cocinero cocinero = new Cocinero("Cocinero " + i, pedidos, lock);
            cocinero.start();        // Se lanza el hilo inmediatamente (flujo concurrente)
            cocineros.add(cocinero); // Se guarda la referencia para el join posterior
        }
       
        
        // Esperar a que todos los cocineros terminen su trabajo
        // join() bloquea el hilo principal hasta que cada hilo finaliza.
        for (Cocinero cocinero : cocineros) {
            try {
                cocinero.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Error esperando al cocinero " + cocinero.getName());
            }
        }

        System.out.println("\nTodos los pedidos han sido procesados.");
    }
}