main = return ()

f x = x + x

-- funkcija, ki vzame število in vrne njegovo faktorielo
factorial :: Int -> Int
factorial n
    | n <= 1 = 1
    | otherwise = n * factorial (n-1)
    
    
-- nasa implementacija
fact :: Integer -> Integer  -- ker int overflowa
fact n = if n == 0  then 1 else n * fact (n - 1)

-- ne rabimo pisati tipa ker, haskel sam lahko izpelje, ampak je dobra praksa
fact2 0 = 1
fact2 n = n * fact2(n - 1)


-- v hasklu ima funkcija prednost pred operatorji, zato rabimo pisati oklepaje

-- neenacaj v hasklu  je /=
fib :: Integer -> Integer
fib n 
  | n <= 0 = 0
  | n == 1 = 1
  | otherwise = (fib (n - 1)) + (fib (n - 2)) 
  
-- Seznami
prazen = []
seznam = [1,2,3]
sestavjen = 1:[2,3,4]
neskoncni = [0..]
omejeni = [1..20]
  
member :: Int -> [Int] -> Bool
member x [] = False
member x (y : ys)
    | x == y = True
    | otherwise = member x ys
    
-- ali takole
member2 :: Int -> [Int] -> Bool
member2 x [] = False
member2 x (h : t) = x == h || member2 x t

above :: Integer -> [Integer] -> Bool
above x [] = True
above x (h : t) = x >= h && above x t

-- Izpeljani seznami
prvih_dvajset = [1..20]
soda = [n | n <- prvih_dvajset, n `mod` 2 == 0]

-- \x anonimna oz. lambda funkcija
kvadrati n = map (\x -> x*x) [1..n]

kvadrati2 n =
  let kvadriraj k = k * k in
  map kvadriraj [1..n]
  
kvadrati3 n =
  map kvadriraj [1..n] where
  kvadriraj k = k * k

-- tukaj ne rabimo okelepajev ker sta oba operatorja (pri i+1 )
pari n =
  [(i,j) |  i <- [1..n], j <- [(i+1)..n]]
  
type Square = (Int, Int)
type Queen = Square

board :: Int -> [Square]
board n =
  [(i,j) |  i <- [1..n], j <- [1..n]]

attacks :: Queen -> Square -> Bool
attacks (x1,y1) (x2, y2) = x1 == x2 || y1 == y2 || abs (x1 - x2) == abs(y1 - y2)

attack :: [Queen] -> Square -> Bool
attack [] p = False
attack (h:t) p = attacks h p || attack t p

place1 :: Int -> Int -> [Queen] -> [Square]
place1 n x qs = [(x,y) | y <- [1..n], not (attack qs (x,y))]

place :: Int -> Int -> [Queen] -> [[Queen]]
place n x qs 
  | x > n = [qs]
  | otherwise = concat [place n (x+1) (novQ:qs) | novQ <- (place1 n x qs)]
  
  
queens n = place n 1 []

queens2 n = place 1 [] where
  place x qs 
    | x > n = [qs]
    | otherwise = concat [place (x+1) (novQ:qs) | novQ <- (place1 n x qs)]