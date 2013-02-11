;Filename: prac2.asm
;Author: Byron Miles
;Student Number: 220057347
;Email: bmiles3@une.edu.au
;Assignment: 2010s1 COMP283 Prac 2

;Compilation instructions:
;yasm -f elf -m amd64 -o prac2.o prac2.asm
;ld -o prac2 prac2.o

;Description:
;Builds a 32 bit unsigned integer number as the user inputs digits.
;The user can backspace over digits if they make a mistake.
;
;If the input exceeds the maximum value that can be held in 32 bits
;an error message will be displayed and the user prompted for a new number.
;
;The program can terminated at any time by entering a space

;Notes:
;Please use 'v' key instead of 'backspace' key to backspace :)
;The running total of the value entered is stored in eax
;The maximum value of a 32 bit unsigned int is: 4,294,967,295

%include "system.inc"

section .bss
   ;Variables
   buf          resd  1 ;Input buffer, 1 word

section .data
   ;Variables
   inputLength  db    0 ;Number of characters in the input

   ;Useful characters / character sequences
   newline      db    0xa ;new line. Length 1
   space        db    0x20 ;space. Length 1
   bsKey        db    0x76 ;key acting as backspace. Length 1
   bsb          db    0x8,0x20,0x8 ;backspace, space, backspace. Length 3

   ;Messages
   promptMsg    db    'Enter a 32bit positive integer: '
   promptLen    equ   $ - promptMsg

   errorMsg1    db    0xa,'Error: Overflow caused by multiplication',0xa
   errorLen1    equ   $ - errorMsg1

   errorMsg2    db    0xa,'Error: Overflow caused by addition',0xa
   errorLen2    equ   $ - errorMsg2


section .text
   global _start   ;must be declared for linker

