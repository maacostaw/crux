package server

import (
	"net/http"

	"github.com/gin-gonic/gin"
	"github.com/maacostaw/crux/internal/notes"
	"go.mongodb.org/mongo-driver/v2/mongo"
)

func healthHandler(c *gin.Context) {
	c.JSON(http.StatusOK, gin.H{
		"ok":     true,
		"status": "healthy",
	})
}

func NewRouter(database *mongo.Database) *gin.Engine {
	router := gin.Default()

	router.GET("/health", healthHandler)

	notes.RegisterRoutes(router, database)

	return router
}
