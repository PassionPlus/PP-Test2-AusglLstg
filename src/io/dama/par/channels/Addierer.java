package io.dama.par.channels;

/*-
package main
import "fmt"
 */

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class Addierer {
	
	/**
	 * Der Tasker f�gt Werte in die Queues ein
	 * @param x int wird in Queue c eingef�gt (nicht blockierend)
	 * @param y int wird in Queue c eingef�gt (nicht blockierend)
	 * @param c Queue erh�lt die Werte x und y
	 * @param r Queue wird ausgegeben
	 * @param d Queue erh�lt true (blockierend)
	 */
	private static void tasker(final int x, final int y, // 
			final BlockingQueue<Integer> c, //
			final BlockingQueue<Integer> r, //
			final BlockingQueue<Boolean> d) {
		try {
			c.offer(x); // f�gt x in die Queue c ein (nicht blockierend)
			c.offer(y); // f�gt y in die Queue c ein (nicht blockierend)
			System.out.printf("%d+%d=%d\n", x, y, r.take()); // liest Queue r aus (blockierend)
			d.put(true); // f�gt true in Queue d ein (blockierend)
		} catch (final InterruptedException e) { 
			e.printStackTrace();
		}
	}
	/*
	 func tasker (c, r chan int, d chan bool) {
    c <- 1; c <- 2
    fmt.Println (<-r); d <- true
	}
	 */

	/**
	 * nimmt zwei Werte aus der c Queue, addiert sie und f�gt sie in die r Queue ein
	 * @param c Queue: daraus werden die Werte entnommen
	 * @param r Queue: darin kommt das Ergebnis
	 */
	private static void add(final BlockingQueue<Integer> c, final BlockingQueue<Integer> r) {
		try {
			r.put(c.take() + c.take()); // 
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}
	/*
	func add (c, r chan int) {
    r <- <-c + <-c
	}
	 */

	/**
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(final String[] args) throws InterruptedException {
		final BlockingQueue<Integer> c = new LinkedBlockingQueue<>(); // erstellt c Queue
		final BlockingQueue<Integer> r = new LinkedBlockingQueue<>(); // erstellt r Queue
		final BlockingQueue<Boolean> done = new LinkedBlockingQueue<>(); // erstellt done Queue
		(new Thread(() -> Addierer.tasker(1, 2, c, r, done))).start(); // startet einen neuen tasker Thread
		(new Thread(() -> Addierer.add(c, r))).start(); // startet einen adder Thread
		done.take(); // nimmt Wert aus der done Queue
	}
	/*
	func main () {
    c, r:= make(chan int), make(chan int)
    done:= make(chan bool)
    go tasker (c, r, done)
    go add (c, r)
    <-done
	}	
	 */
}
