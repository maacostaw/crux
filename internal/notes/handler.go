package notes

import (
	"net/http"
	"time"

	"github.com/gin-gonic/gin"
	"go.mongodb.org/mongo-driver/v2/bson"
)

type Handler struct {
	repo *NotesRepo
}

func NewHandler(repo *NotesRepo) *Handler {
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
