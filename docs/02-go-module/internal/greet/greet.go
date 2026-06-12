package greet

import "strings"

// Las funciones para exportar comienzan en mayuscula
// otros paquetes la pueden llamar
func Hello(name string) string {
	clean := normalizeName(name)

	return "Hello," + clean
}

// Esta función será algo así como "privada"
func normalizeName(name string) string {
	n := strings.TrimSpace(name)

	if n == "" {
		return "Guest"
	}

	return strings.ToUpper(n)
}
