;  Filename: prac4.asm
;    Author: Byron Miles
;Student No: 220057347
;     Email: bmiles3@une.edu.au
;Assignment: 2010s1 COMP283 Prac 4

;To compile and run on turing:
; $ yasm -f elf -m amd64 -o prac4.o prac4.asm
; $ ld -o prac4 prac4.o
; $ ./prac4

;Description:
;Asks the user for an exponent (n), then calculates the displays the result of
;2^n.

;Notes:
;This is a 64-bit program. The number read in for the exponent can be up to
;2^64-1 (though it is not recommend to enter an exponent > ~700000 for reasons
;explained below) and the result of the calculation can be up to 2^64-1.
;
;I have chosen to pass 2 as a parameter to the _exp function as it means I can
;write the _exp function to be more universal in the values it accepts, rather
;than just be a function for 2^n.
;For similar reasons I have included the overflow checking in the _exp function
;rather than simply checking the value of the user input in the main function.
;However there is at least one problem with this: Due to the recursive nature
;of _exp, and the way it is implemented (ie. it does the calculation as it
;winds back down through the call stack) a large (> ~700000) exponent will
;cause a segmentation fault and crash the program.
;
;The main functions in this program, namely: _writenum, _readnum, and _exp
;adhear to the AMD 64 bit calling convention, in that preserve the value of
;rbx across the function call (they don't uses any other 'must presever'
;registers), use rdi and rsi for the first and second integer parameters
;respectfully, and return values are placed in rax.
;
;Most of the sub functions also adhear to this convention, thought not all of
;them do as some of the main functions make use of the rbx register to keep
;track to various things, so certain sub functions need to manipulate rbx, in
;which case they cannot preserve its value.
;
;All functions names begin with the _ to differentiate them for jump labels.
;'Sub' functions begin with the name of their 'main' function. eg. _readnum_init
;All functions are also seperated by a row of ; to makes it more visible where
;one finsihes and the next one begins.
;
;The backspace KEY does not have the same value as the backspace CHARACTER,
;the KEY has the value 0x7f, while the CHARACTER has the value 0x8. This is
;important to differentiate between when displaying a backspace vs. checking
;if the backspace key has been pressed.
;
;Registers need to poped in the reverse order they where pushed to restore
;their values correctly.
;It is not an error to pop them in non-reverse order, but their values will get
;mixed up. It is also important to keep the order and placement of call and
;return statements in mind, as they also push and pop values from the stack.
;
;Given a 64 bit source (eg. rcx) div (divide) automatically gets it's second
;value from the combination of the rdx:rax registers. The calculation is
;rdx:rax / src. The quotient is automatically stored in rax while the remainder
;is stored in rdx.
;
;Similarly, given a 64 bit source (eg. rcx) mul (multiply) automatically gets
;it's second value from the rax registers. The calculation is rax * src. The
;result is automatically stored in rdx:rax register combination, and the carry
;flag set if rdx contains any 1's (excpting the sign bit for signed numbers).

;The carry flag is also set by add if result overflows the destination register,
;Therefore a good way to check if the result of a calculation can fit in to, for
;example, a single 64 bit register (given a 64 bit source) is to check the
;value of the carry flag after the calculation, which this program does.
;
;The maximum decimal value of a 64 bit unsigned int is:
;18,446,744,073,709,551,615

%include "system.inc"

section .bss
   buf          resb  1             ;8bit buffer for character input / output

