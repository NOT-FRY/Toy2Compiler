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
int message = 100;int glob ( );
void scoping ( int n,int m);
int glob (  ){
return message;

 }
void main (  ){
char* message = stringCopy("level 0");

int n,m;
int k = 6;
while(k >= 1){
printf("Inserisci n ");
scanf("%d",&n);

printf("Inserisci m ");
scanf("%d",&m);

printf("I valori inseriti sono %de %d\n",n,m);
scoping ( n,m );
k = k - 1;

}

printf("%s\n",message);
printf("%d\n",glob (  ) );
 }
void scoping ( int n,int m ){
char* message = stringCopy("level 1");

if ( n <= 1 ){
double message = 2.1;
if ( m <= 1 ){
char* message = stringCopy("level 3.1");

printf("%s\n",message);
 }
else if ( m > 1 && m < 5 ){
char* message = stringCopy("level 3.2");

printf("%s\n",message);
}
 else{
char* message = stringCopy("level 3.3");

printf("%s\n",message);
 }

printf("%lf\n",message);
 }
 else{
double message = 2.2;
if ( m <= 1 ){
char* message = stringCopy("level 3.4");

printf("%s\n",message);
 }
else if ( m > 1 && m < 5 ){
char* message = stringCopy("level 3.5");

printf("%s\n",message);
}
 else{
char* message = stringCopy("level 3.6");

printf("%s\n",message);
 }

printf("%lf\n",message);
 }

printf("%s\n",message);
 }
