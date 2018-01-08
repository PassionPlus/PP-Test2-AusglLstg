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
	final static int eol = 13; // EnterKnopf, Ascii-Zeichen 13
	private static String input = null;

	/**
	 * Sorgt dafür, dass Kleinbuchstaben klein bleiben und große groß
	 * 
	 * @param c
	 *            Character
	 * @return gibt den gleichen Buchstaben als großen zurück
	 */
	private static Character cap(final Character c) {
		if (c >= 'a') {
			return Character.toUpperCase(c);
		}
		return c;
	}

	/*
	 * Eingegebene Zeichen aus der Konsoleneingabe der main, werden in die text
	 * Queue eingelesen
	 * 
	 * @param text Queue mit Chars
	 */
	public static void dictate(final BlockingQueue<Character> text) {
		for (Character c : input.toCharArray()) {
			try {
				text.put(c);
			} catch (InterruptedException e) {
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
				// Wenn eines dieser drei Zeichen , dann nicht verschluesseln
				if (c == ' ' || c == '.' || c == eol) {
					crypted.put(c);
				} else if (cap(c) < 'X') { // ab hier verschluesselungs magic
					crypted.put((char) (c + 3));
				} else {
					crypted.put((char) (c - 23));
				}
			} catch (InterruptedException e) {
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
		System.out.println("This is a git test");
		// Laufe den Text duch bis man zu eol angekommen ist-> siehe go, hier nicht so
		// umgesetzt
		// for (Character c : crypted) {
		// System.out.print(c);
		// }

		//NervNerv hier weiter ...nervnerv 
		/*hier gerne poll mit take() ersetzen und abbruch bedingung eol verwenden . Es verlässt nicht die schleife */
		Character c;
		while ((c = crypted.poll()) != null) {
			System.out.println(c);
		}

		try {
			done.put(true); // d <- true , Nachricht Senden
		} catch (InterruptedException e) {
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
		System.out.print("Bitte geben Sie einen Text ein, der verschlüsselt werden soll: ");
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
		return; // um main zu beenden , wenns net klappt restlicher Code soll ja nicht
				// ausgeführt werden deshalb return
	}
}
