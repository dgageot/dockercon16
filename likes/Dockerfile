FROM golang:1.6.1-alpine

EXPOSE 8080
WORKDIR /go/src/likes
CMD ["./likes"]

COPY . ./
RUN go get -d -v && go build -v
