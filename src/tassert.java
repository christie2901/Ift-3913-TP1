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

            if (!line.isBlank()){
                if (line.replace(" ","").length()>=2 && line.replace(" ","").substring(0,2).compareTo("/*")==0) {
                    // ignore les blocs de commentaires
                    line = scanner.nextLine().replace(" ","");
                    while (scanner.hasNextLine() && ( line.substring(0,1).compareTo("*") == 0 || line.substring(line.length()-2).compareTo("*/") == 0 ) ) {
                        line = scanner.nextLine().replace(" ","");
                    }
                }

                if (line.replace(" ","").length()>=2 && !(line.replace(" ","").substring(0,2).compareTo("//")==0)){
                    if (line.contains("assert") || line.contains("fail")){
                        nbAssert++;
                    }
                }
            }
        }

        return nbAssert;
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(tassert("C:/Users/tiffa/Desktop/IFT3913/TP1_Tester.txt"));
        //"C:/Users/tiffa/Desktop/IFT2255/Robotix_FINAL/Implementation/Robot/TachesTest.java"
    }

}