section .data
   NEWLINE      db    0xa           ;Address that holds a newline character
   SPACE        db    0x20          ;Address that holds value of Space key
   BSKEY        db    0x7F          ;Address that holds value of BackSpace key
   BSCHAR       db    0x8           ;Address that hold value of BackSpace char
   BSB          db    0x8,0x20,0x8  ;BackSpace, Space, BackSpace sequence

   OPPADD       db    0x2B          ;The '+' character, addition
   OPPSUB       db    0x2D          ;The '-' character, subtraction
   OPPMUL       db    0x2A          ;The '*' character, multiplication
   OPPDIV       db    0x2F          ;The '/' character, devision
   OPPEXP       db    0x5E          ;The '^' character, exponent
   OPPMOD       db    0x25          ;The '%' character, modulus
   OPPBAS       db    0x62          ;The 'b' character, base conversion

   numMsg       db    'Enter an unsigned 64-bit integer: '
   numLen       equ   $ - numMsg    ;Length (in bytes) of numMsg

   oppMsg       db    'Enter an operator (+,-,*,/,^,%,b): '
   oppLen       equ   $ - oppMsg    ;Length (in bytes) of oppMsg

   errOverMsg   db    0xA,'Error: Overflow. Max value: 2^64-1',0xA
   errOverLen   equ   $ - errOverMsg ;Length (in bytes) of errOverMsg

   errUnderMsg  db    0xA,'Error: Underflow. Min value 0',0xA
   errUnderLen  equ   $ - errUnderMsg ;Length (in bytes) of errUnderMsg

   errZeroMsg   db    0xA,'Error: Divide by Zero',0xA
   errZeroLen   equ   $ - errZeroMsg ;Length (in bytes) of errZeroMsg

   errBaseMsg   db    0xA,'Error: Base range is 2 to 36',0xA
   errBaseLen   equ   $ - errBaseMsg ;Length (in bytes) of errBaseMsg

   answerMsg    db    'Answer: '
   answerLen    equ   $ - answerMsg ;Lengeth (in bytes) of answerMsg

section .text
   global _start                    ;Must be declared for linker

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; MAIN ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

_start:                             ;Start execution from here
   rawmode                          ;Macro, put keyboard in direct input mode

   call   _readnum                  ;Get the first unsigned integer from the user
   push   rax                       ;Save it on the stack
   
   call   _getopp                   ;Get the operator from the user
   push   rax                       ;Save it on the stack

   call   _readnum                  ;Get the second unsigned integer from the user
   
   pop    rcx                       ;Restore the operator into rcx
   pop    rdi                       ;Restore the first unsigned interger into rdi
   mov    rsi,   rax                ;Move the second unsigned interger into rsi

   cmp    cl,    [OPPADD]              ;Check for addition operator
   jne    start_notAdd
   call   _oppadd                   ;Call addition function
   jmp    start_answer              ;Jump to answer display
start_notAdd:

   cmp    cl,    [OPPSUB]              ;Check for subtraction operator
   jne    start_notSub
   call   _oppsub                   ;Call subtraction function
   jmp    start_answer              ;Jump to answer display
start_notSub:

   cmp    cl,    [OPPMUL]              ;Check for multiplication operator
   jne    start_notMul
   call   _oppmul                   ;Call multiplication function
   jmp    start_answer              ;Jump to answer display
start_notMul:

   cmp    cl,    [OPPDIV]              ;Check for division operator
   jne    start_notDiv
   call   _oppdiv                   ;Call division function
   jmp    start_answer              ;Jump to answer display
start_notDiv:

   cmp    cl,    [OPPEXP]              ;Check for exponent operator
   jne    start_notExp
   call   _oppexp                   ;Call exponent function
   jmp    start_answer              ;Jump to answer display
start_notExp:

   cmp    cl,    [OPPMOD]              ;Check for modulus operator
   jne    start_notMod
   call   _oppmod                   ;Call modulus function
   jmp    start_answer              ;Jump to answer display
start_notMod:

   cmp    cl,    [OPPBAS]              ;Check for base conversion operator
   jne    start_notBas
   call   _oppbas                   ;Call base conversion function
   jmp    _exit                     ;Terminate the program
start_notBas:

start_answer:

   push   rax                       ;Save register a
                                    ;Write "Answer: " to the console
   mov    rdx,   answerLen          ;Number of characters to write
   mov    rcx,   answerMsg          ;Staring address of message
   mov    rbx,   1                  ;Write to stdout
   mov    rax,   4                  ;Using sys_write
   int    0x80                      ;Execute sys_write

   pop    rax                       ;Restore register a 

   mov    rdi,   rax                ;First integer paramenter = return _exp
   call   _writenum                 ;Call _writenum  function

   call   _exit                     ;End the program

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; EXIT ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

_exit:                              ;Prints \n and terminates the program
   normalmode                       ;Marco, return the keyboard to normal

   call   _newline                  ;Move cursor to new line
                                    ;Terminater the program
   mov    rax,   1                  ;Using sys_exit
   int    0x80                      ;Execute sys_exit


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; WRITE NUM (UINT) ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

