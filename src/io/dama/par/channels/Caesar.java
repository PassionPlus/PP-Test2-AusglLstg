package io.dama.par.channels;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Caesar {
	/*-
	package main
	import (
	    "bufio"
	    "fmt"
	    "os"
	)
	const eol = 13
	var input *bufio.Reader
	*/

	/**
	 * Macht aus Kleinbuchstaben -> Großbuchstaben
	 * 
	 * @param c
	 *            Character
	 * @return gibt den gleichen Buchstaben als großen zurück
	 */
	private static Character cap(final Character c) {
		if (c >= 'a') {
			return c - 'a' + 'A'; // TODO kann einfach gecastet werden?
		}
		/*-
		    func cap(b byte) byte {
		        if b >= 'a' {
		            return b - 'a' + 'A'
		        }
		        return b
		    }
		 */
		return c;
	}

	/**
	 * Eingabe von Zeichen, die in die t Queue eingelesen werden
	 * 
	 * @param t
	 *            Queue mit Chars
	 */
	public static void dictate(final BlockingQueue<Character> t) {
		/*-
		    func dictate(t chan byte) {
		        for {
		            b, _ := input.ReadByte()
		            t <- b
		            if b == eol {
		                break
		            }
		        }
		    }
		*/
	}

	/**
	 * Verschlüsselung liest die Zeichen aus Queue, verschiebt diese um 3 Stellen
	 * und schreibt sie in die andere Queue
	 * 
	 * @param t
	 *            Queue mit chars
	 * @param c
	 *            Queue mit chars
	 */
	public static void encrypt(final BlockingQueue<Character> t, final BlockingQueue<Character> c) {
		/*-
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
		*/
	}

	/**
	 * Gibt die c Queue aus und trägt true in done Queue ein
	 * 
	 * @param c
	 *            Queue mit chars
	 * @param done
	 *            Queue mit Boolean
	 */
	public static void send(final BlockingQueue<Character> c, final BlockingQueue<Boolean> done) {
		/*-
		    func send(c chan byte, d chan bool) {
		        b := byte(0)
		        for b != eol {
		            b = <-c
		            fmt.Print(string(b))
		        }
		        fmt.Println()
		        d <- true
		    }
		*/
	}

	/**
	 * MAIN
	 * 
	 * @param args
	 */
	public static void main(final String[] args) throws InterruptedException {
		System.out.printf("PP\nAusgleichsleistung zum Test 2\nvon Stella Nesa und Benjamin Schönke\n\n");
		System.out.printf("Caesar-Verschlüsselung als nebenläufiges Java-Programm\n\n");
		// TODO input = bufio.NewReader(os.Stdin)
		// System.out.printf("Bitte geben Sie einen String ein, der verschlüsselt werden soll: ");
		
		final BlockingQueue<Character> textchan = new LinkedBlockingQueue<>(); // textchan := make(chan byte)
		final BlockingQueue<Character> cryptchan = new LinkedBlockingQueue<>(); // cryptchan := make(chan byte)
		final BlockingQueue<Boolean> done = new LinkedBlockingQueue<>(); // done := make(chan bool)
		(new Thread(() -> Caesar.dictate(textchan))).start(); // go dictate(textchan)
		(new Thread(() -> Caesar.encrypt(textchan, cryptchan))).start(); // go encrypt(textchan, cryptchan)
		(new Thread(() -> Caesar.send(cryptchan, done))).start(); // go send(cryptchan, done)
		done.take(); // <-done
	}
}
