import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Main {
    static String source = "C:\\Users\\tiffa\\Desktop\\IFT3913\\TP2\\jfreechart-1.5.4";
    static String sourceMain = source.replace("\\","/") + "/src/main/java";
    static String sourceTest = source.replace("\\","/") + "/src/test/java";

    public static double tpc() throws FileNotFoundException {
        //TPC (tests par classe)
        //Retourne la moyenne des tests par classe de toutes les classes tests

        File folder = new File(sourceTest);

        ArrayList<File> allFiles = getFiles(folder);
        double nbTest = 0;
        for (File file : allFiles){
            nbTest += tassert.tassert(file);
        }

        return nbTest/allFiles.size();

    }

    public static long age(){
        //Retourne la moyenne des différences d'âge des classes dans le main avec leur classe test
        File folderMain = new File(sourceMain);
        File folderTest = new File(sourceTest);

        ArrayList<File> classMain = getFiles(folderMain);
        ArrayList<File> classTest = getFiles(folderTest);

        long differences = 0;
        int nbClass = 0;
        for (File file : classTest){

            long dateTest = TimeUnit.MILLISECONDS.toDays(file.lastModified());
            long dateMain = 0;

            if (file.getName().contains("Test")){
                String name = file.getName().substring(0,file.getName().indexOf("Test")) + ".java";
                for (File fichier : classMain){
                    if (fichier.getName().compareTo(name)==0){
                        dateMain = TimeUnit.MILLISECONDS.toDays(fichier.lastModified());
                        break;
                    }
                }

                differences += (dateMain - dateTest);
                nbClass++;

            }
        }

        return differences/nbClass;
    }

    public static double ratio() throws FileNotFoundException {
        //Moyenne de tous les ratios taille code/taille test
        File folderMain = new File(sourceMain);
        File folderTest = new File(sourceTest);

        ArrayList<File> classMain = getFiles(folderMain);
        ArrayList<File> classTest = getFiles(folderTest);

        double sommeCode = 0;
        double sommeTest = 0;
        int nbClass = 0;

        for (File file : classTest) {
            sommeTest += tloc.tloc(String.valueOf(file));
        }

        for (File file : classMain) {
            sommeCode += tloc.tloc(String.valueOf(file));
        }

//        for (File file : classTest){
//
//            double code = tloc.tloc(String.valueOf(file));
//            double test = 0;
//
//            if (file.getName().contains("Test")){
//                String name = file.getName().substring(0,file.getName().indexOf("Test")) + ".java";
//                for (File fichier : classMain){
//                    if (fichier.getName().compareTo(name)==0){
//                        test = tloc.tloc(String.valueOf(fichier));
//                        break;
//                    }
//                }
//

//                if (test!=0){
//                    somme += (code/test);
//                    nbClass++;
//                }

//
//            }
//        }

        return sommeCode/sommeTest;
    }

    public static double complexiteCyclo() throws FileNotFoundException {
        //Retourne la moyenne des complexités cyclomatiques de toutes les classes tests
        File folder = new File(sourceTest);

        ArrayList<File> allFiles = getFiles(folder);

        double complexite = 0;
        for (File file : allFiles){
            complexite += compcyclo.compcyclo(file);
        }

        return complexite/allFiles.size();

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

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(tpc());
        System.out.println(age());
        System.out.println(ratio());
        System.out.println(complexiteCyclo());
        System.out.println(pmnt.pmnt(source));
    }

}