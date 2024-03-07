#include<stdio.h>
#include<stdlib.h>
#include<string.h>
main (  ){
double a,double b,double risultato;
int ans = 1;
 %d ansscegli un'operazione (0 o 1.Per uscire 2.Addizione 3.Sottrazione 4.Moltiplicazione 5.Divisione)
while(ans != 0 && ans != 1){
 %f ainserisci il primo numero: 
 %f binserisci il secondo numero: 
if ( ans == 2 ){ 
risultato = somma ( ab ) ;
 } 
else if ( ans == 3 ){
risultato = sottrazione ( ab ) ;
}
else if ( ans == 4 ){
divisione ( abrisultato ) 
}
else if ( ans == 5 ){
moltiplicazione ( abrisultato ) 
}
 else{
Seleziona un comando valido
 } 

 %f risultatoil risultato è¨: 
 %d ansscegli un'operazione (0 o 1 .Per uscire 2.Addizione 3.Sottrazione 4.Moltiplicazione 5.Divisione)
}

ciao
 } 
divisione ( a,b,&risultato ){
risultato = a / b;
 } 
moltiplicazione ( a,b,&risultato ){
risultato = a * b;
 } 
somma ( a,b ){
double risultato;
risultato = a + b;
return risultato;

 } 
sottrazione ( a,b ){
double risultato;
risultato = a - b;
return risultato;

 } 
