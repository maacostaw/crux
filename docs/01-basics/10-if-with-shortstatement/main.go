//go:build ignore

package main

import "fmt"

func main() {
	items := 3
	pricePerItem := 49

	// osea parece que puedo hacer una asignación previa a la evaluación
	if total := items * pricePerItem; total >= 100 {
		fmt.Println("Elegible for shipping")
	} else {
		fmt.Println("Not eligible for shipping ***")
	}
}
