module AISimple where

import Data.List
import Board
import Interactive

{-
    Întoarce tabla rezultată din aplicarea acelei mutări care maximizează
    scorul adversarului.
-}
step :: Board -> (House, Board)
step board = last $ sortBy compareScores $ successors board

{-
	Compara 2 perechi casă-configurație in functie de scorul obtinut de adversar.
-}
compareScores :: (House, Board) -> (House, Board) -> Ordering
compareScores (h1, b1) (h2, b2) = compare (snd $ scores b1) (snd $ scores b2)

{-
    Urmărește pas cu pas evoluția jocului, conform strategiei implementate.
-}
userMode :: IO ()
userMode = humanVsAI step
