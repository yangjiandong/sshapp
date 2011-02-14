package org.ssh.app.example.script.groovy

import org.ssh.app.example.service.PdfGenerator;

/**
 * This Groovy implementation class is used in the examples where we use Groovy scripts.
 */
class GroovyPdfGenerator implements PdfGenerator {

    String companyName

    public String pdfFor() {
    return "Hello"
    }
}

