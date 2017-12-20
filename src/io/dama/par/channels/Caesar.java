package io.dama.par.channels;

import java.util.concurrent.BlockingQueue;

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

    private static Character cap(final Character c) {
        /*-
            func cap(b byte) byte {
                if b >= 'a' {
                    return b - 'a' + 'A'
                }
                return b
            }
         */
        return null;
    }

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

    public static void main(final String[] args) {
        /*-
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
        */
    }
}
