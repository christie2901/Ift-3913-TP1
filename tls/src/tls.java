import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class tls {

    public static void tls(String source) throws FileNotFoundException {
        for (String out : tlsInter(source)){
            System.out.println(out);
        }
    }

    public static void tls(String sortie, String source) throws IOException {
        ArrayList<String> out = tlsInter(source);

        //produire un fichier csv
        FileWriter fileWriter = new FileWriter(sortie);
        for (String r : out) {
            fileWriter.append(r);
            fileWriter.append("\n");
        }
        fileWriter.close();
    }

    public static ArrayList<String> tlsInter(String source) throws FileNotFoundException {
        File folder = new File(source);
        ArrayList<String> out = new ArrayList<>();
        
        for (File file : Objects.requireNonNull(folder.listFiles())){

            String filename = file.getName();
            String chemin = source.replace("\\","/") + "/" + filename;
            //il faut ignorer les fichiers qui ne sont pas une classe java
            if (!filename.contains(".java") || !filename.contains("Test")){
                continue;
            }
            String nomClasse = filename.substring(0,filename.indexOf(".java"));

            //Besoin de scanner pour avoir le package
            String paquet = "";

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

            String pack = paquet.replace(".","/");
            String path = chemin.replace(chemin.substring(0,chemin.lastIndexOf(pack)+pack.length()),".");

            String result = path + ", " + paquet + ", " + nomClasse + ", " + nblignes + ", " + nbAssert + ", " + String.format("%.2f",tcmp);
            //Sortir sur la ligne de commande
            out.add(result);
        }
        return out;
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

    public static void main(String[] args) throws FileNotFoundException {
        if (args.length == 1){
            tls(args[0]);
        } else {
            tls(args[0],args[1]);
        }
    }
}
