package main

import "fmt"

func main() {
	const appName = "Go Basics"

	// Typed constants
	const maxUpload int = 25
	const discountedPrice float64 = 10.3

	fmt.Println(appName, maxUpload, discountedPrice)
}
