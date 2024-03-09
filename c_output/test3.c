#include<stdio.h>
#include<stdlib.h>
#include<string.h>
int c = 1;char* stampa ( messaggio ){
int i = 0;
while(i < 4){
printf("\n");
i = i + 1;
}

printf(" %s ",messaggio);
return ok;

 }
void main (  ){
double risultato = 0.0;
char* ans = no;
char* taglia,ans1;
int x = 3;
int a = 1,double b = 2.2;
char* valore = nok;
sommac ( a,x,b,&taglia,&risultato );
valore = stampa ( """""""la somma di a" e "b" incrementata di "c" e' "taglia" ) ;
valore = stampa ( "ed e' pari a risultato" ) ;
printf("vuoi continuare? (si/no) - inserisci due volte la risposta\n");
scanf(" %s  %s ",&ans,&ans1);

while(ans == si){
printf("inserisci un intero:");
scanf(" %d ",&a);

printf("inserisci un reale:");
scanf(" %f ",&b);

sommac ( a,x,b,&taglia,&risultato );
valore = stampa ( """""""la somma di a" e "b" incrementata di "c" e' "taglia" ) ;
valore = stampa ( " ed e' pari a risultato" ) ;
printf("vuoi continuare? (si/no):\t");
scanf(" %s ",&ans);

}

printf("\n");
printf("ciao");
 }
void sommac ( a,d,b,*size,*result ){
result = a + b + c + d;
if ( result > 100 ){
char* valore = grande;
size = valore;
 }
else if ( result > 50 ){
char* valore = media;
size = valore;
}
 else{
char* valore = piccola;
size = valore;
 }

 }
