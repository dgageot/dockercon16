package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"math/rand"
	"net"
	"net/http"
	"time"
)

func main() {
	rand.Seed(time.Now().UnixNano())
	http.HandleFunc("/", forward("words-java", 8080))
	http.ListenAndServe(":8080", nil)
}

func forward(host string, port int) func(w http.ResponseWriter, r *http.Request) {
	return func(w http.ResponseWriter, r *http.Request) {

		addrs, err := net.LookupHost(host)
		if err != nil {
			log.Println("Error", err)
			http.Error(w, err.Error(), 500)
			return
		}

		log.Printf("%s %d available ips: %v", r.RequestURI, len(addrs), addrs)
		ip := addrs[rand.Intn(len(addrs))]
		log.Printf("%s I choose %s", r.RequestURI, ip)

		url := fmt.Sprintf("http://%s:%d%s", ip, port, r.RequestURI)
		log.Printf("%s Calling %s", r.RequestURI, url)

		resp, err := http.Get(url)
		if err != nil {
			log.Println("Error", err)
			http.Error(w, err.Error(), 500)
			return
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
