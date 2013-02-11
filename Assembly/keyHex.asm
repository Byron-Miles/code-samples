
%include "system.inc"

section .bss
   ;Variables
   buf          resd  1 ;Input buffer, 1 word

section .data
   newline      db    0xa

   promptMsg    db    'Press any key -> Hex: '
   promptLen    equ   $ - promptMsg

section .text
   global _start   ;must be declared for linker

;;;;;;;;;;;;;;;;;;;;;;;;;;;;; FUNCTIONS  ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

   init: ;Displays the prompt
      ;Save registers a, b, c and d
      push rax
      push rbx
      push rcx
      push rdx

      ;Display prompt
      mov edx,promptLen
      mov ecx,promptMsg
      mov ebx,1   ;stdout
      mov eax,4   ;sys_write
      int 0x80

      ;Restore registers a, b, c and d
      pop rdx
      pop rcx
      pop rbx
      pop rax

      ret ;Return
   ;End init

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

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

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

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

   hexValue: ;Recursive function that prints the hexidecimal value in buf
      ;Save registers a, b, c and d
      push rax
      push rbx
      push rcx
      push rdx

      ;Check for base case
      mov eax,[buf] ;put the value in eax
      cmp eax,0
      je endif_hexValue_1 ;just return if there is nothing left to process
      
         ;Get the quotent and modulus
         mov edx,0     ;clear edx so it doesn't mess with the calculation
         mov ecx,0x10  ;the number to devide by (10 in hex)
         div ecx       ;devide eax:edx by ecx. / in eax, % in edx

         push rdx      ;save the modulus for printing later
         mov [buf],eax ;put the quotent back into buf

         ;Recursively call to process rest of value
         call hexValue

         ;Print the modulus
         pop rdx                   ;restore the modulus
         ;Check if a digit or letter needs to be printed
         cmp edx,9
         jg else_hexValue_2        ;If it's a digit
            add edx,0x30           ;add 0x30, 0x0 to 0x9, for printing a digit
            jmp endif_hexValue_2   
         else_hexValue_2:          ;Else it's a letter
            add edx,0x37           ;add 0x37, 0xa to 0xf, for printing a letter
         endif_hexValue_2:

         mov [buf],edx ;put character in buf for printing
         call echoChar ;print

      endif_hexValue_1:

      ;Restore registers a, b, c and d
      pop rdx
      pop rcx
      pop rbx
      pop rax

      ret ;Return
   ;End hexValue

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

   exit: ;Prints a new line before terminating the program

      ;Print a new line
      mov edx,1
      mov ecx,newline
      mov ebx,1
      mov eax,4
      int 0x80

      ;Terminate the program
      mov eax,1  ;sys_exit
      int 0x80
   ;End exit

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;   MAIN   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

   _start:
      ;Display prompt
      call init

      ;Get keypress
      call getChar

      ;Print it's hex value
      call hexValue

      ;Terminate the program
      call exit
