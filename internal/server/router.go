package server

import (
	"net/http"

	"github.com/gin-gonic/gin"
)

func healthHandler(c *gin.Context) {
	c.JSON(http.StatusOK, gin.H{
		"ok":     true,
		"status": "healthy",
	})
}

func NewRouter() *gin.Engine {
	router := gin.Default()

	router.GET("/health", healthHandler)

	return router
}
