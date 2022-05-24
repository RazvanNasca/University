bits 32
global start

extern exit
import exit msvcrt.dll

extern scanf
import scanf msvcrt.dll

extern printf
import printf msvcrt.dll

segment data use32 class=data
	 format db "%d", 0
	 a times 4 db 0

segment code use32 class=code
	start:
		push dword a
		push dword format
		call [scanf]
		add esp, 4 * 2

		mov AL, [a]
		sub AL, 2
		mov [a], AL

		push dword [a]
		push dword format
		call [printf]
		add esp, 4 * 2

		push dword 0
		call [exit]
