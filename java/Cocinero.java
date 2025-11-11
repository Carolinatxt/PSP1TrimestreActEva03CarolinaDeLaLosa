import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Clase que representa un cocinero (hilo).
 * Cada cocinero toma pedidos de la lista compartida y los prepara.
 * Se sincroniza el acceso con un objeto lock común pasado desde Cocina.
 */
public class Cocinero extends Thread {

    private String nombreCocinero;
    private List<Pedido> pedidos;
    private Object lock; //Candado compartido entre todos los cocineros
    private static final String LOG_FILE = "log_pedidos.txt";

    public Cocinero(String nombreCocinero, List<Pedido> pedidos, Object lock) {
        this.nombreCocinero = nombreCocinero;
        this.pedidos = pedidos;
        this.lock = lock;
    }

    @Override
    public void run() {
    	// Bucle principal del hilo: procesa pedidos hasta que no quede ninguno
        while (true) {
            Pedido pedidoActual = null;  // Referencia temporal al pedido que va a preparar

            // Acceso sincronizado a la lista compartida
            synchronized (lock) { // Entra en sección crítica protegida por lock
                if (pedidos.isEmpty()) break; // Si no quedan pedidos, sale del bucle y termina el hilo

                pedidoActual = pedidos.remove(0); //// Toma el primer pedido disponible 
                registrarYMostrar("["+nombreCocinero + "] recibe " + pedidoActual);
             // Simular tiempo de preparación 
                registrarYMostrar("["+nombreCocinero + "] está preparando " + pedidoActual + "...");
             // Sale de la sección crítica (otros hilos pueden entrar)
            }

            try {
                Thread.sleep((int) (Math.random() * 4000 + 2000)); //Pausa aleatoria
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();  // Restablece el flag de interrupción
                return; // salir si el hilo es interrumpido
            }

            // Registrar finalización (con lock por seguridad)
            synchronized (lock) {   // Sección crítica para log de finalización
                registrarYMostrar("✅ " + nombreCocinero + " ha completado " + pedidoActual);
                // Sale de sección crítica
            }
        }
    }

    /**
     * Muestra mensaje limpio en consola y lo registra con timestamp en el archivo log.
     */
    private static final DateTimeFormatter FORMATO =
    	    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); //// Formato de timestamp en una constante estática

    	private void registrarYMostrar(String mensaje) {
    	    System.out.println(mensaje); // Salida inmediata a consola (sin timestamp)
    	    String mensajeLog = "[" + LocalDateTime.now().format(FORMATO) + "] " + mensaje;
    	    try (PrintWriter pw = new PrintWriter(new FileWriter(LOG_FILE, true))) {
    	        pw.println(mensajeLog);
    	    } catch (IOException e) {
    	        e.printStackTrace();
    	    }
    	}
}