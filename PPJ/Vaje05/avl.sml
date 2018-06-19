datatype avltree = Empty | Node of int * int * avltree * avltree (*vsebina, visina, levi, desni sin *)

val t =
    Node(5, 3,
        Node(3, 2,
            Node(1, 1, Empty, Empty),
            Node(4, 1, Empty, Empty)
            ),
        Node(8, 1, Empty, Empty)
    )

fun height Empty = 0
    | height (Node(v,h,l,r)) = h

fun leaf x = Node(x, 1, Empty, Empty)

fun node (x, l, r) = Node(x, Int.max(height(l), height(r)) + 1, l, r)

val t' = 
    node(5, 
        node(3, 
            leaf(1),
            leaf(4)
            ),
        leaf(8)
    )

val bipolarno = 
    node(5,
        node(4,
            node(3,
                node(2,Empty, Empty),
                Empty),
            Empty),
        Empty
        )

fun toList Empty = []
    | toList (Node(x, h, l, r)) = toList(l) @ [x] @ toList(r)

fun search s Empty = false  
   |search s (Node(x,h,l,r)) = case Int.compare(s,x) of
      EQUAL => true
    | LESS => search s l
    | GREATER => search s r

fun imbalance Empty = 0
   |imbalance (Node(_,_,l,r)) = height(l) - height(r)

fun leftson Empty = Empty
    |leftson (Node(_, _, l, _)) = l

fun rightson Empty = Empty
    |rightson (Node(_, _, _, r)) = r

fun value Empty = 0
   |value (Node(x, _, _, _)) = x

(*fun rotateLeft Empty = Empty
   |rotateLeft (Node(x,h,l,r)) = (Node(value(r),h,Node(x,h - 1,r, leftson(r)),rightson(r)))
*)

fun rotateLeft (Node(x, _, a, (Node(y, _, b, c)))) = node(y, node(x,a,b), c)
   |rotateLeft t = t (*To je pa ce je drugacno drevo in nam bo pokrilo vse corner case, zelo lazje da nam ni treba vseh jezikov samo pisati*)


fun rotateRight (Node(y, _, (Node(x, _, a, b)), c)) = node(x, a, node(y,b,c))
   |rotateRight t = t 


 fun balance Empty = Empty
    |balance (t as Node(x,h,l,r)) = 
    case imbalance t of 
    2 => 
        
        if imbalance l >= 0 then
            rotateRight(t)
        else
            rotateRight(node(x, rotateLeft l, r))

        (*lahko bi tudi tako naredili da izpostavimo rotateRight, ker if vrne vrednost ki je avl tree
        
        rotateRight(if imbalance l >= 0 then
            t
        else
            node(x, rotateLeft l, r))
        )
        
        *)
    | ~2 =>
        if imbalance l <= 0 then
            rotateLeft t
        else
            rotateLeft(node(x, rotateRight l, r))

    | _ => t

fun add x Empty = leaf(x)
    | add x (t as Node(y,h,l,r)) = 
    case Int.compare(x, y) of
        EQUAL => t
        | LESS => add x l
        | GREATER => add x r
