//go:build ignore

package main

import (
	"encoding/json"
	"fmt"
	"net/http"
	"time"
)

func successHandler(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")

	w.WriteHeader(http.StatusOK)

	res := map[string]any{
		"ok":       true,
		"message":  "JSON encode successful",
		"datetime": time.Now().UTC(),
	}

	// Crea una herramienta que escribe json en una respuesta http
	encoder := json.NewEncoder(w)

	// Le decimos al encoder que escriba sobre la respuesta el objeto res

	encoder.Encode(res)
}

func main() {
	// Creamos un mutex de direcciones
	mux := http.NewServeMux()

	// Creamos los handlers
	mux.HandleFunc("/ok", successHandler)

	// Abrimos el puerto 8080
	err := http.ListenAndServe(":8080", mux)
	fmt.Println(err)
}
