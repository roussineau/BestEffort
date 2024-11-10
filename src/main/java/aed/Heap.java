package aed;

import java.util.ArrayList;

public class Heap<T> {
    // Vamos a usar 2 heaps para ordenar Traslados por rentabilidad y por
    // antiguedad. Como ambos van a tener un int para ordenar la prioridad
    // (ganancia y timestamp), la prioridad de los nodos se puede hacer con int.
    // El tipo generico T es para los elementos del heap.

    ArrayList<Nodo> elems;
    int longitud;

    public class Nodo {
        private int prioridad;
        private T valor;
        private Nodo padre;
        private Nodo izq;
        private Nodo der;

        Nodo(int prior, T v) {
            prioridad = prior;
            valor = v;
            padre = null;
            izq = null;
            der = null;
        }
    }

    // Constructor
    public Heap() {
        this.elems = new ArrayList();
        this.longitud = 0;
    }

    // Devuelve la prioridad maxima (no el elemento) (asume que hay al menos 1
    // elemento)
    public int pMax() {
        return elems.get(0).prioridad;
    }

    // Devuelve el elemento de maxima prioridad
    public T vMax() {
        return elems.get(0).valor;
    }

    // Dado un elemento, lo encola. El entero p sera el criterio que
    // usemos para encolar, o sea, ganancias, perdidas o antiguedad.
    public void encolar(int p, T elem) {
        Nodo nuevo = new Nodo(p, elem);
        elems.add(nuevo);
        if (longitud > 0) {
            nuevo.padre = elems.get(longitud / 2);
            if (elems.get((longitud / 2) * 2) != nuevo) { // Primero divide y trunca y luego multiplica por 2
                nuevo.padre.der = nuevo;
            } else {
                nuevo.padre.izq = nuevo;
            }
            heapifyUp(nuevo);
        }
        longitud++;
    }

    public void desencolar(int p, T elem) {

    }

    // Ingresado un nuevo elemento al array, lo ubica donde corresponde
    public void heapifyUp(Nodo n) {
        int pPadre = n.padre.prioridad;
        int pHijo = n.prioridad;
        T vPadre = n.padre.valor;
        T vHijo = n.valor;
        if (pPadre < pHijo) {
            n.padre.prioridad = pHijo;
            n.prioridad = pPadre;
            n.padre.valor = vHijo;
            n.valor = vPadre;
            heapifyUp(n);
        }
    }

    public void heapifyDown(Nodo n) {
        int pPadre = n.prioridad;
        T vPadre = n.valor;
        if (n.izq != null && n.der != null) {
            int pIzq = n.izq.prioridad;
            int pDer = n.der.prioridad;
            T vIzq = n.izq.valor;
            T vDer = n.der.valor;
            if (n.izq.prioridad > n.der.prioridad) {
                if (pIzq > pPadre) {
                    n.prioridad = pIzq;
                    n.valor = vIzq;
                    n.izq.prioridad = pPadre;
                    n.izq.valor = vPadre;
                    heapifyDown(n);
                } else {
                    if (pDer > pPadre) {
                        n.prioridad = pDer;
                        n.valor = vDer;
                        n.der.prioridad = pPadre;
                        n.der.valor = vPadre;
                        heapifyDown(n);
                    }
                }
            }
        } else {
            if (n.izq != null) {
                int pIzq = n.izq.prioridad;
                T vIzq = n.izq.valor;
                if (pIzq > pPadre) {
                    n.prioridad = pIzq;
                    n.valor = vIzq;
                    n.izq.prioridad = pPadre;
                    n.izq.valor = vPadre;
                    heapifyDown(n);
                }
            } else {
                if (n.der != null) {
                    int pDer = n.der.prioridad;
                    T vDer = n.der.valor;
                    if (pDer > pPadre) {
                        n.prioridad = pDer;
                        n.valor = vDer;
                        n.der.prioridad = pPadre;
                        n.der.valor = vPadre;
                        heapifyDown(n);
                    }
                }
            }
        }
    }

    public String toStringPrioridad() {
        String res = "[";
        int i = 0;
        while (i < longitud) {
            res += elems.get(i).prioridad;
            if (i < longitud - 1) {
                res += ", ";
            }
            i++;
        }
        res += "]";
        return res;
    }

    public String toStringValor() {
        String res = "[";
        int i = 0;
        while (i < longitud) {
            res += elems.get(i).valor;
            if (i < longitud - 1) {
                res += ", ";
            }
            i++;
        }
        res += "]";
        return res;
    }
}
