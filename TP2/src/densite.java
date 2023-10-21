import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class densite {

    public static double densiteComment(String source) throws FileNotFoundException {
        double loc = loc(source);
        double cloc = loc(source) - tloc.tloc(source);

        return cloc/loc;
    }

    public static int loc(String source) throws FileNotFoundException {
        File classTest = new File(source);

        Scanner scanner = new Scanner(classTest);

        int nbLignes = 0;
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            if (!(line.compareTo("")==0)){
                // ignore les lignes vides
                nbLignes++;
            }
        }

        return nbLignes;
    }

}
