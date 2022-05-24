%{
    #include <string.h>
    #include <stdio.h>
    #include <stdlib.h>
    #include <stdbool.h>
    #include <ctype.h>

    extern int yylex();
    extern int yyparse();
    extern FILE* yyin;
    extern int linie;
    void yyerror();

	FILE* file;
	char *fileName;

	char importuri[100][100], declaratii[100][100], cod[1000][100]; 
	int k1=0, k2=0, k3=0;

	char readVar[100][100];
	int n=0, nr=0;

	char expresie[1000][1000];
	int m=0;

	void afiseazaImporturi();
	void afiseazaDeclaratii();
	void afiseazaCod();
	bool exista(char colectie[][100], int n, char* var);
	void proceseazaExpresie(char* el);
%}

%union
{
   char * charVal;
};

%token INCLUDE
%token CIN
%token COUT
%token MAIN
%token NAMESPACE
%token STD
%token INT
%token IOSTREAM
%token USING
%token DIGIT
%token ID
%token LBR
%token RBR
%token LPR
%token RPR
%token RS
%token LS
%token PLUS
%token MINUS
%token MUL
%token SEMICOLON
%token ASSIGN

%%     

program: header header program_main
	;

header: INCLUDE librarie
	| USING NAMESPACE STD SEMICOLON
	;

librarie: IOSTREAM
	;

program_main: INT MAIN LBR lista_instr RBR
	;

lista_instr: instr lista_instr 
	| instr 
	;

instr: INT atribuire SEMICOLON
    | atribuire SEMICOLON
	| instr_intrare
	| instr_iesire
	;

atribuire: ID ASSIGN expr
        {   
            char temp[1000];
            strcpy(temp, $<charVal>3);
            char *token;
            token = strtok(temp, " ");
            while( token != NULL )
            {
                strcpy(expresie[m++], token);
                token = strtok(NULL, " ");
            }
            proceseazaExpresie($<charVal>1);
        }
	; 

expr: 	ID
        {   
            char temp[100];
            strcpy(temp, " ");
            strcat(temp, $<charVal>1);
            if(exista(declaratii, k2, temp)==false)
                strcpy(declaratii[k2++], strcat(temp, " times 4 db 0"));
                    
        }
	| DIGIT
	| DIGIT operatie expr
        {   
           char temp[1000];
            strcpy(temp, $<charVal>1);
            strcat(temp, " ");
            strcat(temp, $<charVal>2);
            strcat(temp, " ");
            strcat(temp, $<charVal>3);
            $<charVal>$ = strdup(temp);
        }
	| ID operatie expr
        {   
            char temp[1000];
            strcpy(temp, $<charVal>1);
            strcat(temp, " ");
            strcat(temp, $<charVal>2);
            strcat(temp, " ");
            strcat(temp, $<charVal>3);
            $<charVal>$ = strdup(temp);
        }
	;   

operatie: PLUS
	| MINUS
	| MUL
	;

instr_intrare: CIN RS ID SEMICOLON
	{   
        if(exista(importuri, k1, "scanf")==false)
            strcpy(importuri[k1++], "scanf"); 
        if(exista(declaratii, k2,"format")==false)
            strcpy(declaratii[k2++], " format db \"%d\", 0");

		n=0;
        strcpy(readVar[n], "push dword ");
        strcat(readVar[n], $<charVal>3);
        strcat(readVar[n], "\n\t\tpush dword format");
        strcat(readVar[n], "\n\t\tcall [scanf]");
        strcat(readVar[n], "\n\t\tadd esp, 4 * 2\n");
        n++;

		for(int i=n-1; i>=0; i--)
         		strcpy(cod[k3++], readVar[i]);
    }     
	;

instr_iesire: COUT LS expr SEMICOLON
	{
		if(exista(importuri, k1, "printf")==false)
			strcpy(importuri[k1++], "printf");
		if(exista(declaratii, k2,"format")==false)
			strcpy(declaratii[k2++], " format db \"%d\", 0");
   
        strcpy(cod[k3], "push dword [");
        strcat(cod[k3], $<charVal>3);
        strcat(cod[k3++], "]");
        strcpy(cod[k3++], "push dword format");
        strcpy(cod[k3++], "call [printf]");
        strcpy(cod[k3++], "add esp, 4 * 2\n");
    }     
	;

                    
