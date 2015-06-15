#lang racket

(require test-engine/racket-tests)
(include "decision-tree-test.rkt")

;; primește un nod si întoarce tipul acestuia
(define get-type cadr)

;; pentru frunze:

;; primește un nod; întoarce #t dacă acesta este frunză, #f altfel
(define is-leaf?
  (λ (node) (equal? (get-type node) 'leaf)))

;; primește un nod frunză; întoarce clasa exemplelor din frunză
(define get-leaf-class caddr)

;; pentru frunze speciale (BONUS):

;; primește un nod; întoarce #t dacă nodul este frunză specială (frunză fără exemple sau cu exemple de clase diferite), #f altfel
;; dacă nu implementați bonus, trebuie ca funcția să întoarcă #f
(define is-special-leaf?
  (λ (node) (or (equal? (get-type node) 'default) (equal? (get-type node) 'majority))))

;; primește un nod frunză specială; întoarce tipul frunzei speciale (trebuie să fie unul dintre membrii 2 și 3 ai strings
;; clasa exemplelor din frunza specială va fi verificată tot cu get-leaf-class
(define get-special-leaf-type get-type)

;; pentru noduri care nu sunt frunze:

;; primește un nod; întoarce numele atributului verificat în nod
(define get-attribute-name caddr)

;; primește un nod si întoarce nodurile copii ale acestuia
(define get-children cdddr)

;; primește un nod și o valoare a atributului verificat în nod
;; întoarce nodul copil (nod intern sau frunză) corespunzător valorii date
(define get-child
  (λ (node value) (assoc value (get-children node))))

;; opțional: verificare nod
;; primește un argument; întoarce #t dacă argumentul este o reprezentare corectă a unui nod (frunză sau nu) din arbore; #f altfel
(define is-node?
  (λ (node)
    (if (or (is-leaf? node) (is-special-leaf? node))
        (empty? (get-children node))
        (not (empty? (get-children node))))))

; asamblare funcții de acces arbore
(define functions (list is-leaf? get-leaf-class is-special-leaf? get-special-leaf-type get-attribute-name get-child is-node?))

;; TASK (pregătitor):
;; scrieți (manual) în formatul ales un arbore de decizie pentru exemple conținând 1 atribut - shape, care are două valori - round și square
;; un exemplu va fi în clasa "yes" dacă este rotund, și în "no" altfel
;; arborele trebuie să fie astfel:
;;    shape
;;     / \
;; round square
;;   /     \
;; yes     no
(define tree-1 
  '(none node shape
         (round leaf yes)
         (square leaf no)
   ))
;; fiecare nod este reprezentat ca o lista, astfel:
;; - pe prima pozitie valoarea atributului din nodul parinte pentru exemplele din nodul curent
;; - pe a doua pozitie tipul nodului: 'node, 'leaf, 'default, 'majority
;; - pe a treia pozitie:
;;           -> clasa, daca e frunza
;;           -> atributul, daca e nod
;; - de la a patra pozitie pana la final copii nodului curent (daca nu e frunza)

(check-expect (is-node? tree-1) #t)
(check-expect (is-leaf? tree-1) #f)
(check-expect (get-attribute-name tree-1) 'shape)
(check-expect (not (get-child tree-1 'round)) #f)
(check-expect (not (get-child tree-1 'square)) #f)
(check-expect (is-leaf? (get-child tree-1 'round)) #t)
(check-expect (is-leaf? (get-child tree-1 'square)) #t)
(check-expect (is-special-leaf? (get-child tree-1 'round)) #f)
(check-expect (get-leaf-class (get-child tree-1 'round)) 'yes)
(check-expect (get-leaf-class (get-child tree-1 'square)) 'no)


;; TASK
;; scrieți funcția de mai jos pentru a calcula entropia unui set de exemple, fiecare exemplu conținând informație despre clasa sa
;; funcția log2 este implementată în decision-tree-test

;; examples: o listă de exemple (setul S), nevidă, în care fiecare exemplu este o listă de perechi, una dintre ele fiind (<nume-atribut-clasă> <clasă>)
;; class-attribute: o listă de forma (<nume-atribut-clasă> <clasă-1> <clasă-2> <clasă-3>)
;; întoarce: entropia setului de exemple în raport cu clasa, H(S) = - sumă-peste-clase p(clasă)*log2 p(clasă)
;;   unde p(clasă) este numărul de exemple cu clasa <clasă> împărțit la numărul de exemple din setul S

(define compute-enthropy
  (λ (examples class-attribute)
    
    ; < label-class > este < nume-atribut-clasă >
    ; < no-ex > este numarul total de exemple din setul S
    (let ([label-class (car class-attribute)] [no-ex (length examples)])
      
      ; parcurgem toate clasele
      ;
      ; < class > este clasa curenta
      ; < classes > este setul de clase curent
      ; < sum > este H(S)
      (let iter ([class (cadr class-attribute)] [classes (cddr class-attribute)] [sum 0])
        
        ; < no-ex-class > este numarul de exemple cu clasa curenta (class)
        ; < p > este raportul dintre numărul de exemple cu clasa class si numărul total de exemple
        ; < new-sum > este actualizarea lui H(S); adaugam valoarea pentru clasa curenta
        (let* ([no-ex-class (length (filter (λ (x) (equal? (cdr (assoc label-class x)) class)) examples))]
               [p (/ no-ex-class no-ex)]
               [new-sum (if (equal? p 0) sum (- sum (* p (log2 p))))])
          
          ; daca am parcurs toate clasele intoarcem H(S)
          ; atfel continuam parcurgerea
          (if (empty? classes)
              new-sum
              (iter (car classes) (cdr classes) new-sum)))))))    

(define tolerance 0.001)
;(check-within (compute-enthropy '() '(classname yes no)) 0 tolerance) ; expect error
(check-within (compute-enthropy '(((shape . round) (classname . yes)) ((shape . square) (classname . yes))) '(classname yes no)) 0 tolerance)
(check-within (compute-enthropy '(((shape . round) (classname . no)) ((shape . square) (classname . no))) '(classname yes no)) 0 tolerance)
(check-within (compute-enthropy '(((shape . round) (classname . yes)) ((shape . square) (classname . no))) '(classname yes no)) 1 tolerance)
(check-within (compute-enthropy '(((shape . round) (classname . yes)) ((shape . square) (classname . no)) ((shape . square) (classname . no))) '(classname yes no maybe)) 0.918 tolerance)
(check-within (compute-enthropy '(((shape . round) (classname . yes)) ((shape . square) (classname . no)) ((shape . square) (classname . maybe))) '(classname yes no maybe)) 1.584 tolerance)

;; TASK
;; scrieți funcția de mai jos pentru a calcula câștigul informațional al unui atribut în raport cu clasa, pentru un set de exemple

;; examples: o listă de exemple, nevidă, în care fiecare exemplu este o listă de perechi, una dintre ele fiind (<nume-atribut-clasă> <clasă>)
;; attribute: o listă de forma (<nume-atribut> <valore-1> <valoare-2> <valoare-3>)
;; class-attribute: o listă de forma (<nume-atribut-clasă> <clasă-1> <clasă-2> <clasă-3>)
;; întoarce: câștigul informațional al atributului, G(S, A) = H(S) - sumă-peste-valori-A p(v)*H(Sv)
;;   unde p(v) este numărul de exemple cu valoarea v pentru A împărțit la numărul de exemple din S
;;   iar Sv este mulțimea exemplelor din S care au valoarea v pentru A

;; functie ce intoarce setul de exemple ce au valoarea < attr-value > pentru atributul < attr-name >
(define set-for-value
  (λ (attr-name attr-value examples)
      (filter (λ (x) (equal? (get-attr-value attr-name x) attr-value)) examples)))

(define compute-gain
  (λ (examples attribute class-attribute)
    
    ; < attr-name > este numele atributului al carui castig informational va fi calculat
    ; < no-ex > este numarul total de exemple din setul S
    ; < Hs > este entropia setului de exemple (setul S)
    (let ([attr-name (car attribute)]
          [no-ex (length examples)]
          [Hs (compute-enthropy examples class-attribute)])
      
      ; parcurgem toate valorile atributului
      ;
      ; < attr-value > este valoarea curenta a atributului
      ; < attr-values > este setul de valori curent
      ; < sum > este suma-peste-valori a atributului
      (let iter ([attr-value (cadr attribute)] [attr-values (cddr attribute)] [sum 0])

        ; < Sv > este setul de exemple ce au valoarea < attr-value > pentru atributul < attr-name >
        ; < p > este raportul dintre numărul de exemple din Sv si numărul de exemple din S
        ; < HSv > este entropia setului de exemple Sv
        ; < new-sum > este actualizarea sumei-peste-valori a atributului
        (let* ([Sv (set-for-value attr-name attr-value examples)]
               [p (/ (length Sv) no-ex)]
               [HSv (if (empty? Sv) 0 (compute-enthropy Sv class-attribute))]
               [new-sum (if (equal? p 0) sum (+ sum (* p HSv)))])

          ; daca am parcurs toate valorile intoarcem castigul informational
          ; atfel continuam parcurgerea
          (if (empty? attr-values)
              (- Hs new-sum)
              (iter (car attr-values) (cdr attr-values) new-sum)))))))

(check-within (compute-gain 
               '(((shape . round) (classname . yes)) ((shape . square) (classname . yes)))
               '(shape round square)
               '(classname yes no)
               ) 0 tolerance)
(check-within (compute-gain 
               '(((shape . round) (classname . no)) ((shape . square) (classname . yes)))
               '(shape round square)
               '(classname yes no)
               ) 1 tolerance)
(check-within (compute-gain 
               '(((shape . round) (classname . no)) ((shape . round) (classname . yes)))
               '(shape round square)
               '(classname yes no)
               ) 0 tolerance)
(check-within (compute-gain 
               '(((shape . round) (size . 1) (classname . yes))
                 ((shape . round) (size . 2) (classname . no))
                 ((shape . square) (size . 1) (classname . yes))
                 ((shape . square) (size . 2) (classname . yes)))
               '(shape round square)
               '(classname yes no)
               ) 0.311 tolerance)
(check-within (compute-gain 
               '(((shape . round) (size . 1) (classname . yes))
                 ((shape . round) (size . 2) (classname . no))
                 ((shape . square) (size . 1) (classname . yes))
                 ((shape . square) (size . 2) (classname . no))
                 ((shape . square) (size . 2) (classname . yes)))
               '(size 1 2)
               '(classname yes no)
               ) 0.419 tolerance)

;; TASK
;; creați un arbore de decizie pentru mulțimea dată de exemple, pentru muțimea dată de atribute, și pentru clasa dată

;; examples: o listă de exemple, nevidă, în care fiecare exemplu este o listă de perechi, una dintre ele fiind (<nume-atribut-clasă> <clasă>)
;; attributes: o listă de liste de forma (<nume-atribut> <valore-1> <valoare-2> <valoare-3>)
;; class-attribute: o listă de forma (<nume-atribut-clasă> <clasă-1> <clasă-2> <clasă-3>)
;; întoarce: un arbore de decizie, în formatul ales, corespunzător cu argumentele date


;; verifica daca un set de exemple contine clase diferite
;; daca nu, intoarce false
;; daca da, intoarce clasa majoritara (util pentru bonus)
(define (diff-classes? class-label examples)
  
  ; < classes > este multimea tuturor claselor ce apar in examples
  ; < without-dup > este multimea claselor ce apar in examples, fara duplicate
 (let* ([classes (map cdr (map (λ (x) (assoc class-label x)) examples))]
        [without-dup (remove-duplicates classes)])
   
   ; daca toate exemplele au aceeasi clasa
   (if (equal? (length without-dup) 1)
       #f ; intoarce false
       
       ; altfel, calculeaza clasa majoritara parcurgand toate clasele din < without-dup >
       ;
       ; < max > este numarul maxim de aparitii ale unei clase
       ; < majority > este clasa cu numarul maxim de aparitii
       ; < class-names > setul curent de clase
       (let iter ([max 0]
                  [majority 'none]
                  [class-names without-dup])
         
         ; daca am parcurs toate clasele
         (if (empty? class-names)
             majority ; intoarce clasa majoritara
             
             ; altfel continua pargurcerea
             ;
             ; < class > este clasa curenta
             ; < number > este numarul de aparitii ale clasei curente
             (let* ([class (car class-names)]
                    [number (count (λ (x) (equal? class x)) classes)])
 
               ; verific daca trebuie sa reactualizez < max > si < majority >
               (if (> number max)
                   (iter number class (cdr class-names))
                   (iter max majority (cdr class-names)))))))))       


;; FUNCTIA DE CREARE
(define create-tree
  (λ (examples attributes class-attribute)
    
    ; < clas-label > este < nume-atribut-clasă >
    (let ([class-label (car class-attribute)])
      
      ; creez recursiv fiecare nod din arbore
      (let iter (
                 [value 'none] ; valoarea atributului din parinte (ex: pentru nodul 'yes este 'round)
                 [majority 'no-examples] ; clasa majoritara a exemplelor din nodul părinte
                 [set examples] ; setul curent de exemple (in recursivitate se face impartirea dupa valorile atributului)
                 [remaining-attr attributes] ; atributele ramase la pasul curent
                 )
        
        ; daca nu mai am exemple
        (if (empty? set)
            (list value 'default majority) ; creez o frunza < default >
            
            ; altfel
            ;
            ; < new-majority > este clasa majoritara a exemplelor din nodul curent
            (let ([new-majority (diff-classes? class-label set)])
              
              ; daca toate exemplele au aceeasi clasa
              (if (equal? new-majority #f)
                  
                  ; creez o frunza normala ce contine
                  ;  -> valoarea atributului din parinte
                  ;  -> clasa exemplelor din setul curent
                  (list value 'leaf (cdr (assoc class-label (car set))))
                  
                  ; daca au clase diferite, dar nu mai am atribute dupa care sa impart
                  (if (empty? remaining-attr)
                      
                      ; creez o frunza < majority >
                      (list value 'majority new-majority)
                      
                      ; altfel continui recursiv constructia arborelui
                      ;
                      ; < sorted-attr > este lista atributelor ramase, sortate descrescator dupa castigul informational
                      ; < best-attr > este atributul cu castig informational maxim, cu valorile sale
                      ; < attr-name > este numele atributului cu castig informational maxim
                      (let* (
                             [sorted-attr (sort remaining-attr #:key (λ (x) (compute-gain set x class-attribute)) >)]
                             [best-attr (car sorted-attr)]
                             [attr-name (get-attr-name best-attr)]
                             )
; apelul recursiv concateneaza 2 liste:
;  -> prima contine valoarea atributului din parinte, tipul si numele atributului curent
;  -> a doua contine lista de noduri copii ai nodului curent
(append (list value 'node attr-name) 
        (map    (λ (x) (iter x new-majority (set-for-value attr-name x set) (cdr sorted-attr)))    (get-attr-values best-attr)))
)))))))))

(define I-DID-THE-BONUS #t)

(check-expect (perform-test functions 'food-small create-tree) #t)
(check-expect (perform-test functions 'food-big create-tree) #t)
(check-expect (perform-test functions 'objects create-tree) #t)
(check-expect (perform-test functions 'weather create-tree) #t)

(check-expect (and (perform-test functions 'food-small create-tree) (get-tree-height functions (get-tree 'food-small create-tree) (get-test 'food-small))) 2)
(check-expect (and (perform-test functions 'food-big create-tree)   (get-tree-height functions (get-tree 'food-big create-tree) (get-test 'food-big)))   4)
(check-expect (and (perform-test functions 'weather create-tree)    (get-tree-height functions (get-tree 'weather create-tree) (get-test 'weather)))    3)
(check-expect (and (perform-test functions 'objects create-tree)    (get-tree-height functions (get-tree 'objects create-tree) (get-test 'objects)))    3)

(if I-DID-THE-BONUS (display "BONUS DONE\n") (display "bonus not done\n"))
(check-expect (if I-DID-THE-BONUS (perform-test functions 'bonus create-tree) #t) #t)
(when I-DID-THE-BONUS (display (get-tree 'bonus create-tree)) (newline))

(test)

(define E '(
   ((Food . great) (Chat . yes) (Speedy . yes) (Price . adequate) (Bar . no) (BigTip . yes))
   ((Food . great) (Chat . no) (Speedy . yes) (Price . adequate) (Bar . no) (BigTip . yes))
   ((Food . mediocre) (Chat . yes) (Speedy . no) (Price . high) (Bar . no) (BigTip . no))
   ((Food . great) (Chat . yes) (Speedy . yes) (Price . adequate) (Bar . yes) (BigTip . yes))
   ))
(define C '(BigTip yes no))
(define A '((Food great mediocre) (Chat yes no) (Speedy yes no) (Price adequate high) (Bar yes no)))