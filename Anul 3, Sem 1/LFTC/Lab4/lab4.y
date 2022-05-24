%{
    #include <string.h>
    #include <stdio.h>
    #include <stdlib.h>

    extern int yylex();
    extern int yyparse();
    extern FILE* yyin;
    extern int linie;
    void yyerror();
%}

%token INCLUDE
%token CIN
%token COUT
%token MAIN
%token NAMESPACE
%token STD
%token INT
%token DOUBLE
%token IOSTREAM
%token CMATH
%token USING
%token DO
%token WHILE
%token DIGIT
%token ID
%token LBR
%token RBR
%token LPR
%token RPR
%token RS
%token LS
%token LT
%token GT
%token PLUS
%token MINUS
%token MUL
%token SEMICOLON
%token ASSIGN
%token DOT
%token REPETA
%token PANACAND
%token SFREPETA
%%     

program: header header program_main
	;

header: INCLUDE librarie
	| USING NAMESPACE STD SEMICOLON
	;

librarie: IOSTREAM 
	| CMATH
	;

program_main: INT MAIN LBR lista_instr RBR
	;

lista_instr: instr lista_instr 
	| instr 
	;

instr: atribuire SEMICOLON
	| instr_do_while 
	| instr_intrare 
	| instr_iesire 
	| instr_myrepeta
	;

atribuire: tip_data ID ASSIGN expr 
	| tip_data ID
	| ID ASSIGN expr 
	; 

tip_data: INT 
	| DOUBLE
	;

expr: 	ID
	| DIGIT
	| DIGIT operatie expr
	| ID operatie expr
	;   

operatie: PLUS
	| MINUS
	| MUL
	;

cond: ID bool DIGIT
	| ID bool ID
	;

bool: LT
	| GT
	;

instr_intrare: CIN RS ID SEMICOLON 
	;

instr_iesire: COUT LS expr SEMICOLON 
	;

instr_do_while: DO LBR instr RBR WHILE LPR cond RPR SEMICOLON
	;

instr_myrepeta: REPETA LBR instr RBR PANACAND LPR ID RPR SFREPETA
	;

                    
%%

int main(int argc, char* argv[]) {
    ++argv, --argc;
    
    // sets the input for flex file
    if (argc > 0) 
        yyin = fopen(argv[0], "r"); 
    else 
        yyin = stdin; 
    
    //read each line from the input file and process it
    while (!feof(yyin)) {
        yyparse();
    }
    printf("The file correct!\n");
    return 0;
}

void yyerror() {
    printf("Error at line: %d ! \n", linie);
    exit(1);
}