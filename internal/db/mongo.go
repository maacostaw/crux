package db

import (
	"context"
	"fmt"
	"time"

	"github.com/maacostaw/crux/internal/config"
	"go.mongodb.org/mongo-driver/v2/mongo"
	"go.mongodb.org/mongo-driver/v2/mongo/options"
)

// Crea un cliente mongo, verifica la conección y te devuelve un db handler
func Connect(config config.Config) (*mongo.Client, *mongo.Database, error) {
	// Crea el objeto base de la metadata para configurar un cliente y le setea la url de mongo
	clientOptions := options.Client().ApplyURI(config.MongoURI)

	// Crea el administrador de conecciones y usa las opciones de antes
	client, err := mongo.Connect(clientOptions)
	if err != nil {
		return nil, nil, fmt.Errorf("mongo connect failed")
	}

	// Creamos un contexto para jutarlo a la petición de ping
	context, cancel := context.WithTimeout(context.Background(), 10*time.Second)

	// Ponemos un defer para asegurar liberar el contexto
	defer cancel()

	// Verifica que MongoDB responde correctamente haciendo ping
	if err := client.Ping(context, nil); err != nil {
		return nil, nil, fmt.Errorf("mongo ping failed")
	}

	// Crea un handler para interactuar con la base de datos
	database := client.Database(config.MongoDB)

	return client, database, nil

}

func Disconnect(client *mongo.Client) error {
	context, cancel := context.WithTimeout(context.Background(), 5*time.Second)

	defer cancel()

	return client.Disconnect(context)
}
