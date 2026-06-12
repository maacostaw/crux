//go:build ignore

package main

import "fmt"

func main() {
	// common collection type
	// dynamic and can grow
	// []<type>{values...}
	results := []int{1, 2, 63}
	fmt.Println("original list", results)

	// Modify by index
	results[0] = 15

	// access by index
	fmt.Println("first", results[0], "last", results[len(results)-1])

	// add element
	results = append(results, 10)

	// check lenght
	fmt.Println("new lenght", len(results))
}
