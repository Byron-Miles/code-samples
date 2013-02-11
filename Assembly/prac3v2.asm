;  Filename: prac2.asm
;    Author: Byron Miles
;Student No: 220057347
;     Email: bmiles3@une.edu.au
;Assignment: 2010s1 COMP283 Prac 2

;To compile and run on turing:
; $ yasm -f elf -m amd64 -o prac2.o prac2.asm
; $ ld -o prac2 prac2.o
; $ ./prac2

;Description:
;Builds a 32 bit unsigned decimal integer as the user inputs digits.
;The user can also delete the most recent digit with backspace.
;
;If the input exceeds the maximum value that can be held in 32 bits
;an error message will be displayed and the user prompted for a new number.
;
;The program can terminated at any time by entering a Space character


;Notes:
;While this program primarily makes use of 32-bit registers, i.e. eax, it
;uses 64-bit registers to interact with the stack in order to be compatible
;with the 64-bit architecture of turing.
;
;The backspace KEY does not have the same value as the backspace CHARACTER,
;the KEY has the value 0x7f, while the CHARACTER has the value 0x8. This is
;important to differentiate between when displaying a backspace vs. checking
;if the backspace key has been pressed.
;
;The running total of the value entered is stored in eax, or when eax needs to
;be used for something else, its value is pushed to the stack using rax and
;later restored by poping to rax.
;
;A running total of the number of characters in the input is stored in ebx,
;or when ebx needs to be used for something else, its value is pushed to
;the stack using rbx and later restored by poping to rbx.
;
;Registers need to poped in the reverse order they where pushed to restore
;their values correctly.
;It is not an error to pop them in non-reverse order, but their values will get
;mixed up. It is also important to keep the order and placement of call and
;return statements in mind, as they also push and pop values from the stack.
;
;Given a 32 bit source (eg. ecx) div (divide) automatically gets it's second
;value from the combination of the edx:eax registers. The calculation is
;edx:eax / src. The quotient is automatically stored in eax while the remainder
;is stored in edx.
;
;Similarly, given a 32 bit source (eg. ecx) mul (multiply) automatically gets
;it's second value from the eax registers. The calculation is eax * src. The
;result is automatically stored in edx:eax register combination, and the carry
;flag set if edx contains any 1's (execpting the sign bit for signed numbers).

;The carry flag is also set by add if result overflows the destination register,
;Therefore a good way to check if the result of a calculation can fit in to, for
;example, a single 32 bit register (given a 32 bit source) is to check the
;value of the carry flag after the calculation, which this program does.
;
;The maximum decimal value of a 32 bit unsigned int is: 4,294,967,295
;
;Unsigned version of jump instructions, as we are dealing with unsigned numbers

%include "system.inc"

section .bss
   buf          resd  1             ;General buffer for holding input / output
   array        resd  10            ;Array, holds 10 32bit unsigned integers

section .data
   ;offset       dd    0             ;Address offset of next element in array
   ;index        dd    0             ;Index of next element in the array

   newline      db    0xa           ;Address that holds a newline character
   space        db    0x20          ;Address that holds value of Space key
   bsKey        db    0x7F          ;Address that holds value of BackSpace key
   bsChar       db    0x8           ;Address that hold value of BackSpace char
   bsb          db    0x8,0x20,0x8  ;BackSpace, Space, BackSpace sequence

   promptMsg    db    'Enter an integer (then Enter to continue): '
   promptLen    equ   $ - promptMsg ;Length (in bytes) of promptMsg

   errorMsg     db    0xa,'Error: Overflow. Max value: 4,294,967,295',0xa
   errorLen     equ   $ - errorMsg  ;Length (in bytes) of errorMsg

   highMsg      db    'The highest value entered was: '
   highLen      equ   $ - highMsg   ;Length (in bytes) of highMsg


section .text
   global _start                    ;Must be declared for linker

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; MAIN ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

_start:                             ;Start execution from here
   rawmode                          ;Macro, put keyboard in direct input mode

   mov    edx,   0                  ;Set starting highest value
   mov    ebx,   0                  ;Set starting offset
   mov    eax,   0                  ;Set starting index

main_loop:                          ;Main program loop
   call   getInteger                ;Get an integer from the user, stored in buf
   mov    ecx,   [buf]              ;Integer from user
  
   mov    [array + ebx], ecx        ;Store integer at next element

   cmp    ecx,   edx                ;Check if new number is the highest
   jbe    noNewHighest              ;Unsigned version of jle
   mov    edx,   ecx                ;Set new highest number
noNewHighest:

   add    ebx,   4                  ;Increase offset by 4 bytes (64bits)
   inc    eax                       ;Increase index by 1

   cmp    eax,   10                 ;Check if arrays' limit has been reached
   jb     main_loop

   mov    [buf], edx
   call   displayHighest

   call   exit                ;End the program

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;FUNCTIONS;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

