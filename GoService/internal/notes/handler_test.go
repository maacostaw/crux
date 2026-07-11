package notes

import (
	"context"
	"encoding/json"
	"net/http"
	"net/http/httptest"
	"testing"

	"github.com/gin-gonic/gin"
	"go.mongodb.org/mongo-driver/v2/bson"
)

type fakeNoteRepo struct {
	notes []Note
	err   error
}

func (f fakeNoteRepo) Create(ctx context.Context, note Note) (Note, error) {
	return Note{}, nil
}

func (f fakeNoteRepo) List(ctx context.Context) ([]Note, error) {
	return f.notes, f.err
}

func (f fakeNoteRepo) GetById(ctx context.Context, id bson.ObjectID) (Note, error) {
	return Note{}, nil
}

func (f fakeNoteRepo) UpdateById(ctx context.Context, id bson.ObjectID, req UpdateNoteRequest) (Note, error) {
	return Note{}, nil
}

func (f fakeNoteRepo) DeleteById(ctx context.Context, id bson.ObjectID) (Note, error) {
	return Note{}, nil
}

func TestHandler_ListNotes(t *testing.T) {
	gin.SetMode(gin.TestMode)

	handler := NewHandler(fakeNoteRepo{
		notes: []Note{
			{
				ID:      bson.NewObjectID(),
				Title:   "First note",
				Content: "Hello world",
				Pinned:  true,
			},
		},
	})

	recorder := httptest.NewRecorder()
	ctx, _ := gin.CreateTestContext(recorder)
	ctx.Request = httptest.NewRequest(http.MethodGet, "/notes", nil)

	handler.ListNotes(ctx)

	if recorder.Code != http.StatusOK {
		t.Fatalf("expected status %d, got %d", http.StatusOK, recorder.Code)
	}

	var response struct {
		Notes []Note `json:"notes"`
	}

	if err := json.Unmarshal(recorder.Body.Bytes(), &response); err != nil {
		t.Fatalf("failed to decode response: %v", err)
	}

	if len(response.Notes) != 1 {
		t.Fatalf("expected 1 note, got %d", len(response.Notes))
	}

	if response.Notes[0].Title != "First note" {
		t.Fatalf("expected note title %q, got %q", "First note", response.Notes[0].Title)
	}

	if response.Notes[0].Content != "Hello world" {
		t.Fatalf("expected note content %q, got %q", "Hello world", response.Notes[0].Content)
	}
}
