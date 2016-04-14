package main

import (
  "fmt"
  "net/http"
  "io/ioutil"
  "github.com/gorilla/mux"
	"encoding/json"
)

// Like a rolling stone
type Like struct {
  Name string `json:"name"`
}

// Server serves
type Server struct {
  likes []Like
}

func (server *Server) start(port int) {
  router := mux.NewRouter()
  router.HandleFunc("/likes", server.getLike).Methods("GET")
  router.HandleFunc("/likes", server.putLike).Methods("PUT")
  router.PathPrefix("/").Handler(http.FileServer(http.Dir("./app")))
  http.Handle("/", router)
  
  fmt.Println("Listening on", port)
  http.ListenAndServe(fmt.Sprintf(":%d", port), nil)
}

func main() {
  server := Server{[]Like{Like{"foo"},Like{"bar"}}}
  server.start(8080)
}

func (server *Server) putLike(writer http.ResponseWriter, request *http.Request ) {
  bytes, err := ioutil.ReadAll(request.Body)
	if err != nil {
		http.Error(writer, err.Error(), http.StatusInternalServerError)
    return
	}
  like := Like{}
  err = json.Unmarshal(bytes, &like)
	if err != nil {
		http.Error(writer, err.Error(), http.StatusInternalServerError)
    return
	}
  server.likes = append([]Like{like}, server.likes...)
  writer.Header().Set("Content-Type", "application/json")
  writer.WriteHeader(http.StatusCreated)
}

func (server *Server) getLike(writer http.ResponseWriter, request *http.Request ) {
  payload, err := json.Marshal(server.likes) // sort them by descending dates
  if err != nil {
    http.Error(writer, err.Error(), http.StatusInternalServerError)
    return
  }
  writer.Header().Set("Content-Type", "application/json")
  writer.Write(payload)
}