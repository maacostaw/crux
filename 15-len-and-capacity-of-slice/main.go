package main

import "fmt"

func main() {
	// lenght is how many elements you currently have
	// capacity is how many elements can you store
	// make([]<type>, <lenght>, <capacity>) its a good approach for performance to pre-define this things
	scores := make([]int, 0, 5)

	fmt.Println(scores, len(scores), cap(scores))

	scores = append(scores, 100)

	scores = append(scores, 220, 3000)
	fmt.Println("after appending 200 and 3000", "lenght", len(scores), "capacity", cap(scores), scores)

	scores = append(scores, 55, 45)
	fmt.Println("after appending 55 and 45", "lenght", len(scores), "capacity", cap(scores), scores)

	// If in case we excede capacity, go grows the backing array (usually doubles)
	scores = append(scores, 60)
	fmt.Println("after appending 60", "lenght", len(scores), "capacity", cap(scores), scores)

	todos := []string{"do youtube", "workout everyday"}

	more := []string{"learn golang"}

	// Si quiero adicionar los elementos de un slice a otro, tengo que usar spread notation
	todos = append(todos, more...)
	fmt.Println(todos)
}
