//go:build ignore

package main

import "fmt"

// modela datos en forma de objetos

type User struct {
	ID    int
	NAME  string
	EMAIL string
	AGE   int
}

func main() {
	u1 := User{
		ID:    1,
		NAME:  "Miguel",
		EMAIL: "maacostaw3@gmail.com",
		AGE:   24,
	}
	fmt.Println(u1)

	// Mutable by default
	u1.AGE = 200

	fmt.Println(u1)

	u2 := User{
		NAME: "jhon",
	}

	fmt.Println("Partial user", u2)
}
