%{ 				
#include <math.h> 			/* -> atof()  */
%} 

%option noyywrap

DIGIT 		[1-9]*
ID 		[a-zA-Z][1-9]*
CONST		[-?DIGIT.?DIGIT*$] 

%%
 
"#include"|"cin"|"cout"|"main()"|"namespace"|"std"|"int"|"double"|"<iostream>"|"using"|"do"|"while" { printf( "A keyword: %s\n", yytext ); } 

{DIGIT} 	{printf( "CONST: %s\n", yytext ); }

"{"|"}"|"("|")" {printf( "Par: %s\n", yytext );}

">>"|"<<"|"+"|"-"|"*"|"<"|">" {printf( "An operator: %s\n", yytext ); }

"{"|"}"|";"|"="	{printf( "Other sign: %s\n", yytext ); }

" " {printf( "Whitespace\n" ); }

{ID}{1,8} 	{printf( "An ID : %s\n", yytext ); }

. {printf( "Undefined : %s\n", yytext );}

%% 
int main( argc, argv ) 
int argc; 
char **argv; 
{ 
    ++argv, --argc; /* skip over program name */ 
    if ( argc > 0 ) 
    	yyin = fopen( argv[0], "r" ); 
    else 
     	yyin = stdin; 
    yylex(); 
} 
