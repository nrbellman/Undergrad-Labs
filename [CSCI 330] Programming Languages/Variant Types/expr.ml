(* 
 * Name: Nicholas Bellman
 * Date: 17 October 2020
 * Course: CSCI 330 - Programming Languages
 * Assignment: Variant Types
 *
 * Assignment Attribution:
 *   This lab is based on code by Chris Stone (lab from CSE 130 by Sorin Lerner at UCSD)
 *
 * Description: OCaml turns math into A R T I S T I C  E X P R E S S I O N
 *)

let pi = 4.0 *. (atan 1.0)

type expr = 
  | VarX
  | VarY
  | Sine     of expr
  | Cosine   of expr
  | Average  of expr * expr
  | Times    of expr * expr
  | Thresh   of expr * expr * expr * expr	
(* TODO: add two new "types" of expressions *)
  | Max      of expr * expr
  | Volumize of expr * expr * expr

type rng = int * int -> int
type builder_fun = rng * int -> expr

let rec exprToString e =
  match e with
  | VarX -> "x"
  | VarY -> "y"
  | Sine e1 -> "sin(pi*" ^ exprToString e1 ^ ")"
  | Cosine e1 -> "cos(pi*" ^ exprToString e1 ^ ")"
  | Average (e1, e2) -> "((" ^ exprToString e1 ^ "+" ^ exprToString e2 ^ ")/2)"
  | Times (e1, e2) -> exprToString e1 ^ "*" ^ exprToString e2
  | Thresh (e1, e2, e3, e4) -> "(" ^ exprToString e1 ^ "<" ^ exprToString e2 ^ "?" ^ exprToString e3 ^ ":" ^ exprToString e4 ^ ")"
  | Max (e1, e2) -> "(" ^ exprToString e1 ^ ">=" ^ exprToString e2 ^ "?" ^ exprToString e1 ^ ":" ^ exprToString e2 ^ ")"
  | Volumize (e1, e2, e3) -> "(" ^ exprToString e1 ^ "*" ^ exprToString e2 ^ "*" ^ exprToString e3 ^ ")"

(* build functions:
     Use these helper functions to generate elements of the expr
     datatype rather than using the constructors directly.  This
     provides a little more modularity in the design of your program *)

let buildX()                       = VarX
let buildY()                       = VarY
let buildSine(e)                   = Sine(e)
let buildCosine(e)                 = Cosine(e)
let buildAverage(e1,e2)            = Average(e1,e2)
let buildTimes(e1,e2)              = Times(e1,e2)
let buildThresh(a,b,a_less,b_less) = Thresh(a,b,a_less,b_less)

(* TODO: add two new buildXXXXXXX functions *)
let buildMax(e1,e2)                = Max(e1, e2)
let buildVolumize(e1, e2, e3)      = Volumize(e1, e2, e3)

let rec eval (e, x, y) =
  match e with
  | VarX -> x
  | VarY -> y
  | Sine e1 -> sin(pi *. eval(e1, x, y))
  | Cosine e1 -> cos(pi *. eval(e1, x, y))
  | Average (e1, e2) -> ((eval(e1, x, y) +. eval(e2, x, y)) /. 2.0)
  | Times (e1, e2) -> (eval(e1, x, y)) *. (eval(e2, x, y))
  | Thresh (e1, e2, e3, e4) -> 
    if eval(e1, x, y) < eval(e2, x, y) then 
      eval(e3, x, y) 
    else
      eval(e4, x, y)
  | Max (e1, e2) ->
    if eval(e1, x, y) >= eval(e2, x, y) then
      eval(e1, x, y)
    else
      eval(e2, x, y)
  | Volumize (e1, e2, e3) -> (eval(e1, x, y) *. eval(e2, x, y) *. eval(e3, x, y))

(* (eval_fn e (x,y)) evaluates the expression e at the point (x,y) and then
 * verifies that the result is between -1 and 1.  If it is, the result is returned.  
 * Otherwise, an exception is raised.
 *)
let eval_fn e (x,y) = 
  let rv = eval (e,x,y) in
  assert (-1.0 <= rv && rv <= 1.0);
  rv

let sampleExpr =
      buildCosine(buildSine(buildTimes(buildCosine(buildAverage(buildCosine(
      buildX()),buildTimes(buildCosine (buildCosine (buildAverage
      (buildTimes (buildY(),buildY()),buildCosine (buildX())))),
      buildCosine (buildTimes (buildSine (buildCosine
      (buildY())),buildAverage (buildSine (buildX()), buildTimes
      (buildX(),buildX()))))))),buildY())))

let sampleExpr2 =
  buildThresh(buildX(),buildY(),buildSine(buildX()),buildCosine(buildY()))




(******************* Functions you need to write **********)

(* build: (int*int->int) * int -> Expr 
   Build an expression tree.  The second argument is the depth, 
   the first is a random function.  A call to rand(2,5) will give
   you a random number in the range [2,5)  
   (2 inclusive, and 5 exclusive).

   Your code should call buildX, buildSine, etc. to construct
   the expression.
*)

let rec build (rand,depth) = 
  match (rand, depth) with
    | (_,0) -> if rand(0,1) = 0 then buildX() else buildY()
    | (_,d) -> let n = rand(0,7) in
      match n with
      | 0 -> buildX()
      | 1 -> buildY()
      | 2 -> buildSine(build(rand, d - 1))
      | 3 -> buildCosine(build(rand, d - 1))
      | 4 -> buildAverage(build(rand, d - 1), build(rand, d - 1))
      | 5 -> buildTimes(build(rand, d - 1), build(rand, d - 1))
      | _ -> buildThresh(build(rand, d -1), build(rand, d - 1), build(rand, d - 1), build(rand, d - 1))

let rec build2 (rand,depth) = 
  match (rand, depth) with
    | (_,0) -> if rand(0,1) = 0 then buildX() else buildY()
    | (_,d) -> let n = rand(0,9) in
      match n with
      | 0 -> buildX()
      | 1 -> buildY()
      | 2 -> buildSine(build(rand, d - 1))
      | 3 -> buildCosine(build(rand, d - 1))
      | 4 -> buildAverage(build(rand, d - 1), build(rand, d - 1))
      | 5 -> buildTimes(build(rand, d - 1), build(rand, d - 1))
      | 6 -> buildThresh(build(rand, d -1), build(rand, d - 1), build(rand, d - 1), build(rand, d - 1))
      | 7 -> buildMax(build(rand, d - 1), build(rand, d - 1))
      | _ -> buildVolumize(build(rand, d - 1), build(rand, d - 1), build(rand, d - 1))


(* g1,c1 : unit -> ((int*int->int) * int -> Expr) * int * int * int
 * these functions should return the parameters needed to create your 
 * top color / grayscale pictures.
 * they should return (function,depth,seed1,seed2)
 * Function should be build or build2 (whichever you used to create
 * the image)
 *)

let g1 () = (build, 13, 112358, 6) 

let c1 () = (build2, 10, 0, 0)