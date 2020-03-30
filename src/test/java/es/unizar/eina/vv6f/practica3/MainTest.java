package es.unizar.eina.vv6f.practica3;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

import static java.lang.System.err;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.junit.Assert.*;

public class MainTest {

    private static InputStream system_in;
    private static PrintStream system_out;
    private static Path temp_dir;

    @BeforeClass
    public static void guardarReferenciasSystem(){
        system_in = System.in;
        system_out = System.out;
    }

    @AfterClass
    public static void restaurarReferenciasSystem(){
        System.setIn(system_in);
        System.setOut(system_out);
    }

    @BeforeClass
    public static void copiarFicherosPruebas() throws IOException {
        // Creamos el directorio temporal
        temp_dir = Files.createTempDirectory("vv-practica3");

        // Primero el directorio src/main/res/
        Stream<Path> paths = Files.walk(Paths.get("src/main/res"));
        paths
                .filter(Files::isRegularFile)
                .forEach(path -> copiar(path.toString(), path.getFileName().toString()));

        // Después el directorio src/test/res/
        Stream<Path> paths2 = Files.walk(Paths.get("src/test/res"));
        paths2
                .filter(Files::isRegularFile)
                .forEach(path -> copiar(path.toString(), path.getFileName().toString()));
    }

    @AfterClass
    public static void eliminarFicherosPruebas() throws IOException {
        Files.walk(temp_dir)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }

    @Test
    public void frecuencias_quijote_sistema() throws IOException {
        File salida = new File(temp_dir.toString() + "/salida.txt");
        File salida_esperada = new File("src/test/res/salida-quijote.txt");

        System.setIn(new ByteArrayInputStream("src/main/res/quijote.txt".getBytes()));
        System.setOut(new PrintStream(salida));

        Main.main(null);

        // Eliminamos la primera línea del output
        salida = eliminarLineaInicial(salida);

        byte[] f1 = Files.readAllBytes(salida.toPath());
        byte[] f2 = Files.readAllBytes(salida_esperada.toPath());

        assertArrayEquals(f1, f2);
    }

    @Test
    public void frecuencias_hamlet_sistema() throws IOException {
        File salida = new File(temp_dir.toString() + "/salida.txt");
        File salida_esperada = new File("src/test/res/salida-hamlet.txt");

        System.setIn(new ByteArrayInputStream("src/main/res/hamlet.txt".getBytes()));
        System.setOut(new PrintStream(salida));

        Main.main(null);

        // Eliminamos las primeras líneas del output
        salida = eliminarLineaInicial(salida);

        byte[] f1 = Files.readAllBytes(salida.toPath());
        byte[] f2 = Files.readAllBytes(salida_esperada.toPath());

        assertArrayEquals(f1, f2);
    }

    @Test
    public void frecuencias_regenta_sistema() throws IOException {
        File salida = new File(temp_dir.toString() + "/salida.txt");
        File salida_esperada = new File("src/test/res/salida-regenta.txt");

        System.setIn(new ByteArrayInputStream("src/main/res/regenta.txt".getBytes()));
        System.setOut(new PrintStream(salida));

        Main.main(null);

        // Eliminamos las primeras líneas del output
        salida = eliminarLineaInicial(salida);

        byte[] f1 = Files.readAllBytes(salida.toPath());
        byte[] f2 = Files.readAllBytes(salida_esperada.toPath());

        assertArrayEquals(f1, f2);
    }



    private static void copiar(String path, String nombre) {
        try {
            Files.copy(new File(path).toPath(),
                        new File(temp_dir.toString() + "/" + nombre).toPath(), REPLACE_EXISTING);
        }
        catch (IOException e){
            err.println(e.getMessage());
        }
    }

    private File eliminarLineaInicial(File conLineas) throws IOException {
        File sinLineas = new File(conLineas.getPath());

        FileReader fr = new FileReader(conLineas);
        BufferedReader br = new BufferedReader(fr);
        StringBuffer sb = new StringBuffer();
        String line;
        for(int n = 0; (line = br.readLine()) != null; n++) {
            if(n >= 1){
                sb.append(line);
                sb.append("\n");
            }
        }
        fr.close();
        sinLineas.delete();

        OutputStream os = new FileOutputStream(sinLineas,true);
        os.write(String.valueOf(sb).getBytes(), 0, sb.length());
        os.close();

        return sinLineas;
    }
}
