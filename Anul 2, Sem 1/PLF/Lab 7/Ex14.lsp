; Folosid functii MAP, scrie-ti un program care calculeaza adancimea unui arbore n-ar
; myMaxim(l1l2..ln) = maxim(l1, l2l3...ln) 
 
(defun myMaxim(L)
	(maxim (car L) (cdr L))
)

; maxim(elem, l1l2..ln) = elem, n = 0
; 			  maxim(l1, l2l3..ln), l1 > elem
;			  maxim(elem, l2l3..ln), altfel

(defun maxim(elem L) 
	(cond
		((null L) elem)
		((> (car L) elem) (maxim (car L) (cdr L) ))
		(t (maxim elem (cdr L)))
	)
)

; adancime(l1l2..ln) = 0, n = 1
; 		       1 + max(adancime(l2), adancime(l3), ..., adancime(ln)), altfel
; (a (b (c)) (d) (e (f)))


(defun adancime(L)
	(cond
		((null (cdr L)) 0)
		(t (+ 1 (apply #'myMaxim (list (mapcar #'adancime (cdr L))))))
	)
)