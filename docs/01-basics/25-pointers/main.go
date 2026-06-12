//go:build ignore

package main

import "fmt"

func main() {
	// Pointers guardan la dirección en memoria de cualquier valor

	// &x -> address of x (crea un pointer)
	// *x -> derreference x (ve a esa dirección y lee/escribe)

	score := 10
	fmt.Println("before:", score)

	addScore(&score)

	fmt.Println("after:", score)
}

func addScore(score *int) {
	*score = *score + 5
}
