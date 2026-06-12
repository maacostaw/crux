//go:build ignore

package main

import (
	"fmt"
	"net/http"
)

func main() {
	url := "https://jsonplaceholder.typicode.com/todos"

	// Acá es importante entender que nos están dando una conexión, luego toca cerrarla
	resp, err := http.Get(url)
	if err != nil {
		fmt.Println(err)
		return
	}

	// Como dijimos, toca cerrarla, en este caso solo pedimos una cosa, pero si tocara descargar un archivo
	// de 5 gigas, toca abrir una conexión e ir bajando cosas, cuando ya esté, cerramos
	defer resp.Body.Close()

	fmt.Println("status code", resp.StatusCode)
	// Acá por ejemplo queda en claro que resp, no es solo un string, es un objeto complejo
	fmt.Println("resp", resp)
}
