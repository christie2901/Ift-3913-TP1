import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;


public class pmnt {

    public static double pmnt(String source) throws FileNotFoundException {
        //source serait tout le projet

        //il faut obtenir le nombre de méthodes des classes de code
        //et il faut obtenir le nombre de méthodes des classes de tests

        //on présume que pour tester une méthode de la classe cible, on crée une méthode dans la classe test


        String sourceMain = source.replace("\\","/") + "/src/main/java";
        String sourceTest = source.replace("\\","/") + "/src/test/java";

        File folderMain = new File(sourceMain);
        File folderTest = new File(sourceTest);

        ArrayList<File> classMain = getFiles(folderMain);
        ArrayList<File> classTest = getFiles(folderTest);


        double nbMethod = 0;
        double nbMethodTest = 0;
        for (File file : classTest) {

            nbMethodTest += getMethods(file);
        }

        for (File fileMain : classMain) {
            nbMethod += getMethods(fileMain);
        }

        //Retourne le pourcentage de méthodes non testées pour tout le projet
        return (nbMethod - nbMethodTest)/nbMethod * 100;
    }

    public static int getMethods(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);

        int nbMethods = 0;
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();

            if (line.contains("public")||line.contains("private")){
                if (line.contains("{")){
                    nbMethods++;
                }
            }
        }
        return nbMethods;
    }


    //code provenant de notre TP1
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

}
