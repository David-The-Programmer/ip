package atom.parser;

import java.util.NoSuchElementException;

/**
 * Acts as a stateful token stream for command parsing.
 * Tracks parsing progress using an internal cursor to consume parts of the raw input.
 */
public class RawCommandStream {
    private int index;
    private String rawCommand;

    public RawCommandStream(String rawCommand) {
        this.rawCommand = rawCommand;
        this.index = 0;
    }

    /**
     * Returns true if there are no more tokens left in the stream, false otherwise.
     *
     * @return boolean to determine if stream is exhausted.
     */
    public boolean isExhausted() {
        return index >= rawCommand.length();
    }

    /**
     * Returns the next word (segment until earliest space character) in the stream.
     *
     * @return Segment before the earliest space character in the stream.
     * @throws NoSuchElementException If invoked when isExhausted returns true, i,e, stream is empty.
     */
    public String nextWord() throws NoSuchElementException {
        if (isExhausted()) {
            throw new NoSuchElementException();
        }
        while (!isExhausted() && rawCommand.charAt(index) == ' ') {
            index++;
        }
        if (isExhausted()) {
            return "";
        }
        return nextUntil(" ");
    }

    /**
     * Returns the next string segment in the stream up to the specified delimiter.
     *
     * @param delimiter The sequence marking the end of the segment.
     * @return Segment before the delimiter.
     * @throws NoSuchElementException If invoked when isExhausted() returns true, i,e, stream is empty.
     */
    public String nextUntil(String delimiter) throws NoSuchElementException {
        if (isExhausted()) {
            throw new NoSuchElementException();
        }
        int delimiterIndex = rawCommand.indexOf(delimiter, index);
        if (delimiterIndex == -1) {
            return nextRemaining();
        }
        String nextSegment = rawCommand.substring(index, delimiterIndex);
        index = delimiterIndex + delimiter.length();
        return nextSegment;
    }

    /**
     * Returns the remaining substring of the stream.
     *
     * @return remaining substring of the stream.
     * @throws NoSuchElementException If invoked when hasNext() returns false, i,e, stream is empty.
     */
    public String nextRemaining() {
        if (isExhausted()) {
            throw new NoSuchElementException();
        }
        String remaining = rawCommand.substring(index);
        index = rawCommand.length();
        return remaining;
    }
}
