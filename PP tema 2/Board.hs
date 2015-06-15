{-
    Tabla de joc și mutările posibile.

    Modulul exportă numai funcțiile enumerate mai jos, ceea ce înseamnă
    că doar acestea vor fi vizibile din alte module. Justificarea vizează
    prevenirea accesului extern la structura obiectelor 'Board', și a eventualei
    coruperi a consistenței interne a acestora.
-}
module Board
    ( Board
    , Player (..)  -- Exportăm și constructorii de date 'You' și 'Opponent'.
    , House
    , build
    , yourSeeds
    , oppsSeeds
    , who
    , isOver
    , initialBoard
    , move
    , scores
    , successors
    ) where

import Consecutive

{-
    Jucătorul care urmează să mute.
-}
data Player = You | Opponent deriving (Eq, Show)

{-
    Tipul caselor, definit pentru lizibilitate.
-}
type House = Int

{-
    Tipul 'Board', care reține informații despre starea jocului, inclusiv tabla de joc.
-}
data Board = Board { nextPlayer :: Player -- jucătorul care urmează să mute.
				   , score :: (Int, Int) -- (scorul utilizatorului, scorul oponentului)
				   , playerSeeds :: [Int] -- numarul de seminte din fiecare casuta a utilizatorului
				   , opponentSeeds :: [Int] -- numarul de seminte din fiecare casuta a adversarului
				   } deriving Eq
{-
	Instanțierea clasei 'Show' cu tipul 'Board'. 
	Reprezentarea se va face in felul urmator:
	
	>> daca jocul s-a incheiat:
	
		Game over! Score: (x, y)    --> scoruri aferente jucătorilor 'You', respectiv 'Opponent':
		   4  0  1  0  0  0
		 y                  x
		   0  0  0  0  0  0
	   
	>> altfel:
	
		Next player: You
		   4  4  4  4  4  4
		 0                  0
		   4  4  4  4  4  4
-}
instance Show Board where
	show board = unlines $ [generalInfo, oInfo, scoreInfo, pInfo]
				where
				generalInfo
					| isOver board = "\nGame over! Score: " ++ (show $ score board) ++ "\n"
					| otherwise = "\nNext player: " ++ (show $ nextPlayer board) ++ "\n"
				oInfo = "    " ++ (unwords $ map my_show $ opponentSeeds board)
				pInfo = "    " ++ (unwords $ map my_show $ playerSeeds board)
				scoreInfo = (my_show $ snd $ score board) ++ (take 25 $ repeat ' ') ++ (my_show $ fst $ score board)

my_show :: Int -> String
my_show n
	| n < 10 = " " ++ (show n) ++ " "
	| otherwise = (show n) ++ " "

{-
    Clasa 'Consecutive', care determina dacă în două
    configurații ale tablei trebuie să mute același jucător.
-}
instance Consecutive Board where
    b1 >< b2 = nextPlayer b1 == nextPlayer b2

{-
    Construiește tabla de joc.

    Funcția trebuie să determine dacă jocul este deja încheiat, pe baza
    conținutului caselor.
-}
build :: ([Int], Int)  -- Conținutul caselor și al depozitului utilizatorului
      -> ([Int], Int)  -- Conținutul caselor și al depozitului adversarului
      -> Player        -- Jucătorul aflat la rând
      -> Board         -- Tabla construită
build (pSeeds, pScore) (oSeeds, oScore) player =
	Board player (pScore, oScore) pSeeds oSeeds

{-
    Întoarce conținutul caselor și al depozitului utilizatorului.
-}
yourSeeds :: Board -> ([Int], Int)
yourSeeds board = ((playerSeeds board), (fst $ score board))

{-
    Întoarce conținutul caselor și al depozitului adversarului.
-}
oppsSeeds :: Board -> ([Int], Int)
oppsSeeds board = ((opponentSeeds board), (snd $ score board))

{-
    Întoarce jucătorul aflat la rând.
-}
who :: Board -> Player
who board = nextPlayer board

