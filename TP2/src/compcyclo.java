import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class compcyclo {

    public static double compcyclo(File file) throws FileNotFoundException {
        //compter chaque if et boucle (while, for)

        //si or dans if, ajouter 2
        Scanner scanner = new Scanner(file);

        int nbMethods = 0;
        double somme = 0;
        String line = null;

        while(scanner.hasNextLine()){
            if (line==null){
                line = scanner.nextLine();
            }

            if (!line.contains("class")){
                if (line.contains("public")||line.contains("private")){
                    if (line.contains("{")){
                        //On est dans une methode
                        int compteur = 0;
                        line = scanner.nextLine();
                        while (scanner.hasNextLine() && !(!(line.replace(" ","").indexOf("//")==0) && (line.contains("public")|| line.contains("private")) && line.contains("{"))){
                            //condition pour detecter le commencement d'une nouvelle méthode
                            line = scanner.nextLine();

                            if (line.contains("/*")) {
                                // ignore les blocs de commentaires
                                line = scanner.nextLine();
                                while (scanner.hasNextLine() && ( !(line.contains("*/")))) {
                                    line = scanner.nextLine();
                                }
                                continue;
                            }

                            if (!(line.replace(" ","").indexOf("//")==0)){
                                if (line.contains(" if")){
                                    if (line.contains("||")){
                                        compteur += 2;
                                    } else {
                                        compteur++;
                                    }
                                } else if (line.contains(" while")||line.contains(" for")){
                                    compteur++;
                                }
                            }
                        }

                        nbMethods++;
                        somme += compteur + 1;

                    } else {
                        line = scanner.nextLine();
                    }
                } else {
                    line = scanner.nextLine();
                }
            } else {
                line = scanner.nextLine();
            }

        }

        return somme/nbMethods;


    }

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("C:\\Users\\tiffa\\Desktop\\IFT3913\\TP2\\jfreechart-1.5.4\\src\\main\\java\\org\\jfree\\chart\\editor\\DefaultChartEditor.java");
        System.out.println(compcyclo(file));
    }


}




