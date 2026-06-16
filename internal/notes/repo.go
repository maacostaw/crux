package notes

import (
	"context"
	"fmt"
	"time"

	"go.mongodb.org/mongo-driver/v2/bson"
	"go.mongodb.org/mongo-driver/v2/mongo"
	"go.mongodb.org/mongo-driver/v2/mongo/options"
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

func (r *NotesRepo) List(ctx context.Context) ([]Note, error) {
	opCtx, cancel := context.WithTimeout(ctx, 5*time.Second)
	defer cancel()

	filter := bson.M{} // Filtro para hacer match con todo

	// Vamos a obtener un cursor (algo así como un iterador) -> sobre los elementos que hacen match
	cursor, err := r.coll.Find(opCtx, filter)
	if err != nil {
		return nil, fmt.Errorf("find notes failed %w", err)
	}

	// el cursor debe ser cerrado luego de su uso
	defer cursor.Close(opCtx)

	var notes []Note

	if err := cursor.All(opCtx, &notes); err != nil {
		return nil, fmt.Errorf("Decode notes failed: %w", err)
	}

	return notes, nil
}

func (r *NotesRepo) GetById(ctx context.Context, id bson.ObjectID) (Note, error) {
	opCtx, cancel := context.WithTimeout(ctx, 5*time.Second)
	defer cancel()

	filter := bson.M{"_id": id}

	var note Note

	err := r.coll.FindOne(opCtx, filter, options.FindOne()).Decode(&note)

	if err != nil {
		return Note{}, fmt.Errorf("Find note by id failed: %w", err)
	}

	return note, nil
}

func (r *NotesRepo) UpdateById(ctx context.Context, id bson.ObjectID, req UpdateNoteRequest) (Note, error) {
	opCtx, cancel := context.WithTimeout(ctx, 5*time.Second)
	defer cancel()

	filter := bson.M{"_id": id}

	update := bson.M{
		"$set": bson.M{
			"title":     req.Title,
			"content":   req.Content,
			"pinned":    req.Pinned,
			"updatedAt": time.Now().UTC(),
		},
	}

	opts := options.FindOneAndUpdate().SetReturnDocument(options.After)
	var updated Note
	err := r.coll.FindOneAndUpdate(opCtx, filter, update, opts).Decode(&updated)
	if err != nil {
		return Note{}, fmt.Errorf("Update note failed: %w", err)
	}

	return updated, nil
}

func (r *NotesRepo) DeleteById(ctx context.Context, id bson.ObjectID) (Note, error) {
	opCtx, cancel := context.WithTimeout(ctx, 5*time.Second)
	defer cancel()

	filter := bson.M{"_id": id}

	opts := options.FindOneAndDelete()
	var deleted Note
	err := r.coll.FindOneAndDelete(opCtx, filter, opts).Decode(&deleted)
	if err != nil {
		return Note{}, fmt.Errorf("Failed to delete the given note: %w", err)
	}

	return deleted, nil
}
