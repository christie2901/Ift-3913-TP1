import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class tassert{

    public static int tassert(String source) throws FileNotFoundException {
        File classTest = new File(source);

        Scanner scanner = new Scanner(classTest);

        int nbAssert = 0;
        while (scanner.hasNextLine()){
            //si la ligne n'est pas un commentaire et contient "assert" alors nbAssert++
            String line = scanner.nextLine();

            if (!(line.compareTo("")==0)){
                if (line.contains("/*")) {
                    // ignore les blocs de commentaires
                    line = scanner.nextLine();
                    while (scanner.hasNextLine() && ( !(line.contains("*/")))) {
                        line = scanner.nextLine();
                    }
                    continue;
                }

                if (!(line.contains("//"))){
                    if (line.contains("assert") || line.contains("fail")){
                        nbAssert++;
                    }
                }
            }
        }
        return nbAssert;
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(tassert(args[0]));
        //"C:/Users/tiffa/Desktop/IFT2255/Robotix_FINAL/Implementation/Robot/TachesTest.java"
    }
}