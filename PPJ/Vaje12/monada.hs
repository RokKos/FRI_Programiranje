-- http://learnyouahaskell.com/chapters 
-- od FRI studenta in gre zelo globoko v detajle
f x = x - 12

data Maybe a = Just a | Nothing

data Output o a = Out { value :: a, output :: o } 
  deriving Show

write :: o -> Output o ()
write x = Out {value = (), output = x}

instance Functor (Output o) where
    -- fmap :: (a -> b) -> Output a -> Output b
    fmap f (Out x o) = Out (f x) o

-- trik damo Monoid, to je struktura v matematiki, ki ima enoto in operacijo da se zdruzuje
instance Monoid o => Applicative (Output o) where
    -- pure :: a -> Output a
    pure x = Out x mempty  -- mempty prazna enota tipa

    -- (<*>) :: Output o (a -> b) -> Output o a -> Output o b
    (Out f o) <*> (Out x o') = Out (f x) (mappend o o')  -- funkcija, ki ima stranski ucine uporabimo na argumentu in zraven se dodamo izpis

-- primer uporabe
-- (1)
g = Out (\x -> x*x) "hello "
h = Out 42 "wordls"

j = g <*> h

-- (2)

k = [(\x -> x + x), (\x -> x * x)]
l = [1,2,3,4]
o = k <*> l

instance Monoid o => Monad (Output o) where
    -- return  = pure

    -- (>>=) :: Output o a -> (a -> Output o b) -> Output o b 
    -- to operacijo si lahko predstavljamo kot potiskalni, ki svojo cisto vrednost potisne drugi funkciji naprej in una funkcija naprej potisne naslednji funkciji
    (Out x o) >>= f = let Out rezultat izhod = (f x) in -- (f x) vrne Out
                      Out rezultat (mappend o izhod)


-- do je isto kot da bi napisali zaporedoma >>= (bind operator)
-- https://en.wikibooks.org/wiki/Haskell/do_notation
primer = do write "hello "
            x <- return 6
            write "world"
            y <- return 7
            write "!"
            return (x * y)

-- ekvivalentno bi to zapisali tako
-- ubistvu to nam dela tale bind operator >>=
--primer' = let Out _ o = write "hello " in 
--    let Out x o' = return 6 in
--    let Out _ o'' = write "!" in
--    let Out rezultat o''' = return (x * x) in
--    Out rezultat (o ++ o ' ++ o'' ++ o''') 

-- run je funkcija, ki vzame vhod in vrne rezultat in preostanek vhoda
data Input i a = In { run :: [i] -> (a, [i]) }
-- In je construktor tipa Input

-- to je ubistvu read
consume :: Input i i
consume = In {run = (\input -> (head input, tail input))}
-- primer uporabe: (run consume) ["a", "b", "c"]

instance Functor (Input i) where
    -- fmap :: (a -> b) -> (Input i a) -> (Input i b)
    fmap f xi =  In (\vhod -> let (x, vhod') = (run xi vhod) in (f x, vhod'))
    -- to nas spomnija na neskoncne tokove v SML-ju

-- primer uporabe: run (fmap f consume) [1,2,3]

instance Applicative (Input i) where
    -- pure :: a -> Input i a
    pure x = In (\vhod -> (x, vhod))

    fi <*> xi = In (\vhod -> let (f, vhod') = run fi vhod
                                 (x, vhod'') = run xi vhod
                            in   (f x, vhod''))

instance Monad (Input i) where
    -- (>>=) :: Input i a -> (a -> Input i b) -> Input i b
    xi >>= f = In (\vhod -> let (x, vhod') = run xi vhod
                            in run (f x) vhod')

primer2 = do x <- consume
             y <- consume
             return (x * y + 2)

-- primer uporabe: run primer2 [6,7,8]

-- To ne dela
-- Rabili bi monande transformer, ampak se zelo zakomplicira
-- primer3 = do write "Kako ti je ime?"
--             ime <- consume
--             write "Zivjo " ++ ime