#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include <stdbool.h>
#define MAX 512
typedef struct
{
    int* integers;
    double* reals;
    char** strings;
    bool* booleans;
    int int_count;
       int real_count;
      int string_count;
    int boolean_count;
}ReturnValues;
ReturnValues* createReturnValues(int int_count, int real_count, int string_count,int boolean_count) {
    ReturnValues* rv = (ReturnValues*)malloc(sizeof(ReturnValues));
    if (rv == NULL) {
        printf("Errore di allocazione della memoria.\n");
        exit(1);
    }
    rv->integers = (int*)malloc(int_count * sizeof(int));
    rv->reals = (double*)malloc(real_count * sizeof(double));
    rv->strings = (char**)malloc(string_count * sizeof(char*));
    rv->booleans = (bool*)malloc(boolean_count * sizeof(bool));
    for (int i = 0; i < string_count; i++) {
        rv->strings[i] = NULL;
    }
    rv->int_count = int_count;
    rv->real_count = real_count;
    rv->string_count = string_count;
    rv->boolean_count = boolean_count;
    return rv;
}
void freeReturnValues(ReturnValues* rv) {
    if (rv != NULL) {
        free(rv->integers);
        free(rv->reals);
        free(rv->booleans);
        for (int i = 0; i < rv->string_count; i++) {
            free(rv->strings[i]);
        }
        free(rv->strings);
        free(rv);
    }
}
char * concatString(char* str1 , char* str2) {
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
   if (invert)
       return concatString(a, n);
   else
       return concatString(n, a);
}
char * concatDouble(char *a, double b, bool invert){
   char *n = malloc(MAX*sizeof(char));
   sprintf(n, "%f", b);
   if (invert)
       return concatString(a, n);
   else
       return concatString(n, a);
}
char * concatBool(char *a, bool b, bool invert){
   char *n = malloc(MAX*sizeof(char));
   sprintf(n, "%d", b);
   if (invert)
       return concatString(a, n);
   else
       return concatString(n, a);
}
int c = 1;char* stampa ( char* messaggio);
void sommac ( int a,int d,double b,char* *size,double *result);
char* stampa ( char* messaggio ){
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
int a = 1;
double b = 2.2;
char* valore = "nok";
sommac ( a,x,b,&taglia,&risultato );
valore = stampa ( concatString(concatString(concatInt(concatString(concatDouble(concatString(concatInt("la somma di ",a,true)," e "),b,true)," incrementata di "),c,true)," e' "),taglia) ) ;

valore = stampa ( concatDouble("ed e' pari a ",risultato,true) ) ;

printf("vuoi continuare? (si/no) - inserisci due volte la risposta\n");
scanf("%s%s",&ans,&ans1);

while(strcmp(ans,"si")==0){
printf("inserisci un intero:");
scanf("%d",&a);

printf("inserisci un reale:");
scanf("%f",&b);

sommac ( a,x,b,&taglia,&risultato );
valore = stampa ( concatString(concatString(concatInt(concatString(concatDouble(concatString(concatInt("la somma di ",a,true)," e "),b,true)," incrementata di "),c,true)," e' "),taglia) ) ;

valore = stampa ( concatDouble(" ed e' pari a ",risultato,true) ) ;

printf("vuoi continuare? (si/no):\t");
scanf("%s",&ans);

}

if ( strcmp("ciao","ciao")==0 ){
return "ciao";

 }

printf("\n");
printf("ciao");
 }
void sommac ( int a,int d,double b,char* *size,double *result ){
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
