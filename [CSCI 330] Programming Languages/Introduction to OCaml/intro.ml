(* Author:      Nicholas Bellman
 * Instructor:  Prof. Killian
 * Date:        Sept. 7, 2020
 * Assignment:  Lab 1 - Introduction to OCaml
 * Description: A number of simple functions used to increase familiarity with 
                the use of OCaml.
 *)

(* BEGIN PROVIDED FUNCTIONS *)

(* explode : string -> char list
 * (explode s) is the list of characters in the string s in the order in
 *   which they appear
 * e.g.  (explode "Hello") is ['H';'e';'l';'l';'o']
 *)
let explode s =
    let rec _exp i =
        if i >= String.length s then [] else (s.[i])::(_exp (i+1)) in _exp 0;;

(* END PROVIDED FUNCTIONS *)


(* For ALL of the following method stubs (those with failwith "to be written"),
   add documentation comments including expected behavior *)

(*  Accepts a list of integers and returns the sum of the elements of the list.
    
    Tests:
        [1; 2; 3] --> 6
        [6; 6; 6] --> 18
        [10; 10; 10] --> 30
*)
let rec sumList l = 
    match l with
    | [] -> 0
    | hd::tl -> hd + sumList tl
;;

(*  Accepts a positive integer (strictly > 0) and returns a list representation
    of all digits of the integer value.
    
    Tests:
        123 --> [1; 2; 3]
        5125 --> [5; 1; 2; 5]
        6666 --> [6; 6; 6; 6]
*)
let rec digitsOfInt n = 
    match n with
    | 0 -> []
    | _ -> digitsOfInt(n/10) @ (n mod 10)::[]
;;

(*  Adds digits of numbers together, then repeats the process until the remainig
    number is only one digit.

    Returns the number of addition steps to find the final number.

    Tests:
        123 --> 1
        550 --> 2
        6 --> 0
*)
let rec additivePersistence n = 
    if n / 10 = 0 then
        0
    else
        1 + additivePersistence (sumList (digitsOfInt n))
;;

(*  Adds digits of numbers together, then repeats the process until the remainig
    number is only one digit.

    Returns the final number.

    Tests:
        123 --> 6
        550 --> 1
        9999 --> 9
*)
let rec digitalRoot n = 
    if n / 10 = 0 then
        n
    else
        digitalRoot (sumList (digitsOfInt n))
;;

(*  Reverses the elements in a list by appending the head to a new list.

    Tests:
        [1; 2; 3] --> [3; 2; 1]
        [1; 2] --> [2; 1]
        [6; 6; 6; 6] --> [6; 6; 6; 6]
*)
let rec listReverse l = 
    match l with
    | [] -> []
    | hd::tl -> (listReverse tl) @ hd::[]
;;

(*  Checks if a given word is spelled the same forwards and backwards.

    Tests:
        "racecar" --> true
        "cat" --> false
        1001 --> true
*)
let palindrome w = 
    explode w = listReverse (explode w)
;;



(* BEGIN PROVIDED FUNCTIONS *)

(* digits : int -> int list
 * (digits n) is the list of digits of n in the order in which they appear
 * in n
 * e.g. (digits 31243) is [3,1,2,4,3]
 *      (digits (-23422) is [2,3,4,2,2]
 *)
let digits n = digitsOfInt (abs n);;

(* END PROVIDED FUNCTIONS *)

(************** Add Testing Code Here ***************)


