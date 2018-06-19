datatype 'a stream = Cons of 'a * (unit -> 'a stream)  (*hack, preostanek streama zapakiramo v funkcijo, ki jo izracunamo ko rabimo nadaljevanje streama*)

val enice =
  let fun generate () = Cons (1, generate)
  in generate ()
  end

(* Prvih n elementov toka pretvori v seznam *)
fun to_list 0 _ = []
  | to_list n (Cons (x, s)) = x :: (to_list (n-1) (s ()))

(* n-ti element toka *)
fun elementAt 0 (Cons (x, _)) = x
  | elementAt n (Cons (_, s)) = elementAt (n-1) (s ())

fun head (Cons(x, _)) = x

fun tail (Cons(x, s)) = (s())

fun from_list [] r = Cons (r, (fn() => from_list [] r))
   |from_list (x::xs) r = Cons(x, (fn() => from_list xs r))

fun map f (Cons(x, s)) = (Cons(f(x), fn()=>map f (s())))


val four = from_list [1,2,3] 4

fun count k = Cons (k, (fn () => count (k+1)))

val nat = count(0)

val nat' = 
    let fun from k = Cons(k, (fn () => count (k+1)))
    in from 0
    end

(* Neoptimalna imlementacija*)
val fib' = 
    let fun fibo 1 = 1
            |fibo 0 = 0
            |fibo f = (fibo (f-1)) + (fibo (f-2)) (*Pomni!!! operatorji imajo nizjo prioriteto kot klic funkcije*)

    fun fk k = Cons(fibo(k), (fn() => fk(k+1)))
    in fk(0)
    end

val fib = 
    let fun generate a b = Cons(a, (fn () => generate b (a + b)))
    in generate 0 1
    end


fun veckratniki_stevila k =
    let fun stej k n = Cons(k * n, (fn() => stej k (n + 1)))
    in stej k 0
    end



val veckratniki =
    let fun generate k = Cons(veckratniki_stevila k, (fn() => generate (k+1)))
    in generate 0
    end