package notes

import (
	"context"
	"fmt"
	"time"

	"go.mongodb.org/mongo-driver/v2/mongo"
)

// Repository -> capa de acceso a datos

type NotesRepo struct {
	coll *mongo.Collection
}

func NewRepo(db *mongo.Database) *NotesRepo {
	return &NotesRepo{
		coll: db.Collection("notes"),
	}
}

func (nr *NotesRepo) Create(ctx context.Context, note Note) (Note, error) {
	// ctx es el contexto padre de la conexión http
	// operationContext es el contexto hijo con5 segundos extra
	operationContext, cancel := context.WithTimeout(ctx, 5*time.Second)

	defer cancel()

	_, err := nr.coll.InsertOne(operationContext, note)
	if err != nil {
		return Note{}, fmt.Errorf("Insert note failed")
	}

	return note, nil
}
