type status = {version : string, code : int}

datatype transfer_encoding = chunked | compress | deflate | gzip | identity

datatype weekday = Pon | Tor | Sre | Cet | Pet | Sob | Ned


datatype field =
      Server of string
    | ContentLength of int
    | ContentType of string
    | TransferEncoding of transfer_encoding
    | Date of string
    | Expires of string 
    | LastModified of string
    | Location of string;

datatype smer_neba = jug | sever | zahod | vzhod;  (*construktor ki sprejme nic argumentov*)
val s = jug;

type response = {status: status, headers: field list, body: string};
val mystatus = {version = "HTTP/1.1", code = 200} ;

val myfield = Server "Apatche"
val myfield2 = ContentLength 42

val r = {
    status={version="HTTP/1.1", code=404},
    headers=[Server "nginx/1.6.2", ContentLength 12],
    body="hello world!"
}

fun statusToString(s: status): string =
    (#version s) ^ " " ^
    (Int.toString (#code s)) ^ " " ^
    (case (#code s) of
          100 => "Continue"
        | 101 => "Switching Protocols"
        | 102 =>  "Processing"
        | 103 =>  "Early Hints"
        | 201 =>  "Created"
        | 202 =>  "Accepted"
        | 204 =>  "No Content"
        | 200 => "OK"
        | 301 => "Moved Permanently"
        | _   => "")
    
fun TransferEncodingToString(te : transfer_encoding) : string =
    case te of 
        chunked => "chunked"
        | compress => "compress"
        | deflate => "deflate"
        | gzip => "gzip"
        | identity => "identity"
        
    fun fieldToString(s: field): string =
        case s of 
            Server sr => "Server: " ^ sr
            | ContentLength cl => "Content-Length: " ^ Int.toString(cl)
            | ContentType a => a
            | TransferEncoding a => TransferEncodingToString(a)
            | Date a => a
            | Expires a => a 
            | LastModified a => a
            | Location a => a;

(*foldr je pa za zdruzevanje ubistvu je to reduce pri mapreduce ki smo ga imeli pri OPB*)

val arr = [1,2,3,4];
foldr Int.+ 0 arr;

    fun responseToString(r : response) : string = 
        String.concatWith "\n" [
        (statusToString(#status r)),

        String.concatWith "\n" (map fieldToString (#headers r)),  (*Mapa funkcijo cez array in vrne stringe ki jih da skupi*)
        (#body r)]

    fun responseToString2(r : response) : string = 
        String.concatWith "\n" (
        [statusToString(#status r)] @
        (map fieldToString (#headers r)) @ (*@ daje skupaj sezname*)
        [#body r])

    (* fun responseToString3(r : response) : string = 
        String.concatWith "\n" [
        statusToString(#status r),
        [map fieldToString (#headers r)],
        #body r]
        *)