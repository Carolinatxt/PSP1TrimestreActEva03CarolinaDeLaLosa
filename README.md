# Actividad Evaluable 03B — Simulación Concurrente de Cocina en Restaurante

## Módulo
**Programación de Servicios y Procesos (PSP)** — 2º DAM

---

## Descripción del Proyecto

Este proyecto consiste en la **simulación concurrente de una cocina de restaurante**, en la que varios **cocineros (hilos)** procesan pedidos de forma **paralela y sincronizada**.

El objetivo principal es **demostrar el uso de hilos y mecanismos de sincronización** en Java y Python, garantizando que los recursos compartidos (lista de pedidos y archivo de log) no se utilicen simultáneamente por más de un hilo.

El trabajo se desarrolla en dos versiones:

- **Versión Java:** utiliza `Thread` y bloques `synchronized` con un objeto `lock`.
- **Versión Python:** utiliza `threading.Thread` y `threading.Lock`.

---

## Contexto del Problema

En una cocina concurrida, varios cocineros deben preparar pedidos de una lista común.

Cada cocinero:

1. **Toma un pedido** de la lista compartida.
2. **Simula su preparación** (retardo aleatorio con `sleep`).
3. **Registra las operaciones** en consola y en un archivo `log_pedidos.txt`.

El sistema debe asegurar que:

- Solo **un hilo accede a la lista y al archivo de log a la vez**.
- Se registran todas las operaciones correctamente y sin interferencias.
- Al finalizar, se muestre:  
  `Todos los pedidos han sido procesados.`

---

## Estructura de Clases (Java y Python)

### `Pedido`

Representa un pedido con un identificador y el nombre del plato.

- En Java y Python incluye los atributos `id` y `nombrePlato`.
- Redefine `toString()` / `__str__()` para mostrar el pedido en formato legible:  
  `Pedido-1 (Sushi Variado)`.

---

### `Cocinero`

Simula un cocinero trabajando en un hilo independiente.

- Hereda de `Thread` (Java) o `threading.Thread` (Python).
- Recibe el nombre, la lista de pedidos y el objeto `lock`.
- Dentro del método `run()`:
  1. Accede a la lista de forma **sincronizada** con el `lock`.
  2. Muestra que recibe y prepara un pedido.
  3. Espera un tiempo aleatorio (`sleep`) para simular el cocinado.
  4. Registra la finalización del pedido con un **timestamp**.
- Utiliza el método `registrarYMostrar()` / `registrar_y_mostrar()` para escribir tanto en consola como en el log.

**Decisiones de diseño importantes:**

- El `lock` se crea en la clase `Cocina`, no en `Cocinero`, para que todos los hilos compartan el mismo candado.
- Se usa `PrintWriter` junto a `FileWriter` en Java para una escritura más limpia y segura.
- El `timestamp` se genera automáticamente con `LocalDateTime` o `datetime.now()` para un log más realista.
- El archivo `log_pedidos.txt` se abre en modo *append* (`true` / `"a"`) para mantener el historial completo.

---

### `Cocina`

Clase principal que lanza toda la simulación.

1. Genera aleatoriamente:
   - Entre **6 y 10 pedidos**.
   - Entre **3 y 5 cocineros**.
2. Crea un único `lock` compartido.
3. Lanza los hilos `Cocinero` y los guarda en una lista para luego usar `join()`.
4. Espera a que todos terminen y muestra el mensaje final.

**Ejemplo de salida esperada:**
```
Se generarán 8 pedidos aleatorios.

Se asignarán 5 cocineros a la cocina.

[Cocinero 1] recibe Pedido-1 (Pulpo a la Gallega)
[Cocinero 1] está preparando Pedido-1 (Pulpo a la Gallega)...
[Cocinero 5] recibe Pedido-2 (Raviolis de Queso)
[Cocinero 5] está preparando Pedido-2 (Raviolis de Queso)...
✅ Cocinero 2 ha completado Pedido-5 (Lasaña Boloñesa)
✅ Cocinero 3 ha completado Pedido-8 (Pulpo a la Gallega)

Todos los pedidos han sido procesados.
```

El orden de ejecución **puede variar en cada ejecución** debido a la naturaleza concurrente del programa.

---

## Archivo de Log

El archivo `log_pedidos.txt` guarda cada evento con **fecha y hora exacta**, por ejemplo:
```
[2025-10-22 16:47:56] [Cocinero 1] recibe Pedido-1 (Sushi Variado)
[2025-10-22 16:47:56] [Cocinero 1] está preparando Pedido-1 (Sushi Variado)...
[2025-10-22 16:47:59] ✅ Cocinero 1 ha completado Pedido-1 (Sushi Variado)
```

---

## Conceptos de PSP aplicados

- **Hilos (`Thread`):** cada cocinero se ejecuta en paralelo.
- **Sincronización (`Lock` / `synchronized`):** control del acceso concurrente a la lista de pedidos y al log.
- **Exclusión mutua:** solo un hilo entra en la sección crítica a la vez.
- **Retardos (`sleep`):** simulación del tiempo de trabajo real.
- **Comunicación indirecta:** los hilos comparten la lista como medio de comunicación.

---

## Uso de Inteligencia Artificial (IA)

Para esta práctica se ha utilizado **ChatGPT** como herramienta de apoyo para:

- Comprender y estructurar correctamente la lógica concurrente.
- Mejorar la redacción de comentarios y documentación técnica.
- Generar explicaciones y guiones para la exposición oral.

Todo el código fue revisado, comprendido y probado de forma individual, ajustándolo al enunciado.

---

## Conclusiones

Con este proyecto se ha logrado:

- Aplicar los principios de concurrencia con hilos y sincronización.
- Simular un flujo de trabajo paralelo realista (varios cocineros atendiendo pedidos).
- Garantizar la integridad de los recursos compartidos mediante `Lock`.
- Implementar un sistema de logging profesional con timestamp.

Esta práctica demuestra el dominio de los conceptos fundamentales de la programación concurrente en Java y Python.

---

## Información del Proyecto

**Autor:** Carolina  
**Año académico:** 2025–2026  
**Centro:** PRO2FP — 2º DAM  
**Proyecto:** PSP-DAM-ACTEVA03B
