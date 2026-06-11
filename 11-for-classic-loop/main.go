package main

import (
	"fmt"
)

func main() {

	// Classic for <init> <stop condition> <post operation>
	for i := 1; i <= 5; i++ {
		fmt.Println(i)
	}

	N := 10
	sum := 0

	for i := 1; i <= N; i++ {
		sum = sum + i
	}

	fmt.Println(sum)
}
