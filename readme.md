### TODOs ###

* Die TODOs innerhalb von Caesar können noch angegangen werden.
* Es gab bis jetzt paar mal den Fall, wo nach Eingabe eines Wortes das Programm beendet wurde, ohne, dass das verschlüsselte Wort angezeigt wurde. Das heißt, es sollte überprüft werden, ob der Einsatz von Blockierung (put, take) und keine Blockierung (offer, poll) richtig angewendet wurde.
* Überflüssige bzw. nicht mehr benötigte Kommentare können gelöscht werden.
* Der Sinn und der Einsatz von eol (end of line???) ist noch fragwürdig (aus go übernommen).
* Es können natürlich noch sonstige Verschönerungen und Verbesserungen vorgenommen werden.
* Ich habe noch eine Zeitberechnung hinzugefügt. Es sollte überlegt werden, ob die lieber bleibt oder lieber raus soll, wegen evtl. Vor- oder Nachteile dadurch für uns.
* Wenn für das Verständnis nicht mehr benötigt, kann der Addierer gelöscht werden.

# Ausgleichsleistung zu Test 2 #

## Aufgabe ##

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
