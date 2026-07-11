package notes

import (
	"github.com/gin-gonic/gin"
	"go.mongodb.org/mongo-driver/v2/mongo"
)

func RegisterRoutes(r *gin.Engine, db *mongo.Database) {
	// Create a repo and handler once at start up
	repo := NewRepo(db)
	handler := NewHandler(repo)

	notesGroup := r.Group("/notes")
	{
		notesGroup.POST("", handler.CreateNote)
		notesGroup.GET("", handler.ListNotes)
		notesGroup.GET("/:id", handler.GetNoteById)
		notesGroup.PUT("/:id", handler.UpdateNoteById)
		notesGroup.DELETE("/:id", handler.DeleteNoteByID)
	}
}
