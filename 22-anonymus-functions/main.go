package main

import "fmt"

func main() {
	// An anonymus function is a function with no name
	res := func(n int) int {
		return n * 2
	}

	fmt.Println(res(3))

	// Also we can IIFE, inmediate invoked function expression
	// es como hacer un mini procedure porque queda de un solo uso
	res1 := func(a int, b int) int {
		return a + b
	}(5, 10)

	fmt.Println(res1)
}
