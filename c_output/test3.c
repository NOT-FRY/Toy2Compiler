#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#define MAX 512
char * stringConcat(char* str1 , char* str2) {
   char *out= malloc(strlen(str1)+strlen(str2));
   strcpy(out,str1);
   strcat(out , str2);
   return out;
}
char * stringCopy(char * string){
   char *out= malloc(strlen(string)*sizeof(char));
   strcpy(out,string);
   return out;
}
char * concatInt(char *a, int b, bool invert){
   char *n = malloc(MAX*sizeof(char));
   sprintf(n, "%d", b);
   if (invert==true)
       return stringConcat(a, n);
   else
       return stringConcat(n, a);
}
char * concatDouble(char *a, double b, bool invert){
   char *n = malloc(MAX*sizeof(char));
   sprintf(n, "%f", b);
   if (invert==true)
       return stringConcat(a, n);
   else
       return stringConcat(n, a);
}
char * concatBool(char *a, bool b, bool invert){
   char *n = malloc(MAX*sizeof(char));
   sprintf(n, "%d", b);
   if (invert==true)
       return stringConcat(a, n);
   else
       return stringConcat(n, a);
}
int c = 1;char* stampa ( messaggio ){
int i = 0;
while(i < 4){
printf("\n");
i = i + 1;
}

printf("%s",messaggio);
return "ok";

 }
void main (  ){
double risultato = 0.0;
char* ans = "no";
char* taglia,ans1;
int x = 3;
int a = 1,double b = 2.2;
char* valore = "nok";
sommac ( a,x,b,&taglia,&risultato );
valore = stampa ( stringConcat(stringConcat(concatInt(stringConcat(concatDouble(stringConcat(concatInt("la somma di ",a,true)," e "),b,true)," incrementata di "),c,true)," e' "),taglia) ) ;
valore = stampa ( concatDouble("ed e' pari a ",risultato,true) ) ;
printf("vuoi continuare? (si/no) - inserisci due volte la risposta\n");
scanf("%s%s",&ans,&ans1);

while(ans == "si"){
printf("inserisci un intero:");
scanf("%d",&a);

printf("inserisci un reale:");
scanf("%f",&b);

sommac ( a,x,b,&taglia,&risultato );
valore = stampa ( stringConcat(stringConcat(concatInt(stringConcat(concatDouble(stringConcat(concatInt("la somma di ",a,true)," e "),b,true)," incrementata di "),c,true)," e' "),taglia) ) ;
valore = stampa ( concatDouble(" ed e' pari a ",risultato,true) ) ;
printf("vuoi continuare? (si/no):\t");
scanf("%s",&ans);

}

printf("\n");
printf("ciao");
 }
void sommac ( a,d,b,*size,*result ){
result = a + b + c + d;
if ( result > 100 ){
char* valore = "grande";
size = valore;
 }
else if ( result > 50 ){
char* valore = "media";
size = valore;
}
 else{
char* valore = "piccola";
size = valore;
 }

 }
