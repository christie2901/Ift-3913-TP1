import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;

public class tropcomp {

    //seuil int ou double?
    public static void tropcomp(String source, double seuil) throws FileNotFoundException {

        //D'abord il faut aller au path des classes tests, configuration Maven
        // mon_projet/src/test : contient toutes les données nécessaire pour tester l'application
        //mon_projet/src/test/java : contient les codes sources des tests
        //mon_projet/src/test/resources : les ressources qui sont utilisées dans les tests

        File folder = new File(source);

        //trouver les fichiers ayant le tloc dans le <seuil>% de tous les fichiers
        //trouver les fichiers ayant le tcomp dans le <seuil>% de tous les fichiers
        //si un fichier est présent dans les deux listes, alors il est suspect
        String[][] TLOC = new String[Objects.requireNonNull(folder.listFiles()).length][2];
        String [][] TCOMP = new String[Objects.requireNonNull(folder.listFiles()).length][2];

        int nbSuspects = (int) Math.ceil(Objects.requireNonNull(folder.listFiles()).length*seuil/100);
        //si on a 100 fichiers alors le seuil 1% est celui le plus grand
        // si seuil 5%, les 5 plus grands

        //collecte de données
        int compte = 0;
        for (File file : Objects.requireNonNull(folder.listFiles())){
            int nbLignes = tloc.lignes(file.getPath());
            TLOC[compte][0] = file.getName();
            TLOC[compte][1] = String.valueOf(nbLignes);

            int nbAssert = tassert.tassert(file.getPath());
            TCOMP[compte][0] = file.getName();
            TCOMP[compte][1] = String.valueOf(nbLignes/nbAssert);

            compte++;
        }

        TLOC = sort(TLOC);
        TCOMP = sort(TCOMP);


    }

    public static String[][] sort(String[][] list){
        String[][] result = new String[list.length][list[0].length];
        String filename = list[0][0];
        String comp = list[0][1];

        int compte = 0;

        while (compte < list.length){

            for (int i=0; i<list.length; i++){
                boolean present = false;
                filename = list[i][0];
                for (String[] r : result){
                    if (r[0].equals(filename)){
                        present = true;
                        break;
                    }
                }
                if (!present && Integer.parseInt(list[i][1]) > Integer.parseInt(comp)){
                    comp = list[i][1];
                }
            }

            result[compte][0] = filename;
            result[compte][1] = comp;

            compte++;

        }

        return result;
    }


    public static void main(String[] args) throws FileNotFoundException {
        //tropcomp("C:/Users/tiffa/Desktop/IFT2255/Robotix_FINAL/Implementation/Robot",1.0);
        //tropcomp("C:/Users/tiffa/Desktop/IFT2255/Robotix_FINAL/Implementation/Robot",5.0);
        //tropcomp("C:/Users/tiffa/Desktop/IFT2255/Robotix_FINAL/Implementation/Robot",10.0);

    }
}
