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
;The user can also delete the right most digit with backspace.
;
;If the input exceeds the maximum value that can be held in 32 bits
;an error message will be displayed and the user prompted for a new number.
;
;The program can be terminated at any time by pressing the space bar.


;Notes:
;While this program primarily makes use of 32-bit registers, i.e. eax, it
;uses 64-bit registers to interact with the stack in order to be compatible
;with the 64-bit architecture of turing.
;
;The backspace KEY does not have the same value as the backspace CHARACTER.
;The KEY has the value 0x7f, while the CHARACTER has the value 0x8. This is
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
;Given a 32 bit source (eg. ecx) div (unsigned integer divide) automatically
;gets it's second value from the combination of the edx:eax registers. The
;calculation is edx:eax / src. The quotient is automatically stored in eax while
;the remainder is stored in edx.
;
;Similarly, given a 32 bit source (eg. ecx) mul (unsigned integer multiply)
;automatically gets it's second value from the eax registers. The calculation is
;eax * src. The result is automatically stored in the edx:eax register
;combination, and the carry flag is set if edx contains any 1's as a result..

;The carry flag is also set by add if the result overflows the destination
;register,
;Therefore a good way to check if the result of a calculation can fit in to, for
;example, a single 32 bit register (given a 32 bit source) is to check the
;value of the carry flag after the calculation, which this program does.
;
;The maximum decimal value of a 32 bit unsigned int is: 4,294,967,295


%include "system.inc"

section .bss
   buf          resd  1             ;Holds the character input by the user

section .data
   newline      db    0xa           ;Address that holds a newline character
   space        db    0x20          ;Address that holds value of Space key
   bsKey        db    0x7F          ;Address that holds value of BackSpace key
   bsb          db    0x8,0x20,0x8  ;BackSpace, Space, BackSpace sequence

   promptMsg    db    'Enter a 32bit unsigned integer (space to exit): '
   promptLen    equ   $ - promptMsg ;Length (in bytes) of promptMsg

   errorMsg1    db    0xa,'Error: Overflow caused by multiplication',0xa
   errorLen1    equ   $ - errorMsg1 ;Length (in bytes) of errorMsg1

   errorMsg2    db    0xa,'Error: Overflow caused by addition',0xa
   errorLen2    equ   $ - errorMsg2 ;Length (in bytes) of errorMsg2


section .text
   global _start                    ;Must be declared for linker

