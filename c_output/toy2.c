#include<stdio.h>
#include<stdlib.h>
#include<string.h>
void main (  ){
double a,b,risultato;
int ans = 1;
scegli un'operazione (0 o 1.Per uscire 2.Addizione 3.Sottrazione 4.Moltiplicazione 5.Divisione)ans
while(ans != 0 && ans != 1){
inserisci il primo numero: a
inserisci il secondo numero: b
if ( ans == 2 ){ 
risultato = somma ( a,b ) ;
 } 
else if ( ans == 3 ){
risultato = sottrazione ( a,b ) ;
}
else if ( ans == 4 ){
divisione ( a,b,risultato ) 
}
else if ( ans == 5 ){
moltiplicazione ( a,b,risultato ) 
}
 else{
printf("Seleziona un comando valido\n");
 } 

printf("il risultato è:  %f \n",risultato);
printf("Inserisci un numero intero %d ed un numero reale %d ",ans,ans);
scegli un'operazione (0 o 1 .Per uscire 2.Addizione 3.Sottrazione 4.Moltiplicazione 5.Divisione)ans
}

printf("ciao\n");
 } 
void divisione ( a,b,&risultato ){
char* c;
risultato = a / b;
c = "ciao1";
 } 
void moltiplicazione ( a,b,&risultato ){
risultato = a * b;
 } 
double somma ( a,b ){
double risultato;
risultato = a + b;
return risultato;

 } 
double sottrazione ( a,b ){
double risultato;
risultato = a - b;
return risultato;

 } 
