(defun inlocuire (lista nivel elem)
	(cond 
		((null lista) nil)
		((and (atom lista) (eq (mod nivel 2) 0)) lista)
		((and (atom lista) (eq (mod nivel 2) 1)) elem)
		(t (mapcar #'(lambda (lis)
						(inlocuire lis (+ nivel 1) elem)
					 )lista
			)
		)
	)
)

(defun main (lista element)
	(inlocuire lista 1 element)
)