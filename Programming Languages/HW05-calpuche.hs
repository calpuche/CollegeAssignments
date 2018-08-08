splitList :: [a] -> ([a],[a])
splitList n xs = ([take n],[drop (length[a] - n)])

notEmpty :: [a] -> Bool
notEmpty xs = length xs > 0

longEnough :: Int->[a] -> Bool
longEnough 0 [] = true
longEnough n xs = longEnough (n-1) xs

palindrome :: [a] -> Bool
palindrome xs = (reverse xs == xs)

palindrome2 :: [a] -> Bool
palindrome2 xs = (head xs) == (last xs), take 1 xs, drop 1 xs, palindrome2 xs


formPalindrome :: [a] -> [a]
formPalindrome xs = xs, palindrome xs == xs
formPalindrome xs = xs+ reverse xs
