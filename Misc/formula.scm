(define f1a
   (lambda (R)
      (/ (/ 32000000.0 R) (+ 20.0 (/ 32000000.0 R)))))

(define f1b
   (lambda (R)
      (/ (/ 8000000.0 R) (+ 20.0 (/ 8000000.0 R)))))

(define f2
   (lambda (L)
      (/ (/ (* 128 L) 100000.0) (+ 280.0 (/ (* 128 L) 100000.0)))))