import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;



public class tls {

    public static void tls(String source) throws FileNotFoundException {
        File folder = new File(source);

        //il faut ignorer les fichiers qui ne sont pas une classe java
        for (File file : Objects.requireNonNull(folder.listFiles())){

            String filename = file.getName();
            String chemin = source + "/" + filename;
            String nomClasse = filename.substring(0,filename.indexOf(".java"));

            //Besoin de scanner pour avoir le package
            String paquet = null;

            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                if (line.isBlank()){continue;}
                if (line.contains("/*")) {
                    // ignore les blocs de commentaires
                    line = scanner.nextLine();
                    while (scanner.hasNextLine() && ( line.contains("*") || line.contains("*/") ) ) {
                        line = scanner.nextLine();
                    }
                }
                if (!(line.contains("//"))){
                    if (line.contains("package")){
                        paquet = line.replace("package","").replace(" ","").replace(";","");
                        break;
                    } else if (line.contains("public class")){break;}
                }

            }

            int nblignes = tloc.lignes(chemin);
            int nbAssert = tassert.tassert(chemin);
            double tcmp;
            if (nbAssert==0) {
                tcmp = 0.0;
            } else {
                tcmp = (double) nblignes / nbAssert;
            }

            String result = chemin + ", " + paquet + ", " + nomClasse + ", " + nblignes + ", " + nbAssert + ", " + tcmp;
            //Sortir sur la ligne de commande
            System.out.println(result);

        }

    }

    public static void main(String[] args) throws FileNotFoundException {
        tls("C:/Users/tiffa/Desktop/IFT2255/Robotix_FINAL/Implementation/Robot");
    }

}
