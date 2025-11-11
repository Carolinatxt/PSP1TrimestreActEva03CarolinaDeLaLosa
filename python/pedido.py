class Pedido:
    """
    Representa un pedido con un identificador y el nombre del plato.
    """
    def __init__(self, id_pedido, nombre_plato):
        self.id = id_pedido
        self.nombre_plato = nombre_plato

    def __str__(self):
        return f"Pedido-{self.id} ({self.nombre_plato})"