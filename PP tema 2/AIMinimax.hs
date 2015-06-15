module AIMinimax where

import Data.List
import Board
import Consecutive
import Interactive

{-
    Implementați tipul 'Tree s a', al arborilor minimax, unde 's' reprezintă
    tipul stărilor, iar 'a', tipul acțiunilor (în cazul de față, al mutărilor).
-}
data Tree s a = Leaf (Maybe a, s) | Node (Maybe a, s) [Tree s a] deriving Show

{-
    Întoarce cheia radacinii arborelui primit ca parametru.
-}
getKey :: Tree s a -> (a, s)
getKey (Node (Just a, s) childr) = (a, s)
getKey (Leaf (Just a, s)) = (a, s)

{-
    Întoarce starea radacinii arborelui primit ca parametru.
-}
getState :: Tree s a -> s
getState (Node (ma, s) childr) = s
getState (Leaf (ma, s)) = s

{-
    Întoarce copiii radacinii arborelui primit ca parametru.
-}
getChildren :: Tree s a -> [Tree s a]
getChildren (Node _ childr) = childr
getChildren (Leaf _) = []

{-
    Întoarce casa aleasă de o euristică în contextul minimax, alături
    de configurația rezultată.
-}
step :: Board -> (House, Board)
step board = pick (fromIntegral . snd . scores) $ prune 3 $ expand successors board

{-
    Construiește un arbore minimax (eventual infinit), pornind de la funcția
    de generare a succesorilor unui nod și de la rădăcină.
-}
expand :: (s -> [(a, s)]) -> s -> Tree s a
expand f r = buildTree f Nothing r

buildTree f ma s
	| null $ f s = Leaf (ma, s)
	| otherwise = Node (ma, s) (map (\x -> buildTree f (Just $ fst x) (snd x)) (f s))

{-
    Limitează un arbore la numărul de niveluri dat ca parametru.
-}
prune :: Int -> Tree s a -> Tree s a
prune _ (Leaf (ma, s)) = Leaf (ma, s)
prune n (Node (ma, s) childr)
	| n == 0 = Leaf (ma, s)
	| otherwise = Node (ma, s) (map (\x -> prune (n - 1) x) childr)

{-
    Determină valoarea minimax a unui nod MAX. Funcția de evaluare statică
    este dată ca parametru.
-}
maximize :: Consecutive s => (s -> Float) -> Tree s a -> Float
maximize ev (Leaf (ma, s)) = ev s
maximize ev (Node (ma, s) childr) = maximum (map (\x -> if (s >< (getState x)) then maximize ev x else minimize ev x) childr)

{-
    Determină valoarea minimax a unui nod MIN. Funcția de evaluare statică
    este dată ca parametru.
-}
minimize :: Consecutive s => (s -> Float) -> Tree s a -> Float
minimize ev (Leaf (ma, s)) = ev s
minimize ev (Node (ma, s) childr) = minimum (map (\x -> if (s >< (getState x)) then minimize ev x else maximize ev x) childr)

{-
    Întoarce cheia copilului rădăcinii arborelui minimax, ales în conformitate
    cu principiul algoritmului. Funcția de evaluare statică este dată
    ca parametru.
-}
pick :: Consecutive s => (s -> Float) -> Tree s a -> (a, s)
pick ev t = getKey $ last $ sortBy (compareMax (getState t) ev) (getChildren t)

{-
	Compara rezultatele oferite de 2 copii din arborele MiniMax, tinand cont de consecutivitatea starilor.
	Daca starea curenta si cea viitoare (din copil) sunt consecutive, se aplica maximize,
	altfel se aplica minimize.
-}
compareMax :: Consecutive s => s -> (s -> Float) -> Tree s a -> Tree s a -> Ordering
compareMax state ev t1 t2 = compare (getBetter ev t1) (getBetter ev t2)
	where
	getBetter ev t
		| state >< (getState t) = maximize ev t
		| otherwise = minimize ev t
{-
    Urmărește pas cu pas evoluția jocului, conform strategiei implementate.
-}
userMode :: IO ()
userMode = humanVsAI step