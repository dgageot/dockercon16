FROM golang:1.6.0-alpine

EXPOSE 8080

COPY . /go/src/likes
WORKDIR /go/src/likes
RUN go get -d -v
RUN go install -v

CMD likes