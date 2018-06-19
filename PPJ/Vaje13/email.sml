type email = {
    from : string,
    to : string list,
    date : int * int * int * int * int * int,
    subject : string,
    content : string
}

val example_email : email = {
    from = "Andrej Bauer <Andrej.Bauer@andrej.com>",
    to = ["Timotej Lazar <Timotej.Lazar@fri.uni-lj.si>", "Peter Gabrovšek <Peter.Gabrovšek@fri.uni-lj.si>"],
    date = (2018,05,29,09,55,42),
    subjet = "Izpit iz PPJ",
    content = "Prosim, da rešitve izpita popravljata zelo strogo.\n\n Lep pozdrav, Andrej"
}

fun toString (ls : string list) : string = 
    case ls of
        (h::nil) => h
        | (h::l) => h ^ ", " ^ toString(t)

fun emailToString (e : email) : string = 
    "From: " ^ (#from e) ^ "\n" ^
    "To: " ^ (toString(#to e)) ^ "\n" ^
    "Date: " ^ Int.toString (#1 (#date e)) ^ " ... \n" ^
    "Subject: " ^ (#subject e) ^ "\n" ^ 
    "Content: " ^ (#content e) ^ "\n";