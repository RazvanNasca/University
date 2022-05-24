%{ 				
#include <math.h>
#include "lab4.tab.h"
int linie = 1; 			
%} 

%option noyywrap

DIGIT 		[1-9]*
ID 		[a-zA-Z][1-9]*
CONST		[-?DIGIT.?DIGIT*$] 

%%
 
"#include" {return INCLUDE;}
"cin" {return CIN;}
"cout" {return COUT;}
"main()" {return MAIN;}
"namespace" {return NAMESPACE;}
"std" {return STD;}
"int" {return INT;}
"double" {return DOUBLE;}
"<iostream>" {return IOSTREAM;}
"<cmath>" {return CMATH;}
"using" {return USING;}
"do" {return DO;}
"while" {return WHILE;}
"repeta" {return REPETA;}
"panacand" {return PANACAND;}
"sfrepeta" {return SFREPETA;} 

{DIGIT} {return DIGIT;}
{ID}{1,8} {return ID;}

"{" {return LBR;}
"}" {return RBR;}
"(" {return LPR;}
")" {return RPR;}
">>" {return RS;}
"<<" {return LS;}
"<" {return LT;}
">" {return GT;}
"+" {return PLUS;}
"-" {return MINUS;}
"*" {return MUL;}
";" {return SEMICOLON;}
"=" {return ASSIGN;}
"." {return DOT;}

[\n] {linie++;}
%% 