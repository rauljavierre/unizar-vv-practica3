package es.unizar.eina.vv6f.practica3;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Normalizer;

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
    // private File fichero;    Eliminada porque no hago uso de la misma
    private int[] frecuencias = null;

    // Añadidas por mí
    private final long LONGITUD;
    private final String FICHERO_STRING;

    /**
     * Construye un ContadorDeLetras para frecuencias la frecuencia en las que aparecen las letras
     * del fichero «fichero».
     * @param fichero
     *            fichero de texto cuyo contenido será analizado.
     */
    public ContadorDeLetras(File fichero) {
        // this.fichero = fichero;
        this.LONGITUD = fichero.length();
        this.FICHERO_STRING = ;
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
        if (frecuencias == null) {
            this.frecuencias = new int[27];
            for(int i = 0; i < LONGITUD; i++){
                char c = FICHERO_STRING.charAt(i);  // charAt(i) no admite i != int... Problemas con ficheros MUY grandes
                detectamosCaracterYActualizamosFrecuencia(c);
            }
        }
        return frecuencias;
    }

    private void detectamosCaracterYActualizamosFrecuencia(char c){
        c = Character.toLowerCase(c);
        if(c == 'ñ'){
            frecuencias[26]++;
        }
        else{
            Normalizer
                    .normalize("" + c, Normalizer.Form.NFD)
                    .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
            System.out.println(c);
            frecuencias[c - 'a']++;
        }
    }
}
