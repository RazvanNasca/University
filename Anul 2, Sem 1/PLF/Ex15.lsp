; 15. Sa se construiasca lista nodurilor unui arbore de tipul (1) parcurs in postordine.
; Topul (1): (A 2 B 0 C 2 D 0 E 0)
; Model matematic:
;						 []																, n = 0
; postordine(l1l2..ln) = [l1]															, l2 = 0
;						 postordine(stang(l1l2..ln)) + postordine(drept(l1l2..ln)) + l1	, altfel
(defun postordine(l)
    (cond
		((null l) nil)
		((eq (cadr l) 0) (list (car l)))
		(t (append (postordine(stang l)) (postordine(drept l)) (list (car l))))
    )  
)

; Model matematic:
;								 []												, n = 0
; parcurg_st(l1l2..ln, nv, nm) = []												, nv = 1 + nm 
;								 l1 + l2 + parcurg_st(l3..ln, nv + 1, l2 + nm)	, altfel
; l1l2..ln 	- arborele
; nv		- numarul de varfuri (noduri)
; nm		- numarul de muchii
(defun parcurg_st(arb nv nm)
	(cond
		((null arb) nil)
		((= nv (+ 1 nm)) nil)
		(t (cons (car arb) (cons (cadr arb) (parcurg_st (cddr arb) (+ 1 nv) (+ (cadr arb) nm)))))
		; in loc de cons de cons -> ( append ( list (car arb) (cadr arb) ) (parcurg....)
	)
)

; Model matematic:
; stang(l1l2..ln) = parcurg_st(l3l4..ln, 0, 0)
(defun stang(arb)
	(parcurg_st (cddr arb) 0 0)
)

; Model matematic:
;								 []										, n = 0
; parcurg_dr(l1l2..ln, nv, nm) = l1l2..ln								, nv = 1 + nm 
;								 parcurg_dr(l3l4..ln, 1 + nv, l2 + nm)	, altfel
;
(defun parcurg_dr(arb nv nm)
	(cond
		((null arb) nil)
		((= nv (+ 1 nm)) arb)
		(t (parcurg_dr (cddr arb) (+ 1 nv) (+ (cadr arb) nm)))
	)
)

; Model matematic:
; drept(l1l2..ln) = parcurg_dr(l3l4..ln, 0, 0)
(defun drept(arb)
	(parcurg_dr (cddr arb) 0 0)
)