package com.real.matcher;

import java.io.InputStream;

public class CsvStream {
    private InputStream stream;

    public CsvStream(InputStream stream) {
        this.stream = stream;
    }

    public InputStream getStream() {
        return stream;
    }
}
