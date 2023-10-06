# Ift-3913-TP1
# Tiffany Chan-Tave et Christie Embeya Kongandji
https://github.com/christie2901/Ift-3913-TP1.git <br><br>

Vous trouverez dans le repositoire et le dossier .zip remis 4 programmes:<br>
-tloc qui calcule le nombre de lignes de code non-vides qui ne sont pas des commentaires.<br>
Ce programme prend en paramètre le chemin vers un fichier source d'une classe test java et retourne son résultat à la ligne de commande.
<br><br>
-tassert qui calcule le nombre d'assertions JUnit.<br>
Ce programme prend en paramètre le chemin vers un fichier source d'une classe test java et retourne son résultat à la ligne de commande.
<br><br>
-tls qui prend en entrée le chemin d'accès d'un dossier et optionnellement le chemin à la sortie voulu qui contient du code test java organisé <br>
en paquets et produit en sortie en format CSV une ligne pour chaque fichier comprenant en colonne le chemin du fichier, le nom du paquet, <br>
le nom de la classe, le tloc de la classe, le tassert de la classe et le tcmp de la classe (tloc/tassert).
<br><br>
-tropcomp qui prend en entré le chemin principal d'un projet java, un seuil et optionnellement le chemin à la sortie voulu pour suggérer des classes qui sont suspectes de contenir du code<br>
 test qui est trop complexe. Il produit sa sortie en format CSV comme tls.

<br><br>
Pour exécuter nos programmes à partir de nos dossiers dans le repo, il faut utiliser dans la ligne de commande la commande cd jusqu'à ce<br> que l'on soit dans le dossier /out/artifacts/nom-programme_jar. <br>
Pour exécuter nos programmes à partir du dossier remis sur  studium, il faut s'assurer que l'on soit dans ce dossier.
<br><br>
Pour tloc et tassert, il suffit d'entrer dans la ligne de commande: <br>
java -jar programme.jar chemin
<br><br>
Où chemin est un chemin d'un fichier source d'une classe de test java pour tloc et tassert.<br>
Pour tls, chemin est un chemin d'accès d'un dossier qui contient du code test java organisé 
en paquets.<br>
Les résultats seront affichés directement dans la console.
<br><br>
Pour tls, il suffit d'entrer dans la ligne de commande: <br>
java -jar tls.jar chemin
<br>Le résultat sera affiché directement dans la console.
<br><br>OU<br><br>
java -jar tls.jar sortie chemin
<br>
Où sortie est le chemin voulu pour le fichier csv produit et chemin est un chemin principal d’un projet java.
<br><br>
Pour tropcomp, il suffit d'entrer dans la ligne de commande: <br>
java -jar tropcomp.jar chemin seuil
<br>Le résultat sera affiché directement dans la console.
<br><br>OU<br><br>
java -jar tropcomp.jar sortie chemin seuil
<br>
Où sortie est le chemin voulu pour le fichier csv produit, chemin est un chemin principal d’un projet java et seuil est le pourcentage à observer pour déterminer les classes suspectes.
<br>
