above :: Int -> [Int] -> Bool
above x [] = True
above x (h : t) = x >= h && above x t
