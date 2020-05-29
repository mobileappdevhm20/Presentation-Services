package main

import (
	"encoding/json"
	"fmt"
	"net/http"
	"time"
)

type CurrentTime struct {
	UnixTime   int64  `json:"UnixTime"`
	TimeString string `json:"TimeString"`
}

func main() {
	// this function serves the api-endpoint
	handleApiRequest := func(w http.ResponseWriter, r *http.Request) {
		if bytes, err := getCurrentTimeAsBytes(); err != nil {
			http.Error(w, err.Error(), http.StatusInternalServerError)
		} else {
			w.Header().Set("Content-Type", "application/json")
			_, _ = w.Write(bytes)
		}
	}

	// handle the api-endpoint
	http.HandleFunc("/myApiEndpoint", handleApiRequest)
	_ = http.ListenAndServe(fmt.Sprintf(":%d", 8080), nil)
}

func getCurrentTimeAsBytes() ([]byte, error) {
	t := time.Now()
	ct := CurrentTime{
		UnixTime:   t.Unix(),
		TimeString: t.String(),
	}
	if bytes, err := json.Marshal(&ct); err != nil {
		return nil, err
	} else {
		return bytes, nil
	}
}