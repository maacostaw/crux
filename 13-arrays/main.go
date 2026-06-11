package main

import "fmt"

func main() {
	// In go an array is a FIXED-SIZE, ordered sequence of elements of the same type
	// var <variable name> [<number of elements>] <variable type>
	var marks [3]int

	marks[0] = 10
	marks[1] = 20
	marks[2] = 50

	fmt.Println(marks)

	// Asign values inplace
	res := [5]int{2, 3, 4, 5, 6}

	fmt.Println(len(res))
}
