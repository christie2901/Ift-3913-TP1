import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class densite {

    public static double densiteComment(File file) throws FileNotFoundException {
        double loc = loc(file);
        double cloc = loc(file) - tloc.tloc(file.getAbsolutePath());

        return cloc/loc;
    }

    public static int loc(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);

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
