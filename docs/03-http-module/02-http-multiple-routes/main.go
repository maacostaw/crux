//go:build ignore

package main

import (
	"fmt"
	"net/http"
)

func rootHandler(w http.ResponseWriter, r *http.Request) {
	w.Write([]byte("welcome try to /hello?name=miguel"))
}

func helloHandler(w http.ResponseWriter, r *http.Request) {
	var name string
	name = r.URL.Query().Get("name")

	if name == "" {
		name = "Guest"
	}
	w.Write([]byte("hello" + name))
}

func main() {
	// Creamos el multi plexer (guia de ruteo)
	mux := http.NewServeMux()

	// Gestionamos las rutas
	mux.HandleFunc("/", rootHandler)
	mux.HandleFunc("/hello", helloHandler)

	// Abrimos el puerto 8080
	err := http.ListenAndServe(":8080", mux)
	fmt.Println(err)
}
