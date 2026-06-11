package main

import "fmt"

// una función con 1 solo retorno
// func <nombre>(<arg1> <type1>, <arg2> <type2>, ...) <returnType>
func add(a int, b int) int {
	return a + b
}

// una función con más de un retorno
// func <nombre>(<arg1> <type1>, <arg2> <type2>, ...) (<returnType1>, <returnType2>, ...)
func sumAndProduct(a int, b int) (int, int) {
	sum := a + b
	product := a * b
	return sum, product
}

func main() {
	sum1 := add(10, 20)

	s, p := sumAndProduct(3, 4)

	fmt.Println(sum1, s, p)

	// Avoid one of the returns
	onlySum, _ := sumAndProduct(5, 11)
	fmt.Println(onlySum)
}
