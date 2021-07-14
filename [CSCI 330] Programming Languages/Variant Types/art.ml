#use "expr.ml"
(**** You should not need to modify any code below here ****)

(******************** Random Number Generators ************)

(* makeRand int * int -> (int * int -> int)
   Returns a function that, given a low and a high, returns
   a random int between the limits.  seed1 and seed2 are the
   random number seeds.  Pass the result of this function
   to build 

   Example:
      let rand = makeRand(10,39) in 
      let x =  rand(1,4) in 
          (* x is 1,2,3, or 4 *)
*)

let makeRand (seed1, seed2) = 
  let seed = [| seed1; seed2 |] in
  let s = Random.State.make seed in
  (fun (x,y) -> (x + (Random.State.int s (y-x))))

(********************* Bitmap creation code ***************)

(* 
   You should not have to modify the remaining functions.
   Add testing code to the bottom of the file.
*)

(* Converts an integer i from the range [-N,N] into a float in [-1,1] *)
let toReal (i,n) = (float_of_int i) /. (float_of_int n)

(* Converts real in [-1,1] to an integer in the range [0,255]  *)
let toIntensity z = int_of_float (127.5 +. (127.5 *. z))

(* emitGrayscale :  ((real * real) -> real) * int -> unit
 emitGrayscale(f, N) emits the values of the expression
 f (converted to intensity) to the file art.pgm for an 
 2N+1 by 2N+1 grid of points taken from [-1,1] x [-1,1].
 
 See "man pgm" on turing for a full description of the file format,
 but it's essentially a one-line header followed by
 one byte (representing gray value 0..255) per pixel.
 *)

let emitGrayscale (f,n,fname) =
  let n2p1 = n*2+1 in
  let chan = open_out (fname ^ ".pgm"); in
    begin
      output_string chan (Printf.sprintf "P5 %d %d 255\n" n2p1 n2p1);
      for ix = -n to n do
        for iy = -n to n do
          let x = toReal(ix,n) in
          let y = toReal(iy,n) in
          (* Apply the given random function *)
          let z = f (x,y) in
          (* Convert the result to a grayscale value *)
          let iz = toIntensity(z) in
          (* Emit one byte for this pixel *)
          output_char chan (char_of_int iz)
        done
      done;
      close_out chan;
      ignore(Sys.command ("convert "^fname^".pgm "^fname^".jpg"));
      ignore(Sys.command ("rm "^fname^".pgm"))
    end

(* doRandomGray : int * int * int -> unit
 Given a depth and two seeds for the random number generator,
 create a single random expression and convert it to a
 grayscale picture with the name "art.pgm" *)

let doRandomGrayBuilder  prefix (builder,depth,seed1,seed2) =
  (* Initialize random-number generator g *)
  let g = makeRand(seed1,seed2) in
  (* Generate a random expression, and turn it into an ML function *)
  let e = builder (g,depth) in
  let _ = print_string (exprToString e) in
  let f = eval_fn e in
  (* 301 x 301 pixels *)
  let n = 150 in
  (* Emit the picture *)
  let name = Printf.sprintf "%s_%d_%d_%d" prefix depth seed1 seed2 in
  emitGrayscale (f,n,name)

let doRandomGray  (depth,seed1,seed2) = doRandomGrayBuilder "art_g_1" (build,depth,seed1,seed2)
let doRandomGray2 (depth,seed1,seed2) = doRandomGrayBuilder "art_g_2" (build2,depth,seed1,seed2)


(* emitColor : (real*real->real) * (real*real->real) *
               (real*real->real) * int -> unit
 emitColor(f1, f2, f3, N) emits the values of the expressions
 f1, f2, and f3 (converted to RGB intensities) to the output
 file art.ppm for an 2N+1 by 2N+1 grid of points taken 
 from [-1,1] x [-1,1].
 
 See "man ppm" on turing for a full description of the file format,
 but it's essentially a one-line header followed by
 three bytes (representing red, green, and blue values in the
 range 0..255) per pixel.
 *)
let emitColor (f1,f2,f3,n,fname) =
  let n2p1 = n*2+1 in
  let chan = open_out (fname ^ ".pgm"); in
    begin
      output_string chan (Printf.sprintf "P6 %d %d 255\n" n2p1 n2p1);
      for ix = -n to n do
        for iy = -n to n do
          (* Convert grid locations to [-1,1] *)
          let x = toReal(ix,n) in
          let y = toReal(iy,n) in
          (* Apply the given random function *)
          (x,y) |> f1 |> toIntensity |> char_of_int |> output_char chan;
          (x,y) |> f2 |> toIntensity |> char_of_int |> output_char chan;
          (x,y) |> f3 |> toIntensity |> char_of_int |> output_char chan;
        done
      done;
    close_out chan;
    ignore(Sys.command ("convert "^fname^".pgm  "^fname^".jpg"));
    ignore(Sys.command ("rm "^fname^".pgm")) 
    end
    

(* doRandomColor : int * int * int -> unit
 Given a depth and two seeds for the random number generator,
 create a single random expression and convert it to a
 color picture with the name "art.ppm"  (note the different
 extension from toGray) 
 *)
let doRandomColorBuilder prefix (builder,depth,seed1,seed2) =
  (* Initialize random-number generator g *)
  let g = makeRand (seed1,seed2) in
  (* Generate a random expression, and turn it into an ML function *)
  let e1 = builder (g, depth) in
  let e2 = builder (g, depth) in
  let e3 = builder (g, depth) in
  
  let _ = Printf.printf "red   = %s \n" (exprToString e1) in
  let _ = Printf.printf "green = %s \n" (exprToString e2) in
  let _ = Printf.printf "blue  = %s \n" (exprToString e3) in

  let f1 = eval_fn e1 in
  let f2 = eval_fn e2 in
  let f3 = eval_fn e3 in

  (* 301 x 301 pixels *)
  let n = 150 in
  (* Emit the picture *)
  let name = Printf.sprintf "%s_%d_%d_%d" prefix depth seed1 seed2 in
  emitColor (f1,f2,f3,n,name)

let doRandomColor  (depth,seed1,seed2) = doRandomColorBuilder "art_c_1" (build,depth,seed1,seed2)
let doRandomColor2 (depth,seed1,seed2) = doRandomColorBuilder "art_c_2" (build2,depth,seed1,seed2)
