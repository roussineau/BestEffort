package aed;

import java.util.ArrayList;

public class Heap<T extends Identificable> {

    public ArrayList<T> elems;
    public int longitud; // Número de elementos en el heap
    public ArrayList<Integer> inds; // Índice por ID
    public HeapComparator comparator; // Comparador del heap

    // Constructor
    public Heap(boolean c) {
        elems = new ArrayList<>();
        inds = new ArrayList<>();
        longitud = 0;
        inds.add(-1);
        comparator = new HeapComparator(c);
    }

    // Método para encolar un nuevo elemento
    public void encolar(T nuevo) {
        // Asegurar espacio en inds para IDs más grandes
        inds.add(-1); // Inicializa con -1
        elems.add(nuevo); // Agregar elemento al final
        longitud++;
        inds.set(nuevo.getId(), longitud - 1); // Actualizar índice
        heapifyUp(longitud - 1, nuevo.getId()); // Restaurar la propiedad del heap
    }

    // Restaurar propiedad del heap hacia arriba
    public void heapifyUp(int n, int id) {
        if (n == 0)
            return; // Raíz del heap
        int indPadre = (n - 1) / 2;
        T padre = elems.get(indPadre);
        T hijo = elems.get(n);
        if (comparator.compare(hijo, padre) > 0) {
            // Intercambiar padre e hijo
            elems.set(n, padre);
            elems.set(indPadre, hijo);
            inds.set(id, indPadre);
            inds.set(padre.getId(), n);
            heapifyUp(indPadre, id); // Continuar hacia arriba
        }
    }

    // Método para desencolar (obtener el máximo/mínimo)
    public T desencolar() {
        if (longitud == 0)
            throw new IllegalStateException("Heap vacío");
        T primero = elems.get(0);
        T ultimo = elems.remove(longitud - 1); // Último elemento
        longitud--;
        if (longitud > 0) {
            elems.set(0, ultimo); // Reemplazar la raíz
            inds.set(ultimo.getId(), 0);
            heapifyDown(0, ultimo.getId()); // Restaurar la propiedad del heap
        }
        inds.set(primero.getId(), -1); // Actualizar índices
        return primero;
    }

    // Restaurar propiedad del heap hacia abajo
    public void heapifyDown(int n, int id) {
        int indIzq = 2 * n + 1;
        int indDer = 2 * n + 2;
        int mayor = n; // Suponer que el nodo actual es el mayor
        if (indIzq < longitud && comparator.compare(elems.get(indIzq), elems.get(mayor)) > 0) {
            mayor = indIzq;
        }
        if (indDer < longitud && comparator.compare(elems.get(indDer), elems.get(mayor)) > 0) {
            mayor = indDer;
        }
        if (mayor != n) {
            T tmp = elems.get(n);
            elems.set(n, elems.get(mayor));
            elems.set(mayor, tmp);
            inds.set(elems.get(n).getId(), n);
            inds.set(elems.get(mayor).getId(), mayor);
            heapifyDown(mayor, id);
        }
    }

    // Método para sacar un elemento en cualquier posición
    public T sacarElem(int pos) {
        if (pos >= longitud)
            throw new IndexOutOfBoundsException("Posición inválida");
        if (pos == longitud - 1) { // Último elemento
            longitud--;
            return elems.remove(pos);
        }
        T eliminado = elems.get(pos);
        T ultimo = elems.remove(longitud - 1); // Último elemento
        longitud--;
        elems.set(pos, ultimo);
        inds.set(ultimo.getId(), pos);
        heapifyDown(pos, ultimo.getId());
        heapifyUp(pos, ultimo.getId());
        inds.set(eliminado.getId(), -1); // Eliminar referencia
        return eliminado;
    }

    // Actualizar prioridad de un elemento
    public void actualizarPrioridad(T elem, T newElem) {
        int index = inds.get(elem.getId());
        elems.set(index, newElem);
        if (comparator.compare(newElem, elem) > 0) {
            heapifyUp(index, newElem.getId());
        } else {
            heapifyDown(index, newElem.getId());
        }
    }

    // Convertir un arreglo en heap
    public void array2heap(T[] array) {
        for (T elem : array) {
            encolar(elem);
        }
    }

    // Convertir un ArrayList en heap
    public void arrayList2heap(ArrayList<T> arrayList) {
        elems = new ArrayList<>(arrayList);
        longitud = elems.size();
        inds = new ArrayList<>();
        inds.add(-1);
        for (int i = 0; i < longitud; i++) {
            inds.add(-1);
        }
        for (int i = (longitud - 2) / 2; i >= 0; i--) {
            heapify(elems, longitud, i);
        }
        for (int i = 0; i < elems.size(); i++) {
            inds.set(elems.get(i).getId(), i);
        }
        
    }

    // Método auxiliar para heapify en arrayList2heap
    private void heapify(ArrayList<T> elems, int longitud, int i) {
        int mayor = i;
        int izq = 2 * i + 1;
        int der = 2 * i + 2;
        if (izq < longitud && comparator.compare(elems.get(izq), elems.get(mayor)) > 0) {
            mayor = izq;
        }
        if (der < longitud && comparator.compare(elems.get(der), elems.get(mayor)) > 0) {
            mayor = der;
        }
        if (mayor != i) {
            T temp = elems.get(i);
            elems.set(i, elems.get(mayor));
            elems.set(mayor, temp);
            heapify(elems, longitud, mayor);
        }
    }

    public String toString() {
        StringBuilder ret = new StringBuilder("[");
        for (int i = 0; i < longitud; i++) {
            ret.append(elems.get(i).getId());
            if (i + 1 != longitud) {
                ret.append(", ");
            }
        }
        ret.append("]");
        return ret.toString();
    }
}