;;;;;;;;;;;;;;;;;;;;;;;;;;; FUNCTIONS ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

   init: ;Displays the prompt and sets input values back to 0
      ;Save registers b, c and d
      push rbx 
      push rcx
      push rdx

      ;Display prompt
      mov edx,promptLen
      mov ecx,promptMsg
      mov ebx,1   ;stdout
      mov eax,4   ;sys_write
      int 0x80    

      ;Set input length and running total(eax) back to 0
      mov eax,0
      mov cl,0
      mov [inputLength],cl

      ;Restore registers b, c and d
      pop rdx 
      pop rcx
      pop rbx

      ret ;Return
   ;End init

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

   getChar: ;Gets a character from the keyboard and stores it in buf
      ;Save registers a, b, c and d
      push rax
      push rbx
      push rcx
      push rdx  

      rawmode

      ;Read a character into buf from the standard input
      mov edx,1
      mov ecx,buf
      mov ebx,0   ;stdin
      mov eax,3   ;sys_read
      int 0x80

      normalmode

      ;Restore registers a, b, c and d
      pop rdx
      pop rcx
      pop rbx
      pop rax

      ret ;Return
   ;End getChar

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

   echoChar: ;Prints the character currently stored in buf
      ;Save registers a, b, c and d
      push rax
      push rbx
      push rcx
      push rdx  

      ;Print the character in buf to the standard output
      mov edx,1
      mov ecx,buf
      mov ebx,1   ;stdout
      mov eax,4   ;sys_write
      int 0x80

      ;Restore registers a, b, c and d
      pop rdx
      pop rcx
      pop rbx
      pop rax

      ret ;Return
   ;End echoChar

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

   error1: ;Prints a multiplication overflow error message
      ;Save registers a, b, c and d
      push rax
      push rbx
      push rcx
      push rdx  

      ;Print message
      mov edx,errorLen1
      mov ecx,errorMsg1
      mov ebx,1   ;stdout
      mov eax,4   ;sys_write
      int 0x80

      ;Restore registers a, b, c and d
      pop rdx
      pop rcx
      pop rbx
      pop rax

      ret ;Return
   ;End error1

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

   error2: ;Prints an addition overflow error message
      ;Save registers a, b, c and d
      push rax
      push rbx
      push rcx
      push rdx

      ;Print message
      mov edx,errorLen2
      mov ecx,errorMsg2
      mov ebx,1   ;stdout
      mov eax,4   ;sys_write
      int 0x80

      ;Restore registers a, b, c and d
      pop rdx
      pop rcx
      pop rbx
      pop rax

      ret ;Return
   ;End error2

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

   backspace: ;Removes the last digit typed from the screen and running total
      ;Save registers b, c, and d
      push rbx
      push rcx
      push rdx

      ;Check the current length of the input
      mov cl,[inputLength]
      cmp cl,0
      je endif_backspace_1 ;Jump to the end if the length is 0

         ;Remove last digit from running total in eax
         mov edx,0 ;Clear edx so it doesn't mess with the calculation
         mov ecx,10 ;Set the amount to devide by
         div ecx ;Devide eax:edx by ecx. Result is automatically stored in eax
         push rax ;Save register a

         ;Remove last digit from display
         mov edx,3    
         mov ecx,bsb  ;the backspace, space, backspace sequence
         mov ebx,1    ;stdout
         mov eax,4    ;sys_write
         int 0x80

         ;Decrease input length by 1
         mov cl,[inputLength]
         dec cl
         mov [inputLength],cl

         pop rax ;Restore register a

      endif_backspace_1:

      ;Restore registers b, c, and d
      pop rdx
      pop rcx
      pop rbx

      ret ;Return
   ;End backspace

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

   exit: ;Prints a new line before terminating the program
      ;Put the keyboard back into normal mode
      ;normalmode

      ;Print a new line
      mov edx,1
      mov ecx,newline
      mov ebx,1
      mov eax,4
      int 0x80

      ;Terminate the program
      mov eax,1  ;sys_exit
      int 0x80

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; END FUNCTIONS  ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; MAIN ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

   _start:
      ;Put the keybaord into rawmode
      ;rawmode

      ;Display the initial prompt
      call init 

      ;The enter main program loop
      main_loop:
         ;Get a character
         call getChar 

         ;Check the character and decide what to do with it
            mov cl,[buf]

            ;Compare with space
            cmp cl,[space] 
            jne endif_main_1 ;Jump ahead if it's not Space
               call exit  ;Else, call exit
            endif_main_1:

            ;Compare with Backspace
            cmp cl,[bsKey]
            jne endif_main_2 ;Jump ahead if it's not Backspace
               call backspace ;Else, call backspace function
               jmp main_loop ;and jump back to start of the main loop
            endif_main_2:

            ;Compare with Digits
            cmp cl,0x30 ;'0' in ascii
            jl main_loop ;Jump back to start of main loop if less than 0
            cmp cl,0x39 ;'9' in ascii
            jg main_loop ;Jump back to start of main loop if greater than 9

         ;At this point the character is a digit so...
            ;Print the character
            call echoChar
         
            ;Increase the running total
               ;Multiply the current total by 10
               mov ecx,10
               mul ecx ;Multiplies eax by ecx(10). Stores in eax:edx

               ;Check the carry flag, ie. If the result is greater than 32bits
               jnc endif_main_3 ;Jump ahead if the carry flag is not set
                  call error1 ;Else, display multiplication error message,
                  call init ;reset the input and redisplay the prompt,
                  jmp main_loop ;and jump back to the start of the main loop
               endif_main_3:

               ;Add the new digit
               mov ecx,[buf]
               sub ecx,'0' ;Substract ascii value for '0', ie. 0x30
               add eax,ecx ;Add the value of the digit to the running total

               ;Check the carry flag, ie. If the result is greater than 32bits
               jnc endif_main_4 ;Jump ahead if the carry flag is not set
                  call error2 ;Else, display addition error message,
                  call init ;reset the input and redisplay the prompt,
                  jmp main_loop ;and jump back to the start of the main loop
               endif_main_4:

            ;Increase the input length by 1
            mov cl,[inputLength]
            inc cl
            mov [inputLength],cl

         ;Jump back to the start of the main loop
         jmp main_loop 

;;;;;;;;;;;;;;;;;;;;;;;;;;;; END OF PROGRAM  ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
