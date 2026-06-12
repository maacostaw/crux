//go:build ignore

package main

import (
	"fmt"
	"net/http"
)

// w te permite operar sobre la respuesta w.Write(...), w.Header().Set(...), w.WriteHeader(404)
// r te permite operar sobre el request recibido r.Method, r.Header, r.Body
func helloHandler(w http.ResponseWriter, r *http.Request) {
	if r.Method != http.MethodGet {
		// Respuesta para el cliente de error
		http.Error(w, "Only get is allowed", http.StatusMethodNotAllowed)
		return
	}

	// btw write devuelve 2 valores n, err := w.Write(...)
	// n: cantidad de bytes escritos, err: error si ocurrió algo. Pero ps acá no se necesitan
	w.Write([]byte("Hello from GO net/http server"))
}

func main() {
	// Creamos un multiplexer, que luego es quien decide a donde van las peticiones
	mux := http.NewServeMux()

	// Adicionamos el handler al mux para que lo tenga en cuenta
	// Si no tuvieramos mux quedaría http.HandleFunc("/hello", helloHandler) y lo adicionaría al mux por defecto
	mux.HandleFunc("/hello", helloHandler)
	fmt.Println("try going to 8080 port")

	// Indicamos que escuche en el puerto 8080 con el mux que creamos
	// si no tuviesemos mux quedaría http.ListenAndServe(":8080", nil) que buscaria el mux por default
	err := http.ListenAndServe(":8080", mux)
	fmt.Println(err)
}
