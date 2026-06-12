//go:build ignore

package main

import (
	"fmt"
	"log"
	"strconv"
)

func main() {
	// Go dont use exceptions for normal failures
	// functions return errors as normal return values

	// val, err := something()
	// if err != null {handle error}

	if err := run(); err != nil {
		log.Fatal(err)
	}
}

func run() error {

	input := "30"
	level, err := parseLevel(input)

	if err != nil {
		return err
	}

	fmt.Println("selected level", level)
	return err
}

func parseLevel(s string) (int, error) {
	// (value, err)
	// nill error -> success
	// non nil -> failure

	// Pattern
	n, err := strconv.Atoi(s)
	if err != nil {
		return 0, fmt.Errorf("level must be a number")
	}

	if n < 1 || n > 5 {
		return 0, fmt.Errorf("level must be between 1 and 5")
	}

	// We return the value and nil, because there is no error
	return n, nil
}
