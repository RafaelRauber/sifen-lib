package com.roshka.sifen.internal.request;

import com.roshka.sifen.core.SifenConfig;
import com.roshka.sifen.core.exceptions.SifenException;
import com.roshka.sifen.internal.SOAPResponse;
import com.roshka.sifen.internal.ctx.GenerationCtx;
import com.roshka.sifen.internal.helpers.SoapHelper;
import com.roshka.sifen.internal.response.BaseResponse;
import com.roshka.sifen.internal.util.SifenExceptionUtil;
import com.roshka.sifen.internal.util.SifenUtil;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.logging.Logger;

abstract class BaseRequest {
    private final long dId;
    private final SifenConfig sifenConfig;
    private final static Logger logger = Logger.getLogger(BaseRequest.class.toString());

    BaseRequest(long dId, SifenConfig sifenConfig) {
        this.dId = dId;
        this.sifenConfig = sifenConfig;
    }

    abstract SOAPMessage setupSoapMessage(GenerationCtx generationCtx) throws SifenException;

    abstract BaseResponse processResponse(SOAPResponse soapResponse) throws SifenException;
    
    public BaseResponse makeRequest(GenerationCtx generationCtx,String url) throws SifenException {
        try {
            // Preparamos el mensaje
            SOAPMessage message = this.setupSoapMessage(generationCtx);
            message.setProperty(SOAPMessage.WRITE_XML_DECLARATION, "true");
            message.setProperty(SOAPMessage.CHARACTER_SET_ENCODING, "UTF-8");
            logger.info("XML generado, se realiza la petición");

//             Para obtener el xml
            final StringWriter sw = new StringWriter();

            try {
                TransformerFactory.newInstance().newTransformer().transform(
                        new DOMSource(message.getSOAPPart()),
                        new StreamResult(sw));
            } catch (TransformerException e) {
                throw new RuntimeException(e);
            }

            String xml = sw.toString();
            logger.info("Este es el xml a enviarse a la SIFEN \n" + xml);
//            System.out.println(xml);
            // Realizamos la consulta
            String requestUrl = SifenUtil.coalesce(sifenConfig.getUrlBase(), sifenConfig.getUrlBaseLocal()) + url;
            BaseResponse response = this.processResponse(SoapHelper.makeSoapRequest(sifenConfig, requestUrl, message));
            response.setRequestSent(xml);
            logger.info("Petición realizada, se formatea la respuesta");
            return response;
        } catch (SOAPException e) {
            String msg = "Ocurrió un error al realizan la petición a: " + url + ". Mensaje: " + e.getLocalizedMessage();
            throw SifenExceptionUtil.invalidSOAPRequest(msg, e);
        }
    }

    long getdId() {
        return dId;
    }

    SifenConfig getSifenConfig() {
        return sifenConfig;
    }
}