package atom.storage;

import java.util.NoSuchElementException;

/**
 * Acts as a stateful token stream for parsing serialised task string.
 */
public class SerialisedTaskStream {
    private int index;
    private String serialisedTask;

    /**
     * Constructs a stream given the serialised task string.
     *
     * @param serialisedTask Raw serialised task string.
     */
    public SerialisedTaskStream(String serialisedTask) {
        this.serialisedTask = serialisedTask;
        this.index = 0;
    }

    /**
     * Returns true if there are no more tokens left in the stream, false otherwise.
     *
     * @return boolean to determine if stream is exhausted.
     * @throws NoSuchElementException If invoked when hasNext() returns false, i,e, stream is empty.
     */
    public boolean isExhausted() {
        return index >= serialisedTask.length();
    }

    /**
     * Returns the next string segment in the stream up to the specified delimiter.
     *
     * @param delimiter The sequence marking the end of the segment.
     * @return Segment before the delimiter.
     * @throws NoSuchElementException If invoked when isExhausted() returns false, i,e, stream is empty.
     */
    public String nextUntil(String delimiter) throws NoSuchElementException {
        if (isExhausted()) {
            throw new NoSuchElementException();
        }
        int delimiterIndex = serialisedTask.indexOf(delimiter, index);
        if (delimiterIndex == -1) {
            return nextRemaining();
        }
        String nextSegment = serialisedTask.substring(index, delimiterIndex);
        index = delimiterIndex + delimiter.length();
        return nextSegment;
    }

    /**
     * Returns the remaining substring of the stream.
     *
     * @return remaining substring of the stream.
     * @throws NoSuchElementException If invoked when isExhausted() returns true, i,e, stream is empty.
     */
    public String nextRemaining() {
        if (isExhausted()) {
            throw new NoSuchElementException();
        }
        String remaining = serialisedTask.substring(index);
        index = serialisedTask.length();
        return remaining;
    }

    /**
     * Returns the next string segment of the stream of specified size or until stream is exhausted.
     *
     * @return next substring of the stream of specified size or until stream is exhausted.
     * @throws NoSuchElementException If invoked when isExhausted() returns true, i,e, stream is empty.
     */
    public String nextNSizeSegment(int size) {
        if (isExhausted()) {
            throw new NoSuchElementException();
        }
        int segmentEndIndex = Math.min(serialisedTask.length(), index + size);
        String segment = serialisedTask.substring(index, segmentEndIndex);
        index = segmentEndIndex;
        return segment;
    }
}
