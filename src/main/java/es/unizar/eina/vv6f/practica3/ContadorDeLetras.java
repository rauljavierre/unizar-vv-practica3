package es.unizar.eina.vv6f.practica3;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.Scanner;

/**
 * Clase para el análisis de la frecuencia de aparición de letras del alfabeto español en un
 * fichero de texto. Los objetos de esta clase se construyen utilizando como argumento un objeto de
 * la clase File que representa el fichero de texto que se quiere analizar. La primera invocación al
 * método frecuencias() analiza el contenido del fichero de texto y, si se ha podido procesar,
 * devuelve un vector de siempre 27 componentes de tipo entero. Las primeras 26 componentes
 * almacenan el número de apariciones de las 26 letras del alfabeto inglés. La última componente,
 * almacena el número de apariciones de la letra Ñ.
 *
 * No se distingue entre mayúsculas y minúsculas. En español, la letra Ñ es una letra distinta a la
 * N. El resto de apariciones de letras voladas y caracteres con diacríticos (acentos agudos,
 * graves, diéresis, cedillas), se consideran como ocurrencias de la letra correspondiente sin
 * diacríticos.
 *
 */
public class ContadorDeLetras {

    private File fichero;
    private int[] frecuencias = null;

    /**
     * Construye un ContadorDeLetras para frecuencias la frecuencia en las que aparecen las letras
     * del fichero «fichero».
     * @param fichero
     *            fichero de texto cuyo contenido será analizado.
     */
    public ContadorDeLetras(File fichero) {
        this.fichero = fichero;
    }

    /**
     * Si no ha sido analizado ya, analiza el contenido del fichero de texto asociado a este
     * objeto. Devuelve un vector de 27 componentes con las frecuencias absolutas de
     * aparición de cada letra del alfabeto español en el fichero.
     *
     * @return vector de 27 componentes de tipo entero. Las primeras 26 componentes almacenan el
     *         número de apariciones de las 26 letras del alfabeto inglés: la componente indexada
     *         por 0 almacena el número de apariciones de la letra A, la componente indexada por 1,
     *         el de la letra B y así sucesivamente. La última componente, almacena el número de
     *         apariciones de la letra Ñ.
     * @throws FileNotFoundException
     *             si el fichero de texto que se especificó al construir este objeto no existe o no
     *             puede abrirse.
     */
    public int[] frecuencias() throws FileNotFoundException {
        if (this.frecuencias == null) {
            this.frecuencias = new int[27];
            Scanner scanner = new Scanner(this.fichero);
            while(scanner.hasNext()){ // Probablemente haya un mejor método... Investigar
                String palabra = scanner.next();
                for(int i = 0; i < palabra.length(); i++) {
                    char c = palabra.charAt(i);
                    detectamosCaracterYActualizamosFrecuencia(c);
                }
            }
        }
        return frecuencias;
    }

    private void detectamosCaracterYActualizamosFrecuencia(char c){
        if(c == 'ñ' || c == 'Ñ') {
            frecuencias[26]++;
        }
        else {
            c = Normalizer
                    .normalize("" + c, Normalizer.Form.NFD)
                    .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                    .charAt(0);
            if(isAlpha(c)){
                if(isUppercase(c)){
                    c = (char) (c - 'A' + 'a');
                }
                frecuencias[c - 'a']++;
            }
        }
    }

    private boolean isAlpha(char c){
        return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z';
    }

    private boolean isUppercase(char c){
        return c >= 'A' && c <= 'Z';
    }
}
