package es.unizar.eina.vv6f.practica3;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilePermission;
import java.io.IOException;
import java.util.Scanner;

import static org.junit.Assert.*;

public class ContadorDeLetrasTest {

    private static final int NUM_LETRAS = 27;
    private static final int POS_Ñ = 14;

    @Test
    public void frecuencias_quijote() throws FileNotFoundException {
        int[] vectorEsperado = leerSalidaEsperada(new File("src/test/res/salida-quijote.txt"));
        ContadorDeLetras contador = new ContadorDeLetras(new File("src/main/res/quijote.txt"));
        int[] vectorObtenido = contador.frecuencias();

        assertArrayEquals(vectorEsperado, vectorObtenido);
    }

    @Test
    public void frecuencias_hamlet() throws FileNotFoundException {
        int[] vectorEsperado = leerSalidaEsperada(new File("src/test/res/salida-hamlet.txt"));
        ContadorDeLetras contador = new ContadorDeLetras(new File("src/main/res/hamlet.txt"));
        int[] vectorObtenido = contador.frecuencias();

        assertArrayEquals(vectorEsperado, vectorObtenido);
    }

    @Test // Falla pero puede que sea por un error tipográfico en los expected... 129829 -> 129820
    public void frecuencias_regenta() throws FileNotFoundException {
        int[] vectorEsperado = leerSalidaEsperada(new File("src/test/res/salida-regenta.txt"));
        ContadorDeLetras contador = new ContadorDeLetras(new File("src/main/res/regenta.txt"));
        int[] vectorObtenido = contador.frecuencias();

        assertArrayEquals(vectorEsperado, vectorObtenido);
    }

    @Test(expected = NullPointerException.class)
    public void frecuencias_fichero_null() throws FileNotFoundException {
        ContadorDeLetras contador = new ContadorDeLetras(null);
        contador.frecuencias();
        fail();
    }

    @Test(expected = FileNotFoundException.class)
    public void frecuencias_fichero_no_existente() throws FileNotFoundException {
        ContadorDeLetras contador = new ContadorDeLetras(new File("src/test/res/fichero_no_existente.txt"));
        contador.frecuencias();
        fail();
    }

    @Test(expected = FileNotFoundException.class)
    public void frecuencias_fichero_sin_permiso_lectura() throws FileNotFoundException {
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
