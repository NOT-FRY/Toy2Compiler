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
   char *out= malloc(MAX*sizeof(char));
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
double sottrazione ( double n1,double n2);
double divisione ( double n1,double n2);
double moltiplicazione ( double n1,double n2);
double somma ( double n1,double n2);
void main (  ){
bool condizione2 = true;
bool condizione1 = true;
double n1,n2;
int scelta = 2;
while(condizione1 && condizione2){
printf("Inserisci 2 per eseguire la somma");
printf("\n");
printf("Inserisci 3 per eseguire la moltiplicazione");
printf("\n");
printf("Inserisci 4 per eseguire la divisione");
printf("\n");
printf("Inserisci 5 per eseguire la sottrazione");
printf("\n");
printf("Inserisci 1 o 0 per chiudere il programma");
printf("Inserisci un numero : \t");
scanf("%d",&scelta);

if ( scelta == 2 ){
printf("Inserisci primo operando : \t");
scanf("%lf",&n1);

printf("Inserisci secondo operando : \t");
scanf("%lf",&n2);

printf("\n");
printf("Il risultato � : \t%lf",somma ( n1,n2 ) );
 }
else if ( scelta == 3 ){
printf("Inserisci primo operando : \t");
scanf("%lf",&n1);

printf("Inserisci secondo operando : \t");
scanf("%lf",&n2);

printf("\n");
printf("Il risultato � : \t%lf",moltiplicazione ( n1,n2 ) );
}
else if ( scelta == 4 ){
printf("Inserisci primo operando : \t");
scanf("%lf",&n1);

printf("Inserisci secondo operando : \t");
scanf("%lf",&n2);

printf("\n");
printf("Il risultato � : \t%lf",divisione ( n1,n2 ) );
}
else if ( scelta == 5 ){
printf("Inserisci primo operando : \t");
scanf("%lf",&n1);

printf("Inserisci secondo operando : \t");
scanf("%lf",&n2);

printf("\n");
printf("Il risultato � : \t%lf",sottrazione ( n1,n2 ) );
}
 else{
condizione1 = false;

 }

printf("\n");
printf("\n");
}

 }
double sottrazione ( double n1,double n2 ){
double risultato;
risultato = n1 - n2;

return risultato;

 }
double divisione ( double n1,double n2 ){
double risultato;
risultato = n1 / n2;

return risultato;

 }
double moltiplicazione ( double n1,double n2 ){
double risultato;
risultato = n1 * n2;

return risultato;

 }
double somma ( double n1,double n2 ){
double risultato;
risultato = n1 + n2;

return risultato;

 }
