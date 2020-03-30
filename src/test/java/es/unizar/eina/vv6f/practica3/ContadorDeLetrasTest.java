package es.unizar.eina.vv6f.practica3;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.Assert.*;

public class ContadorDeLetrasTest {

    private static final int NUM_LETRAS = 27;
    private static final int POS_Ñ = 14;


    // Al lado de cada etiqueta @Test se muestra un identificador que es el que
    // corresponde con el nº de prueba definido en el documento entregado (3.2):
    // https://docs.google.com/document/d/18nUjEPvHE0HxsKs7Nr6fc4JbkmZsCevkOYnhbZuKVuY/edit?usp=sharing

    @Test   // #1
    public void frecuencias_quijote_1() throws FileNotFoundException {
        int[] vectorEsperado = leerSalidaEsperada(new File("src/test/res/salida-quijote.txt"));
        ContadorDeLetras contador = new ContadorDeLetras(new File("src/main/res/quijote.txt"));
        int[] vectorObtenido = contador.frecuencias();

        assertArrayEquals(vectorEsperado, vectorObtenido);
    }

    @Test   // #2
    public void frecuencias_hamlet_2() throws FileNotFoundException {
        int[] vectorEsperado = leerSalidaEsperada(new File("src/test/res/salida-hamlet.txt"));
        ContadorDeLetras contador = new ContadorDeLetras(new File("src/main/res/hamlet.txt"));
        int[] vectorObtenido = contador.frecuencias();

        assertArrayEquals(vectorEsperado, vectorObtenido);
    }

    @Test   // #3
    public void frecuencias_regenta_3() throws FileNotFoundException {
        int[] vectorEsperado = leerSalidaEsperada(new File("src/test/res/salida-regenta.txt"));
        ContadorDeLetras contador = new ContadorDeLetras(new File("src/main/res/regenta.txt"));
        int[] vectorObtenido = contador.frecuencias();

        assertArrayEquals(vectorEsperado, vectorObtenido);
    }

    @Test   // #4
    public void frecuencias_cero_caracteres_4() throws FileNotFoundException {
        int[] vectorEsperado = new int[NUM_LETRAS]; // en Java los arrays de enteros inicializan las componentes a 0
        ContadorDeLetras contador = new ContadorDeLetras(new File("src/test/res/fichero_0_caracteres.txt"));
        int[] vectorObtenido = contador.frecuencias();

        assertArrayEquals(vectorEsperado, vectorObtenido);
    }

    @Test   // #5
    public void frecuencias_caracteres_que_no_cuentan_5() throws FileNotFoundException {
        int[] vectorEsperado = new int[NUM_LETRAS]; // en Java los arrays de enteros inicializan las componentes a 0
        ContadorDeLetras contador = new ContadorDeLetras(new File("src/test/res/fichero_caracteres_que_no_cuentan.txt"));
        int[] vectorObtenido = contador.frecuencias();

        assertArrayEquals(vectorEsperado, vectorObtenido);
    }

    @Test   // #6
    public void frecuencias_alfabeto_ingles_6() throws FileNotFoundException {
        int[] vectorEsperado = new int[NUM_LETRAS]; // en Java los arrays de enteros inicializan las componentes a 0
        for(int i = 0; i < NUM_LETRAS - 1; i++){
            vectorEsperado[i] = 1;
        }
        ContadorDeLetras contador = new ContadorDeLetras(new File("src/test/res/fichero_alfabeto_ingles.txt"));
        int[] vectorObtenido = contador.frecuencias();

        assertArrayEquals(vectorEsperado, vectorObtenido);
    }

    @Test   // #7
    public void frecuencias_ñ_Ñ_7() throws FileNotFoundException {
        int[] vectorEsperado = new int[NUM_LETRAS]; // en Java los arrays de enteros inicializan las componentes a 0
        vectorEsperado[26] = 2;
        ContadorDeLetras contador = new ContadorDeLetras(new File("src/test/res/fichero_ñ_Ñ.txt"));
        int[] vectorObtenido = contador.frecuencias();

        assertArrayEquals(vectorEsperado, vectorObtenido);
    }

