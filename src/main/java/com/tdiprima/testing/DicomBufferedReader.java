package com.tdiprima.testing;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Reads a DICOM file using a buffered stream to validate its header and 
 * manage basic DICOM metadata.
 * 
 * @author tdiprima
 */
public class DicomBufferedReader {

    public static void readDicomFile(String filePath) {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath))) {
            // DICOM file header is typically 128 bytes of preamble + 4 bytes "DICM"
            byte[] preamble = new byte[128];
            byte[] dicmHeader = new byte[4];

            // Read preamble
            bis.read(preamble);

            // Read DICM header to verify
            bis.read(dicmHeader);
            String headerCheck = new String(dicmHeader);

            if (!"DICM".equals(headerCheck)) {
                throw new IOException("Invalid DICOM file format");
            }

            // Now start reading actual metadata
            // See: DicomParser.java

            System.out.println("DICOM file header validated");

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error reading DICOM file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        readDicomFile("file.dcm");
    }
}
