package com.fisglobal.taxreporting.entity.model.taxreporting;

import java.io.Serializable;
import java.util.Objects;


public class UniqueKey implements Serializable {
    private static final long serialVersionUID = 1L;

    private String mandSl;
    private String keyIdNr;
    private long keyMeldejahr;
    private String keyMuster;
    private long keyLaufnummer;
    private long keySysDatum;
    private long keyUhrzeit;
    private String keySatzart;

    public UniqueKey(String mandSl, String keyIdNr, long keyMeldejahr, String keyMuster, long keyLaufnummer,
            long keySysDatum, long keyUhrzeit, String keySatzart) {
        this.mandSl = mandSl;
        this.keyIdNr = keyIdNr;
        this.keyMeldejahr = keyMeldejahr;
        this.keyMuster = keyMuster;
        this.keyLaufnummer = keyLaufnummer;
        this.keySysDatum = keySysDatum;
        this.keyUhrzeit = keyUhrzeit;
        this.keySatzart = keySatzart;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mandSl, keyIdNr, keyMeldejahr, keyMuster, keyLaufnummer, keySysDatum, keyUhrzeit);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UniqueKey other = (UniqueKey) obj;
        return Objects.equals(keyIdNr, other.keyIdNr) && keyLaufnummer == other.keyLaufnummer
                && keyMeldejahr == other.keyMeldejahr && Objects.equals(keyMuster, other.keyMuster)
                && Objects.equals(keySatzart, other.keySatzart) && keySysDatum == other.keySysDatum
                && keyUhrzeit == other.keyUhrzeit && Objects.equals(mandSl, other.mandSl);
    }
}