exit:                               ;Prints \n and terminates the program     
   normalmode                       ;Marco, return the keyboard to normal

   call displayNewline
                                    ;Terminater the program
   mov    eax,   1                  ;Using sys_exit
   int    0x80                      ;Execute sys_exit

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;GET INTEGER;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

getInteger:                         ;Gets a 32bit undsigned decimal integer
   push   rax                       ;Save register a
   push   rbx                       ;Save register b
   push   rcx                       ;Save register c
   push   rdx                       ;Save register d

   call   init                      ;Display the initial prompt

getInteger_loop:
   call   getChar                   ;Get a character from the user

   mov    cl,    [buf]              ;Put the value of the character in cl
                                    ;Check the character and decide what to do
   cmp    cl,    [space]            ;Check if the character is space
   je     getInteger_return

   cmp    cl,    [newline]          ;Check if the character is enter (newline)
   je     getInteger_return

   cmp    cl,    [bsKey]            ;Check if the character is the backspace key
   jne    notBSKey                  ;If it is the backspace key
   call   backspace                 ;Call the backspace function
   jmp    getInteger_loop           ;And jump back to start of main_loop:
notBSKey:
 
   cmp    cl,    [bsChar]           ;Check if the character is backspace char
   jne    notBSChar                 ;If it is the backspace key
   call   backspace                 ;Call the backspace function
   jmp    getInteger_loop           ;And jump back to start of main_loop:
notBSChar:
                                    ;Check if the character is a digit
   cmp    cl,    0x30               ;Check if it's less than '0' in ascii
   jb     getInteger_loop           ;If it is, jump back to start of main_loop
   cmp    cl,    0x39               ;Check if it's greater than '9' in ascii
   ja     getInteger_loop           ;If it is, jump back to start of main_loop
                                    ;If the character is a digit
   call   displayChar               ;Display the character
                                    ;Multiply the running total by 10
   mov    ecx,   10                 ;Amount to multiply by, 10
   mul    ecx                       ;edx:eax = eax * ecx
                                    ;Check if the result needed more than 32bits
   jnc    noMulOverflow             ;If the carry flag is set
   call   error                     ;Display multiplication error message
   call   init                      ;Reset eax and ebx, and redisplay the prompt
   jmp    getInteger_loop           ;Jump back to start of main_loop
noMulOverflow:
                                    ;Add the value of the new digit to the total
   mov    ecx,   [buf]              ;Put the value of the new digit in ecx
   sub    ecx,   '0'                ;Substract ascii value for '0', i.e. 0x30
   add    eax,   ecx                ;eax += ecx
                                    ;Check if the result needed more than 32bits
   jnc    noAddOverflow             ;If the carry flag is set
   call   error                     ;Display addition error message
   call   init                      ;Reset eax and ebx, and redisplay the prompt
   jmp    getInteger_loop           ;Jump back to start of main_loop
noAddOverflow:
                                    ;If there where no errors
   inc    ebx                       ;Increase the length of the input by 1
   jmp    getInteger_loop           ;Jump back to the start of main_loop

getInteger_return:
   mov    [buf], eax                ;Put the value in buf

   call   displayNewline

   pop    rdx                       ;Restore register d
   pop    rcx                       ;Restore register c
   pop    rbx                       ;Restore register b
   pop    rax                       ;Restore register a

   ret

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;FUNCTIONS;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

init:                               ;Displays promptMsg and sets eax, ebx to 0
   push   rcx                       ;Save register c
   push   rdx                       ;Save register d
                                    ;Display prompt
   mov    edx,   promptLen          ;Number of characters to display
   mov    ecx,   promptMsg          ;Starting address of message
   mov    ebx,   1                  ;Display to stdout
   mov    eax,   4                  ;Using sys_write
   int    0x80                      ;Execute sys_write

   mov    eax,   0                  ;Set eax to 0
   mov    ebx,   0                  ;Set ebx to 0
   mov    dword [buf], 0            ;Clear buf

   pop    rdx                       ;Restore register d
   pop    rcx                       ;Restore register c

   ret                              ;Return

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

getChar:                            ;Gets a character and stores it in buf
   push   rax                       ;Save register a
   push   rbx                       ;Save register b
   push   rcx                       ;Save register c
   push   rdx                       ;Save register d
                                    ;Read a character into buf
   mov    edx,   1                  ;Number of characters to read, 1
   mov    ecx,   buf                ;Where the character is stored, buf
   mov    ebx,   0                  ;Read from stdin
   mov    eax,   3                  ;Using sys_read
   int    0x80                      ;Execute sys_read

   pop    rdx                       ;Restore register d
   pop    rcx                       ;Restore register c
   pop    rbx                       ;Restore register b
   pop    rax                       ;Restore register a

   ret                              ;Return

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