_writenum:                          ;Displays the decimal value passed in rdi
   push   rbx                       ;Save register b

   cmp    rdi,   0                  ;Check for base case
   je     writenum_return           ;Return on base case

   mov    rax,   rdi                ;Put parameter in rax
   mov    rdx,   0                  ;Clear rdx so it doesn't mess up the result
   mov    rcx,   10                 ;The base of the numbering system (10)
   div    rcx                       ;rax = rdx:rax / rcx, rdx = rdx:rax % rcx

   push   rdx                       ;Save the modulus for printing later
   mov    rdi,   rax                ;Pass quotent as value to next call
   call   _writenum                 ;Recursively process quotent
                                    ;Print the modulus
   pop    rdx                       ;Restore the modulus
   add    dl,    0x30               ;add 0x30 to get ascii value
   mov    [buf], dl                 ;Put character in buf for printing
   call   _writeChar                ;Print

writenum_return:
   pop   rbx                        ;Restore register b

   ret                              ;Return

;;;;;;;;;;;;;;;;;;;;;;;;;;;; UINT READNUM ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

_readnum:                           ;Gets a 64bit undsigned decimal integer
   push   rbx                       ;Save register b

   call   _readnum_init             ;Display the initial prompt

readnum_loop:
   push   rax                       ;Save register a
   call   _readChar                 ;Get a character from the user, in buf
   pop    rax                       ;Restore register a

   mov    cl,    [buf]              ;Put the value of the character in cl
                                    ;Check the character and decide what to do
   cmp    cl,    [SPACE]            ;Check if the character is space
   je     readnum_return            ;Return on Space

   cmp    cl,    [NEWLINE]          ;Check if the character is enter (newline)
   je     readnum_return            ;Return on Enter

   cmp    cl,    [BSKEY]            ;Check if the character is the backspace key
   jne    readnum_notBSKEY          ;If it is the backspace key
   call   _readnum_backspace        ;Call the _backspace function
   jmp    readnum_loop              ;And jump back to readnum_loop:
readnum_notBSKEY:
 
   cmp    cl,    [BSCHAR]           ;Check if the character is backspace char
   jne    readnum_notBSCHAR         ;If it is the backspace char
   call   _readnum_backspace        ;Call the _backspace function
   jmp    readnum_loop              ;And jump back to readnum_loop:
readnum_notBSCHAR:
                                    ;Check if the character is a digit
   cmp    cl,    0x30               ;Check if it's less than '0' in ascii
   jb     readnum_loop              ;If it is, jump back to readnum_loop
   cmp    cl,    0x39               ;Check if it's greater than '9' in ascii
   ja     readnum_loop              ;If it is, jump back to readnum_loop
                                    ;If the character is a digit
   push   rax                       ;Save register a
   call   _writeChar                ;Display the character
   pop    rax                       ;Restore register a
                                    ;Multiply the running total by 10
   mov    rcx,   10                 ;Amount to multiply by, 10
   mul    rcx                       ;rdx:rax = rax * rcx
                                    ;Check if the result needed more than 64bits
   jnc    readnum_noMulOverflow     ;If the carry flag is set
   call   _error_Over               ;Display overflow error message
   call   _readnum_init             ;Reset rax and rbx, and redisplay the prompt
   jmp    readnum_loop              ;Jump back to readnum_loop
readnum_noMulOverflow:
                                    ;Add the value of the new digit to the total
   mov    rcx,   [buf]              ;Put the value of the new digit in rcx
   sub    rcx,   '0'                ;Substract ascii value for '0', i.e. 0x30
   add    rax,   rcx                ;rax += rcx
                                    ;Check if the result needed more than 64bits
   jnc    readnum_noAddOverflow     ;If the carry flag is set
   call   _error_Over               ;Display overflow error message
   call   _readnum_init             ;Reset rax and rbx, and redisplay the prompt
   jmp    readnum_loop              ;Jump back to readnum_loop
readnum_noAddOverflow:
                                    ;If there where no errors
   inc    rbx                       ;Increase the length of the input by 1
   jmp    readnum_loop              ;Jump back to readnum_loop

readnum_return:
   
   push   rax                       ;Save register a
   call   _newline                  ;Move cursor to newline
   pop    rax                       ;Restore register a

   pop    rbx                       ;Restore register b

   ret                              ;Return, with return value in rax

