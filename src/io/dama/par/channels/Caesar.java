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
	/*- // TODO ist die Übersetzung von go zu Java und auch sinngemäß so richtig?
	const eol = 13
	var input *bufio.Reader
	*/

	/**
	 * Sorgt dafür, dass Kleinbuchstaben klein bleiben und große groß
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
	 * Eingegebene Zeichen aus der Konsoleneingabe der main, werden in die text
	 * Queue eingelesen
	 * 
	 * @param text
	 *            Queue mit Chars
	 */
	public static void dictate(final BlockingQueue<Character> text) {
		for (Character c : input.toCharArray()) {
			try {
				text.put(c);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		System.out.print("\nDie verschlüsselte Nachricht lautet: ");
		for (Character c : crypted) {
			System.out.print(c);
		}
		System.out.println("\n");
		/*- TODO ist die Übersetzung so richtig und vollständig?
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
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("Parallele Programmierung");
		System.out.println("Ausgleichsleistung zum Test 2");
		System.out.println("von Stella Nesa und Benjamin Schönke");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
		System.out.println("Caesar-Verschlüsselung als nebenläufiges Java-Programm");
		System.out.println("------------------------------------------------------\n");
		System.out.print("Bitte geben Sie einen String ein, der verschlüsselt werden soll: ");
		// Konsoleneingabe in String input speichern
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); // input = bufio.NewReader(os.Stdin)
		try {
			input = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		final double now = System.currentTimeMillis();
		final BlockingQueue<Character> textchan = new LinkedBlockingQueue<>(); // textchan := make(chan byte)
		final BlockingQueue<Character> cryptchan = new LinkedBlockingQueue<>(); // cryptchan := make(chan byte)
		final BlockingQueue<Boolean> done = new LinkedBlockingQueue<>(); // done := make(chan bool)
		(new Thread(() -> Caesar.dictate(textchan))).start(); // go dictate(textchan)
		(new Thread(() -> Caesar.encrypt(textchan, cryptchan))).start(); // go encrypt(textchan, cryptchan)
		(new Thread(() -> Caesar.send(cryptchan, done))).start(); // go send(cryptchan, done)
		boolean isDone = done.take(); // <-done
		if (isDone) {
			System.out.println("Benötigte Zeit: " + (System.currentTimeMillis() - now) + " ms");
			System.out.println("------------------");
			System.out.println("[PROGRAMM BEENDET]");
		}
	}
}
