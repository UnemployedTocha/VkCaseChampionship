FROM golang:latest

COPY ./ ./

RUN go build -o demo_service ./cmd/app/main.go

CMD ["./backend"]