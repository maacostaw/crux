package main

import "fmt"

func main() {
	// defer es una función de limpieza que siempre se ejecuta
	// multiples declaraciones defer son empujadas hacia un stack, cuando la función se complete
	// las acciones defer son ejecutadas en un orden last in first out

	fmt.Println("Case1: comparando ejecución por 2 caminos")
	success := true
	if err := doWork(success); err != nil {
		fmt.Println("error:", err)
	}

	fmt.Println("Case2: comparando las 3 formas de capturar")
	compareDefers()
}

func compareDefers() {
	// muestra cuándo se evalúa el valor de x en cada forma de defer
	// recuerda: los defer se ejecutan en orden LIFO (último en declararse, primero en correr)

	x := 10

	// A: defer "simple" -> los argumentos se evalúan AHORA, captura 10
	defer fmt.Println("A:", x)

	// B: func anónima CON argumento -> el argumento se evalúa AHORA, captura 10
	defer func(v int) {
		fmt.Println("B", v)
	}(x)

	// C: func anónima SIN argumento (closure) -> lee x cuando el defer corre, verá 99
	defer func() {
		fmt.Println("C", x)
	}()

	// Tanto en c, como en b toca poner los parentesis al final para el llamado a la función

	x = 99
}

func doWork(success bool) error {

	// resource related
	// start message -> resource aquired
	// cleanup message -> resource released

	fmt.Println("start: resource aqcuired")

	// defer will gurantee this runs at the end of this func
	// both the paths
	// - success return
	// - errors return

	defer fmt.Println("cleanup: resource released")

	if !success {
		return fmt.Errorf("something went wrong")
	}

	fmt.Println("work: doing something imp")
	fmt.Println("work: this work is done")

	return nil
}