    @Test   // #8
    public void frecuencias_º_ª_8() throws FileNotFoundException {
        int[] vectorEsperado = new int[NUM_LETRAS]; // en Java los arrays de enteros inicializan las componentes a 0
        vectorEsperado['A' - 'A'] = 1;
        vectorEsperado['O' - 'A'] = 1;
        ContadorDeLetras contador = new ContadorDeLetras(new File("src/test/res/ficheroº_ª.txt"));
        int[] vectorObtenido = contador.frecuencias();

        assertArrayEquals(vectorEsperado, vectorObtenido);
    }

    @Test   // #9
    public void frecuencias_vocales_con_diacriticos_9() throws FileNotFoundException {
        int[] vectorEsperado = new int[NUM_LETRAS]; // en Java los arrays de enteros inicializan las componentes a 0
        vectorEsperado['A' - 'A'] = 10;
        vectorEsperado['E' - 'A'] = 10;
        vectorEsperado['I' - 'A'] = 10;
        vectorEsperado['O' - 'A'] = 10;
        vectorEsperado['U' - 'A'] = 10;
        ContadorDeLetras contador = new ContadorDeLetras(new File("src/test/res/fichero_vocales_con_diacriticos.txt"));
        int[] vectorObtenido = contador.frecuencias();

        assertArrayEquals(vectorEsperado, vectorObtenido);
    }

    @Test   // #10
    public void frecuencias_ç_Ç_10() throws FileNotFoundException {
        int[] vectorEsperado = new int[NUM_LETRAS]; // en Java los arrays de enteros inicializan las componentes a 0
        vectorEsperado['C' - 'A'] = 2; // Ha de contarlas como "C"
        ContadorDeLetras contador = new ContadorDeLetras(new File("src/test/res/fichero_ç_Ç.txt"));
        int[] vectorObtenido = contador.frecuencias();

        assertArrayEquals(vectorEsperado, vectorObtenido);
    }

    @Test(expected = NullPointerException.class)    // #11
    public void frecuencias_fichero_null_11() throws FileNotFoundException {
        ContadorDeLetras contador = new ContadorDeLetras(null);
        contador.frecuencias();
        fail();
    }

    @Test(expected = FileNotFoundException.class)   // #12
    public void frecuencias_fichero_no_existente_12() throws FileNotFoundException {
        ContadorDeLetras contador = new ContadorDeLetras(new File("src/test/res/fichero_no_existente.txt"));
        contador.frecuencias();
        fail();
    }

    @Test(expected = FileNotFoundException.class)   // #13
    public void frecuencias_fichero_sin_permiso_lectura_13() throws FileNotFoundException {
        // Es necesario hacerlo de esta manera tan poco práctica porque si no
        // tenemos finalmente permisos de lectura sobre el fichero no deja subirlo a GitHub
        File file = new File("src/test/res/fichero_sin_permiso_lectura.txt");
        file.setReadable(false);
        ContadorDeLetras contador = new ContadorDeLetras(file);
        try {
            contador.frecuencias();
            fail(); // Si no ha saltado la excepción, fallamos el test
        }
        catch (FileNotFoundException e){
            file.setReadable(true);
            throw new FileNotFoundException();  // Hacemos que salte la excepción de nuevo
        }
    }




    private int[] leerSalidaEsperada(File fichero) throws FileNotFoundException {
        int[] apariciones = new int[NUM_LETRAS];
        Scanner scanner = new Scanner(fichero);
        for(int i = 0; i < NUM_LETRAS - 1; i++) {
            scanner.next();
            if(i == POS_Ñ) {
                apariciones[NUM_LETRAS - 1] = scanner.nextInt();
                scanner.next();
            }
            apariciones[i] = scanner.nextInt();
        }
        return apariciones;
    }
}
