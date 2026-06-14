package main

import (
	"fmt"
	"log"

	"github.com/maacostaw/crux/internal/config"
	"github.com/maacostaw/crux/internal/db"
	"github.com/maacostaw/crux/internal/server"
)

// config -> db -> router -> server

func main() {
	// Cargamos la config
	config, err := config.Load()
	if err != nil {
		log.Fatalf("Config error")
	}

	// Conectamos la config a la db
	client, _, err := db.Connect(config)
	if err != nil {
		log.Fatalf("db error")
	}

	// Desconectamos la db
	defer func() {
		if err := db.Disconnect(client); err != nil {
			log.Printf("mongo disconnect error: %v", err)
		}
	}()

	// Creamos el router
	router := server.NewRouter()

	address := fmt.Sprintf(":%s", config.ServerPort)

	if err := router.Run(address); err != nil {
		log.Fatalf("server failed")
	}
}
