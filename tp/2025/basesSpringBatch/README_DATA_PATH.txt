NB: lorsqu'une application java est lancée depuis l'IDE (ou bien en ligne de commande depuis la racine du projet maven),
le chemin relatif "data/..." sera interprété comme en relatif vis à vis de la racine de ce projet
-------------
lorsque le process "appliBatchJava" est lancé depuis une autre application
(ex: myBatchAdmin ou autre) , alors le chemin relatif "data/..." sera interprété comme en relatif vis à vis de la racine de cet autre
 projet lanceur (ex: myBatchAdmin)