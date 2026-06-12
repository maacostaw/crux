//go:build ignore

package main

import (
	"fmt"
	"io"
	"net/http"
)

func main() {
	url := "https://jsonplaceholder.typicode.com/todos"

	resp, err := http.Get(url)
	if err != nil {
		fmt.Println(err)
		return
	}

	defer resp.Body.Close()

	if resp.StatusCode != http.StatusOK {
		fmt.Println(resp.Status)
		return
	}

	// Forma estandar para leer strings, recibimos un []byte
	bodyBytes, err := io.ReadAll(resp.Body)
	if err != nil {
		fmt.Println(err)
		return
	}

	// Luego lo pasamos a string
	bodyText := string(bodyBytes)
	max := 250

	// Es necesario ajustar el max porque más adelante queremos imprimir
	if len(bodyText) < max {
		max = len(bodyText)
	}

	// si max fuera mayor al tamaño de body text esto tiraría panic
	fmt.Println(bodyText[:max])
}
