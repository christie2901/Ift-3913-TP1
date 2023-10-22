import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Main {
    static String source = "C:\\Users\\tiffa\\Desktop\\IFT3913\\TP2\\jfreechart-1.5.4";
    static String sourceMain = source.replace("\\","/") + "/src/main/java";
    static String sourceTest = source.replace("\\","/") + "/src/test/java";

    static File folderMain = new File(sourceMain);
    static File folderTest = new File(sourceTest);

    static ArrayList<File> mainFiles = getFiles(folderMain);
    static ArrayList<File> testFiles = getFiles(folderTest);


    public static double tpc() throws FileNotFoundException {
        //TPC (tests par classe)
        //Retourne la moyenne des tests par classe de toutes les classes tests

        double nbTest = 0;
        for (File file : testFiles){
            nbTest += tassert.tassert(file);
        }

        return nbTest/testFiles.size();

    }

    public static long age(){
        //Retourne la moyenne des différences d'âge des classes dans le main avec leur classe test
        long differences = 0;
        int nbClass = 0;
        for (File file : testFiles){

            long dateTest = TimeUnit.MILLISECONDS.toDays(file.lastModified());
            long dateMain = 0;

            if (file.getName().contains("Test")){
                String name = file.getName().substring(0,file.getName().indexOf("Test")) + ".java";
                for (File fichier : mainFiles){
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
        double sommeCode = 0;
        double sommeTest = 0;
        int nbClass = 0;

        for (File file : testFiles) {
            sommeTest += tloc.tloc(String.valueOf(file));
        }

        for (File file : mainFiles) {
            sommeCode += tloc.tloc(String.valueOf(file));
        }

        return sommeCode/sommeTest;
    }

    public static double complexiteCyclo() throws FileNotFoundException {
        //Retourne la moyenne des complexités cyclomatiques de toutes les classes tests
        double complexite = 0;
        for (File file : testFiles){
            complexite += compcyclo.compcyclo(file);
        }

        return complexite/testFiles.size();
    }

    public static double densite() throws FileNotFoundException {
        double dense = 0;
        for (File file : testFiles){
            dense += densite.densiteComment(file);
        }

        return dense/testFiles.size();
    }

    //Ratio complexité/densité
    public static double compDense() throws FileNotFoundException {
        double ratio = 0;
        for (File file: testFiles){
            ratio += compcyclo.compcyclo(file)/densite.densiteComment(file);
        }
        return ratio/testFiles.size();
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
        System.out.println(densite());
        System.out.println(compDense());
    }

}
