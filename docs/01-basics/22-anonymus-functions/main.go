//go:build ignore

package main

import "fmt"

func main() {
	// una función anonima es una función sin nombre
	res := func(n int) int {
		return n * 2
	}

	fmt.Println(res(3))

	// También podemos IIFE, inmediate invoked function expression
	// es como hacer un mini procedimiento porque queda de un solo uso
	// esto tiene más usos con defers por ejemplo (ver lección 24)
	res1 := func(a int, b int) int {
		return a + b
	}(5, 10)

	fmt.Println(res1)
}
