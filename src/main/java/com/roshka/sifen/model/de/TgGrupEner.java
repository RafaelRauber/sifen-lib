package com.roshka.sifen.model.de;

import com.roshka.sifen.model.Constants;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import java.math.BigDecimal;

public class TgGrupEner {
    private String dNroMed;
    private short dActiv;
    private String dCateg;
    private BigDecimal dLecAnt;
    private BigDecimal dLecAct;
    private BigDecimal dConKwh;

    public void setupSOAPElements(SOAPElement gCamEsp) throws SOAPException {
        SOAPElement gGrupEner = gCamEsp.addChildElement("gGrupEner");
        if (this.dNroMed != null)
            gGrupEner.addChildElement("dNroMed").setTextContent(this.dNroMed);

        if (this.dActiv != 0)
            gGrupEner.addChildElement("dActiv").setTextContent(String.valueOf(this.dActiv));

        if (this.dCateg != null)
            gGrupEner.addChildElement("dCateg").setTextContent(this.dCateg);

        if (this.dLecAnt != null)
            gGrupEner.addChildElement("dLecAnt").setTextContent(String.valueOf(this.dLecAnt));

        if (this.dLecAct != null)
            gGrupEner.addChildElement("dLecAct").setTextContent(String.valueOf(this.dLecAct));

        if (this.dConKwh != null)
            gGrupEner.addChildElement("dConKwh").setTextContent(String.valueOf(this.dConKwh));
    }

    public String getdNroMed() {
        return dNroMed;
    }

    public void setdNroMed(String dNroMed) {
        this.dNroMed = dNroMed;
    }

    public short getdActiv() {
        return dActiv;
    }

    public void setdActiv(short dActiv) {
        this.dActiv = dActiv;
    }

    public String getdCateg() {
        return dCateg;
    }

    public void setdCateg(String dCateg) {
        this.dCateg = dCateg;
    }

    public BigDecimal getdLecAnt() {
        return dLecAnt;
    }

    public void setdLecAnt(BigDecimal dLecAnt) {
        this.dLecAnt = dLecAnt;
    }

    public BigDecimal getdLecAct() {
        return dLecAct;
    }

    public void setdLecAct(BigDecimal dLecAct) {
        this.dLecAct = dLecAct;
    }

    public BigDecimal getdConKwh() {
        return dConKwh;
    }

    public void setdConKwh(BigDecimal dConKwh) {
        this.dConKwh = dConKwh;
    }

}
