; a)Sa se scrie o functie care testeaza daca o lista este liniara
; Model matematic:
; liniara(l1l2..ln) = True, 	n = 0
;		      False, l1 - este list
;		      liniara(l2l3..ln), altfel

(defun liniara(lis)
	(cond
			((null lis) t)
			((listp (car lis)) nil)
			(t (liniara(cdr lis)))
	)
) 


; b) o  functie  care  substituie  prima  aparitie  a  unui  element  intr-o lista data.
; Model matematic:
; substi(l1l2..ln, elem1, elem2, gasit) = [], 	n = 0
;					  elem2 + substi(l2l3..ln, elem1, elem2, 1), l1 = elem1 si gasit = 0
;					  l1 + substi(l2l3..ln, elem1, elem2, gasit), altfel

(defun substi(lis elem1 elem2 gasit)
	(cond
		((null lis) ())
		((and (eq (car lis) elem1) (eq gasit 0)) (cons elem2 (substi (cdr lis) elem1 elem2 1)))
		(t (cons (car lis) (substi (cdr lis) elem1 elem2 gasit)))
	)
)

; substiMain (l1l2..ln, elem1, elem2) = substi(l1l2..ln, elem1, elem2, 0)

(defun substiMain (lis elem1 elem2)
	(substi lis elem1 elem2 0)
)


; c)Sa  se  inlocuiasca  fiecare  sublista  a  unei  liste  cu  ultimul  ei  element.
; (a (b c) (d ((e) f))) ==> (a c ((e) f)) ==> (a c f)
; Model matematic:
; substLista(l1l2..ln) = [] , n = 0
;                        l1 + substLista(l2l3..ln), l1 atom							
; 			 ultimul(l1) + subLista(l2l3..ln), l1 e lista
;
; ultimul_aux(l1l2..ln, col) = 	col, n = 0
;				ultimul(l2l3..ln, l1), altfel
;
; ultimul(l1l2..ln) = ultimul_aux(l1l2..ln, [])
;
; main(lis) = substLista(lis), substLista(lis) este liniara
;	      main(substLista(lis)), altfel 

(defun ultimul (lis)
	(ultimul_aux lis ())
)

(defun ultimul_aux(lis col)
	(cond
		((null lis) col)
		(t (ultimul_aux (cdr lis) (car lis)))
	)
)

(defun substLista (lis)
	(cond 
		((null lis) ())
		((atom (car lis)) (cons (car lis) (substLista (cdr lis))))
		(t (cons (ultimul (car lis)) (substLista (cdr lis))))
	)
)

(defun main (lis)
	(cond
		((liniara (substLista lis)) (substLista lis))
		(t (main (substLista lis)))
	)
)


; d)Definiti o functie care interclaseaza fara pastrarea dublurilor doua liste liniare sortate
; Model matematic:
; inter(a1a2..an, b1b2..bm) = 	b1b2..bm, n = 0 si m > 0
;				a1a2..an, m = 0 si n > 0
;				a1 + inter(a2a3..an, b1b2..bm), a1 < b1
;				b1 + inter(a1a2..an, b2b3..bm), a1 > b1
;				a1 + inter(a2a3..an, b2b3..bm), altfel

(defun inter(a b)
	(cond
		((null a) b)
		((null b) a)
		((< (car a) (car b)) (cons (car a) (inter (cdr a) b)))
		((> (car a) (car b)) (cons (car b) (inter a (cdr b))))
		(t (cons (car a) (inter (cdr a) (cdr b))))
	)
)	
