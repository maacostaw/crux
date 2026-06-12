//go:build ignore

package main

import "fmt"

func main() {
	// Creación de un map inicializado
	// <name> := map[<keyType>]<valueType>
	ages := map[string]int{
		"miguel": 24,
		"angel":  25,
		"test":   20,
	}
	fmt.Println(ages["miguel"], len(ages))

	//Creación de un map no inicializado
	var scores map[string]int
	fmt.Println(scores, scores["a"])

	//Creación de un map con make()
	// make(map[K]V)
	values := make(map[string]int)

	values["math"] = 90

	fmt.Println(values, values["map"])

	users := map[string]string{
		"u1": "Miguel",
		"u2": "Ángel",
		"u3": "Vex",
	}

	fmt.Println("users before delete", users)
	// To delete an element
	delete(users, "u2")
	// If the key doesnt exist, nothing happen
	delete(users, "u2000")

	fmt.Println("users after delete", users)

}
