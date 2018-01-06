package io.dama.par.channels; // package main

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/*-
import (
    "bufio"
    "fmt"
    "os"
)
*/
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Caesar {
	final static int eol = 13;
	static String input = null;
	/*- // TODO ist das so richtig?
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
			return (char) (c - 'a' + 'A'); // TODO kann einfach gecastet werden?
		}
		return c;
	}

	/**
	 * Eingabe von Zeichen, die in die t Queue eingelesen werden
	 * 
	 * @param text
	 *            Queue mit Chars
	 */
	public static void dictate(final BlockingQueue<Character> text) {
		// Konsoleneingabe in String speichern
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			input = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Danach String über CharArray in die Queue einfügen
		for (Character c : input.toCharArray()) {
			text.offer(c);
			if (c == eol) {
				break;
			}
		}
	}

	/**
	 * Verschlüsselung liest die Zeichen aus Queue text, verschiebt diese um 3
	 * Stellen und schreibt sie in die Queue crypted
	 * 
	 * @param text
	 *            Queue mit chars aus Klartext
	 * @param crypted
	 *            Queue mit chars welche verschlüsselt wird
	 */
	public static void encrypt(final BlockingQueue<Character> text, final BlockingQueue<Character> crypted) {
		for (Character c : text) {
			try {
				if (c == ' ' || c == '.' || c == eol) {
					crypted.put(c);
				} else if (cap(c) < 'X') {
					crypted.put((char) (c + 3));
				} else {
					crypted.put((char) (c - 23));
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Gibt die crypted Queue aus und trägt true in done Queue ein
	 * 
	 * @param crypted
	 *            Queue mit chars welche verschlüsselt sind
	 * @param done
	 *            Queue mit Boolean, um das Ende abzufragen
	 */
	public static void send(final BlockingQueue<Character> crypted, final BlockingQueue<Boolean> done) {
		for (Character c : crypted) {
			System.out.print(c);
		}
		System.out.println();
		/*-
		    func send(c chan byte, d chan bool) {
		        b := byte(0)
		        for b != eol {
		            b = <-c
		            fmt.Print(string(b))
		        }
		        fmt.Println()
		    }
		*/
		try {
			
			done.put(true); // d <- true
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		System.out.printf("Bitte geben Sie einen String ein, der verschlüsselt werden soll: ");

		final BlockingQueue<Character> textchan = new LinkedBlockingQueue<>(); // textchan := make(chan byte)
		final BlockingQueue<Character> cryptchan = new LinkedBlockingQueue<>(); // cryptchan := make(chan byte)
		final BlockingQueue<Boolean> done = new LinkedBlockingQueue<>(); // done := make(chan bool)
		(new Thread(() -> Caesar.dictate(textchan))).start(); // go dictate(textchan)
		(new Thread(() -> Caesar.encrypt(textchan, cryptchan))).start(); // go encrypt(textchan, cryptchan)
		(new Thread(() -> Caesar.send(cryptchan, done))).start(); // go send(cryptchan, done)
		done.take(); // <-done
	}
}
