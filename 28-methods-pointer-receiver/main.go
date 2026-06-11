package main

import "fmt"

type User struct {
	Name string
	Age  int
}

func main() {
	u := User{Name: "Miguel", Age: 24}
	fmt.Println(u.Age)
	u.Birthday()
	fmt.Println("after", u.Age)
}

// Pointer receiver significa que acá recibimos el puntero, so podemos hacer cambios al objeto
// a diferencia de val receiver donde recibimos una copia y no podemos modificar el objeto
func (u *User) Birthday() {
	u.Age++
}
