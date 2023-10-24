import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Main {
    static String path = String.valueOf(Paths.get("").toAbsolutePath());

    // "IFT3913_TP2" est le nom du projet
    //Il faut avoir téléchargé la dernière version stable de jfreechart, https://github.com/jfree/jfreechart/tree/v1.5.4
    static String source = path.substring(0,path.indexOf("IFT3913_TP2")) + "jfreechart-1.5.4";
    
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

    public static double seuilTPC() throws FileNotFoundException {
        double nbMethod = 0;
        for (File file: mainFiles){
            nbMethod += pmnt.getMethods(file);
        }
        double nbTest = 0;
        for (File file : testFiles){
            nbTest += (double) tassert.tassert(file)/pmnt.getMethods(file);
        }

        return nbMethod/mainFiles.size()*nbTest/testFiles.size();
    }
    

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Seuil de TPC: " + seuilTPC());
        System.out.println("Moyenne des tests par classe: " + tpc());
        System.out.println("Moyenne des différences d'age: " + age());
        System.out.println("Ratio taille code/taille test: " + ratio());
        System.out.println("Moyenne des complexités cyclomatiques: " + complexiteCyclo());
        System.out.println("Pourcentage de méthodes non testés: " + pmnt.pmnt(source));
        System.out.println("Moyenne des densités de commentaire: " + densite());
        System.out.println("Moyenne du ratio complexité/densité de commentaire: " + compDense());
    }

}
