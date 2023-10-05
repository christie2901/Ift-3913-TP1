import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class tropcomp {
    public static void tropcomp(String source, String s) throws IOException {

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

        double seuil = Double.parseDouble(s);
        int nbSuspects = (int) Math.ceil(allFiles.size()*seuil/100);
        //si on a 100 fichiers alors le seuil 1% est celui le plus grand
        // si seuil 5%, les 5 plus grands

        //collecte de données
        int compte = 0;
        for (File file : allFiles){
            int nbLignes = tloc(file.getPath());
            TLOC[compte][0] = file.getPath();
            TLOC[compte][1] = String.valueOf(nbLignes);

            int nbAssert = tassert(file.getPath());
            TCOMP[compte][0] = file.getPath();
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

        String[] suspects = new String[nbSuspects];
        int a = 0;
        for (int i=0; i<nbSuspects ; i++){
            for (int j=0; j<nbSuspects ; j++){
                if (tlocSeuil[i][0].equals(tcompSeuil[j][0])){
                    suspects[a] = tlocSeuil[i][0];
                    a++;
                    break;
                }
            }
            if (a==suspects.length){
                break;
            }
        }

        //sortir dans le format tls
        ArrayList<String> formatTLS = tls(suspects);
        //produire un fichier csv
        if (formatTLS.isEmpty()){
            System.out.println("Il n'y a pas de classes tests suspectes.");
        } else {
            FileWriter fileWriter = new FileWriter(source + "\\resultats_seuil" + s + ".csv");
            for (String r : formatTLS){
                fileWriter.append(r);
                System.out.println(r);
                fileWriter.append("\n");
            }
            fileWriter.close();
            System.out.println("Chemin du fichier csv produit: " + source + "\\resultats_seuil" + s + ".csv");
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

    
    public static int tloc(String source) throws FileNotFoundException { //prend en paramètre un fichier source d'une classe de test java
        File classTest = new File(source);

        Scanner scanner = new Scanner(classTest);

        int nbLignes = 0;
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            if (!(line.compareTo("")==0)){
                // ignore les lignes vides

                if (line.contains("/*")) {
                    // ignore les blocs de commentaires
                    line = scanner.nextLine();
                    while (scanner.hasNextLine() && ( !(line.contains("*/")))) {
                        line = scanner.nextLine();
                    }
                    continue;
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

    //Modification de la fonction tls pour prendre en paramètre un tableau de chemins au lieu d'un chemin d'un dossier source
    public static ArrayList<String> tls(String[] fichiers) throws FileNotFoundException {
        ArrayList<String> resultTLS = new ArrayList<>();

        for (String fichier : fichiers){
            if (fichier==null){
                break;
            }
            File file = new File(fichier);
            String filename = file.getName();
            String chemin = file.getPath();
            //S'il y a un fichier pas .java
            if (!filename.contains(".java")){
                continue;
            }
            String nomClasse = filename.substring(0,filename.indexOf(".java"));

            //Besoin de scanner pour avoir le package
            String paquet = null;

            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                if (line.compareTo("")==0){continue;}
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

            int nblignes = tloc(chemin);
            int nbAssert = tassert(chemin);
            double tcmp;
            if (nbAssert==0) {
                tcmp = 0.0;
            } else {
                tcmp = (double) nblignes / nbAssert;
            }

            assert paquet != null;
            String pack = paquet.replace(".","/");
            String path = chemin.replace(chemin.substring(0,chemin.lastIndexOf(pack)+pack.length()),".");

            String result = path + ", " + paquet + ", " + nomClasse + ", " + nblignes + ", " + nbAssert + ", " + tcmp;

            resultTLS.add(result);

        }
        return resultTLS;
    }

    public static void main(String[] args) throws IOException {
        tropcomp(args[0],args[1]);
        //"C:\Users\tiffa\Desktop\IFT3913\jfreechart-master\jfreechart-master"
    }
}
