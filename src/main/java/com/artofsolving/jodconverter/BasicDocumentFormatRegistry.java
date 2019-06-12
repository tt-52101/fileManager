package com.artofsolving.jodconverter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author zhulongkun20@163.com
 * @since 2019-05-26 11:35
 */
public class BasicDocumentFormatRegistry implements DocumentFormatRegistry {
    private List documentFormats = new ArrayList();

    public void addDocumentFormat(DocumentFormat documentFormat) {
        documentFormats.add(documentFormat);
    }

    protected List/* <DocumentFormat> */ getDocumentFormats() {
        return documentFormats;
    }

    /**
     * @param extension the file extension
     * @return the DocumentFormat for this extension, or null if the extension
     * is not mapped
     */
    public DocumentFormat getFormatByFileExtension(String extension) {
        if (extension == null) {
            return null;
        }
        //将文件名后缀统一转化
        if (extension.indexOf("doc") >= 0) {
            extension = "doc";
        }
        if (extension.indexOf("ppt") >= 0) {
            extension = "ppt";
        }
        if (extension.indexOf("xls") >= 0) {
            extension = "xls";
        }
        String lowerExtension = extension.toLowerCase();
        for (Iterator it = documentFormats.iterator(); it.hasNext(); ) {
            DocumentFormat format = (DocumentFormat) it.next();
            if (format.getFileExtension().equals(lowerExtension)) {
                return format;
            }
        }
        return null;
    }

    public DocumentFormat getFormatByMimeType(String mimeType) {
        for (Iterator it = documentFormats.iterator(); it.hasNext(); ) {
            DocumentFormat format = (DocumentFormat) it.next();
            if (format.getMimeType().equals(mimeType)) {
                return format;
            }
        }
        return null;
    }
}
