//go:build ignore

package main

import (
	"fmt"
)

func main() {
	views1 := 1000
	views2 := 2000
	totalViews := views1 + views2

	likes := 10
	likes++
	likes++

	// Go no convierte tipos implícitamente por eso si la división puede dar decimal toda indicarlo
	avgViews := float64(totalViews) / 7

	fmt.Println(totalViews, likes, avgViews)

	rating1 := 4.5
	rating2 := 5.1

	avgRating := (rating1 + rating2) / 2

	fmt.Println(avgRating)
}
