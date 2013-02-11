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

   promptMsg    db    'Enter an integer, n, for exp(2,n): '
   promptLen    equ   $ - promptMsg ;Length (in bytes) of promptMsg

   errorMsg     db    0xa,'Error: Overflow. Max value: 2^64-1',0xa
   errorLen     equ   $ - errorMsg  ;Length (in bytes) of errorMsg

   expOverMsg   db    'Exceeds 18,446,744,073,709,551,615'
   expOverLen   equ   $ - expOverMsg;Length (in bytes) of expOverMsg

   answerMsg    db    'Answer: '
   answerLen    equ   $ - answerMsg ;Lengeth (in bytes) of answerMsg

section .text
   global _start                    ;Must be declared for linker

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; MAIN ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

_start:                             ;Start execution from here
   rawmode                          ;Macro, put keyboard in direct input mode

   call   _readnum                  ;Get an unsigned integer from the user

   mov    rdi,   2                  ;First integer parameter = 2
   mov    rsi,   rax                ;Second integer parameter = return _readnum
   call   _exp                      ;Call _exp function

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
   call   _readnum_error            ;Display overflow error message
   call   _readnum_init             ;Reset rax and rbx, and redisplay the prompt
   jmp    readnum_loop              ;Jump back to readnum_loop
readnum_noMulOverflow:
                                    ;Add the value of the new digit to the total
   mov    rcx,   [buf]              ;Put the value of the new digit in rcx
   sub    rcx,   '0'                ;Substract ascii value for '0', i.e. 0x30
   add    rax,   rcx                ;rax += rcx
                                    ;Check if the result needed more than 64bits
   jnc    readnum_noAddOverflow     ;If the carry flag is set
   call   _readnum_error            ;Display overflow error message
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

_readnum_init:                      ;Displays promptMsg and sets rax, rbx to 0
                                    ;Display prompt
   mov    rdx,   promptLen          ;Number of characters to display
   mov    rcx,   promptMsg          ;Starting address of message
   mov    rbx,   1                  ;Display to stdout
   mov    rax,   4                  ;Using sys_write
   int    0x80                      ;Execute sys_write

   mov    rax,   0                  ;Set eax to 0
   mov    rbx,   0                  ;Set ebx to 0

   ret                              ;Return

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

_readnum_error:                     ;Displays overflow error message
   push   rbx                       ;Save register b
                                    ;Display the error message
   mov    rdx,   errorLen           ;Number of characters to display
   mov    rcx,   errorMsg           ;Starting address of message
   mov    rbx,   1                  ;Display to stdout
   mov    rax,   4                  ;Using sys_write
   int    0x80                      ;Execute sys_write

   pop    rbx                       ;Restore register b

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

;;;;;;;;;;;;;;;;;;;;;;;;;;;;UNINT EXP (UINT, UINT) ;;;;;;;;;;;;;;;;;;;;;;;;;;;

_exp:                               ;Returns rdi ^ rsi in rax
   push   rbx                       ;Save register b

   cmp    rsi,   0                  ;Check if exponent is 0
   jne    exp_recurse               ;If it is 0
   mov    rax,   1                  ;Value to return = 1
   jmp    exp_return                ;Return
exp_recurse:
                                    ;Else
   dec    rsi                       ;Decrease exponent by 1
   call   _exp                      ;Recursively call _exp function
   mul    rdi                       ;rdx:rax = rax * rdi (value * base)

   jnc    exp_return                ;If there is an overflow. ie value > 2^64-1
   call   _exp_overflow             ;Call overflow function
   call   _exit                     ;Terminate the program

exp_return:
   pop   rbx                        ;Restore register b

   ret                              ;Return, return value in rax

;;;;;;;;;;;;;;;;;;;;;;;;;;;; EXP FUNCTIONS ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

_exp_overflow:                      ;Displays an overflow error
   push   rbx                       ;Save register b
                                    ;Display the error message
   mov    rdx,   expOverLen         ;Number of characters to display
   mov    rcx,   expOverMsg         ;Starting address of message
   mov    rbx,   1                  ;Display to stdout
   mov    rax,   4                  ;Using sys_write
   int    0x80                      ;Execute sys_write

   pop    rbx                       ;Restore register b

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
