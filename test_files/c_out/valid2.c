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
char* stampa ( char* stringa);
 ReturnValues* tutte_le_operazioni ( double input1,double input2);
double divisione ( double input1,double input2);
double moltiplicazione ( double input1,double input2);
void sottrazione ( double input1,double input2,double *result);
void somma ( double input1,double input2,double *result);
void main (  ){
double input1,input2,answer;
char* operazione = (char*)malloc(MAX*sizeof(char));

double res1,res2,res3,res4;
double result;
bool flag = true;
while(flag == true){
printf("Inserisci l'operazione da effettuare (somma, sottrazione, divisione, moltiplicazione, tutte_le_operazioni): ");
scanf("%s",operazione);

printf("Inserisci il primo input: ");
scanf("%lf",&input1);

printf("Inserisci il secondo input: ");
scanf("%lf",&input2);

printf("hai scelto l'operazione%scon gli argomenti%lf e %lf\n",operazione,input1,input2);
if ( strcmp(operazione,"somma")==0 ){
somma ( input1,input2,&result );
 }
else if ( strcmp(operazione,"sottrazione")==0 ){
sottrazione ( input1,input2,&result );
}
else if ( strcmp(operazione,"divisione")==0 ){
result = divisione ( input1,input2 ) ;

}
else if ( strcmp(operazione,"moltiplicazione")==0 ){
result = moltiplicazione ( input1,input2 ) ;

}
else if ( strcmp(operazione,"tutte_le_operazioni")==0 ){
 ReturnValues* result_res1 = tutte_le_operazioni ( input1,input2 ) ;
res1 = result_res1->reals[0];
res2 = result_res1->reals[1];
res3 = result_res1->reals[2];
res4 = result_res1->reals[3];
freeReturnValues(result_res1);

}
 else{
printf("Operazione non consentita\n");
 }

if ( strcmp(operazione,"tutte_le_operazioni")!=0 ){
printf("Il risultato e': %lf\n",result);
 }
 else{
printf("%s\n",stampa ( concatString(concatDouble(concatString(concatDouble(concatString(concatDouble(concatString(concatDouble("i risultati delle 4 operazioni sono \n",res1,true),"\n"),res2,true),"\n"),res3,true),"\n"),res4,true),"\n") ) );
 }

printf("Vuoi continuare? (1 yes/0 no): ");
scanf("%lf",&answer);

if ( answer == 1 ){
flag = true;

 }
 else{
flag = false;

 }

}

 }
char* stampa ( char* stringa ){
char* s = stringCopy("Ciao! ");

return concatString(s,stringa);

 }
 ReturnValues* tutte_le_operazioni ( double input1,double input2 ){
double somma_res = 0.0,sottrazione_res = 0.0;
somma ( input1,input2,&somma_res );
sottrazione ( input1,input2,&sottrazione_res );

ReturnValues* rv = createReturnValues(0,4,0,0);
rv->reals[0] = somma_res;
rv->reals[1] = sottrazione_res;
rv->reals[2] = divisione ( input1,input2 ) ;
rv->reals[3] = moltiplicazione ( input1,input2 ) ;
return rv;

 }
double divisione ( double input1,double input2 ){
double result;
if ( input2 == 0 ){
printf("Errore");
result = 0.0;

 }

result = input1 / input2;

return result;

 }
double moltiplicazione ( double input1,double input2 ){
double result;
result = input1 * input2;

return result;

 }
void sottrazione ( double input1,double input2,double *result ){
*result = input1 - input2;

 }
void somma ( double input1,double input2,double *result ){
*result = input1 + input2;

 }
