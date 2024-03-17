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
 ReturnValues* numero_positivo ( int a);
 ReturnValues* f ( );
void main (  ){
double a,n;
int negativo;
int c;
bool d;
 ReturnValues* result_a = f (  ) ;
a = result_a->reals[0];
n = result_a->reals[1];
freeReturnValues(result_a);

printf("a:%lfn:%lf\n",a,n);
negativo =  -2;

 ReturnValues* result_c = numero_positivo ( negativo ) ;
c = result_c->integers[0];
d = result_c->booleans[0];
freeReturnValues(result_c);

printf("c:%dd:%d\n",c,d);
 }
 ReturnValues* numero_positivo ( int a ){
bool b;
if ( a > 0 ){
b = true;

 }
 else{
b = false;

 }


ReturnValues* rv = createReturnValues(1,0,0,1);
rv->integers[0] = a;
rv->booleans[0] = b;
return rv;

 }
 ReturnValues* f (  ){
double a = 5.15;
double b = 6.66;

ReturnValues* rv = createReturnValues(0,2,0,0);
rv->reals[0] = a;
rv->reals[1] = b;
return rv;

 }
