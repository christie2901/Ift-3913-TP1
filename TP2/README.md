# Ift-3913-TPS
Tiffany Chan-Tave 20185310, Christie Embeya Kongandji 20170397

Voici le lien de notre répertoire git:
https://github.com/christie2901/Ift-3913-TPS.git 

Vous trouverez dans le répertoire et le dossier .zip remis notre rapport et un programme contenant les classes suivantes:

-Main dans laquelle on calcule les métriques choisies.

-densite qui sert à calculer la densité de commentaire d'une classe. Elle compte le nombre de lignes de la classe. Elle calcule 
la différence entre le nombre de lignes et tloc et divise le tout par le nombre de lignes.

-pmnt qui sert à calculer le pourcentage de méthodes non testées d'une classe. Elle compte le nombre de méthode d'une classe
et le nombre de méthodes de sa classe test, car on présume qu'une méthode est créée dans la classe test pour tester une 
méthode de la classe cible. Elle divise la différence par le nombre de méthodes de la classe et mulitplie le tout par 100.

-compcyclo qui sert à calculer la complexité cyclomatique moyenne d'une classe. Elle additionne la complexité cyclomatique
de chaque méthode et divise le tout par le nombre de méthodes.

-tloc et tassert qui sont du code que nous avons repris de notre TP1.


Pour exécuter le programme à partir de nos dossiers dans le repo, il faut utiliser dans la ligne de commande la commande cd jusqu'à ce 
que l'on soit dans le dossier /out/artifacts/nom-programme_jar.
Pour exécuter le programme à partir du dossier remis sur  studium, il faut s'assurer que l'on soit dans ce dossier.
Il suffit d'entrer dans la ligne de commande:
java -jar 
