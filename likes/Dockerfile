FROM golang:1.6.1-alpine

WORKDIR /go/src/likes
EXPOSE 8080
CMD likes

COPY . ./
RUN go get -d -v && go install -v

