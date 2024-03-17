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
int a = 2;int b = 4; ReturnValues* raddoppia ( bool raddoppia);
void main (  ){
char* ans = (char*)malloc(MAX*sizeof(char));

printf("Vuoi raddoppiare? (si/no)");
scanf("%s",ans);

if ( strcmp(ans,"si")==0 ){
 ReturnValues* result_a = raddoppia ( true ) ;
a = result_a->integers[0];
b = result_a->integers[1];
freeReturnValues(result_a);

printf("hai scelto di raddoppiare: a=%d,b=%d\n",a,b);
 }
 else{
printf("hai scelto di non raddoppiare: a=%d,b=%d\n",a,b);
 }

 }
 ReturnValues* raddoppia ( bool raddoppia ){
if ( raddoppia == true ){

ReturnValues* rv = createReturnValues(2,0,0,0);
rv->integers[0] = a * 2;
rv->integers[1] = b * 2;
return rv;

 }
 else{

ReturnValues* rv = createReturnValues(2,0,0,0);
rv->integers[0] = a;
rv->integers[1] = b;
return rv;

 }


ReturnValues* rv = createReturnValues(2,0,0,0);
rv->integers[0] = 0;
rv->integers[1] = 0;
return rv;

 }
