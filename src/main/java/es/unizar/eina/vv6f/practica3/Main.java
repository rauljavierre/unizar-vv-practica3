package es.unizar.eina.vv6f.practica3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Programa Java que, al iniciar su ejecución, solicita al usuario el nombre de un fichero de texto.
 * A continuación, si el fichero existe y se puede leer, muestra en la salida estándar una lista de
 * las letras del alfabeto español y el número de veces que dicha letra aparece en el fichero
 * introducido. En caso contrario, escribe en la salida estándar un mensaje de error de la forma
 * «El fichero 'f' no existe.», donde 'f' es el nombre de fichero introducido por el usuario.
 * 
 * No se distingue entre mayúsculas y minúsculas. La letra Ñ es una letra en español. El resto de
 * apariciones de letras voladas y caracteres con diacríticos (acentos agudos, graves, diéresis y
 * cedillas), se consideran como ocurrencias de la letra correspondiente sin diacríticos.
 */
public class Main {

    private static final String FORMATO_SALIDA_FRECUENCIAS = "%n%c: %7d";
    private static final int NUM_LETRAS = 27;
    private static final int POS_Ñ = 14;

    /**
     * Método que, al iniciar su ejecución, solicita al usuario el nombre de un fichero de texto.
     * A continuación, si el fichero existe y se puede leer, muestra en la salida estándar una lista
     * de las letras del alfabeto español y el número de veces que dicha letra aparece en el fichero
     * introducido. En caso contrario, escribe en la salida estándar un mensaje de error de la forma
     * «El fichero 'f' no existe.», donde 'f' es el nombre de fichero introducido por el usuario.
     *
     * No se distingue entre mayúsculas y minúsculas. La letra Ñ es una letra en español. El resto
     * de apariciones de letras voladas y caracteres con diacríticos (acentos agudos, graves,
     * diéresis y cedillas), se consideran como ocurrencias de la letra correspondiente sin
     * diacríticos.
     *
     * @param args
     *            no utilizado.
     */
    public static void main(String[] args) {
        try{
            ContadorDeLetras contador = crearContadorEspecifico();
            int[] apariciones = contador.frecuencias();
            imprimirApariciones(apariciones);
        }
        catch(FileNotFoundException | NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    private static ContadorDeLetras crearContadorEspecifico(){
        return new ContadorDeLetras(generarFile());
    }

    private static File generarFile(){
        System.out.print("Nombre de un fichero de texto: ");
        Scanner scanner = new Scanner(System.in);
        String nombreFichero = scanner.nextLine();
        scanner.close();
        return new File(nombreFichero);
    }

    private static void imprimirApariciones(int[] apariciones){
        for(int i = 0; i < NUM_LETRAS - 1; i++) {
            if(i == POS_Ñ) {
                System.out.format(FORMATO_SALIDA_FRECUENCIAS, 'Ñ', apariciones[26]);
            }
            System.out.format(FORMATO_SALIDA_FRECUENCIAS, 'A' + i, apariciones[i]);
        }
    }
}