;;;;;;;;;;;;;;;;;;;;;;;;;;; FUNCTIONS ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

   init:                            ;Displays promptMsg and sets eax, ebx to 0
      push rcx                      ;Save registers c and d
      push rdx
                                    ;Display prompt
      mov edx,promptLen             ;Number of characters to display
      mov ecx,promptMsg             ;Starting address of message
      mov ebx,1                     ;Display to stdout
      mov eax,4                     ;Using sys_write
      int 0x80                      ;Execute sys_write

      mov eax,0                     ;Set eax to 0
      mov ebx,0                     ;Set ebx to 0

      pop rdx                       ;Restore registers c and d
      pop rcx

      ret                           ;Return

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

   getChar:                         ;Gets a character and stores it in buf
      push rax                      ;Save registers a, b, c and d
      push rbx
      push rcx
      push rdx
                                    ;Read a character into buf
      mov edx,1                     ;Number of characters to read, 1
      mov ecx,buf                   ;Address where character is stored, buf
      mov ebx,0                     ;Read from stdin 
      mov eax,3                     ;Using sys_read
      int 0x80                      ;Execute sys_read

      pop rdx                       ;Restore registers a, b, c and d
      pop rcx
      pop rbx
      pop rax

      ret                           ;Return

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

   displayChar:                     ;Displays the character stored in buf
      push rax                      ;Save registers a, b, c and d
      push rbx
      push rcx
      push rdx
                                    ;Display the character in buf
      mov edx,1                     ;Number of characters to print, 1
      mov ecx,buf                   ;Address of character, buf
      mov ebx,1                     ;Display to stdout
      mov eax,4                     ;Using sys_write
      int 0x80                      ;Execute sys_write

      pop rdx                       ;Restore registers a, b, c and d
      pop rcx
      pop rbx
      pop rax

      ret                           ;Return

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

   error1:                          ;Displays multiplication overflow message
      push rax                      ;Save registers a, b, c and d
      push rbx
      push rcx
      push rdx  
                                    ;Display the error message
      mov edx,errorLen1             ;Number of characters to display
      mov ecx,errorMsg1             ;Starting address of message
      mov ebx,1                     ;Display to stdout           
      mov eax,4                     ;Using sys_write
      int 0x80                      ;Execute sys_write

      pop rdx                       ;Restore registers a, b, c and d
      pop rcx
      pop rbx
      pop rax

      ret                           ;Return

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

   error2:                          ;Displays addition overflow message
      push rax                      ;Save registers a, b, c and d
      push rbx
      push rcx
      push rdx
                                    ;Display the error message
      mov edx,errorLen2             ;Number of characters to display
      mov ecx,errorMsg2             ;Starting address of message
      mov ebx,1                     ;Display to stdout
      mov eax,4                     ;Using sys_write
      int 0x80                      ;Execute sys_write

      pop rdx                       ;Restore registers a, b, c and d
      pop rcx
      pop rbx
      pop rax

      ret                           ;Return

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

   backspace:                       ;Removes right most digit
      cmp ebx,0                     ;Check the current length of the input
      je endif_backspace_1          ;Jump to endif_ if the length is 0

         push rcx                   ;Save registers c and d
         push rdx
                                    ;Remove right most digit from running total
         mov edx,0                  ;Clear edx so it doesn't mess up the result
         mov ecx,10                 ;Set the amount to devide by, 10
         div ecx                    ;eax = edx:eax / ecx

         push rax                   ;Save register a
         push rbx                   ;Save register b
                                    ;Remove right most digit from display
         mov edx,3                  ;Number of characters to display, 3
         mov ecx,bsb                ;Starting address of BS,Sp,BS sequence
         mov ebx,1                  ;Display to stdout
         mov eax,4                  ;Using sys_write
         int 0x80                   ;Execute sys_write

         pop rbx                    ;Restore register b
         pop rax                    ;Restore register a

         dec ebx                    ;Decrease the length of the input by 1

         pop rdx                    ;Restore registers c and d
         pop rcx

      endif_backspace_1:            ;endif_

      ret                           ;Return

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

   exit:                            ;Prints \n and terminates the program
      normalmode                    ;Marco, return the keyboard to normal
                                    ;Print a newline, \n
      mov edx,1                     ;Number of characters to display, 1
      mov ecx,newline               ;Address containing \n character
      mov ebx,1                     ;Display to stdout
      mov eax,4                     ;Using sys_write
      int 0x80                      ;Execute sys_write
                                    ;Terminater the program
      mov eax,1                     ;Using sys_exit
      int 0x80                      ;Execute sys_exit

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; MAIN ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

   _start:                          ;Start execution from here
      rawmode                       ;Macro, put keyboard in direct input mode

      call init                     ;Display the initial prompt

      main_loop:                    ;Main program loop
         call getChar               ;Get a character from the user

         mov cl,[buf]               ;Put the value of the character cl (8 bit)
                                    ;Check the character and decide what to do
         cmp cl,[space]             ;Check if the character is space
         jne endif_main_1           ;If it is space
            call exit               ;Call the exit function
         endif_main_1:

         cmp cl,[bsKey]             ;Check if the character is the backspace key
         jne endif_main_2           ;If it is the backspace key
            call backspace          ;Call the backspace function
            jmp main_loop           ;And jump back to start of main_loop:
         endif_main_2:
                                    ;Check if the character is a digit
         cmp cl,0x30                ;Check if it's less than '0' in ascii
         jl main_loop               ;If it is, jump back to start of main_loop
         cmp cl,0x39                ;Check if it's greater than '9' in ascii
         jg main_loop               ;If it is, jump back to start of main_loop
                                    ;Else, the character is a digit, so...
         call displayChar           ;Display the character
                                    ;Multiply the running total by 10
         mov ecx,10                 ;Amount to multiply by, 10
         mul ecx                    ;edx:eax = eax * ecx
                                    ;Check if the result needed more than 32bits
         jnc endif_main_3           ;If the carry flag is set
            call error1             ;Display multiplication error message
            call init               ;Reset eax and ebx, and redisplay the prompt
            jmp main_loop           ;Jump back to start of main_loop
         endif_main_3
                                    ;Add the value of the new digit to the total
         mov ecx,[buf]              ;Put the value of the new digit in ecx
         sub ecx,'0'                ;Substract ascii value for '0', i.e. 0x30
         add eax,ecx                ;eax += ecx
                                    ;Check if the result needed more than 32bits
         jnc endif_main_4           ;If the carry flag is set
            call error2             ;Display addition error message
            call init               ;Reset eax and ebx, and redisplay the prompt
            jmp main_loop           ;Jump back to start of main_loop
         endif_main_4:
                                    ;Else, there where no errors, so...
         inc ebx                    ;Increase the length of the input by 1
         jmp main_loop              ;Jump back to the start of main_loop
