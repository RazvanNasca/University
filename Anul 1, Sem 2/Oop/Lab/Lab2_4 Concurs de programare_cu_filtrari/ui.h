#pragma once

#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>

#include "controller.h"

#define CMD_LEN 255


Concurs participanti;


void ui_help();


void ui_afiseaza(DynamicArray* arr);


void ui_adauga(char** argv, int argc);


void ui_actualizeaza(char** argv, int argc);


void ui_sterge(char** argv, int argc);


void ui_filtreaza(char** argv, int argc);


void ui_ordoneaza(char** argv, int argc);


int parseCommand(char* cmd);


void run();
