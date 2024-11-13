package aed;

import java.util.ArrayList;

public class Heap {
    // Vamos a usar 2 heaps para ordenar Traslados por rentabilidad y por
    // antiguedad. Como ambos van a tener un int para ordenar la prioridad
    // (ganancia y timestamp), la prioridad de los nodos se puede hacer con int.
    // El tipo generico T es para los elementos del heap.

    ArrayList<Nodo> elems;
    int longitud;
    HeapComparator comparator;

    public class Nodo {
        public Traslado valor;
        private Nodo padre;
        private Nodo izq;
        private Nodo der;

        Nodo(Traslado v) {
            valor = v;
            padre = null;
            izq = null;
            der = null;
        }
    }

    public class Handle {
        int referencia;
        
        public Handle(int ref) {
            referencia = ref;
        }
    }

    // Constructor
    public Heap(Boolean atributo) {
        elems = new ArrayList();
        longitud = 0;
        comparator = new HeapComparator(atributo);
    }

    // Devuelve el elemento de maxima prioridad
    public Traslado max() {
        return elems.get(0).valor;
    }

    // Dado un elemento, lo encola. El entero p sera el criterio que
    // usemos para encolar, o sea, ganancias, perdidas o antiguedad.
    public void encolar(Traslado valor) {
        Nodo nuevo = new Nodo(valor);
        if (longitud == 0) {
            elems.add(nuevo);
            longitud++;
        } else {
            elems.add(nuevo);
            longitud++;
            int posicionPadre = longitud / 2 - 1;
            nuevo.padre = elems.get(posicionPadre);
            if (elems.get(posicionPadre).izq == null) {
                elems.get(posicionPadre).izq = nuevo;
            } else {
                elems.get(posicionPadre).der = nuevo;
            }
            heapifyUp(nuevo);
        }

    }

    // Quitar el elemento de maxima prioridad
    public Traslado desencolar() {
        return quitar(elems.get(0));
    }

    // Quitar un traslado especifico
    public Traslado quitar(Nodo n) {
        Nodo ultimo = elems.get(longitud - 1);
        Traslado vUltimo = ultimo.valor;
        Traslado vRaiz = n.valor;
        if (longitud == 1) {
            Traslado ret = elems.remove(0).valor;
            longitud--;
            return ret;
        } else {
            elems.get(0).valor = vUltimo;
            elems.get(longitud - 1).valor = vRaiz;
            if (elems.get(longitud - 1).padre.der == null) {
                elems.get(longitud - 1).padre.izq = null;
            } else {
                elems.get(longitud - 1).padre.der = null;
            }
            elems.get(longitud - 1).padre = null;
            Traslado ret = elems.remove(longitud - 1).valor;
            longitud--;
            heapifyDown(elems.get(0));
            return ret;
        }
    }

    // Ingresado un nuevo elemento al array, lo ubica donde corresponde
    public void heapifyUp(Nodo n) {
        if (n.padre != null) {
            int comparacion = comparator.compare(n.valor, n.padre.valor);
            Traslado vPadre = n.padre.valor;
            Traslado vHijo = n.valor;
            if (comparacion > 0) {
                n.padre.valor = vHijo;
                n.valor = vPadre;
                heapifyUp(n.padre);
            }
        }
    }

    public void heapifyDown(Nodo n) {
        Traslado vPadre = n.valor;
        if (n.izq != null && n.der != null) {
            int comparacionHijos = comparator.compare(n.izq.valor, n.der.valor);
            Traslado vIzq = n.izq.valor;
            Traslado vDer = n.der.valor;
            if (comparacionHijos > 0) {
                int comparacionIzq = comparator.compare(vIzq, n.valor);
                if (comparacionIzq > 0) {
                    n.valor = vIzq;
                    n.izq.valor = vPadre;
                    heapifyDown(n.izq);
                }
            } else {
                int comparacionDer = comparator.compare(vDer, n.valor);
                if (comparacionDer > 0) {
                    n.valor = vDer;
                    n.der.valor = vPadre;
                    heapifyDown(n.der);
                }
            }
        } else {
            if (n.izq != null) {
                Traslado vIzq = n.izq.valor;
                int comparacionIzq = comparator.compare(vIzq, n.valor);
                if (comparacionIzq > 0) {
                    n.valor = vIzq;
                    n.izq.valor = vPadre;
                    heapifyDown(n.izq);
                }
            }
        }
    }

    public String toStringTimestamp() {
        String res = "[";
        int i = 0;
        while (i < longitud) {
            res += elems.get(i).valor.timestamp;
            if (i < longitud - 1) {
                res += ", ";
            }
            i++;
        }
        res += "]";
        return res;
    }
}
