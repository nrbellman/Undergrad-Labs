(* Author:      Nicholas Bellman
 * Instructor:  Prof. Killian
 * Date:        Sept. 17, 2020
 * Assignment:  Lab 2 - Tail Recursion
 * Description: A number of simple functions used to further increase familiarity with 
                the use of OCaml, as well as provide practice with nested functions and
				tail recursion.
*)

(* 
	***** PROVIDE COMMENT BLOCKS AND IMPLEMENTATIONS FOR THE FOLLOWING FUNCTIONS ***** 
	***** INCLUDE TYPE SIGNATURES ***** 
*)

(*	Calculates the squre root of a number to a certain tolerance.
	float -> float -> float
	
	tol: the tolerance the square root is to be calculated to.
	x: the number whose square root is to be calculated.

	guess: a recursive helper function for comparing results to the tolerance.
*)
let rec sqrt tol x = 
	let rec guess tol x y =
		if abs_float (y *. y -. x) <= tol then
			y
		else 
			guess tol x ((y +. x /. y) /. 2.)
	in 
	guess tol x 1.
;;	

(* Your solution for sqrt2 should not need a lambda. Replace
   everything to the right of the =. *)
let rec sqrt2 = sqrt 0.00001;;

(*  Calculates the factorial of a number using an if-then statement.
	int -> int

	x: the number whose factorial is to be calculated.	
*)
let rec factorial1 x = 
	if x = 1 then
		1
	else
		x * factorial1 (x - 1)
;;

(*	Calculates the factorial of a number using pattern matching.
	int -> int

	x: the number whose factorial is to be calculated.
*)
let rec factorial2 x =
	match x with
	| 1 -> 1
	| _ -> x * factorial2 (x - 1)
;;

(*	Calculates the factorial of a number using tail recursion.
	int -> int

	x: the number whose factorial is to be calculated.

	fact_helper: a recursive helper function for factorial 3. Does most of the work calculating factorials.
*)
let rec factorial3 x = 
	let rec fact_helper x a =
		match x with
		| 0 -> a
		| _ -> fact_helper (x - 1) (x * a)
	in
	fact_helper x 1
;;

(*	Calculates the xth number in the fibonacci number sequence.
	int -> int

	x: position of the fibonacci number to be found.
	
	fib_helper:
		x: the current fibonacci position being worked on.
		y: the first addend of the current fibonacci number.
		z: the second addend of the current fibonacci number.
*)
let rec fibonacci x =
	let rec fib_helper x y z =
		match x with
		| 0 -> y
		| 1 -> z
		| _ -> fib_helper (x - 1) z (y + z)
	in
	fib_helper x 0 1
;;

(*	Reverses a list tail recursively.
	'a list -> 'a list
	
	l: the list to be reversed. 
	
	rev_helper:
		l: the list being reversed.
		a: a list of items from l in reverse order.
*)
let rec rev l = 
	let rec rev_helper l a = 
		match l with
		| [] -> a
		| hd::tl -> rev_helper tl (hd::a)
	in
	rev_helper l []

(*	Applies a function to a list of values recursively. 
	('a -> 'b) -> 'a list -> 'b list

	f: the function to be applied.
	l: the list of items that f is to be applied on. 
*)
let rec map f l = 
	match l with
	| [] -> []
	| hd::tl -> (f hd)::(map f tl)


(*	Applies a function to a list of values using tail recursion.
	('a -> 'b) -> 'a list -> 'b list

	f: the function to be applied.
	l: the list of items that f is to be applied on. 

	map_helper:
		f: the function to be applied.
		l: the list of items that f is to be applied on.
		r: the resulting list, after f has been applied to l. Must be reversed before returning.

*)
let rec map2 f l =
	let rec map_helper f l r =
		match l with
		| [] -> r
		| hd::tl -> map_helper f tl (f hd::r)
	in
	rev (map_helper f l [])

(*	Generates a list of values from two given endpoints (inclusive).

	a: the value of the first endpoint.
	b: the value of the final endpoint. Must be larger than a.
*)
let rec range a b =
	if a > b then
		[]
	else a::(range (a + 1) b)

let roots : float list = map sqrt2 (map float_of_int (range 1 20))
