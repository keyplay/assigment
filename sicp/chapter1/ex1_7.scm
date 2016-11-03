(define (good-enough? old new)
        (< (/ (abs (- old new)) old) 0.001))
