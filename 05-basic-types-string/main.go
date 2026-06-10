package main

// Strings is the package to handle strings

import (
	"fmt"
	"strings"
)

func main() {
	firstName := "Miguel"
	lastName := "Angel"
	fullname := firstName + " " + lastName

	fmt.Println(fullname)

	fmt.Println(strings.ToUpper(fullname))
}
