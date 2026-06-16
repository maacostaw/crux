package notes

import (
	"time"

	"go.mongodb.org/mongo-driver/v2/bson"
)

type Note struct {
	ID bson.ObjectID `bson:"_id" json:"id"`

	Title string `bson:"title" json:"title"`

	Content string `bson:"content" json:"content"`

	Pinned bool `bson:"pinned" json:"pinned"`

	CreatedAt time.Time `bson:"createdAt" json:"createdAt"`

	UpdatedAt time.Time `bson:"updatedAt" json:"updatedAt"`
}

// Si pusieramos binding en "pinned" no podríamos poner false, porque tomaría
// false como si no hubiesemos pasado el valor
type CreateNoteRequest struct {
	// Vamos a usar binding
	Title string `json:"title" binding:"required"`

	Content string `json:"content" binding:"required"`

	Pinned bool `json:"pinned"`
}
