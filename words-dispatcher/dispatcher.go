package main

import (
	"io/ioutil"
	"log"
	"net"
	"net/http"
)

func main() {
	http.HandleFunc("/", forward("http://words-java:8080"))
	http.ListenAndServe(":8080", nil)
}

func forward(url string) func(w http.ResponseWriter, r *http.Request) {
	return func(w http.ResponseWriter, r *http.Request) {
		log.Println(r.RequestURI)

		resp, err := http.Get(url + r.RequestURI)
		if err != nil {
			log.Println("Error", err)
			http.Error(w, err.Error(), 500)
			return
		}

		log.Println("answered by", r.RemoteAddr)
		addrs, err := net.LookupHost("words-java")
		if err != nil {
			log.Println(err)
		} else {
			log.Println(addrs)
		}

		buf, err := ioutil.ReadAll(resp.Body)
		if err != nil {
			log.Println("Error", err)
			http.Error(w, err.Error(), 500)
			return
		}

		_, err = w.Write(buf)
		if err != nil {
			log.Println("Error", err)
			http.Error(w, err.Error(), 500)
			return
		}
	}
}
