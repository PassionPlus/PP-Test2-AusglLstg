# Ausgleichsleistung zu Test 2 #

## Aufgaben ##

Implementieren Sie die Caesar-Verschlüsselung als nebenläufiges Java-Programm. Eingabe, Verschlüsselung (Verschieben um 3 Stellen) und Ausgabe sollen dabei als nebenläufige Threads realisiert werden. Sie können das folgende Go-Programm als Ausgangspunkt benutzen:

```package main
import (
	"bufio"
	"fmt"
	"os"
)
const eol = 13
var input *bufio.Reader
func cap(b byte) byte {
	if b >= 'a' {
		return b - 'a' + 'A'
	}
	return b
}
func dictate(t chan byte) {
	for {
		b, _ := input.ReadByte()
		t <- b
		if b == eol {
			break
		}
	}
}
func encrypt(t, c chan byte) {
	for {
		b := <-t
		if b == ' ' || b == '.' || b == eol {
			c <- b
		} else if cap(b) < 'X' {
			c <- b + 3
		} else {
			c <- b - 23
		}
	}
}
func send(c chan byte, d chan bool) {
	b := byte(0)
	for b != eol {
		b = <-c
		fmt.Print(string(b))
	}
	fmt.Println()
	d <- true
}


func main() {
	input = bufio.NewReader(os.Stdin)
	textchan := make(chan byte)
	cryptchan := make(chan byte)
	done := make(chan bool)
	go dictate(textchan)
	go encrypt(textchan, cryptchan)
	go send(cryptchan, done)
	<-done
}
```

Die Kanäle sollten als LinkedBlockingQueue ohne Kapazitätsbeschränkung umgesetzt werden.

Sie sollen die Klasse io.dama.par.channels.Caesar erstellen. Geben Sie genau diese eine Java-Klasse als Caesar.java ab.