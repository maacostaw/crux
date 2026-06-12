//go:build ignore

package main

import (
	"fmt"

	"github.com/maacostaw/crux/internal/greet"
)

func main() {
	msg1 := greet.Hello("Miguel")
	fmt.Println(msg1)
}