{-
    Întoarce 'True' dacă jocul s-a încheiat (unul dintre jucatori nu mai are seminte de mutat).
-}
isOver :: Board -> Bool
isOver board = if (all (== 0) $ playerSeeds board) || (all (== 0) $ opponentSeeds board)
				then True
				else False
{-
    Tabla inițială.
-}
initialBoard :: Board
initialBoard = Board You (0, 0) [4, 4, 4, 4, 4, 4] [4, 4, 4, 4, 4, 4]

{-
    Realizează o mutare pornind de la casa furnizată ca parametru, în funcție
    de configurația actuală a tablei și de jucătorul aflat la rând.

    Întoarce aceeași configurație dacă mutarea nu poate fi efectuată
    din diverse motive, precum numărul eronat al casei, sau casa goală.
-}
move :: House -> Board -> Board
move h board
	-- daca mutarea nu este valida
	| h < 1 || h > 6 || (noSeeds == 0) = board
	-- altfel
	| otherwise = Board newPlayer newScore newPSeeds newOSeeds
	where
		start -- casuta de start
			| (nextPlayer board) == You = h - 1
			| otherwise = 6 - h
		noSeeds -- numarul de seminte ce trebuie mutate
			| (nextPlayer board) == You = playerSeeds board !! start
			| otherwise = (reverse $ opponentSeeds board) !! start
		stop = mod (start + noSeeds) 13 -- casuta de stop
		addition = div noSeeds 13 -- numarul de seminte ce trebuie adaugate in fiecare casuta

		initialConfig -- lista circulara ce retine configuratia initiala (este descrisa amanuntit in readme)
			| (nextPlayer board) == You = (take start $ playerSeeds board) ++ [0] ++ (drop (start + 1) $ playerSeeds board) 
										  ++ [fst $ score board] ++ (reverse $ opponentSeeds board)
			| otherwise = (take start $ reverse $ opponentSeeds board) ++ [0] ++ (drop (start + 1) $ reverse $ opponentSeeds board) 
						  ++ [snd $ score board] ++ playerSeeds board

		tempConfig -- configuratie temporara, fara a lua in calcul posibilitatea adaugarii ultimei seminte intr-o casuta goala
			| start < stop = [ if (p > start && p <= stop) then ad + 1 else ad | p <- [0 .. 12], let ad = (initialConfig !! p) + addition]
			| otherwise = [ if (p > stop && p <= start) then ad else ad + 1 | p <- [0 .. 12], let ad = (initialConfig !! p) + addition]

		newConfig -- noua configuratie, dupa efectuarea mutarii
			| stop < 6 && tempConfig !! stop == 1 && tempConfig !! (12 - stop) > 0 =
				[ if (p == stop || p == 12 - stop) 
					then 0 
					else if (p == 6)
							then (tempConfig !! p) + 1 + (tempConfig !! (12 - stop))
							else tempConfig !! p 
				| p <- [0 .. 12] ]
			| otherwise = tempConfig

		-- noile valori retinute de tabla de joc
		newPlayer
			| stop == 6 = nextPlayer board
			| otherwise = if (nextPlayer board == You) then Opponent else You
		newScore
			| (nextPlayer board) == You = (newConfig !! 6, snd $ score board)
			| otherwise = (fst $ score board, newConfig !! 6)
		newPSeeds
			| (nextPlayer board) == You = take 6 newConfig
			| otherwise = drop 7 newConfig
		newOSeeds
			| (nextPlayer board) == You = reverse $ drop 7 newConfig
			| otherwise = reverse $ take 6 newConfig
			
{-
    Întoarce scorurile (utilizator, adversar).
    Calculul trebuie să țină cont de eventuala încheiere a jocului.
-}
scores :: Board -> (Int, Int)
scores board = if (isOver board)
				then ((foldl (+) 0 $ playerSeeds board) + (fst $ score board),
					 (foldl (+) 0 $ opponentSeeds board) + (snd $ score board))
				else score board
{-
	Întoarce perechile casă-configurație, reprezentând mutările care pot fi
    făcute într-un singur pas, pornind de la configurația actuală.
-}
successors :: Board -> [(House, Board)]
successors board = [ (h, move h board) | h <- [1 .. 6], board /= move h board ] 