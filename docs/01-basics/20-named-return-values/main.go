//go:build ignore

package main

import "fmt"

// Named return values es algo asi como declarar las variables de retorno desde el principio
// por eso lit puedo hasta devolver return sin hacer nada y devolverá un resultado
// pq de entrada ya se inicializo x y y
func divide(a int, b int) (x int, y int) {
	x = a / b
	y = a + b
	return
}

func main() {
	q, r := divide(10, 10)
	fmt.Println(q, r)
}
