//go:build ignore

package main

import (
	"encoding/json"
	"fmt"
	"net/http"
	"strings"
	"time"
)

// Si tuviesemos GIN, no necesitariamos esta función auxiliar
func writeJSON(w http.ResponseWriter, status int, data any) {
	// Headers
	w.Header().Set("Content-Type", "application/json")
	// Status Code
	w.WriteHeader(status)
	// Encoder (podría hacer encode varias veces pero la idea es solo 1 vez)
	encoder := json.NewEncoder(w)
	encoder.Encode(data)
}

type TestRequest struct {
	Name string `json:"name"`
}

func testHandler(w http.ResponseWriter, r *http.Request) {
	if r.Method != http.MethodPost {
		writeJSON(w, http.StatusMethodNotAllowed, map[string]any{
			"ok":    "false",
			"error": "Only post is allowed",
		})
		return
	}
	defer r.Body.Close()

	// Forma estandar de leer un json
	// Creamos un struct que lo represente
	var req TestRequest

	// Abrimos un decoder que le apunte al body
	dec := json.NewDecoder(r.Body)
	// decodificamos relacionando el body con el struct objetivo
	err := dec.Decode(&req)

	// Decoder fallará cuando alguno de los campos esté mal formado
	// Si yo quisiera validar si se mandó o no algún campo eso ya depende de mi
	// Y si semandó algún campo extra simplemente se ignora
	if err != nil {
		writeJSON(w, http.StatusBadRequest, map[string]any{
			"ok":    "false",
			"error": "Invalid json format",
		})
		return
	}

	// Por lo que deciamos antes, si yo quiero validar ahora si, lógica de negocio
	// Ya esa parte me corresponde a mi
	req.Name = strings.TrimSpace(req.Name)

	if req.Name == "" {
		writeJSON(w, http.StatusBadRequest, map[string]any{
			"ok":    "false",
			"error": "name must not be empty",
		})
		return
	}

	// Notese que el req ya está decodificado y que lo hicimos arriba con err := dec.Decode(&req)
	// ahí le pasamos &req osea un puntero osea la operación que se ejecutó fue sobre el objeto real
	writeJSON(w, http.StatusOK, map[string]any{
		"ok":        "true",
		"data":      req,
		"timeStamp": time.Now().UTC(),
	})
}

func main() {
	//Creamos el mutex
	mux := http.NewServeMux()

	// Creamos rutas
	mux.HandleFunc("/test", testHandler)

	// Escuchamos en el puerto 8080
	err := http.ListenAndServe(":8080", mux)
	fmt.Println(err)
}
