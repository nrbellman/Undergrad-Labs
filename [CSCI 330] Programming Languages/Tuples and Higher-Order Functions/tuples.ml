(* Author:      Nicholas Bellman
 * Instructor:  Prof. Killian
 * Date:        Oct. 5, 2020
 * Assignment:  Lab 3 - Tuples and Higher-Order Functions
 * Description: A number of functions for use in practicing with tuples and
                higher order functions.
 *)

(* ***** DOCUMENT ALL FUNCTIONS YOU WRITE OR COMPLETE ***** *)

(* Accepts a single parameter as a tuple of three values (d,k,l) where l is a 
list of key-value pairs [ (k1,v1) ; (k2,v2) ; ... ] and finds the first key 
which equals k. If such a key is found, then the value of that pair is returned.
Otherwise, the default value d is returned.

'a * 'b * ('b * 'a) list -> 'a *)
let rec assoc (d,k,l) = 
  let rec helper def key lst =
    match lst with
    | [] -> def
    | (x,y)::lst' ->
      if x = key then
        y
      else 
        helper def key lst'
  in helper d k l
  

(* Accepts a list, l, where we return the list of elements of l where all 
duplicates are removed and all remaining appear in the same order as in l. 

'a list -> 'a list *)

(* fill in the code wherever it says : failwith "to be written" *)
let removeDuplicates l = 
  let rec helper (seen,rest) = 
      match rest with 
        [] -> seen
      | h::t -> 
        let seen' = if List.mem h seen then seen else h::seen in
        let rest' = t in 
	  helper (seen',rest') 
  in
      List.rev (helper ([],l))


(* Takes as input a tuple (f,b) and calls the function f on input b to get a 
pair (b',c'). wwhile should continue calling f on b' to update the pair as long
as c' is true. Once f returns a c' that is false, wwhile should return b'.

('a -> 'a * bool) * 'a -> 'a *)

(* Small hint: see how ffor is implemented below *)
let rec wwhile (f,b) =
  match f b with
  | (b', c') -> 
    match c' with
    | true -> wwhile(f, b')
    | false -> b'
    

(*  Repeatedly updates b with f(b) until b = f(b) and then returns b.

('a -> 'a) * 'a -> 'a *)

(* fill in the code wherever it says : failwith "to be written" *)
let fixpoint (f,b) = wwhile ((let f' b' = (f b', b' <> f b') in f'),b)


(* ffor: int * int * (int -> unit) -> unit
   Applies the function f to all the integers between low and high
   inclusive; the results get thrown away.
 *)

let rec ffor (low,high,f) = 
  if low>high 
  then () 
  else let _ = f low in ffor (low+1,high,f)