error:                              ;Displays overflow error message
   push   rax                       ;Save register a
   push   rbx                       ;Save register b
   push   rcx                       ;Save register c
   push   rdx                       ;Save register d
                                    ;Display the error message
   mov    edx,   errorLen           ;Number of characters to display
   mov    ecx,   errorMsg           ;Starting address of message
   mov    ebx,   1                  ;Display to stdout
   mov    eax,   4                  ;Using sys_write
   int    0x80                      ;Execute sys_write

   pop    rdx                       ;Restore register d
   pop    rcx                       ;Restore register c
   pop    rbx                       ;Restore register b
   pop    rax                       ;Restore register a

   ret                              ;Return

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

backspace:                          ;Removes most recent digit
   push   rcx                       ;Save register c
   push   rdx                       ;Save register d

   cmp    ebx,   0                  ;Check the current length of the input
   je backspace_return              ;Jump to endif_ if the length is 0
                                    ;Remove most recent digit from running total
   mov    edx,   0                  ;Clear edx so it doesn't mess up the result
   mov    ecx,   10                 ;Set the amount to devide by, 10
   div    ecx                       ;eax = edx:eax / ecx

   push   rax                       ;Save register a
   push   rbx                       ;Save register b
                                    ;Remove most recent digit from display
   mov    edx,   3                  ;Number of characters to display, 3
   mov    ecx,   bsb                ;Starting address of BS,Sp,BS sequence
   mov    ebx,   1                  ;Display to stdout
   mov    eax,   4                  ;Using sys_write
   int    0x80                      ;Execute sys_write

   pop    rbx                       ;Restore register b
   pop    rax                       ;Restore register a

   dec    ebx                       ;Decrease the length of the input by 1

backspace_return: 

   pop    rdx                       ;Restore register c
   pop    rcx                       ;Restore register d

   ret                              ;Return

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;DISPLAY HIGHEST;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

displayHighest:                     ;Displays highest value entered
   push   rax                       ;Save register a
   push   rbx                       ;Save register b
   push   rcx                       ;Save register c
   push   rdx                       ;Save register d
   
   mov    edx,   highLen            ;Number of characters to write
   mov    ecx,   highMsg            ;Starting address of message, highMsg
   mov    ebx,   1                  ;Write to stdout
   mov    eax,   4                  ;Using sys_write
   int    0x80                      ;Execute sys_write

   call   displayDecimal            ;Display the value

   call   displayNewline

   pop    rdx                       ;Restore register d
   pop    rcx                       ;Restore register c
   pop    rbx                       ;Restore register b
   pop    rax                       ;Restore register a

   ret                              ;Return

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;FUNCTIONS;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

displayDecimal:                     ;Displays the decimal value of buf
   mov    eax,   [buf]
   cmp    eax,   0                  ;Check for base case
   je displayDecimal_return
         
   mov    edx,   0                  ;Clear edx
   mov    ecx,   10                 ;The base of the numbering system (10)
   div    ecx                       ;eax = edx:eax / ecx, edx = edx:eax % ecx

   push   rdx                       ;Save the modulus for printing later
   mov    [buf], eax                ;Put the quotent back into buf

   call   displayDecimal            ;Recursively process quotent (value of buf)
                                    ;Print the modulus
   pop    rdx                       ;Restore the modulus
   add    edx,   0x30               ;add 0x30 to get ascii value
   mov    [buf], edx                ;Put character in buf for printing
   call   displayChar               ;Print

displayDecimal_return:
   ret                              ;Return

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;GENERAL FUNCTIONS;;;;;;;;;;;;;;;;;;;;;;;;;;;;

displayNewline:                     ;Moves console cursor to the next line

   push   rax                       ;Save register a
   push   rbx                       ;Save register b
   push   rcx                       ;Save register c
   push   rdx                       ;Save register d

   mov    edx,   1                  ;Number of characters to write, 1
   mov    ecx,   newline            ;Address of character, newline
   mov    ebx,   1                  ;Write to stdout
   mov    eax,   4                  ;Using sys_write
   int    0x80                      ;Execute sys_write

   pop    rdx                       ;Restore register d
   pop    rcx                       ;Restore register c
   pop    rbx                       ;Restore register b
   pop    rax                       ;Restore register a

   ret                              ;Return

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

displayChar:                        ;Displays the character store in buf

   push   rax                       ;Save register a
   push   rbx                       ;Save register b
   push   rcx                       ;Save register c
   push   rdx                       ;Save register d

   mov    edx,   1                  ;Number of characters to write, 1
   mov    ecx,   buf                ;Address of character, buf
   mov    ebx,   1                  ;Write to stdout
   mov    eax,   4                  ;Using sys_write
   int    0x80                      ;Execute sys_write

   pop    rdx                       ;Restore register d
   pop    rcx                       ;Restore register c
   pop    rbx                       ;Restore register b
   pop    rax                       ;Restore register a

   ret                              ;Return