;;;;;;;;;;;;;;;;;;;;;;;;;;;;; READ NUM FUNCTIONS ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

_readnum_init:                      ;Displays numMsg and sets rax, rbx to 0
                                    ;Display prompt
   mov    rdx,   numLen             ;Number of characters to display
   mov    rcx,   numMsg             ;Starting address of message
   mov    rbx,   1                  ;Display to stdout
   mov    rax,   4                  ;Using sys_write
   int    0x80                      ;Execute sys_write

   mov    rax,   0                  ;Set eax to 0
   mov    rbx,   0                  ;Set ebx to 0

   ret                              ;Return

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

_readnum_backspace:                 ;Removes most recent digit

   cmp    rbx,   0                  ;Check the current length of the input
   je     backspace_return          ;Return if the length is 0
                                    ;Remove most recent digit from running total
   mov    rdx,   0                  ;Clear rdx so it doesn't mess up the result
   mov    rcx,   10                 ;Set the amount to devide by, 10
   div    rcx                       ;rax = rdx:rax / rcx

   push   rax                       ;Save register a
   push   rbx                       ;Save register b
                                    ;Remove most recent digit from display
   mov    rdx,   3                  ;Number of characters to display, 3
   mov    rcx,   BSB                ;Starting address of BS,Sp,BS sequence
   mov    rbx,   1                  ;Display to stdout
   mov    rax,   4                  ;Using sys_write
   int    0x80                      ;Execute sys_write

   pop    rbx                       ;Restore register b
   pop    rax                       ;Restore register a

   dec    rbx                       ;Decrease the length of the input by 1

backspace_return: 

   ret                              ;Return

;;;;;;;;;;;;;;;;;;;;;;;;;;;;; GET OPP ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
_getopp:                            ;Gets a mathmatical operator from the user
   push   rbx                       ;Save register b
                                    ;Display prompt
   mov    rdx,   oppLen             ;Number of characters to display
   mov    rcx,   oppMsg             ;Starting address of message
   mov    rbx,   1                  ;Write to stdout
   mov    rax,   4                  ;Using sys_write
   int    0x80                      ;Execute sys_write

getopp_loop:                        ;Start of input / validation loop
   call   _readChar                 ;Get a character from the user

   mov    cl,    [buf]              ;Put the character in cl for validation

   cmp    cl,    [OPPADD]              ;Check for to addition operator
   je     getopp_return             ;Return addition operator
   cmp    cl,    [OPPSUB]              ;Check for to subtraction operator
   je     getopp_return             ;Return subtraction operator
   cmp    cl,    [OPPMUL]              ;Check for to multiplication operator
   je     getopp_return             ;Return multiplication operator
   cmp    cl,    [OPPDIV]              ;Check for to divide operator
   je     getopp_return             ;Return divide operator
   cmp    cl,    [OPPEXP]              ;Check for to exponent operator
   je     getopp_return             ;Return exponent operator
   cmp    cl,    [OPPMOD]              ;Check for to modulus operator
   je     getopp_return             ;Return modulus operator
   cmp    cl,    [OPPBAS]              ;Check for to base conversion operator
   je     getopp_return             ;Return base conversion operator

   jmp    getopp_loop               ;Get another character if not valid

getopp_return:                      ;Exit the loop and return the operator
   call   _writeChar                ;Display the operator
   call   _newline                  ;Move cursor to next line

   mov    rax,   [buf]              ;Return operator in rax

   pop    rbx                       ;Restore rbx
   ret                              ;Return

;;;;;;;;;;;;;;;;;;;;;;UINT OPPADD (UINT, UINT) ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

_oppadd:                            ;Returns rdi + rsi in rax
   push    rbx                      ;Save register rbx

   add     rdi,  rsi                ;rdi += rsi
   jnc     oppadd_noOverflow        ;Check for overflow
   call    _error_Over              ;Display overflow error message
   call    _exit                    ;Terminate the program
oppadd_noOverflow:

   mov     rax,  rdi                ;Return the result in rax  

   pop     rbx                      ;Restore register rbx
   ret                              ;Return

;;;;;;;;;;;;;;;;;;;;;;UINT OPPSUB (UINT, UINT) ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

