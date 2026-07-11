package notes

import (
	"context"
	"errors"
	"net/http"
	"time"

	"github.com/gin-gonic/gin"
	"go.mongodb.org/mongo-driver/v2/bson"
	"go.mongodb.org/mongo-driver/v2/mongo"
)

type Handler struct {
	repo NoteRepository
}

type NoteRepository interface {
	Create(ctx context.Context, note Note) (Note, error)
	List(ctx context.Context) ([]Note, error)
	GetById(ctx context.Context, id bson.ObjectID) (Note, error)
	UpdateById(ctx context.Context, id bson.ObjectID, req UpdateNoteRequest) (Note, error)
	DeleteById(ctx context.Context, id bson.ObjectID) (Note, error)
}

func NewHandler(repo NoteRepository) *Handler {
	return &Handler{repo: repo}
}

func (h *Handler) CreateNote(c *gin.Context) {
	// Objeto vacio del create
	var req CreateNoteRequest

	// Parsea el json en tu struct y valida con base a lo que hayamos puesto en tags
	if err := c.ShouldBindJSON(&req); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{
			"error": "Invalid Json",
		})
		return
	}

	now := time.Now().UTC()

	note := Note{
		ID:      bson.NewObjectID(),
		Title:   req.Title,
		Content: req.Content,
		Pinned:  req.Pinned,

		CreatedAt: now,
		UpdatedAt: now,
	}

	created, err := h.repo.Create(c.Request.Context(), note)
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{
			"error": "Failed to create note here!",
		})
		return
	}

	c.JSON(http.StatusCreated, created)
}

func (h *Handler) ListNotes(c *gin.Context) {
	notes, err := h.repo.List(c.Request.Context())
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{
			"error": "Failed to fetch all notes",
		})
		return
	}

	c.JSON(http.StatusOK, gin.H{
		"notes": notes,
	})
}

func (h *Handler) GetNoteById(c *gin.Context) {
	idStr := c.Param("id")

	objID, err := bson.ObjectIDFromHex(idStr)
	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{
			"error": "Invalid ID",
		})
		return
	}

	note, err := h.repo.GetById(c.Request.Context(), objID)
	if err != nil {
		if errors.Is(err, mongo.ErrNoDocuments) {
			c.JSON(http.StatusNotFound, gin.H{
				"error": "Note not found for that given ID",
			})
			return
		}
		c.JSON(http.StatusInternalServerError, gin.H{
			"error": "Failed to fetch the note",
		})
		return
	}

	c.JSON(http.StatusOK, note)
}

func (h *Handler) UpdateNoteById(c *gin.Context) {
	idStr := c.Param("id")

	objID, err := bson.ObjectIDFromHex(idStr)

	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{
			"error": "Invalid ID",
		})
		return
	}

	var req UpdateNoteRequest

	if err := c.ShouldBindJSON(&req); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{
			"error": "Invalid json format",
		})
		return
	}

	updated, err := h.repo.UpdateById(c.Request.Context(), objID, req)
	if err != nil {
		if errors.Is(err, mongo.ErrNoDocuments) {
			c.JSON(http.StatusNotFound, gin.H{
				"error": "Note not found for that given ID",
			})
			return
		}
		c.JSON(http.StatusInternalServerError, gin.H{
			"error": "Failed to fetch the note",
		})
		return
	}

	c.JSON(http.StatusOK, updated)
}

func (h *Handler) DeleteNoteByID(c *gin.Context) {
	idStr := c.Param("id")

	objID, err := bson.ObjectIDFromHex(idStr)

	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{
			"error": "Invalid ID",
		})
		return
	}

	deleted, err := h.repo.DeleteById(c.Request.Context(), objID)
	if err != nil {
		if errors.Is(err, mongo.ErrNoDocuments) {
			c.JSON(http.StatusNotFound, gin.H{
				"error": "Note not found",
			})
			return
		}
		c.JSON(http.StatusInternalServerError, gin.H{
			"error": "failed to delete the note",
		})
		return
	}

	c.JSON(http.StatusOK, deleted)
}
