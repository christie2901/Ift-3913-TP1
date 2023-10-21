
//code provenant de notre TP1
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class tassert{

    public static int tassert(File source) throws FileNotFoundException {

        Scanner scanner = new Scanner(source);

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

}
