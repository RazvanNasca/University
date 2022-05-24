%{ 				
#include <math.h>
#include <string.h>
#include "lab4.tab.h"
#define YY_DECL int yylex()

int linie = 1; 			
%} 

%option noyywrap

DIGIT 		[1-9]*
ID 		[a-zA-Z][1-9]*

%%
 
"#include" {return INCLUDE;}
"cin" {yylval.charVal = strdup(yytext); return CIN;}
"cout" {yylval.charVal = strdup(yytext); return COUT;}
"main()" {return MAIN;}
"namespace" {return NAMESPACE;}
"std" {return STD;}
"int" {return INT;}
"<iostream>" {return IOSTREAM;}
"using" {return USING;}

{DIGIT} {yylval.charVal = strdup(yytext); return DIGIT;}
{ID}{1,8} {yylval.charVal = strdup(yytext); return ID;}

"{" {return LBR;}
"}" {return RBR;}
"(" {return LPR;}
")" {return RPR;}
">>" {return RS;}
"<<" {return LS;}
"+" {yylval.charVal = strdup(yytext); return PLUS;}
"-" {yylval.charVal = strdup(yytext); return MINUS;}
"*" {yylval.charVal = strdup(yytext); return MUL;}
";" {return SEMICOLON;}
"=" {return ASSIGN;}

[\n] {linie++;}
%% 