_oppsub:                            ;Returns rdi - rsi in rax
   push    rbx                      ;Save register rbx

   sub     rdi,  rsi                ;rdi += rsi
   jnc     oppsub_noUnderflow       ;Check for underflow
   call    _error_Under             ;Display underflow error message
   call    _exit                    ;Terminate the program
oppsub_noUnderflow:

   mov     rax,  rdi                ;Return the result in rax  

   pop     rbx                      ;Restore register rbx
   ret                              ;Return

;;;;;;;;;;;;;;;;;;;;;;UINT OPPMUL (UINT, UINT) ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

_oppmul:                            ;Returns rdi * rsi in rax
   push    rbx                      ;Save register rbx

   mov     rax,  rdi                ;Put first value into rax
   mul     rsi                      ;rdx:rax = rax * rsi
   jnc     oppmul_noOverflow        ;Check for overflow
   call    _error_Over              ;Display overflow error message
   call    _exit                    ;Terminate the program
oppmul_noOverflow:

   pop     rbx                      ;Restore register rbx
   ret                              ;Return

;;;;;;;;;;;;;;;;;;;;;;UINT OPPDIV (UINT, UINT) ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

_oppdiv:                            ;Returns rdi / rsi in rax
   push    rbx                      ;Save register rbx
 
   cmp     rsi,  0                  ;Check for devide by zero
   jne      oppdiv_noZero      
   call    _error_Zero              ;Display divide by zero error message
   call    _exit                    ;Terminate the program
oppdiv_noZero:

   mov     rdx,  0                  ;Clear rdx so it doesn't mess with the result
   mov     rax,  rdi                ;Put the first value in rax
   div     rsi                      ;rax = rdx:rax / rsi

   pop     rbx                      ;Restore register rbx
   ret                              ;Return

;;;;;;;;;;;;;;;;;;;;;;;;;;;;UINT OPPEXP (UINT, UINT) ;;;;;;;;;;;;;;;;;;;;;;;;;;;

_oppexp:                            ;Returns rdi ^ rsi in rax
   push   rbx                       ;Save register b

   cmp    rsi,   0                  ;Check if exponent is 0
   jne    oppexp_recurse            ;If it is 0
   mov    rax,   1                  ;Value to return = 1
   jmp    oppexp_return             ;Return
oppexp_recurse:
                                    ;Else
   dec    rsi                       ;Decrease exponent by 1
   call   _oppexp                   ;Recursively call _exp function
   mul    rdi                       ;rdx:rax = rax * rdi (value * base)

   jnc    oppexp_return             ;If there is an overflow. ie value > 2^64-1
   call   _error_Over               ;Call overflow function
   call   _exit                     ;Terminate the program

oppexp_return:
   pop   rbx                        ;Restore register b

   ret                              ;Return, return value in rax

;;;;;;;;;;;;;;;;;;;;;;UINT OPPMOD (UINT, UINT) ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

_oppmod:                            ;Returns rdi % rsi in rax
   push    rbx                      ;Save register rbx
 
   cmp     rsi,  0                  ;Check for devide by zero
   jne      oppmod_noZero      
   call    _error_Zero              ;Display divide by zero error message
   call    _exit                    ;Terminate the program
oppmod_noZero:

   mov     rdx,  0                  ;Clear rdx so it doesn't mess with the result
   mov     rax,  rdi                ;Put the first value in rax
   div     rsi                      ;rdx = rdx:rax % rsi
   
   mov     rax,  rdx                ;Return result in rax

   pop     rbx                      ;Restore register rbx
   ret                              ;Return

;;;;;;;;;;;;;;;;;;;;;;;VOID OPPBAS (UNIT, UINT) ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
_oppbas:                            ;Displays rdi as a base rsi number
   push   rbx                       ;Save register b

   cmp    rsi,   2                  ;Check base is at least 2
   jae     oppbas_notLow               
   call   _error_Base               ;Display an invalid base error
   call   _exit                     ;Terminatre the program
oppbas_notLow:
   cmp    rsi,   36                 ;Check base is no higher than 36
   jbe    oppbas_notHigh
   call   _error_Base               ;Display invalid base error
   call   _exit                     ;Terminate the program