%%

int main(int argc, char* argv[])
{
	FILE* f = NULL;
    if ( argc > 1 )
    { 
        f = fopen( argv[1], "r" );
        fileName = argv[1];
        int i = strlen(fileName)-1;

        while(fileName[i] != '.' && i>0)
                i--;
    
        fileName[i]='\0';
        strcat(fileName, "ASM.asm");
    }
	else 
        strcpy(fileName, "output.asm");
	
	if(!f){
		perror("Could not open file! Switching to console...\n");
		yyin = stdin;
	}
    else
        yyin = f;


    strcpy(importuri[k1++], "exit"); 
     	
	while(!feof(yyin))
		yyparse();

	
	printf("Result yyparse: OK\n");
    
    	
	file = fopen(fileName,"w+");
    
    //Inceputul fisierului
    fprintf(file, "bits 32\nglobal start\n\n");

    //Importuri
    afiseazaImporturi();

    //Declaratii
    fprintf(file, "segment data use32 class=data\n");
    afiseazaDeclaratii();

    //Cod
    fprintf(file, "\nsegment code use32 class=code\n\tstart:\n");
    strcpy(cod[k3++], "push dword 0");
    strcpy(cod[k3++], "call [exit]");
    afiseazaCod();

    printf("The file correct!\n");
	return 0;
}

void yyerror() 
{
    printf("Error at line: %d ! \n", linie);
    exit(1);
}

void afiseazaImporturi()
{
    for(int i=0; i<k1; i++)
        fprintf(file, "extern %s\nimport %s msvcrt.dll\n\n", importuri[i], importuri[i]);
}

void afiseazaCod()
{
    for(int i=0; i<k3; i++)
        fprintf(file, "\t\t%s\n", cod[i]);
}

void afiseazaDeclaratii()
{
    for(int i=0; i<k2; i++)
        fprintf(file, "\t%s\n", declaratii[i]);
}

bool exista(char colectie[][100], int n, char* var)
{
    char temp[100];
    strcpy(temp, var);
    strcat(temp, " ");
    for(int i=0; i<n; i++)
        if(strstr(colectie[i], temp)!=NULL)
            return true;

    return false;
}

void proceseazaExpresie(char* el)
{
    strcpy(cod[k3], "mov AL, [");
    strcat(cod[k3], expresie[0]);
    strcat(cod[k3++], "]");

    for(int i=1; i<m-1; i+=2)
    {
        if(strcmp(expresie[i], "*") == 0)
        {
            if (isdigit(expresie[i+1][0]))
            {
                strcpy(cod[k3], "mov  DL, ");
                strcat(cod[k3++], expresie[i+1]);
                strcpy(cod[k3++], "mul  DL");
            }
	    else 
            {
                strcpy(cod[k3], "mul byte [");
                strcat(cod[k3], expresie[i+1]);
                strcat(cod[k3++], "]");
            }
        }
	    else 
            if(strcmp(expresie[i], "+")==0)
            {
                if (isdigit(expresie[i+1][0])){
                    strcpy(cod[k3], "add AL, ");
                    strcat(cod[k3++], expresie[i+1]);
                }
                else 
                {
                    strcpy(cod[k3], "add AL, byte [");
                    strcat(cod[k3], expresie[i+1]);
                    strcat(cod[k3++], "]");
                }
            }
	        else 
                if(strcmp(expresie[i], "-")==0)
                {
                    if (isdigit(expresie[i+1][0]))
                    {
                        strcpy(cod[k3], "sub AL, ");
                        strcat(cod[k3++], expresie[i+1]);
                    }
                    else 
                    {
                        strcpy(cod[k3], "sub AL, byte [");
                        strcat(cod[k3], expresie[i+1]);
                        strcat(cod[k3++], "]");
                    }
                }
    }

    strcpy(cod[k3], "mov [");
    strcat(cod[k3], el);
    strcat(cod[k3++], "], AL\n");
    m=0;
}