import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;

public class tropcomp {

    //seuil int ou double?
    public static void tropcomp(String source, double seuil) throws FileNotFoundException {

        //D'abord il faut aller au path des classes tests, configuration Maven
        // mon_projet/src/test : contient toutes les données nécessaire pour tester l'application
        //mon_projet/src/test/java : contient les codes sources des tests
        //mon_projet/src/test/resources : les ressources qui sont utilisées dans les tests

         //dans mon_projet/src/test/java il peut y avoir des dossiers contenant des classes test, des classes test et d'autres fichiers qui ne sont pas des .java
        String sourceTest = source + "/src/test/java";


        File folder = new File(sourceTest);

        ArrayList<File> allFiles = getFiles(folder);

        //trouver les fichiers ayant le tloc dans le <seuil>% de tous les fichiers
        //trouver les fichiers ayant le tcomp dans le <seuil>% de tous les fichiers
        //si un fichier est présent dans les deux listes, alors il est suspect
        String[][] TLOC = new String[allFiles.size()][2];
        String [][] TCOMP = new String[allFiles.size()][2];

        int nbSuspects = (int) Math.ceil(allFiles.size()*seuil/100);
        //si on a 100 fichiers alors le seuil 1% est celui le plus grand
        // si seuil 5%, les 5 plus grands

        //collecte de données
        int compte = 0;
        for (File file : allFiles){
            int nbLignes = tloc.lignes(file.getPath());
            TLOC[compte][0] = file.getName();
            TLOC[compte][1] = String.valueOf(nbLignes);

            int nbAssert = tassert.tassert(file.getPath());
            TCOMP[compte][0] = file.getName();
            if (nbAssert==0){
                TCOMP[compte][1] = String.valueOf(0);
            } else {
                TCOMP[compte][1] = String.valueOf(nbLignes/nbAssert);
            }

            compte++;
        }

        TLOC = sorted(TLOC);
        TCOMP = sorted(TCOMP);

        String[][] tlocSeuil = new String[nbSuspects][2];
        String[][] tcompSeuil = new String[nbSuspects][2];

        //Déterminer les fichiers dans le top <seuil>%
        for (int i=0; i<nbSuspects ; i++){
            tlocSeuil[i] = TLOC[i];
            tcompSeuil[i] = TCOMP[i];
        }

        String[][] suspects = new String[nbSuspects][2];
        int a = 0;
        for (int i=0; i<nbSuspects ; i++){
            for (int j=0; j<nbSuspects ; j++){
                //tlocSeuil et tcompSeuil n'ont aucune classe en commun, bizarre ...
                if (tlocSeuil[i][0].equals(tcompSeuil[j][0])){
                    suspects[a] = tlocSeuil[i];
                    a++;
                    break;
                }
            }
            if (a==suspects.length){
                break;
            }
        }

        for (String[] s : suspects){
            System.out.println(s[0]);
        }

    }

    public static ArrayList<File> getFiles(File folder){

        int nbFiles = Objects.requireNonNull(folder.listFiles()).length;
        ArrayList<File> allFiles = new ArrayList<>();

        if (nbFiles>0){
            //dossier ou classe test?
            for (File file : Objects.requireNonNull(folder.listFiles())){
                if (file.listFiles() == null){
                    //fichier
                    if (file.getName().contains(".java")){
                        allFiles.add(file);
                    }
                } else {
                    //dossier
                    allFiles.addAll(getFiles(file));
                }
            }
        }
        return allFiles;
    }

    public static String[][] sorted(String[][] list){
        String[][] result = new String[list.length][2];
        //on compare les valeurs element[i][1] et on veut sauvegarder les noms des classes element[i][0]
        ArrayList<String[]> pool = new ArrayList<>();
        for (String[] l : list){
            pool.add(new String[] {l[0],l[1]});
        }

        int compte = 0;
        while(!pool.isEmpty()){
            String[] element = null;
            String filename = null;
            String comp = "0";
            for (String[] strings : pool) {
                if (Integer.parseInt(strings[1]) >= Integer.parseInt(comp)) {
                    element = strings;
                    filename = strings[0];
                    comp = strings[1];
                }
            }
            result[compte][0] = filename;
            result[compte][1] = comp;
            compte++;
            pool.remove(element);
        }
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        tropcomp("C:\\Users\\tiffa\\Desktop\\IFT3913\\jfreechart-master\\jfreechart-master",1);

    }
}
