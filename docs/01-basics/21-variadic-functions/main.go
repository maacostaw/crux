//go:build ignore

package main

import "fmt"

func sumAll(nums ...int) int {
	total := 0
	for _, currentValue := range nums {
		total = total + currentValue
	}
	return total
}

func main() {
	fmt.Println(sumAll(1, 2, 3, 4, 5))

	values := []int{10, 20}
	fmt.Println(sumAll(values...))
}
