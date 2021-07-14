(* CSCI 330: Programming Assignment 5
 * misc5.ml
 *)

(* For this assignment, you may use the following library functions:

   List.map
   List.fold_left
   List.fold_right
   List.split
   List.combine
   List.length
   List.append
   List.rev

   See http://caml.inria.fr/pub/docs/manual-ocaml/libref/List.html for
   documentation.
*)



(* Do not change the skeleton code! The point of this assignment is to figure
 * out how the functions can be written this way (using fold). You may only
 * replace the   failwith "to be implemented"   part. *)



(*****************************************************************)
(******************* 1. Warm Up   ********************************)
(*****************************************************************)


let sqsum xs = 
  let f a x = a + (x * x) in
  let base = 0 in
    List.fold_left f base xs

let pipe fs s = 
  let f a x = x a in
  let base = s in
    List.fold_left f base fs

let pipec fs = 
  let f a x = fun x' -> pipe fs x' in
  let base = fun c -> pipe fs c in
    List.fold_left f base fs

let rec sepConcat sep sl = match sl with 
  | [] -> ""
  | h :: t -> 
      let f a x = a ^ sep ^ x in
      let base = h in
      let l = t in
        List.fold_left f base l

let stringOfList f l = "[" ^ sepConcat "; " (List.map f l) ^ "]"

let prodLists l1 l2 =
  let f a x = List.map2 (fun a x -> a * x) l1 l2 in
  let base = [] in
  let args = l1 in
    List.fold_left f base args

(*****************************************************************)
(******************* 2. Big Numbers ******************************)
(*****************************************************************)

(* clone : 'a -> int -> 'a list 

clone takes as input x and an integer n. The result is a list of length n, where each element is x. 
If n is 0 or negative, clone will return the empty list. 

# clone 3 5;;
- : int list = [3; 3; 3; 3; 3] 
# clone "foo" 2;;
- : string list = ["foo"; "foo"]
# clone clone (-3);;
- : ('_a -> int -> '_a list) list = [])
*)
let rec clone x n = if (n <= 0) then [] else x::(clone x (n - 1))

(*
padZero : int list -> int list -> int list * int list 

padZero takes two lists: [x1,...,xn] [y1,...,ym] and adds zeros in front to make the lists equal in length. 

# padZero [9;9] [1;0;0;2];;
- : int list * int list = ([0;0;9;9],[1;0;0;2]) 
# padZero [1;0;0;2] [9;9];;
- : int list * int list = ([1;0;0;2],[0;0;9;9]) 
*)
let rec padZero l1 l2 = 
   let l1len = List.length l1 in
   let l2len = List.length l2 in
    ((clone 0 (l2len-l1len)@l1), (clone 0 (l1len-l2len)@l2))

(*
removeZero : int list -> int list 

removeZero takes a list and removes a prefix of trailing zeros. 

# removeZero [0;0;0;1;0;0;2];;
- : int list = [1;0;0;2] 
# removeZero [9;9];;
- : int list = [9;9] 
# removeZero [0;0;0;0];;
- : int list = [] 
*)

let rec removeZero l = 
  match l with
  | 0::t -> removeZero t
  | _ -> l

let bigAdd l1 l2 = 
  let add (l1, l2) = 
    let f a x = 
      let (carry, result) = a in
        let (x1, x2) = x in
          let sum = x1 + x2 + carry in
            (sum / 10, (sum mod 10)::result) in
    let base = (0, []) in
    let args = List.combine (List.rev (0::l1)) (List.rev (0::l2)) in
    let (carry, res) = List.fold_left f base args in
      carry::res
  in 
    removeZero (add (padZero l1 l2))

(* EXTRA CREDIT BELOW *)

let rec mulByDigit i l =
  let mult (l1, l2) = 
    let f a x = 
      let (carry, accumulator) = a in
        let product = (i * x) + carry in
          let d = product mod 10 in
            let carry = product / 10 in
              let result = d::accumulator in
                carry, result in
    let base = (0, []) in
    let args = List.rev l in
    let (carry, res) = List.fold_left f base args in
      carry::res
  in 
    removeZero (mult (i, l))

let bigMul l1 l2 = 
  let f a x = 
    let (s, result) = a in
      let (num, digit) = x in
        let product = mulByDigit digit (num @ (clone 0 s)) in
          (s + 1, bigAdd result product) in
  let base = (0, []) in
  let args = List.combine (clone l1 (List.length l2)) (List.rev l2) in
  let (_, res) = List.fold_left f base args in
    res
