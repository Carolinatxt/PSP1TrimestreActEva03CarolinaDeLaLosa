import threading
import time
import random
from datetime import datetime

class Cocinero(threading.Thread):
    """
    Hilo que representa a un cocinero.
    Cada cocinero toma pedidos de la lista compartida, los prepara,
    y registra sus acciones en consola y en un archivo log.
    """

    LOG_FILE = "log_pedidos.txt"

    def __init__(self, nombre_cocinero, pedidos, lock):
        super().__init__(name=nombre_cocinero)
        self.nombre_cocinero = nombre_cocinero
        self.pedidos = pedidos
        self.lock = lock

    def run(self):
        """
        Método principal del hilo.
        Procesa los pedidos hasta que no quede ninguno.
        """
        while True:
            pedido_actual = None

            # Acceso sincronizado a la lista compartida
            with self.lock:
                if not self.pedidos:
                    break  # No hay más pedidos → termina el hilo

                pedido_actual = self.pedidos.pop(0)
                self.registrar_y_mostrar(f"[{self.nombre_cocinero}] recibe {pedido_actual}")
                self.registrar_y_mostrar(f"[{self.nombre_cocinero}] está preparando {pedido_actual}...")

            # Simular tiempo de preparación (fuera del lock)
            time.sleep(random.uniform(2.0, 4.0))

            # Registrar finalización (de nuevo dentro del lock)
            with self.lock:
                self.registrar_y_mostrar(f"✅ {self.nombre_cocinero} ha completado {pedido_actual}")

    def registrar_y_mostrar(self, mensaje):
        """
        Muestra el mensaje en consola y lo registra con timestamp en el archivo de log.
        """
        print(mensaje)
        timestamp = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
        mensaje_log = f"[{timestamp}] {mensaje}"

        # Escribir en log (modo append)
        with open(self.LOG_FILE, "a", encoding="utf-8") as f:
            f.write(mensaje_log + "\n")