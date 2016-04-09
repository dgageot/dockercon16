FROM golang:1.6.0-alpine

EXPOSE 8080

COPY dispatcher.go .
RUN go build dispatcher.go
CMD ["./dispatcher"]
