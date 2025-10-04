FROM golang:latest

COPY ./ ./

RUN go build -o backend ./cmd/app/main.go &&\
    go build -o producer ./cmd/producer/main.go

CMD ["./backend"]