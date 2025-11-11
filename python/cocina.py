from cocinero import Cocinero
from pedido import Pedido
import threading
import random

def main():
    random.seed()  # Inicializar generador aleatorio

    # Lista de platos disponibles
    platos_disponibles = [
        "Pizza Margarita", "Lasaña Boloñesa", "Sushi Variado", "Paella Mixta", "Tacos de Pollo",
        "Ensalada César", "Hamburguesa Doble", "Canelones de Espinacas", "Sopa de Tomate", "Pollo al Curry",
        "Burritos de Ternera", "Filete con Patatas", "Gazpacho Andaluz", "Pasta Carbonara", "Raviolis de Queso",
        "Huevos Rotos con Jamón", "Croquetas Caseras", "Pulpo a la Gallega", "Tarta de Queso", "Arroz con Leche",
        "Empanadillas de Atún", "Paella de Verduras", "Tallarines con Marisco", "Pizza Barbacoa", "Costillas BBQ"
    ]

    # Número aleatorio de pedidos (6–10)
    num_pedidos = random.randint(6, 10)
    print(f"Se generarán {num_pedidos} pedidos aleatorios.\n")

    # Crear la lista de pedidos
    pedidos = []
    for i in range(1, num_pedidos + 1):
        nombre_plato = random.choice(platos_disponibles)
        pedidos.append(Pedido(i, nombre_plato))

    # Crear un lock compartido entre todos los hilos
    lock = threading.Lock()

    # Número aleatorio de cocineros (3–5)
    num_cocineros = random.randint(3, 5)
    print(f" Se asignarán {num_cocineros} cocineros a la cocina.\n")

    # Crear y lanzar cocineros
    cocineros = []
    for i in range(1, num_cocineros + 1):
        cocinero = Cocinero(f"Cocinero {i}", pedidos, lock)
        cocinero.start()
        cocineros.append(cocinero)

    # Esperar a que todos terminen
    for cocinero in cocineros:
        cocinero.join()

    print("\nTodos los pedidos han sido procesados correctamente.")

if __name__ == "__main__":
    main()