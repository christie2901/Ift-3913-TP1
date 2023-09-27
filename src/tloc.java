import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class tloc{
    //retourne le nombre de lignes de code non-vides qui ne sont pas des commentaires


    public static int lignes(String source) throws FileNotFoundException { //prend en paramètre un fichier source d'une classe de test java
        File classTest = new File(source);

        Scanner scanner = new Scanner(classTest);

        int nbLignes = 0;
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            if (!line.isBlank()){
                // ignore les lignes vides

                if (line.contains("/*")) {
                    // ignore les blocs de commentaires
                    line = scanner.nextLine();
                    while (scanner.hasNextLine() && ( line.contains("*") || line.contains("*/") ) ) {
                        line = scanner.nextLine();
                    }
                }

                if (!(line.contains("//"))){
                    // ignore les lignes de commentaires et incrémente sinon
                    nbLignes++;

                } else if (line.replace(" ","").length()==1){
                    //incrémente s'il y a un caractère }
                    nbLignes++;
                }
            }
        }

        return nbLignes;
    }
    public static void main(String[] args) throws FileNotFoundException {
        //À tester, si bloc de commentaire à la fin du fichier

        System.out.println(lignes("C:/Users/tiffa/Desktop/IFT3913/TP1_tester.txt"));
        //Robot/Taches.java doit donner 320 "C:/Users/tiffa/Desktop/IFT2255/Robotix_FINAL/Implementation/Robot/Taches.java"
        //UtilitairesFichier.java doit donner 62
        //"C:/Users/tiffa/Desktop/IFT3913/TP1_tester.txt" 22
        //String test = "     * Utilisée par les méthodes modifTaches, supprimerTaches et assignerTaches.*/";
        //System.out.println(test.substring(test.length()-2).compareTo("*/"));
    }
}
