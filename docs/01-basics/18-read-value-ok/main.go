//go:build ignore

package main

import "fmt"

func main() {
	points := map[string]int{
		"a": 10,
		"b": 0, //Valid value
	}

	// Problem: its not differenciable between points["b"] and a value that doesnt exist
	fmt.Println("a", points["a"])
	fmt.Println("b", points["b"])
	fmt.Println("c", points["c"])

	//Acá hacemos eso de if with short statement
	if val, ok := points["c"]; ok {
		fmt.Println(val)
	} else {
		fmt.Println("c key is not present in the map")
	}

	prices := map[string]int{
		"xyz": 500,
		"def": 1800,
	}

	// To iterate over de map
	total := 0
	for key, value := range prices {
		fmt.Println(key, value)
		total = total + value
	}
	fmt.Println(total)

	// To using only the key
	for key := range prices {
		fmt.Println(key)
	}

	// To using only the value
	for _, key := range prices {
		fmt.Println(key)
	}
}