oppbas_notHigh:

   cmp    rdi,   0                  ;Check for base case
   je     oppbas_return             ;Return on base case

   mov    rax,   rdi                ;Put parameter in rax
   mov    rdx,   0                  ;Clear rdx so it doesn't mess up the result
   div    rsi                       ;rax = rdx:rax / rsi, rdx = rdx:rax % rsi

   push   rdx                       ;Save the modulus for printing later
   mov    rdi,   rax                ;Pass quotent as value to next call
   call   _oppbas                   ;Recursively process quotent
                                    ;Print the modulus
   pop    rdx                       ;Restore the modulus
   cmp    dl,    9                  ;Check if a digit or letter needs to be displayed
   ja     oppbas_char               
   add    dl,    0x30               ;Add 0x30 for displaying a digit
   jmp    oppbas_write   
oppbas_char:
   add    dl,    0x37               ;Add 0x37 for displaying a letter

oppbas_write:
   mov    [buf], dl                 ;Put character in buf for printing
   call   _writeChar                ;Print

oppbas_return:
   pop   rbx                        ;Restore register b

   ret                              ;Return

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;GENERAL FUNCTIONS;;;;;;;;;;;;;;;;;;;;;;;;;;;;

_newline:                           ;Moves console cursor to the next line
   push   rbx                       ;Save register b

   mov    rdx,   1                  ;Number of characters to write, 1
   mov    rcx,   NEWLINE            ;Address of character, newline
   mov    rbx,   1                  ;Write to stdout
   mov    rax,   4                  ;Using sys_write
   int    0x80                      ;Execute sys_write

   pop    rbx                       ;Restore register b

   ret                              ;Return

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

_readChar:                          ;Gets a character and stores it in buf
   push   rbx                       ;Save register b
                                    ;Read a character into buf
   mov    rdx,   1                  ;Number of characters to read, 1
   mov    rcx,   buf                ;Where the character is stored, buf
   mov    rbx,   0                  ;Read from stdin
   mov    rax,   3                  ;Using sys_read
   int    0x80                      ;Execute sys_read

   pop    rbx                       ;Restore register b

   ret                              ;Return

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

_writeChar:                         ;Displays the character store in buf
   push   rbx                       ;Save register b

   mov    rdx,   1                  ;Number of characters to write, 1
   mov    rcx,   buf                ;Address of character, buf
   mov    rbx,   1                  ;Write to stdout
   mov    rax,   4                  ;Using sys_write
   int    0x80                      ;Execute sys_write

   pop    rbx                       ;Restore register b

   ret                              ;Return

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

_error_Over:                        ;Displays overflow error message
   push   rbx                       ;Save register b
                                    ;Display the error message
   mov    rdx,   errOverLen         ;Number of characters to display
   mov    rcx,   errOverMsg         ;Starting address of message
   mov    rbx,   1                  ;Display to stdout
   mov    rax,   4                  ;Using sys_write
   int    0x80                      ;Execute sys_write

   pop    rbx                       ;Restore register b

   ret                              ;Return

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

_error_Under:                       ;Displays underflow error message
   push   rbx                       ;Save register b
                                    ;Display the error message
   mov    rdx,   errUnderLen        ;Number of characters to display
   mov    rcx,   errUnderMsg        ;Starting address of message
   mov    rbx,   1                  ;Display to stdout
   mov    rax,   4                  ;Using sys_write
   int    0x80                      ;Execute sys_write

   pop    rbx                       ;Restore register b

   ret                              ;Return

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

_error_Zero:                        ;Displays divide by zero error message
   push   rbx                       ;Save register b
                                    ;Display the error message
   mov    rdx,   errZeroLen         ;Number of characters to display
   mov    rcx,   errZeroMsg         ;Starting address of message
   mov    rbx,   1                  ;Display to stdout
   mov    rax,   4                  ;Using sys_write
   int    0x80                      ;Execute sys_write

   pop    rbx                       ;Restore register b

   ret                              ;Return

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

_error_Base:                        ;Displays invalid base error message
   push   rbx                       ;Save register b
                                    ;Display the error message
   mov    rdx,   errBaseLen         ;Number of characters to display
   mov    rcx,   errBaseMsg         ;Starting address of message
   mov    rbx,   1                  ;Display to stdout
   mov    rax,   4                  ;Using sys_write
   int    0x80                      ;Execute sys_write

   pop    rbx                       ;Restore register b

   ret                              ;Return

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
