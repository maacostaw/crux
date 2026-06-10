package main

// fmt is the standard package for formatting input and output

// imports brings external packages into the file that you're working

import (
	"fmt"
	"math"
)

func main() {
	// packageName.FunctionName -> call a function from a package
	fmt.Println("sqrt(25)", math.Sqrt(25))
}
