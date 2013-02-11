;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;                            COMP318 ASSIGNMENT 2                            ;;
;;                                                                            ;;
;;                                BYRON MILES                                 ;;
;;                                 220057347                                  ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;Question 1
(define listaiter
  (lambda (l)
    (lambda ()
         (let ((r (car l)))
           (set! l (cdr l))
           r))))
;
;> (define li (listaiter '(1 2 3 4)))
;> (define lj (listaiter '(a b c d)))
;> (li)
;1
;> (lj)
;a
;> (li)
;2
;> (li)
;3
;> (lj)
;b
;> (li)
;4
;> (li)
;Exception in car: () is not a pair
;Type (debug) to enter the debugger.
;> 


;Question 2
(define-syntax cond-set!
  (syntax-rules ()
    ((_ pred var val)
      (if pred (set! var val)))))
;
;> (cond-set! (> 1 0) var 5)
;> var
;5
;> (cond-set! (= 1 0) var 10)
;> var
;5
;> (cond-set! (< 1 2) var 2)
;> var
;2
;>


;Question 3
(define (condf-set! test variable value)
  (if test 
    (set! variable value)))
;The probelm with this function is that functions cannot affect outside 
;variables due to closure. I.e. 'variable' in the above function is local
;to the function, so while 'variable' gets set to value, it has no effect
;on the variable you called the function with, as shown in the following 
;output:
;
;> (define var 0)
;> (condf-set! (= 1 1) var 10)
;> var
;0
;> 


;Question 4
(define-syntax update-if-true!
  (syntax-rules ()
    ((_ (pred var)) ;Base case, one argument
      (let ((test pred)) 
        (if test (set! var test))))
    ((_ (pred1 var1) (pred2 var2) ...) ;Recursive case, more than one argument
      (begin
        (let ((test pred1)) 
          (if test (set! var1 test)))
          (update-if-true! (pred2 var2) ...)))))
;
;> (define x 7)
;> (define y 3)
;> (update-if-true! ((> x 5) x-is-bigger))
;> x-is-bigger
;#t
;> (update-if-true! ((> x 5) x-is-bigger) ((> y 5) y-is-smaller))
;> y-is-smaller
;Exception: variable y-is-smaller is not bound
;Type (debug) to enter the debugger.
;> (update-if-true! ((> x 5) x-is-bigger) ((< y 5) y-is-smaller))
;> y-is-smaller
;#t
;> (update-if-true! ((> x 5) x-is-bigger) ((< y 5) y-is-smaller)
;    ((= x 7) x-is-7))
;> x-is-7
;#t
;>

 
;Question 5
(define frozen)
(append '(the call/cc returned)
  (list (call-with-current-continuation
     (lambda (cc)
       (set! frozen cc)
       'a))))
;(the call/cc returned a)
;;(the call/cc returned) is append to the list returned by list, which is
;;the value return by the curren-continuation, is this case a, which is what
;;the lambda procedure returns after seting frozen to the value of the 
;;current-continuation which it was passed by call-with-current-continuation.
;
;> (frozen 'again)
;(the call/cc returned again)
;;as frozen was set to the value of the current-continuation, it will return
;;the value it is passed, in this case again, which is returned to list, and
;;then to append to create the output
;
;> (+ 1(frozen 'safely))
;(the call/cc returned safely)
;;like above, but this time the value passed is safely. It doesn't process the
;;(+ 1 as this is waiting on the return value of (frozen 'safely), but as
;;frozen is the current-continuation the procedure does not return to the
;;(+ 1.


;Question 6
(define prenume 
  (lambda (l)
    (if (pair? l)
      (car l))))
;> (prenume '(byron miles))
;byron
;>

(define prenume-list
  (lambda (l)
    (if (list? l)
      (prenume-list2 l '()))))

(define prenume-list2
  (lambda (l r)
    (if (null? l)
    r
    (if (pair? (car l))
      (cons (caar l) (prenume-list2 (cdr l) r))
      (prenume-list2 (cdr l) r)))))
;> (prenume-list '((byron miles) (john smith) (mia hill) (junbin gao)))
;(byron john mia junbin)
;> 
;
;;Or using the procedure map
;> (map prenume '((byron miles) (john smith) (mia hill) (junbin gao)))
;(byron john mia junbin)
;>


;Question 7
(define-syntax list*
  (syntax-rules ()
    ((_ a1)
      'a1)
    ((_ a1 a2 ...)
      (cons a1 (list* a2 ...)))))
;
;> (list* ())
;()
;> (list* 1)
;1
;> (list* 1 2)
;(1 . 2)
;> (list* 1 2 3)
;(1 2 . 3)
;> (list* 1 2 3 4)
;(1 2 3 . 4)
;> (list* 1 2 3 ())
;(1 2 3)
;> 


;Question 8
(define add1
  (lambda (n)
    (+ n 1)))
;
;> (add1 5)
;6
;>

(define sub1
  (lambda (n)
    (- n 1)))
;
;> (sub1 5)
;4
;>

(define addend
  (lambda (n m)
    (if (= m 0)
      n
      (addend (add1 n) (sub1 m)))))
;
;I used tail recusion.
;
;> (trace addend)
;(addend)
;> (addend 5 -1)
; ...
;|(addend 15123 -15119)
;|(addend 15124 -15120)
;|(addend 15125 -15121)
;|(addend 15126 -15122)
;|(addend 15127 -15123)
; ...
;
;> (trace addend)
;(addend)
;> (addend 5 1.5)
; ...
;|(addend 15356 -15349.5)
;|(addend 15357 -15350.5)
;|(addend 15358 -15351.5)
;|(addend 15359 -15352.5)
;|(addend 15360 -15353.5)
; ...
;
;It never stops because m never equals 0


;Question 9
(define primultim
  (lambda (l)
    (if (list? l)
       (values (car l)
        (values (cdr l))))))
;
;> (primultim '(1 2 3))
;1
;(2 3)
;> (primultim '(1 2))
;1
;(2)
;> (primultim '(1))
;1
;()
;>


;Question 10
(letrec ((x 3)) 
  ;x is 3 here
  (letrec ((x 5)) 
    x;x is 5 here
  )
  ;x is back to 3 here
)
;Note: Copying and pasting this into petite will return 5, this is due to the
;x in the body of the inner letrec, which is there because it has to have a
;non-empty body. You can use (display x) to display the value of x at the 
;respective position

