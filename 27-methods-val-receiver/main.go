package main

import "fmt"

type User struct {
	Name string
	Age  int
}

func main() {
	u := User{Name: "Miguel", Age: 24}
	fmt.Println(u.Intro())
}

// val receiver significa que este método recibe una copia del usuario
// Esto es como crearle un método al struct User, tipo es como un método de objeto
func (u User) Intro() string {
	return fmt.Sprintf("Hi, I am %s", u.Name)
}
