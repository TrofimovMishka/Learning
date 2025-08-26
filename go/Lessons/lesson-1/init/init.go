package main

import (
	"fmt"

	"rsc.io/quote"

	"lesson-1.com/greetings"
)

func main() {
	fmt.Println(quote.Go())

	fmt.Printf(greetings.Hello("From outside"))
}
