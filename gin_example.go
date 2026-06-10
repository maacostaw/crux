package main

import (
    "github.com/gin-gonic/gin"
)

type saludoRequest struct {
    Nombre string `json:"nombre" binding:"required"`
}

func main() {
    r := gin.Default()
    r.GET("/ping", func(c *gin.Context) {
        c.JSON(200, gin.H{"message": "pong"})
    })
    r.POST("/saludo", func(c *gin.Context) {
        var req saludoRequest
        if err := c.ShouldBindJSON(&req); err != nil {
            c.JSON(400, gin.H{"error": err.Error()})
            return
        }
        c.JSON(200, gin.H{"mensaje": "hola -" + req.Nombre + "-"})
    })
    r.Run(":8080")
}
