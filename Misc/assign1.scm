;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;                            COMP318 ASSIGNMENT 1                            ;;
;;                                                                            ;;
;;                                BYRON MILES                                 ;;
;;                                 220057347                                  ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;                                   NOTES                                    ;;
;;                                                                            ;;
;;Where a procedure name was not specified I have used the name of the .txt   ;;
;;file specified for the question as the procedure name.                      ;;
;;e.g. the procedure for Question 3 is called cyl                             ;;
;;                                                                            ;;
;;I have included basic argument checking, so if one or more of the arguments ;;
;;are not valid the procedure will display "Invaid argument"                  ;;
;;                                                                            ;;
;;Below each procedure definition (or for questions 1 and 2) I have included  ;;
;;some sample output of the procedure running on turing.                      ;;
;;                                                                            ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;                               HELPER FUNCTIONS                             ;;
;;                                                                            ;;
;;Define natural? to be an integer? and >= 0                                  ;;
(define natural?
   (lambda (n)
      (and (integer? n) (>= n 0))))
;;                                                                            ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;;Question 1 
;> (* 1 -2 3 -3 4 -4 5)
;-1440
;> 

;;Question 2
;> (car (quote (cdr quote cons)))
;cdr
;>
; 
;quote evaluates to the list (cdr quote cons), 
;car evaluates to head of that list, which is cdr
;
;> (car (car (car '((1 2 3) (4 5)))))
;Exception in car: 1 is not a pair
;Type (debug) to enter the debugger.
;>
; 
;quote (') evaluates to the list ((1 2 3) (4 5)),
;the first car evaluates to the list (1 2 3), 
;the second car evaluates to 1, which is not a list,
;so the third car generates an exception.

;;Question 3
(define cyl
   (let ((pi 3.1415926))
      (lambda (r h)
	 (if (and (number? r) (number? h))
	    (* pi r r h)
	    (display "Invalid argument")))))
;
;> (cyl 2 5)
;62.831852
;> (cyl 'a 5)
;Invalid argument
;> (cyl 2 'b)
;Invalid argument
;> 

;;Question 4
(define meet
   (lambda (d s1 s2)
      (if (and (number? d) (number? s1) (number? s2))
	 (let ((s (+ s1 s2)))
	 (cond
	    ((<= d 0) (display "They have already met"))
	    ((<= s 0) (display "They will never meet"))
	   (else 
	    (* (/ d s) 60))))
	 (display "Invalid argument"))))
;
;> (meet 0 0 0)
;They have already met
;> (meet 1 0 0)
;They will never meet
;> (meet 1 -1 0)
;They will never meet
;> (meet 1 -1 2)
;60
;> (meet 1 'a 2)
;Invalid argument
;> (meet 10 2 2)
;150
;> (meet -1 10 20)
;They have already met
;> 


;;Question 5
(define convert4
   (lambda (n1 n2 n3 n4)
      (if (and (natural? n1) (natural? n2) (natural? n3) (natural? n4))
	 (+ n4 (* 10 n3) (* 100 n2) (* 1000 n1))
	 (display "Invalid argument"))))
;
;> (convert4 1 2 3 4)
;1234
;> (convert4 5 6 9 1)
;5691
;> (convert4 5 'a 'b 1)
;Invalid argument
;> (convert4 'c 6 9 'd)
;Invalid argument
;>

;;Question 6
(define btwn
   (lambda (n)
      (if (number? n)
	 (and (>= n 0.0) (< n 1.0))
	 (display "Invalid argument"))))
;
;> (btwn 1)
;#f
;> (btwn 0)
;#t
;> (btwn .3)
;#t
;> (btwn 'a)
;Invalid argument
;>

;;Question 7
(define acker
   (lambda (x y)
      (if (and (natural? x) (natural? y))
	 (cond
	    ((= x 0) (+ y 1))
	    ((and (= y 0) (> x 0)) (acker (- x 1) 1))
	    ((and (> x 0) (> y 0)) (acker (- x 1) (acker x (- y 1)))))
	 (display "Invalid argument"))))
;
;> (acker 2 1)
;5
;> (acker 'a 1)
;Invalid argument
;> (acker 3 'b)
;Invalid argument
;> (acker 3 2)
;29
;> (acker 3 3)
;61
;> (acker 3 4)
;125
;>

;;Question 8
;Note: sin(3x) = 3 sin(x) - 4 sin^3(x) ... sin(x) = 3 sin(x/3) - 4 sin^3(x/3)
;Due to the nature of the procedure negative values are invalid
(define sine
   (lambda (x)
      (if (and (number? x) (>= x 0.0))
	 (if (< x 0.1)
	    x
	    (- (* 3 (sin(/ x 3))) (* 4 (expt (sin(/ x 3)) 3))))
	 (display "Invalid argument"))))
;
;With built in 'sin'
;> (sin (/ 3.1415926 3))
;0.8660253948528064
;> (sin (/ 3.1415926 6))
;0.49999999226497965
;> (sin (/ 3.1415926 2))
;0.9999999999999997

;With custom 'sine'
;> (sine (/ 3.1415926 3))
;0.8660253948528063
;> (sine (/ 3.1415926 6))
;0.49999999226497965
;> (sine (/ 3.1415926 2))
;0.9999999999999998
;> (sine 0.001)
;0.001
;> (sine 'a)
;Invalid argument
;>

;;Question 9
;Note: This overides the built in 'expt' procedure
(define expt
   (lambda (a n)
      (if (and (number? a) (natural? n))
	 (if (= n 0)
	    1
	    (* a (expt a (- n 1))))
	 (display "Invalid Argument"))))
;
;> (expt 2 10)
;1024
;> (expt 2 'a)
;Invalid Argument
;> (expt 2 34)
;17179869184
;> (expt 17 17)
;827240261886336764177
;> (trace expt)
;(expt)
;> (expt 2 4)
;|(expt 2 4)
;| (expt 2 3)
;| |(expt 2 2)
;| | (expt 2 1)
;| | |(expt 2 0)
;| | |1
;| | 2
;| |4
;| 8
;|16
;16
;>

;;Question 10
(define expor
   (lambda (a n)
      (if (and (number? a) (natural? n))
	 (let ex ((i n) (j 1))
	    (if (= i 0)
	       j
	       (ex ( - i 1) (* j a))))
	 (display "Invaid Argument"))))
;
;> (expor 2 10)
;1024
;> (expor 2 34)
;17179869184
;> (expor 17 17)
;827240261886336764177
;> (trace expor)
;(expor)
;> (expor 2 4)
;|(expor 2 4)
;|16
;16
;> (untrace expor)
;(expor)
;> (expor 'a 4)
;Invaid Argument
;> 
