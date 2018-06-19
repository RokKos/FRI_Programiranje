fib :: Int -> Int
fib x
  | x <= 2 = 1
  | otherwise = fib (x - 1) + fib (x - 2)