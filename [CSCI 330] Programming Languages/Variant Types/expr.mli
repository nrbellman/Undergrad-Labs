type expr
type rng = int * int -> int
type builder_fun = rng * int -> expr

val exprToString : expr -> string

val eval : expr * float * float -> float

val eval_fn : expr -> float * float -> float


val build : builder_fun
val build2 : builder_fun

val g1 : unit -> builder_fun * int * int * int

val c1 : unit -> builder_fun * int * int * int