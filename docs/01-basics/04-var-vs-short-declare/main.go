//go:build ignore

package main

import (
	"fmt"
)

func main() {

	// Variable declaring types
	var city string
	city = "london"

	// Variable infering type
	var channel = "test channel"

	// Short declaration infering type
	subscribers := 5000
	subscribers = subscribers + 1000

	// Short declaration declaring multiple variables
	likes, comments := 110, 30

	fmt.Println(city, channel, subscribers, likes, comments)

}
