package aed;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class BestEffortTests {

    int cantCiudades;
    Traslado[] listaTraslados;
    ArrayList<Integer> actual;

    @BeforeEach
    void init() {
        // Reiniciamos los valores de las ciudades y traslados antes de cada test
        cantCiudades = 7;
        listaTraslados = new Traslado[] {
                new Traslado(1, 0, 1, 100, 10),
                new Traslado(2, 0, 1, 400, 20),
                new Traslado(3, 3, 4, 500, 50),
                new Traslado(4, 4, 3, 500, 11),
                new Traslado(5, 1, 0, 1000, 40),
                new Traslado(6, 1, 0, 1000, 41),
                new Traslado(7, 6, 3, 2000, 42)
        };
    }

    void assertSetEquals(ArrayList<Integer> s1, ArrayList<Integer> s2) {
        assertEquals(s1.size(), s2.size());
        for (int e1 : s1) {
            boolean encontrado = false;
            for (int e2 : s2) {
                if (e1 == e2)
                    encontrado = true;
            }
            assertTrue(encontrado, "No se encontró el elemento " + e1 + " en el arreglo " + s2.toString());
        }
    }

    @Test
    void despachar_con_mas_ganancia_de_a_uno() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasRedituables(1);

        assertSetEquals(new ArrayList<>(Arrays.asList(6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());

        sis.despacharMasRedituables(1);
        sis.despacharMasRedituables(1);

        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(0, 3)), sis.ciudadesConMayorPerdida());
    }

    @Test
    void despachar_con_mas_ganancia_de_a_varios() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasRedituables(3);

        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(0, 3)), sis.ciudadesConMayorPerdida());

        sis.despacharMasRedituables(3);

        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());

    }

    @Test
    void despachar_mas_viejo_de_a_uno() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasAntiguos(1);

        assertSetEquals(new ArrayList<>(Arrays.asList(0)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(1)), sis.ciudadesConMayorPerdida());

        sis.despacharMasAntiguos(1);
        assertSetEquals(new ArrayList<>(Arrays.asList(4)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());

        sis.despacharMasAntiguos(1);
        assertSetEquals(new ArrayList<>(Arrays.asList(0, 4)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(1, 3)), sis.ciudadesConMayorPerdida());
    }

    @Test
    void despachar_mas_viejo_de_a_varios() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasAntiguos(3);
        assertSetEquals(new ArrayList<>(Arrays.asList(0, 4)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(1, 3)), sis.ciudadesConMayorPerdida());

        sis.despacharMasAntiguos(3);
        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());

    }

    @Test
    void despachar_mixtos() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasRedituables(3);
        sis.despacharMasAntiguos(3);
        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());

        sis.despacharMasAntiguos(1);
        assertSetEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorPerdida());

    }

    @Test
    void agregar_traslados() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        Traslado[] nuevos = new Traslado[] {
                new Traslado(8, 0, 1, 10001, 5),
                new Traslado(9, 0, 1, 40000, 2),
                new Traslado(10, 0, 1, 50000, 3),
                new Traslado(11, 0, 1, 50000, 4),
                new Traslado(12, 1, 0, 150000, 1)
        };

        sis.registrarTraslados(nuevos);

        sis.despacharMasAntiguos(4);
        assertSetEquals(new ArrayList<>(Arrays.asList(1)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(0)), sis.ciudadesConMayorPerdida());

        sis.despacharMasRedituables(1);
        assertSetEquals(new ArrayList<>(Arrays.asList(0)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(1)), sis.ciudadesConMayorPerdida());

    }

    @Test
    void promedio_por_traslado() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        sis.despacharMasAntiguos(3);
        assertEquals(333, sis.gananciaPromedioPorTraslado());

        sis.despacharMasRedituables(3);
        assertEquals(833, sis.gananciaPromedioPorTraslado());

        Traslado[] nuevos = new Traslado[] {
                new Traslado(8, 1, 2, 1452, 5),
                new Traslado(9, 1, 2, 334, 2),
                new Traslado(10, 1, 2, 24, 3),
                new Traslado(11, 1, 2, 333, 4),
                new Traslado(12, 2, 1, 9000, 1)
        };

        sis.registrarTraslados(nuevos);
        sis.despacharMasRedituables(6);

        assertEquals(1386, sis.gananciaPromedioPorTraslado());

    }

    @Test
    void mayor_superavit() {
        Traslado[] nuevos = new Traslado[] {
                new Traslado(1, 3, 4, 1, 7),
                new Traslado(7, 6, 5, 40, 6),
                new Traslado(6, 5, 6, 3, 5),
                new Traslado(2, 2, 1, 41, 4),
                new Traslado(3, 3, 4, 100, 3),
                new Traslado(4, 1, 2, 30, 2),
                new Traslado(5, 2, 1, 90, 1)
        };
        BestEffort sis = new BestEffort(this.cantCiudades, nuevos);

        sis.despacharMasAntiguos(1);
        assertEquals(2, sis.ciudadConMayorSuperavit());

        sis.despacharMasAntiguos(2);
        assertEquals(3, sis.ciudadConMayorSuperavit());

        sis.despacharMasAntiguos(3);
        assertEquals(2, sis.ciudadConMayorSuperavit());

        sis.despacharMasAntiguos(1);
        assertEquals(2, sis.ciudadConMayorSuperavit());

    }

    ////////////////////
    // NUESTROS TESTS //
    ////////////////////

    @Test
    void megaTest() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        Traslado[] nuevos = new Traslado[] {
                new Traslado(8, 0, 1, 10001, 5),
                new Traslado(9, 6, 2, 40000, 2),
                new Traslado(10, 2, 1, 50000, 3),
                new Traslado(11, 1, 6, 50000, 4),
                new Traslado(12, 5, 3, 150000, 9),
                new Traslado(13, 3, 0, 50000, 45),
                new Traslado(14, 3, 4, 410000, 21),
                new Traslado(15, 4, 6, 400, 17),
                new Traslado(16, 1, 0, 17000, 11),
                new Traslado(17, 5, 1, 130000, 13),
                new Traslado(18, 0, 6, 250000, 78),
                new Traslado(19, 4, 2, 90000, 60)
        };

        sis.registrarTraslados(nuevos);

        sis.despacharMasAntiguos(3);
        assertEquals(new ArrayList<>(Arrays.asList(2, 1)), sis.ciudadesConMayorGanancia());
        assertEquals(new ArrayList<>(Arrays.asList(1, 6)), sis.ciudadesConMayorPerdida());

        sis.despacharMasRedituables(5);
        assertEquals(new ArrayList<>(Arrays.asList(3)), sis.ciudadesConMayorGanancia());
        assertEquals(new ArrayList<>(Arrays.asList(4)), sis.ciudadesConMayorPerdida());

        assertEquals(146250, sis.gananciaPromedioPorTraslado());

        assertEquals(5, sis.ciudadConMayorSuperavit());

    }

    @Test
    void heapTest() {
        Heap<Traslado> traslados = new Heap<>(false);
        Traslado traslado_1 = new Traslado(1, 3, 4, 42, 7);
        Traslado traslado_2 = new Traslado(2, 6, 5, 6, 6);
        Traslado traslado_3 = new Traslado(3, 5, 6, 7, 5);
        Traslado traslado_4 = new Traslado(4, 2, 1, 203, 4);
        Traslado traslado_5 = new Traslado(5, 3, 4, 84, 3);
        Traslado traslado_6 = new Traslado(6, 1, 2, 117, 2);
        Traslado traslado_7 = new Traslado(7, 2, 1, 29, 1);

        traslados.encolar(traslado_1);
        traslados.encolar(traslado_2);
        traslados.encolar(traslado_3);
        traslados.encolar(traslado_4);
        traslados.encolar(traslado_5);
        traslados.encolar(traslado_6);
        traslados.encolar(traslado_7);

        assertEquals(new ArrayList<>(
                Arrays.asList(traslado_4, traslado_5, traslado_6, traslado_2, traslado_1, traslado_3, traslado_7)),
                traslados.elems);
        assertEquals(7, traslados.longitud);
        assertEquals(new ArrayList<>(Arrays.asList(-1, 4, 3, 5, 0, 1, 2, 6)), traslados.inds); // El primer indice es -1
                                                                                               // por que no hay ningún
                                                                                               // traslado con ID 0

        traslados.desencolar();

        assertEquals(
                new ArrayList<>(Arrays.asList(traslado_6, traslado_5, traslado_7, traslado_2, traslado_1, traslado_3)),
                traslados.elems);
        assertEquals(6, traslados.longitud);
        assertEquals(new ArrayList<>(Arrays.asList(-1, 4, 3, 5, -1, 1, 0, 2)), traslados.inds);

        traslados.sacarElem(3);

        assertEquals(new ArrayList<>(Arrays.asList(traslado_6, traslado_5, traslado_7, traslado_3, traslado_1)),
                traslados.elems);
        assertEquals(5, traslados.longitud);
        assertEquals(new ArrayList<>(Arrays.asList(-1, 4, -1, 3, -1, 1, 0, 2)), traslados.inds);

        ArrayList<Traslado> arrayListEnOrdenHeap = new ArrayList<Traslado>(
                Arrays.asList(traslado_4, traslado_5, traslado_6, traslado_2, traslado_1, traslado_3, traslado_7));
        ArrayList<Traslado> arrayList = new ArrayList<Traslado>(
                Arrays.asList(traslado_1, traslado_2, traslado_3, traslado_4, traslado_5, traslado_6, traslado_7));

        traslados.arrayList2heap(arrayList);
        assertEquals(new ArrayList<>(arrayListEnOrdenHeap), traslados.elems);
        assertEquals(traslados.elems, arrayListEnOrdenHeap);


    }

    @Test
    void heapComparatorTest() {
        Ciudad ciudad_1 = new Ciudad(1);
        Ciudad ciudad_0 = new Ciudad(0);

        ciudad_0.ganancia = 300;
        ciudad_0.perdida = 423;
        ciudad_0.superavit = 300 - 423;

        ciudad_1.ganancia = 50;
        ciudad_1.perdida = 33;
        ciudad_1.superavit = 50 - 33;

        HeapComparator comparador_0 = new HeapComparator(false);

        assertEquals(true, comparador_0.compare(ciudad_0, ciudad_1) < 0);

        Traslado traslado_0 = new Traslado(19, 4, 2, 90000, 60);
        Traslado traslado_1 = new Traslado(5, 3, 4, 84, 3);

        assertEquals(true, comparador_0.compare(traslado_0, traslado_1) > 0);

        HeapComparator comparador_1 = new HeapComparator(true);

        assertEquals(true, comparador_1.compare(traslado_0, traslado_1) < 0);

    }

}
